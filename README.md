# OJforJava
一个web项目，实现在线判题系统。

功能：
实现了用户管理，需要登录，并且区分用户和管理员，管理员可以增加新的管理员账户，也可以增加题目，或删除题目。
实现判题系统，将用户传入的代码传入编译执行，对比结果，判定对错。并保存其答题记录，方便用户查看、纠错。

工程结构：
models：存储题目的模板代码，做提示功能，防止用户传参或返回类型出错。
questions：存储每道题的题目描述。
testCase：存储每道题目的测试用例
pom.xml：管理maven
src：源码
  main：源码
    java：java代码
      beans：实体类
      common：一些公用类，包括一些自定义异常，用于传递信息
      configure：spring boot的java配置类
      interceptor：拦截器
      utils：工具类
      controllor、service、dao三层建构web
    resources：资源及配置文件
      mybatis：mybatis相关配置及mapper文件
      templates：thymeleaf模板
      application.properties：spring boot配置文件
  test：测试程序
