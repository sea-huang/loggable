package com.github.seahuang.log;
/**
 * 代表<b>执行成功</b>的特殊异常类型，附带返回结果。
 * A special Throwable represent Success with the result returning.
 */
public class Success extends Throwable {
	private static final long serialVersionUID = 1L;
	//成功执行的返回结果
	private Object result;
	
	public Success(Object result){
		this.result = result;
	}

	public Object getResult() {
		return result;
	}
	
}
