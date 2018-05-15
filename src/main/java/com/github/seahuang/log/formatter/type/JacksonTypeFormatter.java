package com.github.seahuang.log.formatter.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.seahuang.log.Level;

public class JacksonTypeFormatter implements TypeFormatter {
	private Logger logger = LoggerFactory.getLogger(JacksonTypeFormatter.class);
	private ObjectMapper mapper = new ObjectMapper();
	
	public String format(Level level, Object source) {
		try {
			return mapper.writeValueAsString(source);
		} catch (Exception e) {
			logger.warn("Error writing json string", e);
			return "";
		}
	}
}
