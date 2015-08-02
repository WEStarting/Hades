package com.fh.controller.weixin;

import java.util.Date;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.ResultVo;
import com.fh.controller.base.BaseController;
import com.fh.entity.system.FhmSwordsman;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.swordsman.SwordsManService;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/app")
public class AppController extends BaseController {

	@Resource(name = "appuserService")
	private AppuserService appuserService;
	@Resource(name = "swordsManService")
	private SwordsManService swordsManService;

	@RequestMapping("/jbx_add")
	@ResponseBody
	public ResultVo jbx(String name, String type, HttpServletRequest httpServletRequest) {
		try {
			String ip = this.getRemortIP(httpServletRequest);
			String userId = (String) httpServletRequest.getSession().getAttribute("userId");
			String id = swordsManService.save(name, type,ip,userId);
			PageData pgq = new PageData();
			pgq.put("USER_ID", userId);
			PageData pg = appuserService.findByUId(pgq);
			if(pg != null) {
				PageData pd = new PageData();
				pd.put("SWORDSMAN_ID", id);
				pd.put("USER_ID", userId);
				appuserService.editByUID(pd);
			}
			httpServletRequest.getSession().setAttribute("dxId", id);
			return new ResultVo(0);
		} catch (Exception e) {
			return new ResultVo(1, "作品失败");
		}

	}
	
	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public String getRemortIP(HttpServletRequest request) { 
		  if (request.getHeader("x-forwarded-for") == null) { 
		   return request.getRemoteAddr(); 
		  } 
		  return request.getHeader("x-forwarded-for"); 
	} 

	@RequestMapping("/swordsMan")
	public ModelAndView swordsMan() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("swordsMan");
		return mv;
	}

	@RequestMapping("/user_add")
	@ResponseBody
	public synchronized ResultVo user(String name, String mobile, String address, HttpServletRequest httpServletRequest) {
		try {
			PageData pd1 = new PageData();
			pd1.put("PHONE", mobile); // ID
			PageData mobile1 = appuserService.findByMobile(pd1);

			PageData pd = new PageData();
			String userId = this.get32UUID();
			pd.put("USER_ID", userId); // ID
			pd.put("USERNAME", name); // ID
			pd.put("PHONE", mobile); // ID
			pd.put("Address", address); // ID
			pd.put("LAST_LOGIN", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			pd.put("IP", this.getRemortIP(httpServletRequest));
			String dxId = (String) httpServletRequest.getSession().getAttribute("dxId");
			if (!StringUtils.isEmpty(dxId))
				pd.put("swordsman_id", dxId); // ID
			appuserService.saveU(pd);
			httpServletRequest.getSession().setAttribute("userId", userId);
			return new ResultVo(0);
		} catch (Exception e) {
			return new ResultVo(1, "注册失败");
		}
	}

	@RequestMapping("/user_ishas")
	@ResponseBody
	public ResultVo user_ishas(String name, String mobile, String address, HttpServletRequest httpServletRequest) {
		try {
			PageData pd1 = new PageData();
			pd1.put("PHONE", mobile); // ID
			PageData mobile1 = appuserService.findByMobile(pd1);
			if (null != mobile1) {
				return new ResultVo(0, "已经注册了");
			} else {
				return new ResultVo(2, "未注册");
			}
		} catch (Exception e) {
			return new ResultVo(1, "注册失败");
		}
	}

	@RequestMapping("/appuser")
	public ModelAndView appuser() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("appuser");
		return mv;
	}

}
