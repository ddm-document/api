package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 限制只能用于方法上
 * 
 * @ClassName: ParamApiController
 * @Description: TODO(API 接口说明、以及设置不需要扫描请求参数)
 * @author DDM
 * @date 2019年11月22日 上午10:29:08
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiMethod {

	/**
	 * 接口名称
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String value();

	/**
	 * 描述说明
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String describe() default "";

	/**
	 * 作者
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String author() default "";

	/**
	 * 版本，默认V1.0
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String version() default "V1.0";

	/**
	 * 设置不能要扫描的请求参数
	 * 
	 * @author DDM 2019年11月26日
	 * @return
	 */
	public String[] hides() default {};
}
