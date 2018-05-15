package com.github.seahuang.log.formatter.type;

import com.github.seahuang.log.Level;

public class DefaultTypeFormatter implements TypeFormatter {

	public String format(Level level, Object source) {
		if(source == null){
			return "null";
		}
		return source.toString();
	}
}
