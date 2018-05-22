package com.github.seahuang.log.duration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StopWatch;

import com.github.seahuang.log.Loggable;

public class DefaultDurationRecorder implements DurationRecorder {
	protected Boolean defaultLogDuration = false;
	protected Boolean logDurationResult;
	protected StopWatch stopWatch = new StopWatch();
	
	public void start(JoinPoint jp){
		decideLogDuration(jp);
		if(!logDurationResult){
			return;
		}
		stopWatch.start();
	}
	
	public Long stop(){
		if(!logDurationResult){
			return null;
		}
		stopWatch.stop();
		return stopWatch.getTotalTimeMillis();
	}
	
	protected void decideLogDuration(JoinPoint jp){
		MethodSignature methodSignature = (MethodSignature)jp.getSignature();
		Loggable loggable = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Loggable.class);
		LogDuration logDuration = loggable.logDuration();
		switch(logDuration){
		case YES: logDurationResult = true; break;
		case NO: logDurationResult = false; break;
		case DEFAULT: logDurationResult = defaultLogDuration; break;
		}
	}
	
	public Boolean getDefaultLogDuration() {
		return defaultLogDuration;
	}
	public void setDefaultLogDuration(Boolean defaultLogDuration) {
		this.defaultLogDuration = defaultLogDuration;
	}
	
}
