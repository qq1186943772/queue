package taskQueue.entity;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class QueueBean implements Serializable{

	private String uuid ;  		//主键表示
	private String queueName;	//队列名称
	private Date createTime;	//创建时间
	private int queueType;		//队列类型 1.快队列  2.中等队列 3.慢队列  4延迟队列
	private String createUser;	//创建人
	
	public QueueBean() {
		super();
	}

	public QueueBean(String queueName,int queueType) {
		super();
		this.queueName = queueName;
		this.queueType = queueType;
	}

	public QueueBean(String uuid, String queueName, Date createTime, int queueType, String createUser) {
		super();
		this.uuid = uuid;
		this.queueName = queueName;
		this.createTime = createTime;
		this.queueType = queueType;
		this.createUser = createUser;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getQueueType() {
		return queueType;
	}

	public void setQueueType(int queueType) {
		this.queueType = queueType;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Override
	public String toString() {
		return "QueueBean [uuid=" + uuid + ", queueName=" + queueName + ", createTime=" + createTime + ", queueType="
				+ queueType + ", createUser=" + createUser + "]";
	}

	

}
