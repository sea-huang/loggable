package com.github.seahuang.log.formatter.type;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seahuang.log.Level;
import com.github.seahuang.log.LogFormat;
import com.github.seahuang.log.LogIgnore;
import com.github.seahuang.log.LogLength;
import com.github.seahuang.log.LogScript;

public class DefaultTypeFormatterAdapter implements TypeFormatterAdaper {
	private Logger logger = LoggerFactory.getLogger(DefaultTypeFormatterAdapter.class);
	protected TypeFormatter lengthFormatter;
	protected TypeFormatter ignoreFormatter;
	protected TypeFormatter defaultFormatter;
	protected ScriptTypeFormatter scriptTypeFormatter;
	
	public DefaultTypeFormatterAdapter(
			  TypeFormatter lengthFormatter
			, TypeFormatter ignoreFormatter
			, TypeFormatter defaultFormatter
			, ScriptTypeFormatter scriptTypeFormatter){
		this.lengthFormatter = lengthFormatter;
		this.ignoreFormatter = ignoreFormatter;
		this.defaultFormatter = defaultFormatter;
		this.scriptTypeFormatter = scriptTypeFormatter;
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
		LogScript logScript = getAnnotation(annotaions, LogScript.class);
		if(logScript != null && Arrays.asList(logScript.onLevel()).contains(level)){
			return scriptTypeFormatter.format(level, value, logScript);
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
