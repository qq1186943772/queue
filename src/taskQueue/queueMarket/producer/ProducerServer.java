package taskQueue.queueMarket.producer;

import java.util.concurrent.locks.Lock;

import taskQueue.conn.Connect;
import taskQueue.entity.TaskBean;
import taskQueue.queueMarket.monitor.MonitorFactory;

/**
 * 消息生生产者
 * @author 王勃
 *
 */
public class ProducerServer {

	Connect connect;
	final static Lock lock = MonitorFactory.QUEUELOCK;
	
	public ProducerServer(Connect connect,Lock lock) {
		this.connect = connect;
	}
	
	/**
	 * 发送消息到任务队列
	 * @param task 需要执行任务
	 */
	public void send(TaskBean task){
		
		try {
			lock.lock();
			connect.push(task);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		
		MonitorFactory.createProducerMonitor(task.getQueue(),connect);
    }
	
}
