package com.iwish.wxapi.ctrl;
 

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;  

import com.iwish.core.util.secty.SignUtil;
 

@Controller  
@RequestMapping("/wechat") 
public class WxApiCtrl {

	 
	
	@RequestMapping(value = "/hello.html")	
	public String hello(){ 
		System.out.println("11111111111");
		ApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext.xml");
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println("1wwww1111");
		
		return "welcome"; 
	}
	 
 
	/**
	 * GET请求：进行URL、Tocken 认证�?
	 * 1. 将token、timestamp、nonce三个参数进行字典序排�?
	 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. �?发�?�获得加密后的字符串可与signature对比，标识该请求来源于微�?
	 */
	@RequestMapping(value = "/message",  method = RequestMethod.GET)
	public @ResponseBody String doGet(HttpServletRequest request) {
		 System.out.println("2");
			String token = "iwish";//获取token，进行验证；
			String signature = request.getParameter("signature");// 微信加密签名
			String timestamp = request.getParameter("timestamp");// 时间�?
			String nonce = request.getParameter("nonce");// 随机�?
			String echostr = request.getParameter("echostr");// 随机字符�?
			
			// 校验成功返回  echostr，成功成为开发�?�；否则返回error，接入失�?
			if (SignUtil.validSign(signature, token, timestamp, nonce)) {
				return echostr;
			} 
		return "error";
	}
	
	 
	
}
