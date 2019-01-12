package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.vo.CheckBox;
import com.jt.sys.entity.SysRole;

public interface SysRoleDao {//SysRoleMapper.xml
	 /**
	  * 查询所有角色的id,name字段的值,
	  * 每行记录封装为一个checkbox对象
	  * @return
	  */
	  List<CheckBox> findObjects();
	  
	  /**
	   * 更新角色自身信息
	   * @param entity
	   * @return
	   */
	  int updateObject(SysRole entity);
	
	   /**
	    * 基于角色Id查询角色自身信息
	    * @param id
	    * @return
	    */
	   SysRole findObjectById(Integer id);
	
	   /**
	    * 向数据库写入角色信息
	    * @param entity
	    * @return
	    */
	   int insertObject(SysRole entity);
	    /***
	     * 基于角色id删除角色自身信息
	     * @param id 角色id
	     * @return 删除的行数
	     */
	    int deleteObject(Integer id);
	    
	    /**
	     * 分页查询角色信息
	     * @param startIndex 上一页的结束位置
	     * @param pageSize 每页要查询的记录数
	     * @return 当前页的角色信息
	     */
		List<SysRole> findPageObjects(
	            @Param("name")String name,
				@Param("startIndex")Integer startIndex,
				@Param("pageSize")Integer pageSize);
		/**
		 * 按照条件查询记录总数
		 * @return
		 */
		int getRowCount(@Param("name")String name);

}
