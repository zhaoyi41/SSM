package com.jt.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jt.common.annotation.RequiredCache;
import com.jt.common.annotation.RequiredClearCache;
import com.jt.common.annotation.RequiredLog;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import com.jt.sys.vo.SysUserDeptResult;

@Transactional(rollbackFor=Throwable.class)
@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		//1.参数有效性验证
		if(id==null||id<1)
		throw new IllegalArgumentException("id值不合法");
		//2.查询用户以及关联的部门信息
		SysUserDeptResult user=sysUserDao.findObjectById(id);
		if(user==null)
		throw new ServiceException("记录可能已经不存在");
		//3.查询用户对应的角色信息
		List<Integer> roleIds=
		sysUserRoleDao.findRoleIdsByUserId(id);
		//4.封装查询结果并返回
		Map<String,Object> map=new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	@RequiredLog(operation="保存用户信息")
	@Override
	public int saveObject(SysUser entity,
			Integer[] roleIds) {
		//1.参数合法性校验
		if(entity==null)
		throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
		throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
		throw new IllegalArgumentException("密码不能为空");
		if(entity.getDeptId()==null)
		throw new IllegalArgumentException("必须为用户指定部门");
		if(roleIds==null || roleIds.length==0)
		throw new ServiceException("至少要为用户分配角色");
		//其它验证.....
		//2.对密码进行加密
		//2.1 获取一个盐值(salt):借助UUID获得一个随机串
		String salt=UUID.randomUUID().toString();
		System.out.println("salt="+salt);
		//2.2 对密码进行加密(借助Shiro框架API):MD5加密(消息摘要加密算法)
		SimpleHash sh=new SimpleHash(//Shiro提供
				"MD5",//algorithmName (算法名称)
				 entity.getPassword(),//Source(原密码)
				 salt);//salt 盐值
		String hashPassword=sh.toHex();//将加密结果转换为16进制
		System.out.println("hashPassword="+hashPassword);
		//3.对盐值,新的密码进行封装
		entity.setSalt(salt);
		entity.setPassword(hashPassword);
		//4.保存用户自身信息
		System.out.println("deptId="+entity.getDeptId());
		int rows=sysUserDao.insertObject(entity);
		//5.保存用户和角色的关系数据
		sysUserRoleDao.insertObject(entity.getId(),
				roleIds);
		//6.返回操作结果
		return rows;
	}
	@RequiredLog(operation="更新用户信息")
	@RequiredClearCache
	@Override
	public int updateObject(SysUser entity,
			Integer[] roleIds) {
		//1.参数合法性校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(entity.getDeptId()==null)
			throw new IllegalArgumentException("必须为用户指定部门");
		if(roleIds==null || roleIds.length==0)
			throw new ServiceException("至少要为用户分配角色");
		//2.保存用户自身信息
		int rows=sysUserDao.updateObject(entity);
		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		//3.保存用户和角色的关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObject(entity.getId(),
				roleIds);
		//4.返回操作结果
		return rows;
	}
	/**
	 * 通过此注解,告诉系统,执行此方法需要进行授权操作.
	 * 
	 * 1)获取用户权限(登录用户):{"sys:user:update","sys:user:valid"}
	 * 2)获取注解内部定义的权限标识,例如"sys:user:valid"
	 * 3)判定用户权限中是否包含注解内部定义的权限标识
	 * 
	 * 系统访问此方法时会获取Subject对象,然后执行如下调用
	 * Subject.isPermitted("sys:user:valid")
	 * 通过此方法验证用户是否有权限访问.
	 * 
	 * @param id
	 * @param valid
	 * @param modifiedUser
	 * @return
	 */
	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//1.参数合法性校验
		if(id==null||id<1)
		throw new IllegalArgumentException("id值无效");
		if(valid==null||(valid!=0&&valid!=1))
		throw new IllegalArgumentException("用户状态值不合法");
		//2.执行状态更新操作
		int rows=sysUserDao.validById(id, valid, modifiedUser);
		if(rows==0)throw new ServiceException("记录可能已经不存在");
		//3.验证结果,并返回
		return rows;
	}
	@RequiredCache
	@RequiredLog(operation="query")
	@Override
	public PageObject<SysUserDeptResult> findPageObjects(
			String username, Integer pageCurrent) {
		System.out.println("user.findPageObjects");
		// 1.数据合法性验证
		if (pageCurrent == null || pageCurrent <= 0)
		throw new IllegalArgumentException("参数不合法");
		// 2.依据条件获取总记录数
		int rowCount = sysUserDao.getRowCount(username);
		if (rowCount == 0)
		throw new ServiceException("记录不存在");
		// 3.计算startIndex的值
		int pageSize = 3;
		int startIndex = (pageCurrent - 1) * pageSize;
		// 4.依据条件获取当前页数据
		List<SysUserDeptResult> records = sysUserDao.findPageObjects(username, startIndex, pageSize);
		// 5.封装数据
		PageObject<SysUserDeptResult> pageObject = new PageObject<>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);
		return pageObject;

	}

}
