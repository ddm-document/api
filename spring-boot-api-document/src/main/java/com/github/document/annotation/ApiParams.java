package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

/**
 * 建议所有参数使用 bean 包装，建议使用 JSON 交互(请求参数前加 @RequestBody),不支持 Map
 * 
 * @ClassName: ApiParams
 * @Description: TODO(使用在接口上，用于描述请求参数)
 * @author DDM
 * @date 2019年11月29日 上午8:50:58
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Mapping
public @interface ApiParams {
	/**
	 * 请求参数
	 * 
	 * @author DDM 2019年11月22日
	 * @return
	 */
	ApiParam[] value();
}
