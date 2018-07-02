package com.github.seahuang.log.formatter.type;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.github.seahuang.log.Level;
import com.github.seahuang.log.LogScript;

@Order(Ordered.LOWEST_PRECEDENCE)
public class CompositeScriptTypeFormatter implements ScriptTypeFormatter {
	private Logger logger = LoggerFactory.getLogger(CompositeScriptTypeFormatter.class);
	@Autowired
	private List<ScriptTypeFormatter> underlyings;
	
	@Override
	public String format(Level level, Object source, LogScript logScript) {
		for(ScriptTypeFormatter each : underlyings){
			if(each.langMatch(logScript.lang())){
				return each.format(level, source, logScript);
			}
		}
		logger.warn("No ScriptTypeFormatter found for " + logScript.lang());
		return "";
	}

	@Override
	public boolean langMatch(String lang) {
		return true;
	}

}
