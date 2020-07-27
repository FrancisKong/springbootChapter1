package com.linfengda.sb.chapter1.common.cache.manager;

/**
 * @description: 缓存命名空间
 * @author: linfengda
 * @date: 2020-07-27 23:54
 */
public interface CachePrefix {
    /**
     * 用户token-userId缓存
     */
    String USER_TOKEN_CACHE = "sys:org:u:t";
    /**
     * 系统组织：部门
     */
    String SYS_ORG_PRODUCTION_TEAM_CACHE = "sys:org:pt";
    /**
     * 系统组织：项目
     */
    String SYS_ORG_TEAM_CACHE = "sys:org:tm";
    /**
     * 系统组织：人员
     */
    String SYS_ORG_USER_CACHE = "sys:org:usr";
    /**
     * 部门集合
     */
    String SYS_ORG_PRODUCTION_TEAM_SET_CACHE = "sys:org:pt:set";
    /**
     * 项目集合
     */
    String SYS_ORG_TEAM_SET_CACHE = "sys:org:tm:set";
    /**
     * 人员集合
     */
    String SYS_ORG_USER_SET_CACHE = "sys:org:usr:set";
}
