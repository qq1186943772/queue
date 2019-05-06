package taskQueue.queue;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;
import taskQueue.queueMarket.monitor.MonitorFactory;

/**
 * 线程池创建线程执行消费者任务
 * @author 王勃
 *
 */
public class ExecuteTheadPool {
	
	/**
	 * 线程池
	 */
	private ThreadPoolExecutor threadPool ;
	
	public ExecuteTheadPool(int corePoolSize,int maximumPoolSize,int keepAliveTime) {
		 threadPool  =  new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>());
	}
	
	/**
	 * 线程锁，保证任务消费正常
	 */
	private static final Lock lock = MonitorFactory.QUEUELOCK;
	
	/**
	 * 创建线程池，执行消费任务
	 * @param bean
	 * @param conn
	 */
	public void buildThread(QueueBean bean,Connect conn){
		try {
			int num = Integer.parseInt(DeferConfig.loadConfig("thread.type." + bean.getQueueType()+".num"));
			for(int i = 0 ;i < num;i++) {
				Thread one = new ExecuteOne(lock,bean,conn);
				if(num>threadNum(bean.getQueueName())) {
					one.setName(bean.getQueueName());
					threadPool.execute(one);
				}else break ;
			}
			 
		} catch (NumberFormatException e) {
			System.out.println("未能获取到正确的 线程数 ");
		}
	}
	
	/**
	 * 停止线程池
	 */
	public void shutdowb() {
		threadPool.shutdown(); //  线程池 维持线程运行 避免再次进入队伍创建线程
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
