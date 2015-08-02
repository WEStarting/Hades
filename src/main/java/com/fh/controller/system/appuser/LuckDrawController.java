package com.fh.controller.system.appuser;

import java.util.List;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.ResultVo;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.appuser.AppuserService;
import com.fh.service.system.appuser.LuckyDrawService;
import com.fh.util.Const;
import com.fh.util.PageData;

@Controller
@RequestMapping(value = "/user")
public class LuckDrawController extends BaseController {

	@Resource(name = "luckyDrawService")
	private LuckyDrawService luckyDrawService;
	@Resource(name = "appuserService")
	private AppuserService appuserService;

	@RequestMapping("/shark")
	@ResponseBody
	public ResultVo shark() throws Exception {
		try {
			List<PageData> list = appuserService.listAllUserId();
			luckyDrawService.start(list);
		} catch (Exception e) {
			return new ResultVo(1, "摇奖失败");
		}
		return new ResultVo(0);
	}
	
	@RequestMapping(value = "/cj")
	public ModelAndView listAllLuckydraw(Page page) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			String USERNAME = pd.getString("USERNAME");

			if (null != USERNAME && !"".equals(USERNAME)) {
				USERNAME = USERNAME.trim();
				pd.put("USERNAME", USERNAME);
			}

			page.setPd(pd);
			List<PageData> userList = luckyDrawService.listAllLuckydraw(page); // 列出用户列表

			mv.setViewName("system/appuser/luckydraw_list");
			mv.addObject("userList", userList);
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

		return mv;
	}

}
