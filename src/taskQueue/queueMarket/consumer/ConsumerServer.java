package taskQueue.queueMarket.consumer;

import taskQueue.conn.Connect;
import taskQueue.queueMarket.monitor.MonitorFactory;

/**
 * 消费者中心
 * @author 王勃
 *
 */
public class ConsumerServer {

	Connect connect;
	
	public ConsumerServer(Connect connect) {
		this.connect = connect;
	}
	
	/**
	 * 开始消费任务
	 * @param queueName
	 * @param queueType
	 */
	public void consume(String queueName,String queueType) {
		MonitorFactory.createConsumerMonitor(queueName, queueType, connect);
	}
	
}
