package com.tlv8.flw.helper;

import javax.script.ScriptException;

import com.tlv8.flw.expression.BooleanExpression;

public class ScriptEval extends BooleanExpression {
	public static void main(String[] args) {
		System.out.println(getScriptExpressionVal("function a(){return true;}a();"));
		System.out.println(getScriptExpressionVal("33+2*2-5*8>88"));
		System.out.println(getScriptExpressionVal("'李四'=='张三'"));
		System.out.println(getScriptExpressionVal("'zs'.toUpperCase()=='ZS'"));
		System.out.println(getScriptExpressionVal("Math.round(28/3)"));
	}

	/*
	 * 执行JS表达式
	 */
	public static String getScriptExpressionVal(String expression) {
		try {
			return String.valueOf(engine.eval(expression));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return null;
	}
}
