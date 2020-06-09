package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
 * @ClassName: ApiResponse
 * @Description: TODO(接口返回字段描述)
 * @author DDM
 * @date 2019年11月22日 上午10:52:19
 *
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiResponses {
	/**
	 * 返回值列表
	 * 
	 * @author DDM 2019年11月23日
	 * @return
	 */
	ApiResponseParam[] value() default {};
}
