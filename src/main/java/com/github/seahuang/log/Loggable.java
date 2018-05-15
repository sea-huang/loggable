package com.github.seahuang.log;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Inherited
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Loggable {
	/**
	 * 描述方法，该值会被加入日志。可以为空。
	 * Describe the aim of the method with a phase, which will be printed in log.
	 */
	String value() default "";
	/**
	 * 方法成功执行后打日志，默认级别为INFO，可以使用Level.OFF关闭。
	 * Set the log level on success, can be turn off by Level.OFF
	 */
	Level onSuccess() default Level.INFO;
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，打印error日志。默认所有 java.lang.Throwable 类型。
	 * Specify the Throwable types or it's sub types to be logged on Level.ERROR. 
	 * Default to any Throwable type.
	 */
	Class<? extends Throwable>[] errorOn() default {Throwable.class};
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，打印warning日志。默认空。
	 */
	Class<? extends Throwable>[] warningOn() default {};
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，打印info日志。默认空。
	 */
	Class<? extends Throwable>[] infoOn() default {};
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，打印debug日志。默认空。
	 */
	Class<? extends Throwable>[] debugOn() default {};
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，打印trace日志。默认空。
	 */
	Class<? extends Throwable>[] traceOn() default {};
	/**
	 * 指定异常类型。在该异常，或其子类抛出时，不打印日志。默认空。
	 */
	Class<? extends Throwable>[] offOn() default {};
}
