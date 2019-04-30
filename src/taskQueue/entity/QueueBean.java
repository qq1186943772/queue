package taskQueue.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * QueueBean 描述一个任务队列
 * @author 王勃
 *
 */
@Data
@SuppressWarnings("serial")
public class QueueBean implements Serializable{

	private String uuid ;  		//主键表示
	private String queueName;	//队列名称
	private String queueType;	//队列类型 
	private Date createTime;	//创建时间
	private String createUser;	//创建人
	
	public QueueBean() {
		super();
	}

	public QueueBean(String queueName,String queueType) {
		super();
		this.queueName = queueName;
		this.queueType = queueType;
	}

}
