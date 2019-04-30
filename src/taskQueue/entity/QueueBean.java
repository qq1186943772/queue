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
	private int queueType;		//�������� 1.�����  2.�еȶ��� 3.������  4�ӳٶ���
	private Date createTime;	//����ʱ��
	private String createUser;	//������
	
	public QueueBean() {
		super();
	}

	public QueueBean(String queueName,int queueType) {
		super();
		this.queueName = queueName;
		this.queueType = queueType;
	}

}
