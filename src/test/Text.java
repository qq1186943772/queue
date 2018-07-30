package test;

import taskQueue.entity.TestBean;
import taskQueue.util.easyUtil;

public class Text {

	public void text(String str1,String str2) {
		System.out.println(str1);
		System.out.println(str2);
		try {
			System.out.println(easyUtil.toObject(str2,TestBean.class));
		} catch (Exception e) {
			System.out.println("json×ª»»´íÎó£¡~");
		}
	}
	
}
