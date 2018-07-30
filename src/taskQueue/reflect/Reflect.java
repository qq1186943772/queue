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
			method.invoke(task.newInstance(),parameters); //只传递 json 格式的数据 ，也就是 String类型的 如果在调用方法要使用其他类型再进行转换
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
