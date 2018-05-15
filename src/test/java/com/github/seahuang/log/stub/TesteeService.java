package com.github.seahuang.log.stub;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.github.seahuang.log.Level;
import com.github.seahuang.log.LogFormat;
import com.github.seahuang.log.LogIgnore;
import com.github.seahuang.log.LogLength;
import com.github.seahuang.log.Loggable;
import com.github.seahuang.log.formatter.type.TypeFormatter;

@Service
@Validated
public class TesteeService {
	
	@Loggable("简单调用")
	public String simpleCall(String stringArg, Integer intArg){
		return "result";
	}
	
	@Loggable("无返回参数")
	public void returnVoid(String stringArg, Integer intArg){
		
	}
	
	@Loggable("测试异常")
	public String throwException(String stringArg, Integer intArg){
		throw new RuntimeException("故意报个错");
	}
	
	@Loggable("定制日志")
	public @LogLength List<String> customizeLog(@LogIgnore String one
			, @LogFormat(ExceptionTypeFormatter.class) Exception t){
		List<String> result = new ArrayList<String>();
		result.add("one");
		result.add("two");
		return result;
	}
	
	@Loggable("校验参数")
	public @NotNull List<String> validateParameters(@NotEmpty String one, @NotNull Integer two){
		return null;
	}
	
	
	public static class ExceptionTypeFormatter implements TypeFormatter {
		public String format(Level level, Object source) {
			if(source == null){
				return "null";
			}
			Exception realType = (Exception) source;
			return realType.getMessage();
		}
		
	}
}
