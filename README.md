# OJforJava
一个web项目，实现在线判题系统。

功能：<br/>
>实现了用户管理，需要登录，并且区分用户和管理员，管理员可以增加新的管理员账户，也可以增加题目，或删除题目。<br/>
>实现判题系统，将用户传入的代码传入编译执行，对比结果，判定对错。并保存其答题记录，方便用户查看、纠错。<br/>

工程结构：<br/>
>models：存储题目的模板代码，做提示功能，防止用户传参或返回类型出错。<br/>
>questions：存储每道题的题目描述。<br/>
>testCase：存储每道题目的测试用例<br/>
>pom.xml：管理maven<br/>
>src：源码<br/>
>>main：源码<br/>
>>>java：java代码<br/>
>>>>beans：实体类<br/>
>>>>common：一些公用类，包括一些自定义异常，用于传递信息<br/>
>>>>configure：spring boot的java配置类<br/>
>>>>interceptor：拦截器<br/>
>>>>utils：工具类<br/>
>>>>controllor、service、dao三层建构web<br/>
>>>resources：资源及配置文件<br/>
>>>>mybatis：mybatis相关配置及mapper文件<br/>
>>>>templates：thymeleaf模板<br/>
>>>>application.properties：spring boot配置文件<br/>
>>test：测试程序<br/>
