package taskQueue.queueMarket.producer;

import java.util.concurrent.locks.Lock;

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
	final static Lock lock = MonitorFactory.QUEUELOCK;
	
	public ProducerServer(Connect connect,Lock lock) {
		this.connect = connect;
	}
	
	/**
	 * ������Ϣ���������
	 * @param task ��Ҫִ������
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
