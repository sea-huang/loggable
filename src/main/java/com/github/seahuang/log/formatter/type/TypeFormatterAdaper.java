package com.github.seahuang.log.formatter.type;

import java.lang.annotation.Annotation;

import com.github.seahuang.log.Level;

public interface TypeFormatterAdaper {
	String format(Annotation[] annotations, Level level, Object value);
}
