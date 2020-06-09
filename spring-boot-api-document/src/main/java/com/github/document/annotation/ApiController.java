package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller 说明
 * 
 * @ClassName: ParamApiController
 * @Description: TODO(描述 Controller)
 * @author DDM
 * @date 2019年11月22日 上午10:29:08
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // 限制只能用于类上
public @interface ApiController {

	/**
	 * 类名称
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
	 * 版本,默认 V1.0
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String version() default "V1.0";

}
