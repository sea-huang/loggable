package com.github.seahuang.log.formatter.type;

import com.github.seahuang.log.Level;
import com.github.seahuang.log.LogScript;

public interface ScriptTypeFormatter {
	boolean langMatch(String lang);
	String format(Level level, Object source, LogScript logScript);
}
