# queue
## 任务队列 src 下 taskQueue 目录解析
### config 读取配置文件
    DeferConfig.java  读取配置文件的具体信息
### conn 存储连接
    Connect.java 连接的抽象接口
    RedisConn.java  redis 的集体连接操作，以及相关的增删改查操作
### entity 所用到的实体的信息
    QueueBean.java 队列的实体
    TaskBean.java 任务的实体
### queue 任务队列的具体执行
    ExecuteOne.java  一个任务队列是如何执行的（单个线程）
    ExecuteTheadPool.java 任务队列执行的线程池
### reflect 任务队列寻找需要执行的代码的反射
    Reflect.java  反射的具体实现
### task 执行任务
    ExecuteTask.java 任务的执行，执行成功回调，执行失败回调
### util 简单的帮助类
    easyUtil.java 里面主要实现了Gson对json与对象的转换的方法
### src 下 test 文件夹
  Text.java 模拟任务
    
    
