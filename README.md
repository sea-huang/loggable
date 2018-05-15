# loggable
AOP Log facilities to rescue you from logging java methods

### Bring in the facilities
<pre><code><b>@EnableLoggable</b>
@Configuration
public class AnyConfigClass{

}
</code></pre>

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
<pre>ExexptionTest Fail! TesteeService.throwException(stringArg="AA",intArg=10)
	
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
@Loggable("定制日志")
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

### Customization
- Refer to com.github.seahuang.log.spring.LoggableAutoConfiguration for now
- I will get back to this block later