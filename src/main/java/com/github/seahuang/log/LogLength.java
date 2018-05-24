package com.github.seahuang.log;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 指定对象只记录条数，只能用于java.util.Collection或数组。
 * Specify object to only log record counts,
 * can be only apply to java.util.Collection or Array.
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, METHOD})
public @interface LogLength {
	/**
	 * 设置哪些Level级别将在日志中只记录条数, 其他级别应用默认输出。 默认应用于所有级别
	 * @return Set Levels on which onely the length of parameters or result value will be logged,
	 * , other will apply the default formatter. default for all levels.
	 */
	Level[] value() default {Level.ERROR, Level.WARN
		, Level.INFO, Level.DEBUG, Level.TRACE, Level.OFF};
}
