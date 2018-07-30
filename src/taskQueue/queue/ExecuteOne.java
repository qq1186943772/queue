package taskQueue.queue;

import java.util.concurrent.locks.Lock;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;
import taskQueue.task.ExecuteTask;

/**
 * �����̣߳�������������ʱ��ԶС��ָ�����������ʱ�䣬���Ծ����ö��߳���ִ������
 * @author ����
 *
 */
public class ExecuteOne extends Thread{

	//ReadWriteLock readWriter = null;
	Lock lock ;

	ExecuteTask task;
	
	/**
	 * �ɽ�Ҫ�����̵߳��˾�������� �߳̽�Ϊ��һ�����й��������е����ȼ�Ϊ���١�
	 * @param lock ����redis ������̰߳�ȫ �������Ҳ���Ҫ��
	 * @param bean ���̷߳���Ķ���
	 * @param conn �ö����ڵ�����
	 * ����������캯�� ��Ҫ���������ã�����ǽ���������д�� �ļ��� ���ǻ�����Ҫ�õ� ��д��
	 */
	public ExecuteOne(Lock lock,QueueBean bean,Connect conn) {
		task = new ExecuteTask(bean,conn); 
		this.lock = lock;
		String priority = DeferConfig.loadConfig("thread.type." + bean.getQueueType()).trim() ;
		if(!priority.equals("")) {
			this.setPriority(Integer.parseInt(priority));
		}
		this.setName(bean.getQueueName());
	}
	
	/**
	 * Ϊ�˷��㣬ȥ�����õ���������������ź����в������
	 * @param bean ���̷߳���Ķ���
	 * @param conn �ö����ڵ�����
	 * redis �����̰߳�ȫ �ݲ���Ҫ����
	 */
	public ExecuteOne(QueueBean bean,Connect conn) {
		task = new ExecuteTask(bean,conn); 
		String priority = DeferConfig.loadConfig("thread.type." + bean.getQueueType()).trim() ;
		if(!priority.equals("")) {
			this.setPriority(Integer.parseInt(priority));
		}
	}
	@Override
	public void run() {
		while(true) {
			try {
				lock.lock();  // ����ʹ�� ���൱������ʹ��synchronized �ؼ�֮�ˣ���ס������������ 
				if(task.conn.listLength(task.queue) <= 0) {
					break;
				}
				if(task.execute()) {
					task.success();
				}else {
					task.defeated();
				}
			} catch (Exception e) { 
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		}
	}
	
	/*���� redis ����û�в��һ��� �����߳������ ����֪�������ĸ������Ѿ�ִ������ Ҳ���ܺܿ��ҵ��Ǹ�����ɾ��������д�� û����
	 * public void success() {
		try {
			readWriter.writeLock().lock();
			task.success();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			readWriter.writeLock().unlock();
		}
		
	}
	
	public void defeated() {
		try {
			readWriter.writeLock().lock();
			task.defeated();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			readWriter.writeLock().unlock();
		}
		
	}*/
	
}
