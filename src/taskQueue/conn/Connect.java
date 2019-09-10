package taskQueue.conn;

import taskQueue.entity.QueueBean;
import taskQueue.entity.TaskBean;

/**
 * 抽象连接，由于不容的连接，有不同的连接操作，不同的返回值。连接操作暂时没想到抽象方法
 * 抽象 入队出队的操作方法
 * @author 王勃
 *
 */
public interface Connect {

	/**
	 * 返回连接的类型
	 * 任务队列 有可能存在 nosql 中 也肯能存在 sql中
	 * @return String
	 */
	public String connType();

	/**
	 * 关闭相应连接
	 * @return boolean
	 */
	public boolean close();

	/**
	 * 从队列中弹出 执行完了的任务
	 * @param queue 弹出队列的信息
	 * @return boolean
	 */
	public boolean pop(TaskBean bean);

	/**
	 * 将任务添加到队列中
	 * @param queue 添加到队列的信息
	 * @return boolean
	 */
	public boolean push(TaskBean bean);

	/**
	 * 返回队列的最后一个元素（因为添加是添加到队头的）
	 * @return 只返回 redis 中存储的内容，不多做处理
	 */
	public String rpop(QueueBean bean);

	/**
	 * 延迟添加任务
	 * @param time 	延迟添加的时间
	 * @param queue 添加到队列的信息
	 * @return boolean
	 */
	public boolean defer(long time ,TaskBean bean);

	/**
	 * 返回队列的长度
	 * @param bean 队列的名称
	 * @return 队列的长度
	 */
	public long listLength(QueueBean bean);
}
