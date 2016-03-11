package cn.weiyunmei.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用JSONTools转换JSON的时候，有时候需要将long类型转换为一个可读的文本格式，此刻我们需要在
 * 该属性的get方法上标注此注解，便可自动转换。
 * 需要注意的是，此处默认转换的日期格式为：2015-01-22 15:28:27
 * 如果需要修改默认格式，可指定DateFormat的value属性，指定一个格式化模版
 * 格式化模版可参见DateTools
 * 
 * @author 王鹏
 * @Date 2015年1月22日
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
public @interface DateFormat {
	public String value() default DateUtils.formatStr_yyyyMMddHHmmss;
}
