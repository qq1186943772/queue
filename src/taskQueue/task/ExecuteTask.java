package taskQueue.task;

import taskQueue.conn.Connect;
import taskQueue.conn.connImpl.RedisConn;
import taskQueue.entity.QueueBean;
import taskQueue.entity.TaskBean;
import taskQueue.reflect.Reflect;
import taskQueue.util.easyUtil;
/**
 * 任务执行
 * @author 王勃
 *
 */
public class ExecuteTask {
	
	/*private boolean executeState = false;*/ 
	public QueueBean queue = null;
	public Connect conn;

	public ExecuteTask(QueueBean queue, Connect conn) {
		super();
		this.queue = queue;
		this.conn = conn;
	}

	public void defeated() {
		System.out.println("执行失败--------------------------------------------------------------------------------------------");
	}
	
	public void success() {
		/*conn.pop(bean);*/
		System.out.println("执行成功--------------------------------------------------------------------------------------------");
	}
	
	@SuppressWarnings("static-access")
	public boolean execute(TaskBean task) {
		Reflect ref = new Reflect();
		return ref.loadClass(task);
	}
	
	public static void main(String[] args) {
		QueueBean queue = new QueueBean("myQueueTest","fast");
		Connect conn = new RedisConn();
		ExecuteTask task = new ExecuteTask(queue,conn);
		TaskBean bean = (TaskBean) easyUtil.toObject(conn.rpop(queue), TaskBean.class);
		task.execute(bean);
		System.out.println("消息消费完成");
	}
	
}
