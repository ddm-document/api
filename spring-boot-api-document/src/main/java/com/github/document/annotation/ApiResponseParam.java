package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 只能用在 @ApiResponses 注解中
 * 
 * @ClassName: ApiResponseParam
 * @Description: TODO(描述返回值字段)
 * @author DDM
 * @date 2019年11月29日 上午9:29:48
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiResponseParam {
	/**
	 * 字段，多层请用点， 如 a.b
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
	 * 示例说明
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String describe() default "";
}
