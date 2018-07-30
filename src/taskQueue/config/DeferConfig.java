package taskQueue.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
/**
 * ��ȡ�����ļ���Ϣ
 * @author ����
 *
 */
public class DeferConfig {

	/**
	 * �����ļ����ڵ�λ��
	 * ��ǰ�����ʸ�Ŀ¼
	 */
	final static ResourceBundle resource = ResourceBundle.getBundle("config");
	/**
	 * ���������д������ļ�����Ϣ
	 */
	final static Map<String , String> map = new HashMap<>();
	
	/**
	 * �����������������ļ���Ϣ
	 * @return
	 */
	public static Map<String, String> loadConfig() {
		Enumeration<String> e = resource.getKeys();
		while(e.hasMoreElements()) {
			String str = e.nextElement();
			map.put(str.trim(), resource.getString(str).trim());
		}
		return map;
	} 
	
	
	/**
	 * ������Ҫȡֵ�ļ���ȡ��һ�������ļ�����Ϣ
	 * @param key ��Ҫȥ�������ļ��е�ֵ�ļ�
	 * @return  �����ļ��е�ֵ
	 */
	public static String loadConfig(String key) {
		String value = "";
		try {
			value = resource.getString(key).trim();
		} catch (Exception e) {
			System.err.println("δ���� ��" + key +" ���ԡ������������ļ�������������ԣ�");
		}
		
		return value;
	}
	
	public static void main(String[] args) {
		System.out.println(loadConfig("thread.state.3"));
		System.out.println(loadConfig().toString());
	}
	
}
