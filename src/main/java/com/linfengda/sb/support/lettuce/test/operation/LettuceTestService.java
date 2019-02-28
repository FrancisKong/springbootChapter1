package com.linfengda.sb.support.lettuce.test.operation;

import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.chapter1.module1.entity.vo.FilmPlacardInfo;
import com.linfengda.sb.support.lettuce.LettuceTemplate;
import com.linfengda.sb.support.lettuce.test.helper.LettuceTemplateHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-02-19 23:31
 */
@Slf4j
public class LettuceTestService implements TestService {
    private static LettuceTemplate<String, Object> lettuceTemplate = LettuceTemplateHelper.getTemplate();

    @Override
    public void simpleStringOperation(int count) throws Exception {
        long setTime = 0;
        long getTime = 0;
        long delTime = 0;
        for (int i = 0; i < count; i++) {
            long t0 = System.currentTimeMillis();
            lettuceTemplate.set("key", "value");
            long t1 = System.currentTimeMillis();
            lettuceTemplate.get("key");
            long t2 = System.currentTimeMillis();
            lettuceTemplate.del("key");
            long t3 = System.currentTimeMillis();
            setTime += t1-t0;
            getTime += t2-t1;
            delTime += t3-t2;
        }
        log.info("------------------------------------------------<string command> set operation average time={}ms", setTime/count);
        log.info("------------------------------------------------<string command> get operation average time={}ms", getTime/count);
        log.info("------------------------------------------------<string command> del operation average time={}ms", delTime/count);
    }

    @Override
    public void StringSetOperation() throws Exception {
        lettuceTemplate.set("key:" + Thread.currentThread().getId(), "value");
        lettuceTemplate.get("key:" + Thread.currentThread().getId());
    }

    @Override
    public void ListSetOperation() throws Exception {
        lettuceTemplate.set("key", "i am the boss");
        String str = (String) lettuceTemplate.get("key");
        log.info(str);
        Long row = lettuceTemplate.del("key");
        log.info("delete affect row: {}", row);


        FilmPlacardInfo filmPlacardInfo = new FilmPlacardInfo();
        filmPlacardInfo.setFilmId(1);
        filmPlacardInfo.setTitle("流浪地球");
        filmPlacardInfo.setDescription("这是一部吴京导演的科幻片");
        filmPlacardInfo.setReleaseYear("2019");
        filmPlacardInfo.setLanguageName("chinese");
        row = lettuceTemplate.leftPush("myList", filmPlacardInfo);
        log.info("leftPush affect row: {}" + row);
        filmPlacardInfo = (FilmPlacardInfo) lettuceTemplate.rightPop("myList");
        log.info("rightPop object: ", JSONObject.toJSONString(filmPlacardInfo));
    }
}
