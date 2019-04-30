package taskQueue.conn.connImpl;

import redis.clients.jedis.Jedis;
import taskQueue.config.DeferConfig;
import taskQueue.conn.Connect;
import taskQueue.entity.QueueBean;
import taskQueue.entity.TaskBean;
import taskQueue.util.easyUtil;

public class RedisConn implements Connect{
	
	static Jedis j = null;
	static long listNum = 0;
	
	private static class SingletonInstance {
		private static final Jedis REDIS = new Jedis(DeferConfig.loadConfig().get("redis.url"));
	}
	
	public static Jedis radisConn() {
		return SingletonInstance.REDIS;
	}
	
	public static void main(String[] args) {
		
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
			j = null; 
			return true; 
		}
		
	}
	
	@Override
	public String rpop(QueueBean bean) {
		return radisConn().rpop(bean.getQueueName());  
	}
	
 
	public boolean pop(TaskBean bean) {
		String value = radisConn().rpop(bean.getQueue().getQueueName());
		return value=="nil"?false:true; 
	}

	@Override
	public boolean push(TaskBean bean) {
		long origina = radisConn().llen(bean.getQueue().getQueueName()); 
		long now = radisConn().lpush(bean.getQueue().getQueueName(), easyUtil.toGson(bean)); 
		return origina<now?true:false; 
	}

	public long listLength(QueueBean bean) {
		return radisConn().llen(bean.getQueueName());
	}
	
	@Override
	public boolean defer(long time, TaskBean queue) {
		return false;
	}
	
}
