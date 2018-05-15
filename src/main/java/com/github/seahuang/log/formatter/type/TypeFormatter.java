package com.github.seahuang.log.formatter.type;

import com.github.seahuang.log.Level;

public interface TypeFormatter {
	String format(Level level, Object source);
}
