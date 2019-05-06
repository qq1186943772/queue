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
 * ������������߹���
 * @author ����
 *
 */
public class MonitorFactory {
	
	private MonitorFactory() {}
	
	private static class SingletonInstance {
		private static final MonitorFactory FACTORY = new MonitorFactory();
	}
	
	/**
	 * ���ص��� ������Ĺ���
	 * @return 
	 */
	public static MonitorFactory monitorFactory() {
		return SingletonInstance.FACTORY;
	}
	
	private static final int corePoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.corePoolSize"));
	private static final int maximumPoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.maximumPoolSize"));
	private static final int keepAliveTime = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.keepAliveTime"));
	
	/**
	 * ���ж�����ص� ���������
	 */
	public static final Lock QUEUELOCK = new ReentrantLock();
	
	/**
	 * ��Ҫ������ ���е����� �� ������İ�
	 * ÿ������һ��������ģ���ض��е�������� 
	 */
	static Map<String,Thread> producerToServer = new ConcurrentHashMap<String,Thread>();
	
	/**
	 *  ����������������еļ������
	 *  ÿ��������һ���̳߳� �ᴴ��һ���̳߳��� ִ���������
	 */
	static Map<String,ExecuteTheadPool> consumerToServer = new ConcurrentHashMap<String,ExecuteTheadPool>();
	
	/**
	 * �����������
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
	 * �����߼������
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
