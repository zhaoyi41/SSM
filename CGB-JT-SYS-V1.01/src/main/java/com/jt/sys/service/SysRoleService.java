package com.jt.sys.service;

import java.util.List;
import java.util.Map;

import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;

public interface SysRoleService {
	
	 /**
	  * 查询所有角色的id,name字段的值,
	  * 每行记录封装为一个checkbox对象
	  * @return
	  */
	 List<CheckBox> findObjects();
	
	/**
	 * 基于角色id查询角色自身信息以及关联的菜单id
	 * @param id
	 * @return 封装了查询结果的对象
	 */
	Map<String,Object> findObjectById(Integer id);
	
	
	/**
	 * 更新角色和角色与菜单的关系数据
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int updateObject(SysRole entity,Integer[]menuIds);
	
	 /**
	  * 保存角色和角色与菜单的关系数据
	  * @param entity
	  * @param menuIds
	  * @return
	  */
	 int saveObject(SysRole entity,Integer[]menuIds);
	 
	 /**
	  * 删除角色以及关系数据
	  * @param id 角色id
	  * @return 删除的行数
	  */
	 int deleteObject(Integer id);
	 
     /**
      * 分页查询角色信息
      * @param name 角色名称
      * @param pageCurrent 当前页的页码
      * @return 封装了当前页角色信息和分页信息的对象
      */
	 PageObject<SysRole> findPageObjects(
			 String name,
			 Integer pageCurrent);
}
