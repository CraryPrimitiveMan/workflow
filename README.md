# 工作流系统

## 环境依赖

+ h2
+ redis
+ java8+

## DB设计

当前先按最简单的工作流设计

```h2sql
# 过程表
DROP TABLE IF EXISTS PROCESS;

CREATE TABLE PROCESS(
  process_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '过程ID',
  process_name VARCHAR(20) NOT NULL COMMENT '过程名称',
  process_alias VARCHAR(20) NOT NULL COMMENT '过程别名',
  PRIMARY KEY (process_id)
);

# 过程节点表
DROP TABLE IF EXISTS TASK;

CREATE TABLE TASK(
  task_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '节点（任务）ID',
  task_name VARCHAR(20) NOT NULL COMMENT '节点（任务）名称',
  process_id BIGINT(20) NOT NULL COMMENT '所属过程定义ID',
  node_type int(4) NOT NULL DEFAULT 0 COMMENT '节点类型，0:默认值，1:开始节点，2:结束节点，3:任务节点）',
  process_logic INT(4) NOT NULL DEFAULT 0 COMMENT '针对前置节点的过程逻辑属性，0:默认值，1:and合并节点，2:or合并节点',
  role_id BIGINT(20) NOT NULL COMMENT '分配的任务角色ID，该角色可以操作此节点',
  PRIMARY KEY (task_id)
);

# 过程实例表
DROP TABLE IF EXISTS PROCESS_INSTANCE;

CREATE TABLE PROCESS_INSTANCE(
  process_instance_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '流程实例ID',
  description VARCHAR(255) NOT NULL COMMENT '业务实例的内容描述',
  process_id BIGINT(20) NOT NULL COMMENT '所属过程定义ID',
  start_time TIMESTAMP NULL COMMENT '流程实例的创建时间',
  end_time TIMESTAMP NULL COMMENT '流程实例的结束时间',
  start_task_instance_id BIGINT(20) NOT NULL COMMENT '开始任务实例ID',
  next_task_instance_ids VARCHAR(255) NOT NULL AUTO_INCREMENT COMMENT '后续任务实例ID，逗号分隔',
  pre_task_instance_ids VARCHAR(255) NOT NULL AUTO_INCREMENT COMMENT '前驱任务实例ID，逗号分隔',
  PRIMARY KEY (process_instance_id)
);

# 任务实例表
DROP TABLE IF EXISTS TASK_INSTANCE;

CREATE TABLE TASK_INSTANCE(
  task_instance_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '任务实例ID',
  task_id BIGINT(20) NOT NULL COMMENT '节点（任务）ID',
  process_instance_id BIGINT(20) NOT NULL COMMENT '流程实例ID',
  start_time TIMESTAMP NULL COMMENT '流程实例的创建时间',
  end_time TIMESTAMP NULL COMMENT '流程实例的结束时间',
  user_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '处理任务的用户ID',
  next_task_instance_id BIGINT(20) NULL COMMENT '后续任务实例ID',
  pre_task_instance_id BIGINT(20) NULL COMMENT '前驱任务实例ID',
  task_state INT(4) NOT NULL DEFAULT 0 COMMENT '任务执行状态，0:默认值，1:创建完，2:任务进行中，3:任务已完成',
  task_status INT(4) NOT NULL DEFAULT 0 COMMENT '任务审核结果，0:默认值，1:通过，2:不通过',
  task_message VARCHAR(255) NOT NULL DEFAULT '' COMMENT '任务审核信息，通过备注或拒绝理由',
  PRIMARY KEY (task_instance_id)
);

# 迁移表，即任务之间路由表
DROP TABLE IF EXISTS TASK_ROUTE;

CREATE TABLE TASK_ROUTE(
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '迁移ID',
  process_id BIGINT(20) NOT NULL COMMENT '过程定义ID',
  next_task_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '后续任务ID',
  PRIMARY KEY (id)
);

# 用户和角色关系表
DROP TABLE IF EXISTS RELATION_USER_ROLE;

CREATE TABLE RELATION_USER_ROLE(
  user_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '用户ID',
  role_id BIGINT(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (user_id, role_id)
);
```

## entity 定义规则
+ DO（ Data Object）：与数据库表结构一一对应，通过DAO层向上传输数据源对象。
+ DTO（ Data Transfer Object）：数据传输对象，Service或Manager向外传输的对象。
+ BO（ Business Object）：业务对象。 由Service层输出的封装业务逻辑的对象。
+ AO（ Application Object）：应用对象。 在Web层与Service层之间抽象的复用对象模型，极为贴近展示层，复用度不高。
+ VO（ View Object）：显示层对象，通常是Web向模板渲染引擎层传输的对象。
+ POJO（ Plain Ordinary Java Object）：在本手册中， POJO专指只有setter/getter/toString的简单类，包括DO/DTO/BO/VO等。
+ Query：数据查询对象，各层接收上层的查询请求。 注意超过2个参数的查询封装，禁止使用Map类来传输。

当前只使用了DO/DTO和VO

## 

参考资料：

+ https://juejin.im/post/6844904167463485453
+ https://blog.csdn.net/chclvzxx/article/details/7991346