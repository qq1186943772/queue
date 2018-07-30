package taskQueue.task;

import taskQueue.conn.Connect;
import taskQueue.conn.RedisConn;
import taskQueue.entity.QueueBean;
import taskQueue.entity.TaskBean;
import taskQueue.reflect.Reflect;
import taskQueue.util.easyUtil;

public class ExecuteTask {
	
	/*private boolean executeState = false;*/ //任务执行的状态
	public TaskBean bean = null;
	public QueueBean queue = null;
	public Connect conn;

	public ExecuteTask(QueueBean queue, Connect conn) {
		super();
		this.queue = queue;
		this.conn = conn;
	}

	public void defeated() {
		if(bean.getNum() < 10) {
			bean.setNum(bean.getNum() + 1);
			conn.push(bean);
		}else {
			//任务尝试执行10次 ， 如果超过 10 次加入错误队列
		}
	}
	
	public void success() {
		/*conn.pop(bean);*/
		System.out.println("---------------------------------------------------------------------------------------------");
	}
	
	@SuppressWarnings("static-access")
	public boolean execute() {
		bean = (TaskBean) easyUtil.toObject(conn.rpop(queue), TaskBean.class);
		System.out.println(bean.toString());
		Reflect ref = new Reflect();
		return ref.loadClass(bean);
	}
	
	public static void main(String[] args) {
		QueueBean bean = new QueueBean("myQueueTest",1);
		Connect conn = new RedisConn();
		ExecuteTask task = new ExecuteTask(bean,conn);
		task.execute();
		System.out.println("任务执行完成");
	}
	
}
