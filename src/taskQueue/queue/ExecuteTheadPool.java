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

	static ThreadPoolExecutor threadPool ;
	
	static Lock lock;
	
	static {
		
		int corePoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.corePoolSize"));
		int maximumPoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.maximumPoolSize"));
		int keepAliveTime = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.keepAliveTime"));
		
		lock = new ReentrantLock();
		
		if(threadPool == null) {
			threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>());
		}
		
	}
	
	public static void main(String[] args) {
		
		Connect conn = new RedisConn();
		QueueBean bean = new QueueBean("myQueueTest","fast");
		
		buildThread(bean,conn);
		
	}
	
	public static void buildThread(QueueBean bean,Connect conn){
		int num = Integer.parseInt(DeferConfig.loadConfig("thread.type." + bean.getQueueType()+".num"));
		for(int i = 0 ;i < num;i++) {
			Thread one = new ExecuteOne(lock,bean,conn);
			if(num>threadNum(bean.getQueueName())) {
				one.setName(bean.getQueueName());
				threadPool.execute(one);
			}else break ;
		}
		
		// threadPool.shutdown(); 线程池 维持线程状态  
	}
	
	
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
