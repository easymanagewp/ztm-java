package cn.wym.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * JSON工具类
 * @author 王鹏
 * @Date 2015年1月22日
 * @version 1.0.0
 */
public class JSONUtils {
	private static final Logger log = Logger.getLogger(JSONUtils.class);
	
	/**
	 * 将对象转换为JSON字符串
	 * @param obj 需要转换的对象
	 * @return JSON格式的字符串
	 */
	public static String toJSON(Object obj){
		return toJSON(obj, false, null);
	}
	
	public static String toJSON(Object obj,Boolean desc,String key){
		SerializeWriter out = new SerializeWriter();
        try {
            JSONSerializer serializer = new JSONSerializer(out);
            serializer.config(SerializerFeature.BrowserCompatible, true);
            serializer.config(SerializerFeature.WriteNullListAsEmpty, true);
            serializer.config(SerializerFeature.WriteNullNumberAsZero, true);
            serializer.config(SerializerFeature.WriteNullStringAsEmpty, true);
            serializer.config(SerializerFeature.WriteMapNullValue, true);
            serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            serializer.config(SerializerFeature.WriteNullBooleanAsFalse, true);
            serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
            
            serializer.setDateFormat(DateUtils.formatStr_yyyyMMddHHmmss);
             // 对标注DateFormat的long类型字段进行特殊处理转换
            serializer.getValueFilters().add(new ValueFilter(){

				public Object process(Object source, String name, Object value) {
					if(source instanceof Map){
						return value;
					}
					try {
						PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(source, name);
						if(null != pd){
							Method method = pd.getReadMethod();
							DateFormat df = method.getAnnotation(DateFormat.class);
							if(null != df && null != value && (value instanceof Long || value.getClass().equals(long.class))){
								long time = (Long) value;
								return DateUtils.format(new Date(time), df.value());
							}
						}
						return value;
					} catch (IllegalAccessException e) {
						log.error(e);
						return value;
					} catch (InvocationTargetException e) {
						log.error(e);
						return value;
					} catch (NoSuchMethodException e) {
						log.error(e);
						return value;
					}
				}
    		});
           // obj = JSON.toJSONString(obj,SerializerFeature.DisableCircularReferenceDetect);
            serializer.write(obj);
            return out.toString();
        } finally {
            out.close();
        }
	}
	
}
