package com.fh.service.system.swordsman;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.ResultVo;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.FhmSwordsman;
import com.fh.entity.system.Role;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@Service("swordsManService")
public class SwordsManService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 获取煎饼侠列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listPdPageSwordsMan(Page page) throws Exception {
		return (List<PageData>) dao.findForList("FhmSwordsmanMapper.swordslistPage", page);
	}

	public String save(String name, String type,String ip,String userId) throws Exception {
		FhmSwordsman fhmSwordsman = new FhmSwordsman();
		fhmSwordsman.setId(UuidUtil.get32UUID());
		fhmSwordsman.setName(name);
		fhmSwordsman.setType(type);
		fhmSwordsman.setIp(ip);
		fhmSwordsman.setUserId(userId);
		if(type.equals("煎饼侠") || type.equals("坚挺侠") || type.equals("馒头侠")) {
			fhmSwordsman.setSex("男");
		} else {
			fhmSwordsman.setSex("女");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		fhmSwordsman.setCreateTime(format.format(new Date()));
		dao.save("FhmSwordsmanMapper.insert", fhmSwordsman);
		return fhmSwordsman.getId();

	}
	
	public static void main(String[] args) {
		System.out.println(new Date());
	}

}
