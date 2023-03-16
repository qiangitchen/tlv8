package com.tlv8.bean.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SuppressWarnings("rawtypes")
public class SpringBeanFactoryUtils implements ApplicationContextAware {

	private static ApplicationContext appCtx;

	/**
	 * TODO: 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appCtx = applicationContext;
	}

	/**
	 * TODO: 获取ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return appCtx;
	}

	/**
	 * TODO: 这是一个便利的方法，帮助我们快速得到一个BEAN
	 */
	public static Object getBean(String beanName) {
		return appCtx.getBean(beanName);
	}

	/**
	 * 获取类型为requiredType的对象 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
	 * 
	 * @param name         bean注册名
	 * @param requiredType 返回对象类型
	 * @return Object 返回requiredType类型对象
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static Object getBean(String name, Class requiredType) throws BeansException {
		return appCtx.getBean(name, requiredType);
	}

	@SuppressWarnings("unchecked")
	public static Object getBean(Class rclass) {
		return appCtx.getBean(rclass);
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return appCtx.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * 
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return appCtx.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class getType(String name) throws NoSuchBeanDefinitionException {
		return appCtx.getType(name);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return appCtx.getAliases(name);
	}
}