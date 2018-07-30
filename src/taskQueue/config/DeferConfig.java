package taskQueue.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
/**
 * 读取配置文件信息
 * @author 王勃
 *
 */
public class DeferConfig {

	/**
	 * 配置文件所在的位置
	 * 当前配置问根目录
	 */
	final static ResourceBundle resource = ResourceBundle.getBundle("config");
	/**
	 * 用来存所有储配置文件的信息
	 */
	final static Map<String , String> map = new HashMap<>();
	
	/**
	 * 用来返回所有配置文件信息
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
	 * 根据需要取值的键，取得一个配置文件的信息
	 * @param key 需要去的配置文件中的值的键
	 * @return  配置文件中的值
	 */
	public static String loadConfig(String key) {
		String value = "";
		try {
			value = resource.getString(key).trim();
		} catch (Exception e) {
			System.err.println("未配置 ：" + key +" 属性。请现在配置文件中配置相关属性！");
		}
		
		return value;
	}
	
	public static void main(String[] args) {
		System.out.println(loadConfig("thread.state.3"));
		System.out.println(loadConfig().toString());
	}
	
}
