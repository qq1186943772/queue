package taskQueue.queue;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.conn.connImpl.RedisConn;
import taskQueue.entity.QueueBean;

public class ExecuteTheadPool {

	static ThreadPoolExecutor threadPool = SingletonInstance.THREADPOOL;
	
	static Lock lock = SingletonInstance.LOCK;
	
	private ExecuteTheadPool() {}
	
	private static class SingletonInstance {
		
		private static final int corePoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.corePoolSize"));
		private static final int maximumPoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.maximumPoolSize"));
		private static final int keepAliveTime = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.keepAliveTime"));
		
		private static final Lock LOCK = new ReentrantLock();
		
		
		private static final ThreadPoolExecutor THREADPOOL  = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>());
		
	}
	
	public static void main(String[] args) {
		
		Connect conn = new RedisConn();
		QueueBean bean = new QueueBean("myQueueTest","fast");
		
		buildThread(bean,conn);
		
	}
	
	public static void buildThread(QueueBean bean,Connect conn){
		try {
			int num = Integer.parseInt(DeferConfig.loadConfig("thread.type." + bean.getQueueType()+".num"));
			for(int i = 0 ;i < num;i++) {
				Thread one = new ExecuteOne(lock,bean,conn);
				if(num>threadNum(bean.getQueueName())) {
					one.setName(bean.getQueueName());
					threadPool.execute(one);
				}else break ;
			}
			// threadPool.shutdown(); 线程池 维持线程运行 避免再次进入队伍创建线程 
		} catch (NumberFormatException e) {
			System.out.println("未能获取到正确的 线程数 ");
		}
	}
	
	/**
	 * 计算同名线程的个数
	 * @param threadName
	 * @return
	 */
	public static int threadNum(String threadName) {
		int threadNum = 0 ;
		Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
		for(Map.Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
			Thread t = entry.getKey();
			if(threadName.equals(t.getName())) {
				threadNum ++ ;
			}
		}
		return threadNum;
	}
	
}
