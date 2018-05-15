package com.github.seahuang.log.printer;

import org.aspectj.lang.JoinPoint;

public interface LogPrinter {
	
	void printSuccess(JoinPoint jp, Object result);
	
	<T extends Throwable> void printThrowable(JoinPoint jp, T t);
}
