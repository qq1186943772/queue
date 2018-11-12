package taskQueue.entity;

import java.util.Date;

import lombok.Data;

@Data
public class TaskBean {

	private String uuid ; 		//任务唯一表示ID
	private String taskName ;	//任务
	private String taskClass;	//所执行任务的类
	private String taskMethod;	//所执行任务的方法
	private String parameter; 	//所执行任务的参数
	private int num;			//任务执行次数
	private Date create;		//创建时间
	private QueueBean queue;	//任务所属队列

}
