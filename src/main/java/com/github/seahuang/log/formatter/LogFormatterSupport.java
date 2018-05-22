package com.github.seahuang.log.formatter;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import com.github.seahuang.log.Level;
import com.github.seahuang.log.duration.DurationFormatter;
import com.github.seahuang.log.formatter.type.TypeFormatterAdaper;

public abstract class LogFormatterSupport<T extends Throwable> implements LogFormatter<T> {
	@Autowired
	protected TypeFormatterAdaper typeFormatterAdapter;
	@Autowired
	protected DurationFormatter durationFormatter;
	
	@SuppressWarnings("unchecked")
	protected Class<T> targetType = (Class<T>)GenericTypeResolver
		.resolveTypeArgument(this.getClass(), LogFormatter.class);

	public Class<T> getTargetType() {
		return targetType;
	}
	
	public String format(Level level, JoinPoint jp, T throwable, Long milliseconds){
		if(milliseconds != null){
			return format(level, jp, throwable) + " in " + durationFormatter.format(milliseconds);
		}
		return format(level, jp, throwable);
	}
	
	protected abstract String format(Level level, JoinPoint jp, T throwable);
}