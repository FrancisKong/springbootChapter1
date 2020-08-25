package com.linfengda.sb.support.fastjson.serializer.date;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.linfengda.sb.support.util.TimeUtil;

import java.lang.reflect.Type;

/**
 * @description: 日期输出序列化类
 * @author: linfengda
 * @date: 2020-08-23 23:15
 */
public class DateFormatSerializer extends AbstractDateBaseSerializer implements ObjectSerializer {
    public static final DateFormatMdSerializer INSTANCE = new DateFormatMdSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type type, int features){
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeString("");
            return;
        }
        writeDate(object,out);
    }

    @Override
    public void doWrite(Long time, SerializeWriter out) {
        out.writeString(TimeUtil.format(time, "yyyy-MM-dd HH:mm:ss"));
    }
}
