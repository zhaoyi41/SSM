package com.jt.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;

@Controller
@RequestMapping("/user/")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;
	@RequestMapping("doUserListUI")
	public String doUserListUI(){
		return "sys/user_list";
	}
	
	@RequestMapping("doUserEditUI")
	public String doUserEditUI(){
		return "sys/user_edit";
	}
	
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username,
			String password){
		//1.封装用户信息(存储到一个令牌对象)
		UsernamePasswordToken token=
		new UsernamePasswordToken(username, password);
		//2.获取Subject对象(通过此对象提交令牌)
		Subject subject=SecurityUtils.getSubject();
		//3.执行用户身份认证(将令牌交给Shiro中的SecurityManager)
		subject.login(token);//此方法运行时可能会出出现异常
		return new JsonResult("login ok");
	}
	
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id){//id
		return new JsonResult(sysUserService.findObjectById(id));
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(
			SysUser entity,//参数值注入时会调用对象set方法
			Integer[] roleIds){
		sysUserService.updateObject(entity,roleIds);
		return new JsonResult("update ok");
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(
			SysUser entity,//参数值注入时会调用对象set方法
			Integer[] roleIds){
		sysUserService.saveObject(entity,roleIds);
		return new JsonResult("save ok");
	}
	
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer id,Integer valid){
		SysUser user=(SysUser)SecurityUtils.
				getSubject().getPrincipal();//session
		//sysUserService.validById(id, valid,"admin");
		sysUserService.validById(id, valid,user.getUsername());
		return new JsonResult("update ok");
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
		  String username,Integer pageCurrent){
	      return new JsonResult(
	      sysUserService.findPageObjects(username,
	    		  pageCurrent));
	}
}





