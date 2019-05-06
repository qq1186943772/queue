package taskQueue.queueMarket.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;
import taskQueue.queue.ExecuteTheadPool;

/**
 * 监控中兴生产者工厂
 * @author 王勃
 *
 */
public class MonitorFactory {
	
	private MonitorFactory() {}
	
	private static class SingletonInstance {
		private static final MonitorFactory FACTORY = new MonitorFactory();
	}
	
	/**
	 * 返回单例 监控中心工厂
	 * @return 
	 */
	public static MonitorFactory monitorFactory() {
		return SingletonInstance.FACTORY;
	}
	
	private static final int corePoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.corePoolSize"));
	private static final int maximumPoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.maximumPoolSize"));
	private static final int keepAliveTime = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.keepAliveTime"));
	
	/**
	 * 所有队列相关的 都用这个锁
	 */
	public static final Lock QUEUELOCK = new ReentrantLock();
	
	/**
	 * 需要监听的 队列的名称 与 监控中心绑定
	 * 每个队列一个监控中心，监控队列的任务情况 
	 */
	static Map<String,Thread> producerToServer = new ConcurrentHashMap<String,Thread>();
	
	/**
	 *  消费者消费任务队列的监控中心
	 *  每个消费者一个线程池 会创建一个线程池来 执行任务队列
	 */
	static Map<String,ExecuteTheadPool> consumerToServer = new ConcurrentHashMap<String,ExecuteTheadPool>();
	
	/**
	 * 构件监控中心
	 * @param queueName	
	 * @param producer
	 */
	public static void createProducerMonitor(QueueBean queue,Connect connect) {
		
		if(!producerToServer.containsKey(queue.getQueueName())) {
			Thread server = new MonitorServer(queue,connect);
			producerToServer.put(queue.getQueueName(), server);
			server.start();
		}
		
	}
	
	/**
	 * 消费者监控中心
	 * @param queueName
	 * @param connect
	 */
	public static void createConsumerMonitor(QueueBean queue,Connect connect){
		if(!consumerToServer.containsKey(queue.getQueueName())) {
			ExecuteTheadPool pool = new ExecuteTheadPool(corePoolSize,maximumPoolSize,keepAliveTime);
			consumerToServer.put(queue.getQueueName(), pool);
			pool.buildThread(queue,connect);
		}
	}
	
}
