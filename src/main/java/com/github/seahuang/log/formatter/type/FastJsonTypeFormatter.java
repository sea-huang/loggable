package com.github.seahuang.log.formatter.type;

import com.alibaba.fastjson.JSON;
import com.github.seahuang.log.Level;

public class FastJsonTypeFormatter implements TypeFormatter {

	public String format(Level level, Object source) {
		return JSON.toJSONString(source);
	}
}
