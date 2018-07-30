package taskQueue.util;

import com.google.gson.Gson;

/**
 * һЩ�Ƚϼ򵥵İ����ֻ࣬��Ϊ�˼�һ����룬�����ظ�����
 * @author ����
 *
 */
public class easyUtil {

	/**
	 * ˽�� Gson ���� ֻ��ͨ�� getGson ����ȡ
	 */
	private static Gson gson = null;
	
	/**
	 * ��ȡGson ���� ������Ѿ��������Ͳ��ٴ���
	 * @return
	 */
	public static Gson getGson() {
		if(gson == null) {
			gson = new Gson();
		}
		return gson;
	}
	
	/**
	 * ������ת���� json ��ʽ������
	 * @param obj
	 * @return
	 */
	public static String toGson(Object obj) {
		return getGson().toJson(obj);
	}
	
	/**
	 * ��jsonת����  java���� 
	 * @param json
	 * @param cla // ��Ҫת������� ������
	 * @return
	 */
	public static Object toObject(String json,Class<?> cla) {
		return getGson().fromJson(json, cla);
	}
}
