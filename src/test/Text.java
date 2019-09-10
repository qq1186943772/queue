package test;

import taskQueue.conn.connImpl.RedisConn;
import taskQueue.entity.QueueBean;
import taskQueue.entity.TaskBean;
import taskQueue.entity.TestBean;
import taskQueue.util.easyUtil;

public class Text {

	public void text(String str1,String str2) {
		System.out.println(str1);
		System.out.println(str2);
		try {
			System.out.println(easyUtil.toObject(str2,TestBean.class));
		} catch (Exception e) {
			System.out.println("json转换错误！~");
		}
	}
	
	public static void main(String[] args) {
		Text text = new Text();
		RedisConn redis = new RedisConn();
		/*redis.addLisy();*/
		QueueBean bean = new QueueBean("myQueueTest","fast");
		System.out.println(redis.listLength(bean));
		text.delLisy();
		System.out.println(redis.listLength(bean));
		text.addLisy();
		System.out.println(redis.listLength(bean));
		System.out.println("消息添加完成");
	}
	
	public void addLisy() {
		for(int i = 0;i< 20;i++) {
			TaskBean task = new TaskBean();
			task.setUuid(i+"假的UUID");
			task.setTaskName("测试消息");
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
			queue.setQueueType("fast");
			task.setQueue(queue);
			RedisConn.radisConn().lpush(queue.getQueueName(), easyUtil.toGson(task));
		}
	}
	
	public void delLisy() {
		RedisConn redis = new RedisConn();
		QueueBean queue = new QueueBean();
		queue.setQueueName("myQueueTest");
		queue.setQueueType("fast");
		for(int i = 0 ;i< redis.listLength(queue);) {
			RedisConn.radisConn().lpop("myQueueTest"); 
		}
	}
	
}
