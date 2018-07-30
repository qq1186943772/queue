package taskQueue.reflect;

import java.lang.reflect.Method;

import taskQueue.entity.TaskBean;

public class Reflect {

	@SuppressWarnings("all")
	public static boolean loadClass(TaskBean bean) {
		
		try {
			Class<?> task = Class.forName(bean.getTaskClass());
			String[] parameters = bean.getParameter().split("!");
			Class<String>[] cla = new Class[parameters.length];
			for(int i = 0; i < cla.length; i++) {
				cla[i] = String.class;
			}
			Method method = task.getMethod(bean.getTaskMethod(), cla);
			method.invoke(task.newInstance(),parameters); //ֻ���� json ��ʽ������ ��Ҳ���� String���͵� ����ڵ��÷���Ҫʹ�����������ٽ���ת��
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	  
	public static void main(String[] args) {
		TaskBean bean = new TaskBean();
		bean.setTaskClass("test.Text");
		bean.setTaskMethod("text");
		bean.setParameter("hello,word");
		loadClass(bean);
	}

}
