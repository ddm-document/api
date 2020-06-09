package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API请求头说明，限制只能用于Controller和方法上
 * 
 * @ClassName: ParamApiController
 * @Description: TODO(描述请求头，用于 Controller 上则对 整个 Controller 下的 接口加入 请求头描述)
 * @author DDM
 * @date 2019年11月22日 上午10:29:08
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ApiHeader {

	/**
	 * 参数名，如果为空，不处理
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public ApiHeadParam[] value() default {};

}
