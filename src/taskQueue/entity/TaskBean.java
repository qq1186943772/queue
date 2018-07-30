package taskQueue.entity;

public class TaskBean {

	private String uuid ; 		//����Ψһ��ʾID
	private String taskName ;	//����
	private String taskClass;	//��ִ���������
	private String taskMethod;	//��ִ������ķ���
	private String parameter; 	//��ִ������Ĳ���
	private int num;			//����ִ�д���
	private QueueBean queue;	//������������
	
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
