package com.github.seahuang.log;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.seahuang.log.duration.LogDuration;

/**
 * Doc: {@link <a href="https://github.com/sea-huang/loggable">https://github.com/sea-huang/loggable</a>}
 * @author 黄海
 * @see com.github.seahuang.log.LoggableAspect
 * @see com.github.seahuang.log.spring.LoggableAutoConfiguration
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Loggable {
	/**
	 * 描述方法，该值会被加入日志。可以为空。
	 * @return Describe the aim of the method with a phase, which will be printed in log.
	 */
	String value() default "";
	/**
	 * 方法成功执行后打日志，默认级别为INFO，可以使用Level.OFF关闭。
	 * @return Set the log level on success, can be turn off by Level.OFF
	 */
	Level onSuccess() default Level.INFO;
	/**
	 * 设置时间记录策略
	 * @see LogDuration
	 * @return Set the LogDuration strategy. 
	 */
	LogDuration logDuration() default LogDuration.DEFAULT;
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，打印error日志。默认所有 java.lang.Throwable 类型。
	 * @return Specify the Throwable types or it's sub types to be logged on Level.ERROR. 
	 * Default to any Throwable type.
	 */
	Class<? extends Throwable>[] errorOn() default {Throwable.class};
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，打印warning日志。默认空。
	 * @return Specify the Throwable types or it's sub types to be logged on Level.WARN. 
	 * Default to empty
	 */
	Class<? extends Throwable>[] warningOn() default {};
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，打印info日志。默认空。
	 * @return Specify the Throwable types or it's sub types to be logged on Level.INFO. 
	 * Default to empty
	 */
	Class<? extends Throwable>[] infoOn() default {};
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，打印debug日志。默认空。
	 * @return Specify the Throwable types or it's sub types to be logged on Level.DEBUG. 
	 * Default to empty
	 */
	Class<? extends Throwable>[] debugOn() default {};
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，打印trace日志。默认空。
	 * @return Specify the Throwable types or it's sub types to be logged on Level.TRACE. 
	 * Default to empty
	 */
	Class<? extends Throwable>[] traceOn() default {};
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，不打印日志。默认空。
	 * @return Specify the Throwable types or it's sub types to be logged on Level.OFF. 
	 * Default to empty
	 */
	Class<? extends Throwable>[] offOn() default {};
}
