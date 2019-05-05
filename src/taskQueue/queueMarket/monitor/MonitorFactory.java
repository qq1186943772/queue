package taskQueue.queueMarket.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import taskQueue.conn.Connect;

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
	
	/**
	 * ��Ҫ������ ���е����� �� ������İ�
	 * ÿ������һ��������ģ���ض��е�������� 
	 */
	static Map<String,Thread> queuenameToServer = new ConcurrentHashMap<String,Thread>();
	
	/**
	 * �����������
	 * @param queueName	
	 * @param producer
	 */
	public static void createMonitor(String queueName,Connect connect) {
		
		if(!queuenameToServer.containsKey(queueName)) {
			Thread server = new MonitorServer(queueName,connect);
			queuenameToServer.put(queueName, server);
			server.start();
		}
		
	}
	
}
