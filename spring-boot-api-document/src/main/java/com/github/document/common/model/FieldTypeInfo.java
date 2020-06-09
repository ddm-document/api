package com.github.document.common.model;

import java.lang.reflect.Type;

public class FieldTypeInfo {
	private Type genType;

	private Class<?> clazz;
	private FieldType claType;

	private FieldType insideType;
	private Class<?> insideClazz;

	/**
	 * 指定泛型类型
	 * 
	 * @author DDM 2019年11月25日
	 * @return
	 */
	public Type getGenType() {
		return genType;
	}

	/**
	 * 指定泛型类型
	 * 
	 * @author DDM 2019年11月25日
	 * @return
	 */
	public void setGenType(Type genType) {
		this.genType = genType;
	}

	/**
	 * 本身类Class
	 * 
	 * @author DDM 2019年11月25日
	 * @return
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * 本身类Class
	 * 
	 * @author DDM 2019年11月25日
	 * @return
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 本身类型
	 * 
	 * @author DDM 2019年11月25日
	 * @return
	 */
	public FieldType getClaType() {
		return claType;
	}

	/**
	 * 本身类型
	 * 
	 * @author DDM 2019年11月25日
	 * @return
	 */
	public void setClaType(FieldType claType) {
		this.claType = claType;
	}

	/**
	 * 内部类Class
	 * 
	 * @author DDM 2019年11月25日
	 * @return
	 */
	public Class<?> getInsideClazz() {
		return insideClazz;
	}

	/**
	 * 内部类Class
	 * 
	 * @author DDM 2019年11月25日
	 * @return
	 */
	public void setInsideClazz(Class<?> insideClazz) {
		this.insideClazz = insideClazz;
	}

	/**
	 * 内部类型
	 * 
	 * @author DDM 2019年11月25日
	 * @return
	 */
	public FieldType getInsideType() {
		return insideType;
	}

	/**
	 * 内部类型
	 * 
	 * @author DDM 2019年11月25日
	 * @return
	 */
	public void setInsideType(FieldType insideType) {
		this.insideType = insideType;
	}
}
