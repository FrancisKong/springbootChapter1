package com.linfengda.sb.chapter1.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linfengda.sb.chapter1.system.dao.SystemDao;
import com.linfengda.sb.chapter1.system.entity.dto.UserPageQueryDTO;
import com.linfengda.sb.chapter1.system.entity.dto.UserUpdateDTO;
import com.linfengda.sb.chapter1.system.entity.po.SysUserPO;
import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
import com.linfengda.sb.chapter1.system.entity.vo.UserVO;
import com.linfengda.sb.chapter1.system.service.SysUserService;
import com.linfengda.sb.support.orm.BaseService;
import com.linfengda.sb.support.orm.entity.SetValue;
import com.linfengda.sb.support.redis.cache.annotation.CacheKey;
import com.linfengda.sb.support.redis.cache.annotation.QueryCache;
import com.linfengda.sb.support.redis.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 系统服务
 *
 * @author linfengda
 * @create 2019-07-28 15:08
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseService implements SysUserService {
    @Resource
    private SystemDao systemDao;


    @Override
    public Page<UserListVO> pageUserList(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPageNo(), userPageQueryDTO.getPageSize());
        Page<UserListVO> page = (Page) systemDao.pageUserList(userPageQueryDTO.getStatus(), userPageQueryDTO.getUserName());
        return page;
    }

    @QueryCache(type = DataType.OBJECT, prefix = "sys:user", timeOut = 30, timeUnit = TimeUnit.MINUTES, strategies = {CacheExtraStrategy.PRV_CACHE_SNOW_SLIDE, CacheExtraStrategy.PRV_CACHE_HOT_KEY_MULTI_LOAD}, maxSize = 5, maxSizeStrategy = CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU)
    @Override
    public UserVO getUserInfo(@CacheKey Integer userId) throws Exception {
        SysUserPO sysUserPO = findByPrimaryKey(userId, SysUserPO.class);
        if (null == sysUserPO) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setUserId(sysUserPO.getId());
        userVO.setUserName(sysUserPO.getUserName());
        userVO.setPhone(sysUserPO.getPhone());
        userVO.setStatus(sysUserPO.getStatus());
        return userVO;
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(UserUpdateDTO userUpdateDTO) throws Exception {
        SetValue setValue = new SetValue();
        setValue.add("userName", userUpdateDTO.getUserName());
        updateByPrimaryKey(SysUserPO.class, setValue, userUpdateDTO.getUserId());
    }
}
