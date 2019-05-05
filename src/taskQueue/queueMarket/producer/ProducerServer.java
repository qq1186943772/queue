package taskQueue.queueMarket.producer;

import lombok.Data;
import taskQueue.conn.Connect;
import taskQueue.entity.TaskBean;
import taskQueue.queueMarket.monitor.MonitorFactory;

/**
 * ��Ϣ��������
 * @author ����
 *
 */
@Data
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
		
		MonitorFactory.createMonitor(task.getQueue().getQueueName(),connect);
    }
	
}
