package com.github.seahuang.log.formatter;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.seahuang.log.Level;

public class ConstraintViolationExceptionLogFormatter extends LogFormatterSupport<ConstraintViolationException> {
	@Autowired
	@Qualifier("c.g.s.l.f.ThrowableLogFormatter")
	protected LogFormatter<Throwable> throwableLogFormatter;
	
	public String format(Level level, JoinPoint jp, ConstraintViolationException throwable) {
		StringBuilder result = new StringBuilder(throwableLogFormatter.format(level, jp, throwable, null));
		Set<ConstraintViolation<?>> violations = throwable.getConstraintViolations();
		for(ConstraintViolation<?> each : violations){
			Iterator<Path.Node> pathIterator = each.getPropertyPath().iterator();
			Path.Node leaf = pathIterator.next();
			while(pathIterator.hasNext()){
				leaf = pathIterator.next();
			}
			result.append("\n").append(leaf.getName())
			.append("=").append(each.getInvalidValue())
			.append("[").append(each.getMessage()).append("]");
		}
		return result.toString();
	}
}
