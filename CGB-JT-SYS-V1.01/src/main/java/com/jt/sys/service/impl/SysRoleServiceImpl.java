package com.jt.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysRoleDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
	private SysRoleDao sysRoleDao;
    
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    
    
    @Override
    public List<CheckBox> findObjects() {
    	return sysRoleDao.findObjects();
    }
    
    @Override
    public Map<String, Object> findObjectById(
    		Integer id) {
    	//1.参数合法性校验
    	if(id==null||id<1)
    	throw new IllegalArgumentException("id值无效");
    	//2.基于id查询角色自身信息
    	SysRole sysRole=sysRoleDao.findObjectById(id);
    	if(sysRole==null)
    	throw new ServiceException("此记录可能已经不存在");
    	//3.基于id查询角色对应的菜单id
    	List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleId(id);
    	//4.封装查询结果
    	Map<String,Object> map=new HashMap<>();
    	map.put("role", sysRole);
    	map.put("menuIds", menuIds);
    	return map;
    }
    
    
    @Override
    public int updateObject(SysRole entity,
    		Integer[] menuIds) {
    	//1.验证参数合法性
    	if(entity==null)
    	throw new IllegalArgumentException("保存对象不能为空");
    	if(entity.getId()==null||entity.getId()<1)
    	throw new IllegalArgumentException("更新记录的ID值不正确");
    	if(StringUtils.isEmpty(entity.getName()))
    	throw new IllegalArgumentException("角色名不能为空");
    	if(menuIds==null||menuIds.length==0)
    	throw new ServiceException("必须为角色分配权限");
    	//2.保存角色自身信息
    	int rows=sysRoleDao.updateObject(entity);
    	if(rows==0)
    	throw new ServiceException("记录可能已经不存在");
    	//3.删除角色和菜单的关系
    	sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
    	//4.保存角色和菜单新的的关系数据
    	sysRoleMenuDao.insertObject(entity.getId(),menuIds);
    	//4.返回结果
    	return rows;
    }
    @Override
    public int saveObject(SysRole entity,
    		Integer[] menuIds) {
    	//1.验证参数合法性
    	if(entity==null)
    	throw new IllegalArgumentException("保存对象不能为空");
    	if(StringUtils.isEmpty(entity.getName()))
    	throw new IllegalArgumentException("角色名不能为空");
    	if(menuIds==null||menuIds.length==0)
    	throw new ServiceException("必须为角色分配权限");
    	//2.保存角色自身信息
    	int rows=sysRoleDao.insertObject(entity);
    	//3.保存角色和菜单的关系数据
    	sysRoleMenuDao.insertObject(entity.getId(),
    			menuIds);
    	//4.返回结果
    	return rows;
    }
    
    @Override
    public int deleteObject(Integer id) {
    	//1.验证参数有效性
    	if(id==null||id<1)
    	throw new IllegalArgumentException("参数id值无效");
    	//2.删除角色自身信息
    	int rows=sysRoleDao.deleteObject(id);
    	if(rows==0)
    	throw new ServiceException("记录可能已经不存在");
    	//3.删除角色和菜单关系数据
    	sysRoleMenuDao.deleteObjectsByRoleId(id);
    	//4.删除用户和角色的关系数据
    	sysUserRoleDao.deleteObjectsByRoleId(id);
    	return rows;
    }
	
    @Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1.参数有效性验证
    	if(pageCurrent==null||pageCurrent<1)
    	throw new IllegalArgumentException("页码不正确");
		//2.查询总记录数并进行验证
    	int rowCount=sysRoleDao.getRowCount(name);
    	if(rowCount==0)
    	throw new ServiceException("记录不存在");
		//3.查询当前页记录
    	int pageSize=2;//页面大小
    	int startIndex=(pageCurrent-1)*pageSize;//起始位置
    	List<SysRole> records=
    	sysRoleDao.findPageObjects(name,
    			startIndex, pageSize);
		//4.封装结果并返回
    	PageObject<SysRole> pageObject=new PageObject<>();
    	pageObject.setRowCount(rowCount);
    	pageObject.setPageCurrent(pageCurrent);
    	pageObject.setPageSize(pageSize);
    	pageObject.setRecords(records);
    	//pageObject.setPageCount((rowCount-1)/pageSize+1);
		return pageObject;
	}

}










