package taskQueue.entity;

import java.util.Date;

import lombok.Data;

@Data
public class TaskBean {

	private String uuid ; 		//����Ψһ��ʾID
	private String taskName ;	//����
	private String taskClass;	//��ִ���������
	private String taskMethod;	//��ִ������ķ���
	private String parameter; 	//��ִ������Ĳ���
	private int num;			//����ִ�д���
	private Date create;		//����ʱ��
	private QueueBean queue;	//������������

}
