package taskQueue.queue;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.conn.RedisConn;
import taskQueue.entity.QueueBean;

public class ExecuteTheadPool {

	static ThreadPoolExecutor threadPool ;
	/**
	 * 消费者用到的锁
	 * 生产者与消费者要共用这个把锁，在使用生产者的时候，也可以用这把锁
	 * 由于它是静态的，且是由静态代码块赋值的，只会赋值一遍。就是只会有一把锁
	 */
	static Lock lock;
	
	static {
		
		int corePoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.corePoolSize"));
		int maximumPoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.maximumPoolSize"));
		int keepAliveTime = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.keepAliveTime"));
		
		lock = new ReentrantLock();
		/*
		 * 参数列表
		 * 第一个参数  指定 线程核心数
		 * 第二个参数 指定线程最大数
		 * 第三个参数 指定线程空闲时间，空闲时间过后 销毁线程
		 * 四个个参数 指定空闲时间的单位
		 * 第五个参数 指定任务队列的存储方式
		 */
		if(threadPool == null) {
			threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>());
		}
		
	}
	
	public static void main(String[] args) {
		
		Connect conn = new RedisConn();
		QueueBean bean = new QueueBean("myQueueTest",1);
		
		buildThread(bean,conn);
		
	}
	
	/**
	 * 在线程池中添加创建线程执行任务
	 * @param bean
	 * @param conn
	 */
	public static void buildThread(QueueBean bean,Connect conn){
		int num = Integer.parseInt(DeferConfig.loadConfig("thread.type." + bean.getQueueType()+".num"));
		for(int i = 0 ;i < num;i++) {
			Thread one = new ExecuteOne(lock,bean,conn);
			if(num>threadNum(bean.getQueueName())) {
				one.setName(bean.getQueueName());
				threadPool.execute(one);
			}else break ;
		}
		
		threadPool.shutdown();  // shutdown 方法发当线程中的线程任务 全都完成后 关闭线程
		
	}
	
	/**
	 * 判断线程中此线程名的个数
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
