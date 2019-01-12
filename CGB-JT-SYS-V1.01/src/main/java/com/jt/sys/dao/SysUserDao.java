package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysUser;
import com.jt.sys.vo.SysUserDeptResult;

public interface SysUserDao {
	/**
	 * 基于用户名获取用户信息对象
	 * @param username
	 * @return
	 */
	SysUser findUserByUserName(String username);
	
	/**
	 * 查询用户以及用户对应的部门信息
	 * @param id 为用户id
	 * @return 查询结果(值对象)
	 */
	SysUserDeptResult findObjectById(Integer id);
	
	/**
	 * 更新用户自身信息
	 * @param entity
	 * @return
	 */
	int updateObject(SysUser entity);
	
	/**
	 * 保存用户自身信息
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	
	/**
	 * 负责用户状态的禁用和启动操作
	 * @param id 用户id
	 * @param valid 用户状态
	 * @param modifiedUser 修改用户(系统的登录用户)
	 * @return 
	 */
	int validById(
			@Param("id")Integer id,
			@Param("valid")Integer valid,
			@Param("modifiedUser")String modifiedUser);

	/**
	 * 基于用户名查询记录总数
	 * @param username
	 * @return
	 */
	int getRowCount(@Param("username")String username);
	/**
	 * 分页查询用户以及对应的部门信息
	 * @param username
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysUserDeptResult> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	
}



