package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 放在 @ApiParams中
 * 
 * @ClassName: ApiParam
 * @Description: TODO(描述字段属性及校验规则)
 * @author DDM
 * @date 2019年11月29日 上午9:09:17
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiParam {
	/**
	 * 字段
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String field();

	/**
	 * 是否必须，默认 否
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public boolean required() default false;

	/**
	 * 正则校验
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String reg() default "";

	/**
	 * 最小长度，数值为 最小值
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public int min() default -2147483648;

	/**
	 * 最大长度，数值为最大值
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public int max() default 2147483647;

	/**
	 * 示例说明
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String describe() default "";
}
