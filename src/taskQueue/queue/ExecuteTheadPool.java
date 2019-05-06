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
 * �̳߳ش����߳�ִ������������
 * @author ����
 *
 */
public class ExecuteTheadPool {
	
	/**
	 * �̳߳�
	 */
	private ThreadPoolExecutor threadPool ;
	
	public ExecuteTheadPool(int corePoolSize,int maximumPoolSize,int keepAliveTime) {
		 threadPool  =  new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>());
	}
	
	/**
	 * �߳�������֤������������
	 */
	private static final Lock lock = MonitorFactory.QUEUELOCK;
	
	/**
	 * �����̳߳أ�ִ����������
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
			System.out.println("δ�ܻ�ȡ����ȷ�� �߳��� ");
		}
	}
	
	/**
	 * ֹͣ�̳߳�
	 */
	public void shutdowb() {
		threadPool.shutdown(); //  �̳߳� ά���߳����� �����ٴν�����鴴���߳�
	}
	
	/**
	 * ����ͬ���̵߳ĸ���
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
