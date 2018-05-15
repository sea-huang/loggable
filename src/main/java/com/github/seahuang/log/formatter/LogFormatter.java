package com.github.seahuang.log.formatter;

import org.aspectj.lang.JoinPoint;

import com.github.seahuang.log.Level;

public interface LogFormatter<T extends Throwable> {
	
	Class<T> getTargetType();
	
	String format(Level level, JoinPoint jp, T throwable);
	
}
