package com.fh.service.system.appuser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.FhmLuckydraw;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

@Service
public class LuckyDrawService {
	List<PageData> list = new ArrayList<PageData>();

	Set<String> set = new HashSet<String>();

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	java.util.Random ran = new java.util.Random();

	/*
	 * public static void main(String[] args) { LuckyDrawService choujiang = new
	 * LuckyDrawService(); List<String> ll = new ArrayList<String>(); for (long
	 * i = 0l; i < 1000000l; i++) { ll.add(String.valueOf(i)); }
	 * choujiang.start(ll); System.out.println(choujiang.set);
	 * System.out.println(choujiang.set.size()); }
	 */

	public String shake(int size) {
		int base = ran.nextInt(size);
		String winner = list.get(base).getString("USER_ID");
		if (set.contains(winner))
			return shake(size);
		else
			return winner;
	}

	public void start(List<PageData> ll) {
		/*
		 * List<PageData> llMOCK = new ArrayList<PageData>(); for (long i = 0l;
		 * i < 1000000l; i++) { PageData data = new PageData();
		 * data.put("USERID", String.valueOf(i)); llMOCK.add(data); }
		 */
		try{
		list = ll;
		int size = list.size();
		if (!validate())
			throw new RuntimeException();
		for (int i = 0; i < 100; i++) {
			set.add(shake(size));
		}
		list.clear();
		insertResult(set);
		set.clear();}
		finally{
			list.clear();
			set.clear();
		}
	}
	
	public List<PageData> listAllLuckydraw(Page page) throws Exception {
		return (List<PageData>) dao.findForList("FhmLuckydrawMapper.listAllLuckydraw", page);
	}

	// 检查是否可以开奖，人数不足以及是否摇奖过了
	public boolean validate() {
		if (count() == 0 && list.size() > 100)
			return true;
		else
			return false;
	}

	public Integer count() {
		try {
			return (Integer) dao.findForObject("FhmLuckydrawMapper.countNo", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public void insertResult(Set<String> set) {
		// List<FhmLuckydraw> list = new ArrayList<FhmLuckydraw>();
		for (String userId : set) {
			FhmLuckydraw fhmLuckydraw = new FhmLuckydraw();
			fhmLuckydraw.setCustomerId(userId);
			fhmLuckydraw.setId(UuidUtil.get32UUID());
			// list.add(fhmLuckydraw);
			try {
				dao.save("FhmLuckydrawMapper.insert", fhmLuckydraw);

				// dao.batchSave("FhmLuckydrawMapper.insert", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
