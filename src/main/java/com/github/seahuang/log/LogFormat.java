package com.github.seahuang.log;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.github.seahuang.log.formatter.type.TypeFormatter;

/**
 * 自定义日志输出，可以设定指定的Level级别. 未指定的级别使用默认输出
 * Customize the log output, optionally specify the levels to be apply on. 
 * Other levels will apply on default formatter.
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, METHOD})
public @interface LogFormat {
	/**
	 * 指定自定义的日志输出类的类型，确保该类包含一个无参构造器。
	 * @return customize the formatter, a default constructor with no parameter should be assured.
	 */
	Class<? extends TypeFormatter> value();
	/**
	 * 设置哪些Level级别应用这个自定义的日志输出, 其他级别应用默认输出。 默认应用于所有级别
	 * @return Set Levels on which parameters or result value will apply the TypeFormatter defined here,
	 * , other will apply the default formatter. default for all levels.
	 */
	Level[] onLevel() default {Level.ERROR, Level.WARN
		, Level.INFO, Level.DEBUG, Level.TRACE, Level.OFF};
}
