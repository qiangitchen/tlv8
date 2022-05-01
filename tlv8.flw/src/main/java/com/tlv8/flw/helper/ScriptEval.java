package com.tlv8.flw.helper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEval {
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
		ScriptEngineManager engineManager = new ScriptEngineManager();
		ScriptEngine engine = engineManager.getEngineByName("JavaScript"); // 得到脚本引擎
		try {
			return String.valueOf(engine.eval(expression));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return null;
	}
}
