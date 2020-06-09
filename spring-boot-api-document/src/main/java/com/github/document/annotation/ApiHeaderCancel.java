package com.github.document.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制只能用于方法上
 * 
 * @ClassName: ApiHeaderCancel
 * @Description: TODO(用于取消放在Controller上配置请求头描述)
 * @author DDM
 * @date 2019年11月22日 下午2:20:49
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiHeaderCancel {

}
