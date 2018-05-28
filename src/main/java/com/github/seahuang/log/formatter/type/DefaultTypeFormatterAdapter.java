package com.github.seahuang.log.formatter.type;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seahuang.log.Level;
import com.github.seahuang.log.LogFormat;
import com.github.seahuang.log.LogIgnore;
import com.github.seahuang.log.LogLength;

public class DefaultTypeFormatterAdapter implements TypeFormatterAdaper {
	private Logger logger = LoggerFactory.getLogger(DefaultTypeFormatterAdapter.class);
	protected TypeFormatter lengthFormatter;
	protected TypeFormatter ignoreFormatter;
	protected TypeFormatter defaultFormatter;
	
	public DefaultTypeFormatterAdapter(
			  TypeFormatter lengthFormatter
			, TypeFormatter ignoreFormatter
			, TypeFormatter defaultFormatter){
		this.lengthFormatter = lengthFormatter;
		this.ignoreFormatter = ignoreFormatter;
		this.defaultFormatter = defaultFormatter;
	}
	
	
	public String format(Annotation[] annotaions, Level level, Object value){
		LogIgnore logIgnore = getAnnotation(annotaions, LogIgnore.class);
		if(logIgnore != null && Arrays.asList(logIgnore.value()).contains(level)){
			return ignoreFormatter.format(level, value);
		}
		LogLength logLength = getAnnotation(annotaions, LogLength.class);
		if(logLength != null && Arrays.asList(logLength.value()).contains(level)){
			return lengthFormatter.format(level, value);
		}
		LogFormat logformat= getAnnotation(annotaions, LogFormat.class);
		if(logformat != null && Arrays.asList(logformat.onLevel()).contains(level)){
			Class<? extends TypeFormatter> formatter = logformat.value();
			try{
				return formatter.newInstance().format(level, value);
			}catch(Exception e){
				logger.warn("Fail to init Formatter " + formatter, e);
				throw new RuntimeException(e);
			}
		}
		return defaultFormatter.format(level, value);
	}
	
	protected <T extends Annotation> T getAnnotation(Annotation[] annotaions,  Class<T> wanted){
		for(Annotation each : annotaions){
			if(wanted.isInstance(each)){
				return (T)each;
			}
		}
		return null;
	}
}
