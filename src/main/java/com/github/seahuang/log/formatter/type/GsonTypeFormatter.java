package com.github.seahuang.log.formatter.type;

import com.github.seahuang.log.Level;
import com.google.gson.Gson;

public class GsonTypeFormatter implements TypeFormatter {
	protected Gson gson = new Gson();
	
	public String format(Level level, Object source) {
		return gson.toJson(source);
	}
}
