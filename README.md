#### [中文](https://github.com/sea-huang/loggable/blob/master/README_CN.md)

# loggable
AOP Log facilities to rescue you from logging java methods

### Version changes
- 1.0.0  main features  
- 1.0.1  fix autodetecting json frameworks  
- 1.1.0  add support for Gson and <b>Log Duration</b> feature 
- 1.1.1  fix DurationRecorder prototype
- 1.1.2  remove dependency spring-starter-logging, add support for xml config

### Set up
- add maven dependency:

	```xml
	<dependency>
	  <groupId>com.github.sea-huang</groupId>
	  <artifactId>loggable</artifactId>
	  <version>1.1.2</version>
	</dependency>
	```

- optinally dependencies, alibaba's FastJson, Jackson or Gson will be auto detected to seriliaze the arguments and results. If neither found on the path, it will default to Object.toString()

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
	
- Bring in the facilities

	<pre><code><b>@EnableLoggable</b>
	@Configuration
	public class AnyConfigClass{
	
	}
	</code></pre>
	
	or XML config
	
	```xml
	<bean class="com.github.seahuang.log.spring.LoggableAutoConfiguration">
		<property name="globalLogDuration" value="true"/><!--optional-->
	</bean>
	```

### Usage Examples

- Service to be tested 

	<pre><code>@Service
	public class TesteeService {
		<b>@Loggable("Purpose")</b>
		public String simpleCall(String stringArg, Integer intArg){
			return "result";
		}
	}
	</code></pre>

- Just add the @Loggable annotation to the method, and it will log all the arguments and result value as below:
<pre>2018-05-15 11:36:21.879  INFO 63398 --- [           main] 
c.g.seahuang.log.stub.TesteeService      :
<b>Purpose Success! TesteeService.simpleCall(stringArg="AA",intArg=10) returns "result"</b></pre>

- On exception case:

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

- Wanna log only on Exception and keep silence in Success? Set Level.OFF on Success 
<pre>
@Loggable(value="SilenceSuccessTest", <b>onSuccess=Level.OFF</b>)
public String keepSilenceOnSuccess(String one, Integer two){
	return null;
}
</pre>

- Wanna log warnning on some business exception?
<pre>
@Loggable(value="Purpose", <b>warningOn=BusinessException.class</b>)
public String logWarnninngOnBusinessException(String one, Integer two){
	return null;
}
</pre>

- More complicated case:
<pre>
@Loggable("CustomizedLog")
public <b>@LogLength</b> List<String> customizeLog(<b>@LogIgnore</b> String one
			, <b>@LogFormat(ExceptionTypeFormatter.class)</b> Exception t){
	...
}
</pre>
  - @LogLength only log the length of a collection or array.
  - @LogIngore ignore the parameter or result when format the output
 - @LogFormat give a self defined implementation class type for TypeFormatter, by which to format the parameter output

- Combine with Method Validation
<pre>@Loggable("validateMethod")
public <b>@NotNull</b> List<String> validateParameters(<b>@NotEmpty</b> String one, <b>@NotNull</b> Integer two){
		return null;
}
</pre>

- Log the duration time each method call costs
  - global setting, it will apply all @Loggable methods without specific LogDuration assigned or LogDuration equals to Default
  
  	```
  	@EnableLoggable(logDuration=true)
  	```
  
  - each method call assign it's own Duration log strategy, which will override the global settings
  
  	```
  	@Loggable(logDuration=LogDuration.YES)
  	```
  

### Customization
- Refer to com.github.seahuang.log.spring.LoggableAutoConfiguration. All classes are connected by interfaces, and can be replaced by your own implementation. 
- An important explanation, global customized exception log formatter can be apply by implemnt com.github.seahuang.log.formatter.LogFormatter or extend com.github.seahuang.log.formatter.LogFormatterSupport(should register to spring). the most precise exception formatter will be used.