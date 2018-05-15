package com.github.seahuang.log.formatter.type;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.seahuang.log.Level;

public class LengthTypeFormatter implements TypeFormatter {
	private Logger logger = LoggerFactory.getLogger(LengthTypeFormatter.class);
	
	public String format(Level level, Object source){
		if(source == null){
			return "[0]";
		}
		if(source instanceof Collection){
			return "[" + ((Collection<?>)source).size() + "]";
		}
		if(source.getClass().isArray()){
			return "[" + ((Object[])source).length + "]";
		}
		logger.warn("Inappropriate type : " + source.getClass().getSimpleName() + " should be collection or array.");
		return "[not collection nor array]";
	}
	
}
