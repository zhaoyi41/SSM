package com.jt.sys.service;

import java.util.Map;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysUser;
import com.jt.sys.vo.SysUserDeptResult;

public interface SysUserService {
	/**
	 * 更新用户自身信息以及用户和角色的关系数据
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	int updateObject(SysUser entity,
			Integer[] roleIds);

	/**
	 * 基于用户id查询用户以及对应的部门,角色等信息
	 * @param id 用户id
	 * @return 封装了查询结果的一个map对象
	 */
	Map<String,Object> findObjectById(Integer id);
	
	
	/**
	 * 保存用户以及用户和角色的关系数据
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	int saveObject(SysUser entity, Integer[] roleIds);
	 /**
	  * 修改用户的状态信息(启用状态1,禁用状态0)
	  * @param id
	  * @param valid
	  * @param modifiedUser
	  * @return
	  */
	 int validById(Integer id,Integer valid,String modifiedUser);
	
	 /**
	  * 分页查询用户信息
	  * @param username
	  * @param pageCurrent
	  * @return
	  */
	 PageObject<SysUserDeptResult> findPageObjects(
			 String username,Integer pageCurrent);
	 
	 
}





