package taskQueue.queueMarket.monitor;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;

/**
 * 监控中心
 * @author 王勃
 *
 */
public class MonitorServer extends Thread{
	
	String queueName;
	Connect connect;
	
	private static final long FREETIME = Long.valueOf(DeferConfig.loadConfig().get("redis.url")==null
			?DeferConfig.loadConfig().get("redis.url"):"3600000") ;
	
	/**
	 * 初始化需要用到 队列名称 跟 所使用的的连接
	 * @param queueName 队列名称
	 * @param connect	队列连接
	 */
	MonitorServer(String queueName,Connect connect) {
		this.connect = connect;
		this.queueName = queueName;
	}
	
	@Override
	public void run() {
		if(checkQueuelength(queueName,connect)) {
			if(CheckQueueFreeTime(queueName,connect)) {
				suicide(queueName);
			}
		}
	}
	
	/**
	 * 检查对应队列的长度 是否要消费
	 */
	private boolean checkQueuelength(String queueName,Connect connect) {
		while (true) {
			if(connect.listLength(QueueBean.builder().queueName(queueName).build()) > 0) {
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				break;
			}
		}
		return true;
	}
	
	/**
	 * 检查对应队列的 空闲时间是否要停止消费 
	 */
	private boolean CheckQueueFreeTime(String queueName,Connect connect) {
		long freetime = 0L;
		while(true) {
			if(checkQueuelength(queueName,connect)) {
				try {
					long before = System.currentTimeMillis();
					sleep(100);
					long after = System.currentTimeMillis();
					freetime += (after - before);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else if(freetime > FREETIME){
				break;
			}
		}
		return true;
	}
	
	/**
	 * 在监控中心工厂中销毁自己
	 */
	private void suicide(String queueName) {
		MonitorFactory.producerToServer.remove(queueName);
		
		MonitorFactory.consumerToServer.get(queueName).shutdowb();
		MonitorFactory.consumerToServer.remove(queueName);
	}
}
