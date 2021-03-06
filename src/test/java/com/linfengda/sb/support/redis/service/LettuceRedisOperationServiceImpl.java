package com.linfengda.sb.support.redis.service;

import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
import com.linfengda.sb.support.redis.lettuce.LettuceTemplate;
import com.linfengda.sb.support.redis.helper.LettuceTemplateHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-02-19 23:31
 */
@Slf4j
public class LettuceRedisOperationServiceImpl implements RedisOperationService {
    private static LettuceTemplate<String, Object> lettuceTemplate = LettuceTemplateHelper.getTemplate();

    @Override
    public void stringSetOperation() throws Exception {
        lettuceTemplate.set("key:" + Thread.currentThread().getId(), "value");
    }

    @Override
    public void stringSetGetOperation() throws Exception {
        lettuceTemplate.set("key:" + Thread.currentThread().getId(), "value");
        lettuceTemplate.get("key:" + Thread.currentThread().getId());
    }

    @Override
    public void simpleListOperation() throws Exception {
        lettuceTemplate.set("key", "i am the boss");
        String str = (String) lettuceTemplate.get("key");
        log.info(str);
        Long row = lettuceTemplate.del("key");
        log.info("delete affect row: {}", row);


        UserListVO userListVO = new UserListVO();
        userListVO.setUserId(1);
        userListVO.setUserName("流浪地球");
        row = lettuceTemplate.leftPush("myList", userListVO);
        log.info("row length after leftPush: {}", row);
        userListVO = (UserListVO) lettuceTemplate.rightPop("myList");
        log.info("rightPop object: ", JSONObject.toJSONString(userListVO));
    }

    public static void main(String[] args) {
        try {
            LettuceRedisOperationServiceImpl testService = new LettuceRedisOperationServiceImpl();
            testService.simpleListOperation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
