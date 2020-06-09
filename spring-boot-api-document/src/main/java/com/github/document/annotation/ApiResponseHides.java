package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: ApiResponse
 * @Description: TODO(用于隐藏返回值字段)
 * @author DDM
 * @date 2019年11月22日 上午10:52:19
 *
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiResponseHides {
	/**
	 * 需要隐藏的字段
	 * 
	 * @author DDM 2019年11月23日
	 * @return
	 */
	String[] value() default {};
}
