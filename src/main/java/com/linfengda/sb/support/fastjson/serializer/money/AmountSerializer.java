package com.linfengda.sb.support.fastjson.serializer.money;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * @description: 金额数字类型序列化类
 * @author: linfengda
 * @date: 2020-08-22 18:51
 */
public class AmountSerializer implements ObjectSerializer {
    public static final AmountSerializer INSTANCE = new AmountSerializer();
    private static final DecimalFormat DF = new DecimalFormat("###.00");

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeString("");
            return;
        }

        if (object instanceof BigDecimal){
            BigDecimal bigDecimal = (BigDecimal)object;
            if (bigDecimal.compareTo(BigDecimal.ZERO) == 0){
                out.writeString("");
            }else {
                out.writeString(bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString());
            }
        }else if (object instanceof Double){
            Double d = (Double)object;
            if (d == 0){
                out.writeString("");
            }else {
                out.writeString(DF.format(d));
            }
        }
    }
}
