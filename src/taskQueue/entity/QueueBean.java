package taskQueue.entity;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class QueueBean implements Serializable{

	private String uuid ;  		//������ʾ
	private String queueName;	//��������
	private Date createTime;	//����ʱ��
	private int queueType;		//�������� 1.�����  2.�еȶ��� 3.������  4�ӳٶ���
	private String createUser;	//������
	
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
