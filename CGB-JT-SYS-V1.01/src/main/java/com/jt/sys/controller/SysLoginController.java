package com.jt.sys.controller;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;

//@Controller
//@RequestMapping("/")
public class SysLoginController {
	
	/***
	 * 负责执行登录操作的控制层方法
	 * @param username
	 * @param password
	 * @return
	 */
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
}
