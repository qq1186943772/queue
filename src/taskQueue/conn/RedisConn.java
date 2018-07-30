package taskQueue.conn;

import redis.clients.jedis.Jedis;
import taskQueue.config.DeferConfig;
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
		System.out.println("测试数据添加完成");
	}
	
	public void addLisy() {
		RedisConn redis = new RedisConn();
		for(int i = 0;i< 20;i++) {
			TaskBean task = new TaskBean();
			task.setUuid(i+"假设UUID");
			task.setTaskName("订单任务");
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
			j = null; // 断开链接之后 将对象置空，方便下次连接
			return true; // 当前连接状态不是pong 时表示断开链接成功
		}
		
	}
	
	@Override
	public String rpop(QueueBean bean) {
		return radisConn().rpop(bean.getQueueName());  // 移除并获得 队列的第一个值
	}
	
 
	public boolean pop(TaskBean bean) {
		String value = radisConn().rpop(bean.getQueue().getQueueName());
		return value=="nil"?false:true; //当列表不存在最后一个元素的时候 会返回 nil 如果返回的是 nil 则返回false
	}

	@Override
	public boolean push(TaskBean bean) {
		long origina = radisConn().llen(bean.getQueue().getQueueName()); //原始长度
		long now = radisConn().lpush(bean.getQueue().getQueueName(), easyUtil.toGson(bean)); // 现在的长度
		return origina<now?true:false; //如果原始长度小于现在的长度则 返回false
	}

	public long listLength(QueueBean bean) {
		return radisConn().llen(bean.getQueueName()); // 查找到该队列的 队列长度。
	}
	
	@Override
	public boolean defer(long time, TaskBean queue) {
		return false;
	}
	
}
