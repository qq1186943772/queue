package taskQueue.entity;

public class TaskBean {

	private String uuid ; 		//任务唯一表示ID
	private String taskName ;	//任务
	private String taskClass;	//所执行任务的类
	private String taskMethod;	//所执行任务的方法
	private String parameter; 	//所执行任务的参数
	private int num;			//任务执行次数
	private QueueBean queue;	//任务所属队列
	
	public TaskBean() {
		super();
	}
	
	public TaskBean(String uuid, String taskName, String taskClass, String taskMethod, String parameter) {
		super();
		this.uuid = uuid;
		this.taskName = taskName;
		this.taskClass = taskClass;
		this.taskMethod = taskMethod;
		this.parameter = parameter;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskClass() {
		return taskClass;
	}
	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}
	public String getTaskMethod() {
		return taskMethod;
	}
	public void setTaskMethod(String taskMethod) {
		this.taskMethod = taskMethod;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public QueueBean getQueue() {
		return queue;
	}

	public void setQueue(QueueBean queue) {
		this.queue = queue;
	}

	@Override
	public String toString() {
		return "TaskBean [uuid=" + uuid + ", taskName=" + taskName + ", taskClass=" + taskClass + ", taskMethod="
				+ taskMethod + ", parameter=" + parameter + ", num=" + num + ", queue=" + queue + "]";
	}

}
