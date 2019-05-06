package taskQueue.queueMarket.consumer;

import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;
import taskQueue.queueMarket.monitor.MonitorFactory;

/**
 * ����������
 * @author ����
 *
 */
public class ConsumerServer {

	Connect connect;
	
	public ConsumerServer(Connect connect) {
		this.connect = connect;
	}
	
	/**
	 * ��ʼ��������
	 * @param queueName
	 * @param queueType
	 */
	public void consume(QueueBean queue) {
		MonitorFactory.createConsumerMonitor(queue, connect);
	}
	
}
