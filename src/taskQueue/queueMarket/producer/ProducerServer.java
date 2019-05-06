package taskQueue.queueMarket.producer;

import taskQueue.conn.Connect;
import taskQueue.entity.TaskBean;
import taskQueue.queueMarket.monitor.MonitorFactory;

/**
 * ��Ϣ��������
 * @author ����
 *
 */
public class ProducerServer {

	Connect connect;
	
	public ProducerServer(Connect connect) {
		this.connect = connect;
	}
	
	/**
	 * ������Ϣ���������
	 * @param task ��Ҫִ������
	 */
	public void send(TaskBean task){
		connect.push(task);
		
		MonitorFactory.createProducerMonitor(task.getQueue().getQueueName(),connect);
    }
	
}
