package taskQueue.queue;

import java.util.concurrent.locks.Lock;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;
import taskQueue.entity.TaskBean;
import taskQueue.task.ExecuteTask;
import taskQueue.util.easyUtil;

/**
 * 工作线程，由于添加任务的时间远小于指定队列任务的时间，所以决定用多线程来执行任务
 * @author 王勃
 *
 */
public class ExecuteOne extends Thread{

	/**
	 * 线程锁
	 */
	Lock lock ;
	
	/**
	 * 执行任务的执行器
	 */
	static ExecuteTask execute;
	
	/**
	 * 队列是否需要顺序执行
	 */
	boolean isRank = false;
	/**
	 * 由将要启动线程的人决定，这个 线程将为哪一个队列工作，队列的优先级为多少。
	 * @param lock 由于redis 相对于线程安全 所以暂且不需要锁
	 * @param bean 该线程服务的队列
	 * @param conn 该队所在的连接
	 * 这个方法构造函数 主要做保留作用，如果是将队列内容写在 文件中 我们还是需要用到 读写锁
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
	 * 判断是否需要判断队列的 rank
	 * @param lock
	 * @param queue
	 * @param conn
	 * @param isRank 队列是否需要顺序执行
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
				lock.lock();  // 这样使用 就相当于是在使用 synchronized 关键字，锁住了整个方法。 
				
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
