package com.github.seahuang.log.stub;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.seahuang.log.TestApplication;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes = TestApplication.class) 
public class TesteeServiceTest {
	@Autowired
	private TesteeService testeeService;
	
	@Test
	public void testSimpleCall(){
		testeeService.simpleCall("AA", 10);
	}
	
	@Test
	public void testReturnVoid(){
		testeeService.returnVoid("AA", 10);
	}
	
	@Test
	public void testThrowException(){
		testeeService.throwException("AA", 10);
	}
	
	@Test
	public void testCustomizeLog(){
		testeeService.customizeLog("AA", new RuntimeException("RE"));
	}
	
	@Test
	public void validateParameters(){
		testeeService.validateParameters("", null);
	}
	
	@Test
	public void validateReturn(){
		testeeService.validateParameters("AA", 1);
	}
}
