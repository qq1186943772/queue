package taskQueue.conn;

import taskQueue.entity.QueueBean;
import taskQueue.entity.TaskBean;

/**
 * �������ӣ����ڲ��ݵ����ӣ��в�ͬ�����Ӳ�������ͬ�ķ���ֵ�����Ӳ�����ʱû�뵽���󷽷�
 * ���� ��ӳ��ӵĲ�������
 * @author ����
 *
 */
public interface Connect {

	/**
	 * �������ӵ�����
	 * ������� �п��ܴ��� nosql �� Ҳ���ܴ��� sql��
	 * @return String
	 */
	
	public String connType();
	
	/**
	 * �ر���Ӧ����
	 * @return boolean
	 */
	public boolean close();
	
	/**
	 * �Ӷ����е��� ִ�����˵�����
	 * @param queue �������е���Ϣ
	 * @return boolean
	 */
	
	public boolean pop(TaskBean bean);
	
	/**
	 * ��������ӵ�������
	 * @param queue ��ӵ����е���Ϣ
	 * @return boolean
	 */
	public boolean push(TaskBean bean);
	
	/**
	 * ���ض��е����һ��Ԫ�أ���Ϊ�������ӵ���ͷ�ģ�
	 * @return ֻ���� redis �д洢�����ݣ�����������
	 */
	public String rpop(QueueBean bean); 
	
	/**
	 * �ӳ��������
	 * @param time 	�ӳ���ӵ�ʱ��
	 * @param queue ��ӵ����е���Ϣ
	 * @return boolean
	 */
	public boolean defer(long time ,TaskBean bean);
	
	/**
	 * ���ض��еĳ���
	 * @param bean ���е�����
	 * @return ���еĳ���
	 */
	public long listLength(QueueBean bean);
}
