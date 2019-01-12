package com.jt.sys.service;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysLog;

/**
 * 日志模块业务层接口
 * @author ta
 */
public interface SysLogService {

   /**
    * 基于id执行删除操作
    * @param ids
    * @return 删除的行数
    */
   int deleteObjects(Integer... ids);
   /**
    * 按照条件分页查询用户日志数据
    * @param username 查询条件(按用户名查询操作日志)
    * @param pageCurrent 当前页的页码
    * @return 当前页记录以及分页信息
    */
   PageObject<SysLog> findPageObjects(
		   String username,
		   Integer pageCurrent);
   
}




