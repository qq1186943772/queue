package taskQueue.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QueueBean ����һ���������
 * @author ����
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class QueueBean implements Serializable{

	private String uuid ;  		//������ʾ
	private String queueName;	//��������
	private String queueType;	//�������� 
	private Date createTime;	//����ʱ��
	private String createUser;	//������
	
	public QueueBean(String queueName,String queueType) {
		super();
		this.queueName = queueName;
		this.queueType = queueType;
	}

}
