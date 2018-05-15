package com.github.seahuang.log.formatter.type;

import java.lang.reflect.AnnotatedElement;

import com.github.seahuang.log.Level;

public interface TypeFormatterAdaper {
	String format(AnnotatedElement source, Level level, Object value);
}
