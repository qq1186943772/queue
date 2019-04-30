package taskQueue.queue;

import java.util.concurrent.locks.Lock;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;
import taskQueue.entity.TaskBean;
import taskQueue.task.ExecuteTask;
import taskQueue.util.easyUtil;

/**
 * �����̣߳�������������ʱ��ԶС��ָ�����������ʱ�䣬���Ծ����ö��߳���ִ������
 * @author ����
 *
 */
public class ExecuteOne extends Thread{

	/**
	 * �߳���
	 */
	Lock lock ;
	
	/**
	 * ִ�������ִ����
	 */
	static ExecuteTask execute;
	
	/**
	 * �����Ƿ���Ҫ˳��ִ��
	 */
	boolean isRank = false;
	/**
	 * �ɽ�Ҫ�����̵߳��˾�������� �߳̽�Ϊ��һ�����й��������е����ȼ�Ϊ���١�
	 * @param lock ����redis ������̰߳�ȫ �������Ҳ���Ҫ��
	 * @param bean ���̷߳���Ķ���
	 * @param conn �ö����ڵ�����
	 * ����������캯�� ��Ҫ���������ã�����ǽ���������д�� �ļ��� ���ǻ�����Ҫ�õ� ��д��
	 */
	public ExecuteOne(Lock lock,QueueBean queue,Connect conn) {
		execute = new ExecuteTask(queue,conn); 
		this.lock = lock;
		String priority = DeferConfig.loadConfig("thread.type." + queue.getQueueType()).trim() ;
		if(!priority.equals("")) {
			this.setPriority(Integer.parseInt(priority));
		}
		this.setName(queue.getQueueName());
	}
	
	/**
	 * �ж��Ƿ���Ҫ�ж϶��е� rank
	 * @param lock
	 * @param queue
	 * @param conn
	 * @param isRank �����Ƿ���Ҫ˳��ִ��
	 */
	public ExecuteOne(Lock lock,QueueBean queue,Connect conn,boolean isRank) {
		this.isRank = isRank;
		execute = new ExecuteTask(queue,conn); 
		this.lock = lock;
		String priority = DeferConfig.loadConfig("thread.type." + queue.getQueueType()).trim() ;
		if(!priority.equals("")) {
			this.setPriority(Integer.parseInt(priority));
		}
		this.setName(queue.getQueueName());
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				lock.lock();  // ����ʹ�� ���൱������ʹ�� synchronized �ؼ��֣���ס������������ 
				
				if(execute.conn.listLength(execute.queue) <= 0) {
					break;
				}
				
				String strTask = execute.conn.rpop(execute.queue);
				
				if(!isRank) {
					lock.unlock();
				}
				
				TaskBean task = (TaskBean) easyUtil.toObject(strTask, TaskBean.class);
				
				if(execute.execute(task)) {
					execute.success();
				}else {
					execute.defeated();
				}
				
			} catch (Exception e) { 
				e.printStackTrace();
			}finally {
				if(isRank) {
					lock.unlock();
				}
			}
		}
	}
	
}
