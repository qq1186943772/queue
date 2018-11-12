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
	/**
	 * �������õ�����
	 * ��������������Ҫ���������������ʹ�������ߵ�ʱ��Ҳ�����������
	 * �������Ǿ�̬�ģ������ɾ�̬����鸳ֵ�ģ�ֻ�ḳֵһ�顣����ֻ����һ����
	 */
	static Lock lock;
	
	static {
		
		int corePoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.corePoolSize"));
		int maximumPoolSize = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.maximumPoolSize"));
		int keepAliveTime = Integer.parseInt(DeferConfig.loadConfig("ThreadPoolExecutor.keepAliveTime"));
		
		lock = new ReentrantLock();
		/*
		 * �����б�
		 * ��һ������  ָ�� �̺߳�����
		 * �ڶ������� ָ���߳������
		 * ���������� ָ���߳̿���ʱ�䣬����ʱ����� �����߳�
		 * �ĸ������� ָ������ʱ��ĵ�λ
		 * ��������� ָ��������еĴ洢��ʽ
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
	 * ���̳߳�����Ӵ����߳�ִ������
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
		
		threadPool.shutdown();  // shutdown ���������߳��е��߳����� ȫ����ɺ� �ر��߳�
		
	}
	
	/**
	 * �ж��߳��д��߳����ĸ���
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
