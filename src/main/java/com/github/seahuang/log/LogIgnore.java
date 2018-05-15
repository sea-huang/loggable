package com.github.seahuang.log;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 设置在日志中略过指定对象
 * Set to ignore object on logging.
 */
@Inherited
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, METHOD})
public @interface LogIgnore {
	/**
	 * 设置哪些Level级别将在日志中被略过, 其他级别应用默认输出。 默认应用于所有级别
	 * Set Levels on which parameters or result value will be ignored for logging,
	 * , other will apply the default formatter. default for all levels.
	 */
	Level[] value() default {Level.ERROR, Level.WARN
		, Level.INFO, Level.DEBUG, Level.TRACE, Level.OFF};
}
