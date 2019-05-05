package taskQueue.queueMarket.producer;

import lombok.Data;
import taskQueue.conn.Connect;
import taskQueue.entity.TaskBean;
import taskQueue.queueMarket.monitor.MonitorFactory;

/**
 * 消息生生产者
 * @author 王勃
 *
 */
@Data
public class ProducerServer {

	Connect connect;
	
	public ProducerServer(Connect connect) {
		this.connect = connect;
	}
	
	/**
	 * 发送消息到任务队列
	 * @param task 需要执行任务
	 */
	public void send(TaskBean task){
		connect.push(task);
		
		MonitorFactory.createMonitor(task.getQueue().getQueueName(),connect);
    }
	
}
