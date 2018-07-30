package taskQueue.queue;

import java.util.concurrent.locks.Lock;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;
import taskQueue.task.ExecuteTask;

/**
 * 工作线程，由于添加任务的时间远小于指定队列任务的时间，所以决定用多线程来执行任务
 * @author 王勃
 *
 */
public class ExecuteOne extends Thread{

	//ReadWriteLock readWriter = null;
	Lock lock ;

	ExecuteTask task;
	
	/**
	 * 由将要启动线程的人决定，这个 线程将为哪一个队列工作，队列的优先级为多少。
	 * @param lock 由于redis 相对于线程安全 所以暂且不需要锁
	 * @param bean 该线程服务的队列
	 * @param conn 该队所在的连接
	 * 这个方法构造函数 主要做保留作用，如果是将队列内容写在 文件中 我们还是需要用到 读写锁
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
	 * 为了方便，去掉不用到的锁，在这个够着函数中不添加锁
	 * @param bean 该线程服务的队列
	 * @param conn 该队所在的连接
	 * redis 本身线程安全 暂不需要加锁
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
				lock.lock();  // 这样使用 就相当于是在使用synchronized 关键之了，锁住了整个方法。 
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
	
	/*由于 redis 队列没有查找机制 ，多线程情况下 就是知道，是哪个任务已经执行完了 也不能很快找到那个任务删除他，读写锁 没意义
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
