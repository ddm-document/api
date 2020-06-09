package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: ApiField
 * @Description: TODO(用于描述请求参数和返回数据的字段，不支持校验)
 * @author DDM
 * @date 2019年11月29日 上午9:18:44
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // 限制只能用于字段上
public @interface ApiField {
	/**
	 * 名称
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public String value();

	/**
	 * 是否隐藏
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	public boolean hide() default false;

}
