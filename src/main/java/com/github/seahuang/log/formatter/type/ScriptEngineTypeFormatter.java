package com.github.seahuang.log.formatter.type;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.github.seahuang.log.Level;
import com.github.seahuang.log.LogScript;

@Order(Ordered.LOWEST_PRECEDENCE - 10)
public class ScriptEngineTypeFormatter implements ScriptTypeFormatter {
	private Logger logger = LoggerFactory.getLogger(ScriptEngineTypeFormatter.class);
	private ScriptEngineManager manager = new ScriptEngineManager();
	
	public String format(Level level, Object source, LogScript logScript) {
		try{
			ScriptEngine scriptEngine = manager.getEngineByName(logScript.lang());
			if(scriptEngine == null){
				logger.warn("No ScriptEngine found by {}", logScript.lang());
				return "";
			}
			Bindings bindings = scriptEngine.createBindings();
			bindings.put(logScript.alias(), source);
			Object result = scriptEngine.eval(logScript.value(), bindings);
			return result == null ? "" : result.toString();
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
			return "";
		}
	}

	@Override
	public boolean langMatch(String lang) {
		for(ScriptEngineFactory each : manager.getEngineFactories()){
			if(each.getNames().contains(lang)){
				return true;
			}
		}
		return false;
	}
}
