package taskQueue.entity;

import java.util.Date;

import lombok.Data;

/**
 * 	������з���Ķ���
 * 	1.��Ҫ�õ��������
 * 	2.�����������ر��
 * 	3.����ͨ������ִ��
 * 
 * 	ʹ�ö���ע������
 * 	1.���Ǵ洢�Ĳ�����Ϣ����Ҫִ�е�����ͨ�����䣩
 * 	2.������ҪԤ��׼���ý�Ҫִ������ķ���
 * 
 * 	TaskBean ����һ����Ҫִ�е��������������һ�����е���
 * 	@author ����
 *
 */

@Data
public class TaskBean {

	private String uuid ; 		//����Ψһ��ʶID
	private String taskName ;	//����
	private String taskClass;	//��ִ���������
	private String taskMethod;	//��ִ������ķ���
	private String parameter; 	//��ִ������Ĳ���
	private int num;			//����ִ�д���
	private Date create;		//����ʱ��
	private QueueBean queue;	//������������

}

