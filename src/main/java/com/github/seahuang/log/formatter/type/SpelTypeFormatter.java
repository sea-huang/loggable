package com.github.seahuang.log.formatter.type;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.github.seahuang.log.Level;
import com.github.seahuang.log.LogScript;

@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class SpelTypeFormatter implements ScriptTypeFormatter{
	public static final String LANG = "spel";
	private ExpressionParser expressionParser = new SpelExpressionParser();
	private final Map<String, Expression> expressionCache = new HashMap<String, Expression>(256);
	
	public String format(Level level, Object source, LogScript logScript) {
		Expression expr = this.expressionCache.get(logScript.value());
		if (expr == null) {
			expr = this.expressionParser.parseExpression(logScript.value());
			this.expressionCache.put(logScript.value(), expr);
		}
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable(logScript.alias(), source);
		return expr.getValue(context, String.class);
	}

	@Override
	public boolean langMatch(String lang) {
		return LANG.equalsIgnoreCase(lang);
	}
}
