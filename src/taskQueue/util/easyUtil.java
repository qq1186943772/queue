package taskQueue.util;

import com.google.gson.Gson;

/**
 * 一些比较简单的帮助类，只是为了简化一点代码，减少重复部分
 * @author 王勃
 *
 */
public class easyUtil {

	/**
	 * 私有 Gson 对象 只能通过 getGson 来获取
	 */
	private static Gson gson = null;

	/**
	 * 获取Gson 对象 ，如果已经创建过就不再创建
	 * @return
	 */
	public static Gson getGson() {
		if(gson == null) {
			gson = new Gson();
		}
		return gson;
	}

	/**
	 * 将对象转化成 json 格式的数据
	 * @param obj
	 * @return
	 */
	public static String toGson(Object obj) {
		return getGson().toJson(obj);
	}

	/**
	 * 讲json转化成  java对象
	 * @param json
	 * @param cla // 需要转化的类的 类类型
	 * @return
	 */
	public static Object toObject(String json,Class<?> cla) {
		return getGson().fromJson(json, cla);
	}
}
