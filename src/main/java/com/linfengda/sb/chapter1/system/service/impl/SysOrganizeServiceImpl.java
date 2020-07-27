package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.common.cache.bo.SysDepartmentBO;
import com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO;
import com.linfengda.sb.chapter1.system.service.SysOrganizeCacheService;
import com.linfengda.sb.chapter1.system.service.SysOrganizeService;
import com.linfengda.sb.support.orm.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述: 系统组织关系服务
 *
 * @author linfengda
 * @create 2020-01-14 14:10
 */
@Service
@Slf4j
public class SysOrganizeServiceImpl extends BaseService implements SysOrganizeService {
    @Resource
    private SysOrganizeCacheService sysOrganizeCacheService;

    @Override
    public SysDepartmentPO queryDepartment(Integer departmentId, Integer status, Integer isDelete) throws Exception {
        SysDepartmentBO sysDepartmentBO = sysOrganizeCacheService.queryDepartment(departmentId);
        if (null == sysDepartmentBO) {
            return null;
        }
        if (null != status && !status.equals(sysDepartmentBO.getStatus())) {
            return null;
        }
        if (null != isDelete && !isDelete.equals(sysDepartmentBO.getIsDelete())) {
            return null;
        }
        SysDepartmentPO sysDepartmentPO = new SysDepartmentPO();
        BeanUtils.copyProperties(sysDepartmentBO, sysDepartmentPO);
        return sysDepartmentPO;
    }

    @Override
    public void delDepartment(Integer departmentId) throws Exception {
        sysOrganizeCacheService.delDepartment(departmentId);
    }
}
