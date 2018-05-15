package com.github.seahuang.log.formatter.type;

import com.github.seahuang.log.Level;

public class IgnoreTypeFormatter implements TypeFormatter {
	public String format(Level level, Object source) {
		return "N/A";
	}
}
