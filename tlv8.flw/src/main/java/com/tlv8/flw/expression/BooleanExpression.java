package com.tlv8.flw.expression;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlv8.base.CodeUtils;
import com.tlv8.base.Sys;
import com.tlv8.flw.bean.ExpressionBean;
import com.tlv8.flw.helper.ExpressionTreeHelper;

public class BooleanExpression {
	static final Logger logger = LoggerFactory.getLogger(BooleanExpression.class);

	protected static ScriptEngine engine = null;

	static {
		ScriptEngineManager engineManager = new ScriptEngineManager();
		try {
			engine = engineManager.getEngineByName("JavaScript"); // 得到脚本引擎
		} catch (Exception e) {
		}
		if (engine == null) {
			try {
				engineManager.registerEngineName("customScriptEngineFactory", (ScriptEngineFactory) Class
						.forName("org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory").newInstance());
				engine = engineManager.getEngineByName("JavaScript");
			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 表达式值:true或false
	 */
	public static boolean verdict(String express) {
		try {
			express = express.toLowerCase().replace(" or ", " || ");
			express = express.toLowerCase().replace(" and ", " && ");
			String result = String.valueOf(engine.eval(express));
			if ("true".equals(result)) {
				return true;
			}
			if ("false".equals(result)) {
				return false;
			}
			if (Float.valueOf(result) > 0) {
				return true;
			}
		} catch (ScriptException e) {
			logger.info(e.toString());
		}
		return false;
	}

	/**
	 * 执行JS表达式
	 */
	public static String getScriptExpressionVal(String expression) {
		try {
			expression = expression.replace("\n", "");
			return String.valueOf(engine.eval(expression));
		} catch (ScriptException e) {
			logger.info(e.toString());
		}
		return expression;
	}

	/**
	 * 执行JS表达式
	 * 
	 * @param resolutionExpression
	 * @return
	 */
	public static String getScriptExpressionVal(Object resolutionExpression) {
		try {
			resolutionExpression = resolutionExpression.toString().replace("\n", "");
			return String.valueOf(engine.eval(resolutionExpression.toString()));
		} catch (ScriptException e) {
			logger.info(e.toString());
		}
		return (String) resolutionExpression;
	}

	/**
	 * 解析表达式
	 */
	public static Object resolutionExpression(String expression, HttpServletRequest request) {
		Object result = CodeUtils.decodeSpechars(expression);
		List<String> funcIDs = ExpressionResolution.getExpressId(result.toString(), request);
		for (int i = funcIDs.size() - 1; i >= 0; i--) {
			String ExpressId = funcIDs.get(i);
			ExpressionBean express = new ExpressionTreeHelper().getExpression(ExpressId, request);
			if (express != null) {
				Object re = Sys.invoke(express.getJavacode(),
						(Object[]) ExpressionResolution.getExpreesionParam(result.toString(), ExpressId, request))
						.toString();
				String func = ExpressionResolution.getFuncExpress(result.toString(), ExpressId);
				re = re.toString().replace("(", "&rlt;");
				re = re.toString().replace(")", "&rgt;");
				re = re.toString().replace("\"", "&amp;");
				result = result.toString().replace(func, "\"" + re + "\"");
			}
		}
		result = result.toString().replace("&rlt;", "(");
		result = result.toString().replace("&rgt;", ")");
		result = result.toString().replace("&amp;", "\"");
		return result;
	}

}
