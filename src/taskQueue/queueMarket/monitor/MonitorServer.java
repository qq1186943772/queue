package taskQueue.queueMarket.monitor;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;
import taskQueue.queueMarket.consumer.ConsumerServer;

/**
 * �������
 * @author ����
 *
 */
public class MonitorServer extends Thread{
	
	QueueBean queue;
	Connect connect;
	ConsumerServer Consume;
	
	private static final long FREETIME = Long.valueOf(DeferConfig.loadConfig().get("redis.url")==null
			?DeferConfig.loadConfig().get("redis.url"):"3600000") ;
	
	/**
	 * ��ʼ����Ҫ�õ� �������� �� ��ʹ�õĵ�����
	 * @param queueName ��������
	 * @param connect	��������
	 */
	MonitorServer(QueueBean queue,Connect connect) {
		this.connect = connect;
		this.queue = queue;
	}
	
	@Override
	public void run() {
		if(checkQueuelength(queue,connect)) {
			if(CheckQueueFreeTime(queue,connect)) {
				suicide(queue.getQueueName());
			}
		}
	}
	
	/**
	 * ����Ӧ���еĳ��� �Ƿ�Ҫ����
	 */
	private boolean checkQueuelength(QueueBean queue,Connect connect) {
		while (true) {
			if(connect.listLength(queue) > 0) {
				try {
					Consume.consume(queue);
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
	private boolean CheckQueueFreeTime(QueueBean queue,Connect connect) {
		long freetime = 0L;
		while(true) {
			if(checkQueuelength(queue,connect)) {
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
	 * �ڼ�����Ĺ����������Լ�
	 */
	private void suicide(String queueName) {
		MonitorFactory.producerToServer.remove(queueName);
		
		MonitorFactory.consumerToServer.get(queueName).shutdowb();
		MonitorFactory.consumerToServer.remove(queueName);
	}
}
