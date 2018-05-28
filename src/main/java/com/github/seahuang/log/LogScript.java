package com.github.seahuang.log;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Inherited
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, METHOD})
public @interface LogScript {
	/**
	 * @return The script to be executed. The script should return String or any other Object will be serialized by DefaultTypeFormatter
	 *          Within the script, the validated object can be accessed from the {@link javax.script.ScriptContext
	 *         script context} using the name specified in the
	 *         <code>alias</code> attribute.
	 */
	String value();
	
	/**
	 * @return The name of the script language used by this constraint as
	 *         expected by the JSR 223 {@link javax.script.ScriptEngineManager}. A
	 *         {@link javax.validation.ConstraintDeclarationException} will be thrown upon script
	 *         evaluation, if no engine for the given language could be found. 
	 *         Default to javascript.
	 */
	String lang() default "javascript";

	/**
	 * @return The name, under which the annotated element shall be registered
	 *         within the script context. Defaults to "_t".
	 */
	String alias() default "_t";
	
	/**
	 * 设置哪些Level级别将在日志中只记录条数, 其他级别应用默认输出。 默认应用于所有级别
	 * @return Set Levels on which onely the length of parameters or result value will be logged,
	 * , other will apply the default formatter. default for all levels.
	 */
	Level[] onLevel() default {Level.ERROR, Level.WARN
		, Level.INFO, Level.DEBUG, Level.TRACE, Level.OFF};

}
