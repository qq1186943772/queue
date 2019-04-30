package taskQueue.entity;

import java.util.Date;

import lombok.Data;

/**
 * 	任务队列服务的对象
 * 	1.需要用到任务队列
 * 	2.访问量不会特别大
 * 	3.可以通过反射执行
 * 
 * 	使用队列注意事项
 * 	1.我们存储的不是消息而是要执行的任务（通过反射）
 * 	2.我们需要预先准备好将要执行任务的方法
 * 
 * 	TaskBean 描述一个将要执行的任务，他会存在于一个队列当中
 * 	@author 王勃
 *
 */

@Data
public class TaskBean {

	private String uuid ; 		//任务唯一标识ID
	private String taskName ;	//任务
	private String taskClass;	//所执行任务的类
	private String taskMethod;	//所执行任务的方法
	private String parameter; 	//所执行任务的参数
	private int num;			//任务执行次数
	private Date create;		//创建时间
	private QueueBean queue;	//任务所属队列

}

