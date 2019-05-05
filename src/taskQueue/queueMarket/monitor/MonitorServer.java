package taskQueue.queueMarket.monitor;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;

/**
 * �������
 * @author ����
 *
 */
public class MonitorServer extends Thread{
	
	String queueName;
	Connect connect;
	
	private static final long FREETIME = Long.valueOf(DeferConfig.loadConfig().get("redis.url")==null
			?DeferConfig.loadConfig().get("redis.url"):"3600000") ;
	
	/**
	 * ��ʼ����Ҫ�õ� �������� �� ��ʹ�õĵ�����
	 * @param queueName ��������
	 * @param connect	��������
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
	 * ����Ӧ���еĳ��� �Ƿ�Ҫ����
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
	 * ����Ӧ���е� ����ʱ���Ƿ�Ҫֹͣ���� 
	 */
	private boolean CheckQueueFreeTime(String queueName,Connect connect) {
		long freetime = 0L;
		while(true) {
			if(checkQueuelength(queueName,connect)) {
				try {
					sleep(100);
					freetime += 100L;
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
	 * �ڼ�����Ĺ����������Լ�
	 */
	private void suicide(String queueName) {
		MonitorFactory.queuenameToServer.remove(queueName);
	}
}
