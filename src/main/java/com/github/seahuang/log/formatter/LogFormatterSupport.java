package com.github.seahuang.log.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import com.github.seahuang.log.formatter.type.TypeFormatterAdaper;

public abstract class LogFormatterSupport<T extends Throwable> implements LogFormatter<T> {
	@Autowired
	protected TypeFormatterAdaper typeFormatterAdapter;
	
	@SuppressWarnings("unchecked")
	protected Class<T> targetType = (Class<T>)GenericTypeResolver
		.resolveTypeArgument(this.getClass(), LogFormatter.class);

	public Class<T> getTargetType() {
		return targetType;
	}
	
}