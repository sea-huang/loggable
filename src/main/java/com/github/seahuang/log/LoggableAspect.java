package com.github.seahuang.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.seahuang.log.printer.LogPrinter;

@Aspect
public class LoggableAspect {
	@Autowired
	private LogPrinter printer;

	@Around("@annotation(com.github.seahuang.log.Loggable)")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		try{
			Object result = pjp.proceed();
			printer.printSuccess(pjp, result);
			return result;
		}catch(Throwable e){
			printer.printThrowable(pjp, e);
			throw e;
		}
	}
}
