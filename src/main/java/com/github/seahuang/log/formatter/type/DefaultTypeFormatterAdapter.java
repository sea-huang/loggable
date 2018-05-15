package com.github.seahuang.log.formatter.type;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import com.github.seahuang.log.LogIgnore;
import com.github.seahuang.log.LogLength;
import com.github.seahuang.log.Level;
import com.github.seahuang.log.LogFormat;

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
	
	
	public String format(AnnotatedElement source, Level level, Object value){
		if(source.isAnnotationPresent(LogIgnore.class)
			&& Arrays.asList(source.getAnnotation(LogIgnore.class).value()).contains(level)){
			return ignoreFormatter.format(level, value);
		}
		if(source.isAnnotationPresent(LogLength.class)
			&& Arrays.asList(source.getAnnotation(LogLength.class).value()).contains(level)){
			return lengthFormatter.format(level, value);
		}
		if(source.isAnnotationPresent(LogFormat.class)
			&& Arrays.asList(source.getAnnotation(LogFormat.class).onLevel()).contains(level)){
			Class<? extends TypeFormatter> formatter = AnnotationUtils.findAnnotation(source, LogFormat.class).value();
			try{
				return formatter.newInstance().format(level, value);
			}catch(Exception e){
				logger.warn("Fail to init Formatter " + formatter, e);
				throw new RuntimeException(e);
			}
		}
		return defaultFormatter.format(level, value);
	}
}
