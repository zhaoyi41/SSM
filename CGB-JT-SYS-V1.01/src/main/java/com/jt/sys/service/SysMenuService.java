package com.jt.sys.service;

import java.util.List;
import java.util.Map;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;

public interface SysMenuService {
	/**
	 * 将菜单信息按照一定的业务进行更新
	 * @param entity
	 * @return
	 */
	int updateObject(SysMenu entity);
	/**
	 * 将菜单信息按照一定的业务进行保存
	 * @param entity
	 * @return
	 */
	int saveObject(SysMenu entity);
	 
	/**
	 * 查询菜单节点信息
	 * @return
	 */
	List<Node> findZtreeMenuNodes();
	/**
	 * 基于菜单id删除菜单元素
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	
	/**
	 * 通过数据层对象获取所有菜单信息
	 * @return
	 */
	List<Map<String,Object>> findObjects();
}
