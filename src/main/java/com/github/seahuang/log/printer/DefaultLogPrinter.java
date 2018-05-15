package com.github.seahuang.log.printer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import com.github.seahuang.log.Level;
import com.github.seahuang.log.Loggable;
import com.github.seahuang.log.Success;
import com.github.seahuang.log.formatter.LogFormatter;

public class DefaultLogPrinter implements LogPrinter, InitializingBean {
	@Autowired
	protected List<LogFormatter<?>> formatters;
	protected Map<Class<? extends Throwable>, LogFormatter<?>> formaterByThrowable = new HashMap<Class<? extends Throwable>, LogFormatter<?>>();
	
	public void afterPropertiesSet() throws Exception {
		for(LogFormatter<?> each : formatters){
			formaterByThrowable.put(each.getTargetType(), each);
		}
	}
	
	public void printSuccess(JoinPoint jp, Object result) {
		Success t = new Success(result);
		MethodSignature methodSignature = (MethodSignature)jp.getSignature();
		Loggable loggable = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Loggable.class);
		Logger logger = LoggerFactory.getLogger(methodSignature.getDeclaringType());
		Level level = decideLevel(loggable, t);
		LogFormatter<Success> formatter = decideFormatter(t);
		String logContent = formatter.format(level, jp, t);
		
		switch(level){
		case OFF : /* do nothing */ break;
		case TRACE : logger.trace(logContent); break;
		case DEBUG : logger.debug(logContent); break;
		case INFO : logger.info(logContent); break;
		case WARN : logger.warn(logContent); break;
		case ERROR : logger.error(logContent); break;
		}
	}
	
	public <T extends Throwable> void printThrowable(JoinPoint jp, T t) {
		MethodSignature methodSignature = (MethodSignature)jp.getSignature();
		Loggable loggable = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Loggable.class);
		Logger logger = LoggerFactory.getLogger(methodSignature.getDeclaringType());
		Level level = decideLevel(loggable, t);
		LogFormatter<T> formatter = decideFormatter(t);
		String logContent = formatter.format(level, jp, t);
		
		switch(level){
		case OFF : /* do nothing */ break;
		case TRACE : logger.trace(logContent, t); break;
		case DEBUG : logger.debug(logContent, t); break;
		case INFO : logger.info(logContent, t); break;
		case WARN : logger.warn(logContent, t); break;
		case ERROR : logger.error(logContent, t); break;
		}
		
	}
	
	public Map<Class<? extends Throwable>, Level> getLevelMapping(Loggable loggable){
		Map<Class<? extends Throwable>, Level> result = new HashMap<Class<? extends Throwable>, Level>();
		result.put(Success.class, loggable.onSuccess());
		for(Class<? extends Throwable> each : loggable.errorOn()){
			result.put(each, Level.ERROR);
		}
		for(Class<? extends Throwable> each : loggable.warningOn()){
			result.put(each, Level.WARN);
		}
		for(Class<? extends Throwable> each : loggable.infoOn()){
			result.put(each, Level.INFO);
		}
		for(Class<? extends Throwable> each : loggable.debugOn()){
			result.put(each, Level.DEBUG);
		}
		for(Class<? extends Throwable> each : loggable.traceOn()){
			result.put(each, Level.TRACE);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Level decideLevel(Loggable loggable , Throwable t){
		Map<Class<? extends Throwable>, Level> levelByThrowable = getLevelMapping(loggable);
		Class<? extends Throwable> current = t.getClass();
		while(true) {
			Level match = levelByThrowable.get(current);
			if(match != null){
				return match;
			}
			if(Throwable.class.isAssignableFrom(current.getSuperclass())){
				current = (Class<? extends Throwable>)current.getSuperclass();
			}else{
				throw new RuntimeException("No level match for " + current);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Throwable> LogFormatter<T> decideFormatter(Throwable t){
		Class<? extends Throwable> current = t.getClass();
		while(true) {
			LogFormatter<?> match = formaterByThrowable.get(current);
			if(match != null){
				return (LogFormatter<T>)match;
			}
			if(Throwable.class.isAssignableFrom(current.getSuperclass())){
				current = (Class<? extends Throwable>)current.getSuperclass();
			}else{
				throw new RuntimeException("No LogFormater match for " + current);
			}
		}
	}

}
