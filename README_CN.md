#### [English](https://github.com/sea-huang/loggable/blob/master/README.md)

# loggable
这是一个 Log 工具，把你从无聊的日志代码中拯救出来。

### 版本日志
- 1.0.0  主要功能  
- 1.0.1  修复JSON框架自动修复机制  
- 1.1.0  增加Gson支持， 新增记录时长功能<b>Log Duration</b> 
- 1.1.1  修复不复用DurationRecorder
- 1.1.2  移除依赖 spring-starter-logging, 支持xml配置

### 配置
- 添加 Maven 依赖:

	```xml
	<dependency>
	  <groupId>com.github.sea-huang</groupId>
	  <artifactId>loggable</artifactId>
	  <version>1.1.1</version>
	</dependency>
	```

- 可选的依赖，自动检测 阿里巴巴的 FastJson, Jackson 或 Gson 来序列化参数和结果. 如果以上都没有，默认使用 Object.toString()

	```xml
	<dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>fastjson</artifactId>
	</dependency>
	```
	```xml
	<dependency>
		<groupId>com.fasterxml.jackson.core</groupId>
		<artifactId>jackson-databind</artifactId>
	</dependency>
	```
	```xml
	<dependency>
		<groupId>com.google.code.gson</groupId>
		<artifactId>gson</artifactId>
	</dependency>
	```
	
- 配置代码

	<pre><code><b>@EnableLoggable</b>
	@Configuration
	public class AnyConfigClass{
	
	}
	</code></pre>
	
	或者XML配置
	
	```xml
	<bean class="com.github.seahuang.log.spring.LoggableAutoConfiguration">
		<property name="globalLogDuration" value="true"/><!--optional-->
	</bean>
	```

### 举个栗子

- 被测试的服务： 

	<pre><code>@Service
	public class TesteeService {
		<b>@Loggable("Purpose")</b>
		public String simpleCall(String stringArg, Integer intArg){
			return "result";
		}
	}
	</code></pre>

- 把 @Loggable 加到方法上, 就会记录参数和返回结果:
<pre>2018-05-15 11:36:21.879  INFO 63398 --- [           main] 
c.g.seahuang.log.stub.TesteeService      :
<b>Purpose Success! TesteeService.simpleCall(stringArg="AA",intArg=10) returns "result"</b></pre>

- 或者记录异常:

<pre><b>ExexptionTest Fail! TesteeService.throwException(stringArg="AA",intArg=10)</b>
	
java.lang.RuntimeException: Intentional Exception
		at com.github.seahuang.log.stub.TesteeService.throwException(TesteeService.java:35) ~[test-classes/:na]
		at com.github.seahuang.log.stub.TesteeService$$FastClassBySpringCGLIB$$5d883f5f.invoke(<generated>) ~[test-classes/:na]
		at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204) ~[spring-core-4.3.7.RELEASE.jar:4.3.7.RELEASE]
		at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:721) ~[spring-aop-4.3.7.RELEASE.jar:4.3.7.RELEASE]
		at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157) [spring-aop-4.3.7.RELEASE.jar:4.3.7.RELEASE]
		at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:139) ~[spring-context-4.3.7.RELEASE.jar:4.3.7.RELEASE]
		at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179) [spring-aop-4.3.7.RELEASE.jar:4.3.7.RELEASE]
		at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:85) ~[spring-aop-4.3.7.RELEASE.jar:4.3.7.RELEASE]
		at com.github.seahuang.log.LoggableAspect.log(LoggableAspect.java:18) ~[classes/:na]
		...
</pre>

- 想要只在异常时打印日志，成功时不打？ 把Success设为Level.OFF 
<pre>
@Loggable(value="SilenceSuccessTest", <b>onSuccess=Level.OFF</b>)
public String keepSilenceOnSuccess(String one, Integer two){
	return null;
}
</pre>

- 想要在异常时打印Warnning日志？
<pre>
@Loggable(value="Purpose", <b>warningOn=BusinessException.class</b>)
public String logWarnninngOnBusinessException(String one, Integer two){
	return null;
}
</pre>

- 更复杂的栗子:
<pre>
@Loggable("CustomizedLog")
public <b>@LogLength</b> List<String> customizeLog(<b>@LogIgnore</b> String one
			, <b>@LogFormat(ExceptionTypeFormatter.class)</b> Exception t){
	...
}
</pre>
  - @LogLength 只记录集合或数组的大小（长度）
  - @LogIngore 忽略某个参数或结果
 - @LogFormat 自定义参数或结果的输出

- 可以和校验框架结合，打印出违反的校验约束
<pre>@Loggable("validateMethod")
public <b>@NotNull</b> List<String> validateParameters(<b>@NotEmpty</b> String one, <b>@NotNull</b> Integer two){
		return null;
}
</pre>

- 记录每次方法调用的时间
  - 全局设置, 应用于所有没有特别配置的 @Loggable 方法 
  
  	```
  	@EnableLoggable(logDuration=true)
  	```
  
  - 对方法进行指定（覆盖全局配置）
  
  	```
  	@Loggable(logDuration=LogDuration.YES)
  	```
  

### Customization
- 参考 com.github.seahuang.log.spring.LoggableAutoConfiguration. 所有实现都是面向接口的, 可以被自己的实现替换. 
- 值得一提的是, 全局的自定义异常log 可以实现 com.github.seahuang.log.formatter.LogFormatter 或 扩展 com.github.seahuang.log.formatter.LogFormatterSupport(要注入到spring). 最接近的异常类型的LogFormatter会被使用