package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 此DAO对象主要用于操作sys_role_menus中间表(关系表)
 * @author ta
 */
public interface SysRoleMenuDao {//与SysRoleMenuMapper.xml文件绑定
	
	/**
	 * 基于角色id查询关系表中角色id对应菜单id
	 * @param roleId
	 * @return
	 */
	List<Integer> findMenuIdsByRoleId(@Param("roleIds") Integer... roleIds);
	
	/**
	 * 写入角色和菜单的关系数据(本质上是在为角色进行授权操作)
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertObject(@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);
	
	/**
	 * 基于菜单id删除角色与菜单的关系数据
	 * @param menuId
	 * @return
	 */
	int deleteObjectsByMenuId(Integer menuId);

	/**
	 * 基于角色id删除角色与菜单的关系数据
	 * @param roleId
	 * @return 删除的行数
	 */
	int deleteObjectsByRoleId(Integer roleId);
	
	
}
