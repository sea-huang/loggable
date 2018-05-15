package com.github.seahuang.log;

/**
 * 内部日志级别。OFF是个特殊级别，指不输出任何日志。
 * Internal log levels, among which OFF is special,
 *  for that in this level no log will be printed 
 */
public enum Level {
	OFF, 
	TRACE, 
	DEBUG, 
	INFO, 
	WARN, 
	ERROR;
}
