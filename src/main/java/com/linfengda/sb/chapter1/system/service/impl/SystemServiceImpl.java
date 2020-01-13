package com.linfengda.sb.chapter1.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linfengda.sb.chapter1.system.dao.SystemDao;
import com.linfengda.sb.chapter1.system.entity.dto.UserPageQueryDTO;
import com.linfengda.sb.chapter1.system.entity.po.SysUserPO;
import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
import com.linfengda.sb.chapter1.system.entity.vo.UserVO;
import com.linfengda.sb.chapter1.system.service.SystemService;
import com.linfengda.sb.support.dao.BaseService;
import com.linfengda.sb.support.dao.entity.SetValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 描述: 系统服务
 *
 * @author linfengda
 * @create 2019-07-28 15:08
 */
@Service
@Slf4j
public class SystemServiceImpl extends BaseService implements SystemService {
    @Resource
    private SystemDao systemDao;


    @Override
    public Page<UserListVO> pageUserList(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPageNo(), userPageQueryDTO.getPageSize());
        Page<UserListVO> page = (Page) systemDao.pageUserList(userPageQueryDTO.getStatus(), userPageQueryDTO.getUserName());
        return page;
    }

    @Override
    public UserVO getUserInfo(Integer userId) throws Exception {
        SysUserPO sysUserPO = findByPrimaryKey(userId, SysUserPO.class);
        UserVO userVO = new UserVO();
        userVO.setUserId(sysUserPO.getUserId());
        userVO.setUserName(sysUserPO.getUserName());
        userVO.setPhone(sysUserPO.getPhone());
        userVO.setPassword(sysUserPO.getPassword());
        userVO.setStatus(sysUserPO.getStatus());
        return userVO;
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void updateUser(Integer userId, String userName) throws Exception {
        SetValue setValue = new SetValue();
        setValue.add("userName", userName);
        updateByPrimaryKey(SysUserPO.class, setValue, userId);
    }
}
