package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制只能用于类和方法上
 * 
 * @ClassName: ApiHeadParam
 * @Description: TODO(用于描述请求头参数，不支持校验)
 * @author DDM
 * @date 2019年11月29日 上午9:23:09
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ApiHeadParam {
	/**
	 * 字段名
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String value();

	/**
	 * 数据类型，默认 String
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String type() default "String";

	/**
	 * 是否必须，默认 是
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public boolean required() default true;

	/**
	 * 示例说明
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String describe() default "";
}
