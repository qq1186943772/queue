package taskQueue.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * QueueBean ����һ���������
 * @author ����
 *
 */
@Data
@SuppressWarnings("serial")
public class QueueBean implements Serializable{

	private String uuid ;  		//������ʾ
	private String queueName;	//��������
	private String queueType;	//�������� 
	private Date createTime;	//����ʱ��
	private String createUser;	//������
	
	public QueueBean() {
		super();
	}

	public QueueBean(String queueName,String queueType) {
		super();
		this.queueName = queueName;
		this.queueType = queueType;
	}

}
