package taskQueue.conn.connImpl;

import redis.clients.jedis.Jedis;
import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;
import taskQueue.entity.TaskBean;
import taskQueue.entity.TestBean;
import taskQueue.util.easyUtil;

public class RedisConn implements Connect{
	
	static Jedis j = null;
	static long listNum = 0;
	
	public Jedis radisConn() {
		if(j==null) {
			j = new Jedis(DeferConfig.loadConfig().get("redis.url"));
		}
		return j;
	}
	
	public static void main(String[] args) {
		RedisConn redis = new RedisConn();
		/*redis.addLisy();*/
		QueueBean bean = new QueueBean("myQueueTest",1);
		System.out.println(redis.listLength(bean));
		redis.delLisy();
		System.out.println(redis.listLength(bean));
		redis.addLisy();
		System.out.println(redis.listLength(bean));
		System.out.println("��������������");
	}
	
	public void addLisy() {
		RedisConn redis = new RedisConn();
		for(int i = 0;i< 20;i++) {
			TaskBean task = new TaskBean();
			task.setUuid(i+"����UUID");
			task.setTaskName("��������");
			task.setTaskClass("test.Text");
			TestBean test = new TestBean();
			test.setTest1(i+"");
			test.setTest2(i+"");
			test.setTest3(i+"");
			test.setTest4(i+"");
			task.setTaskMethod("text");
			task.setParameter("hoell word!"+easyUtil.toGson(test));
			QueueBean queue = new QueueBean();
			queue.setQueueName("myQueueTest");
			queue.setQueueType(1);
			task.setQueue(queue);
			redis.radisConn().lpush(queue.getQueueName(), easyUtil.toGson(task));
		}
	}
	
	public void delLisy() {
		QueueBean queue = new QueueBean();
		queue.setQueueName("myQueueTest");
		queue.setQueueType(1);
		for(int i = 0 ;i< listLength(queue);) {
			radisConn().lpop("myQueueTest"); 
		}
	}
	
	@Override
	public String connType() {
		return "redis";
	}

	@Override
	public boolean close() {
		radisConn().quit();
		String ping = j.ping();
		if(ping=="PONG") {
			return false;
		}else {
			j = null; // �Ͽ�����֮�� �������ÿգ������´�����
			return true; // ��ǰ����״̬����pong ʱ��ʾ�Ͽ����ӳɹ�
		}
		
	}
	
	@Override
	public String rpop(QueueBean bean) {
		return radisConn().rpop(bean.getQueueName());  // �Ƴ������ ���еĵ�һ��ֵ
	}
	
 
	public boolean pop(TaskBean bean) {
		String value = radisConn().rpop(bean.getQueue().getQueueName());
		return value=="nil"?false:true; //���б��������һ��Ԫ�ص�ʱ�� �᷵�� nil ������ص��� nil �򷵻�false
	}

	@Override
	public boolean push(TaskBean bean) {
		long origina = radisConn().llen(bean.getQueue().getQueueName()); //ԭʼ����
		long now = radisConn().lpush(bean.getQueue().getQueueName(), easyUtil.toGson(bean)); // ���ڵĳ���
		return origina<now?true:false; //���ԭʼ����С�����ڵĳ����� ����false
	}

	public long listLength(QueueBean bean) {
		return radisConn().llen(bean.getQueueName()); // ���ҵ��ö��е� ���г��ȡ�
	}
	
	@Override
	public boolean defer(long time, TaskBean queue) {
		return false;
	}
	
}
