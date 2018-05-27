package cn.haiwai.newsnotification.web.controller;

import java.util.List;

import cn.haiwai.newsnotification.dao.ContentDao;
import cn.haiwai.newsnotification.dao.bean.ContentDO;
import cn.haiwai.newsnotification.manage.PageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import cn.haiwai.newsnotification.manage.util.TimeTransfer;
import cn.haiwai.newsnotification.service.ContentBO;
import cn.haiwai.newsnotification.service.ContentService;
import cn.haiwai.newsnotification.service.TagBO;

@Controller
@RequestMapping
public class ForeController {
	@Autowired
	private ContentService cs;

	@Autowired
	private ContentDao contentDao;
	private static final Logger log= LoggerFactory.getLogger("ForeController");
	/**
	 * @param model
	 * @return 返回到主页
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap model) {
		log.error("---------------------日志级别为：error-----------------------------{}","dd");
		log.info("---------------------日志级别为：info-----------------------------{}","dd");
		log.debug("---------------------日志级别为：debug-----------------------------{}","dd");
		List<ContentBO> contents = cs.listContents();
		if (contents == null) {
			model.addAttribute("message", "最近7天无数据，请查看其他日期！");
		}
		List<TagBO> tags=cs.listAllTag();
		model.addAttribute("tags",tags);
		//访问首页时，默认显示当天日期
		model.addAttribute("keyDate", TimeTransfer.getToday());
		model.addAttribute("contents", contents);
		return "fore/index";
	}

	/**
	 * 所有的没有后缀地址的请求都转发到主页
	 * 
	 * @return 转发到主页
	 */
	@RequestMapping(value = "/")
	public String everyPath() {
		return "forward:index";
	}

	/**
	 * 根据id展示一条content
	 * 
	 * @param cid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/content/{cid}")
	public String content(@PathVariable("cid") String cid, ModelMap model) {
		ContentBO content = cs.getContent(Integer.parseInt(cid));
		if (content == null)
			return "admin/comm/error_404";
		List<TagBO> tags=cs.listAllTag();
		//访问首页时，默认显示当天日期
		model.addAttribute("keyDate", TimeTransfer.getToday());
		model.addAttribute("tags",tags);
		model.addAttribute("content", content);
		return "fore/contentPage";
	}

	@RequestMapping(value = "/listByDate/{date}")
	public String listByDate(@PathVariable("date") String date, ModelMap model) {
		List<ContentBO> contents = cs.listContentsByDate(date);
		if (contents == null) {
			model.addAttribute("message", date + "没有内容，请查看其他日期！");
		}
		List<TagBO> tags=cs.listAllTag();
		model.addAttribute("tags",tags);
		model.addAttribute("contents", contents);
		model.addAttribute("dateTemp", date);
		return "fore/index";
	}

	@RequestMapping(value = "/listByKey/{key}")
	public String listBykeys(@PathVariable("key") String key, ModelMap model) {
		List<ContentBO> contents = cs.listContentsByKey(key);
		if (contents == null) {
			model.addAttribute("message", key + ",没有找到相关信息，搜索其他关键词试试！");
		}
		List<TagBO> tags=cs.listAllTag();
		model.addAttribute("tags",tags);
		model.addAttribute("contents", contents);
		return "fore/index";
	}

	@RequestMapping(value = "/exceptionTest")
	public String exceptionTest() {
		int i = 1 / 0;
		throw new RuntimeException("exceptionTest");
	}

	/**
	 * 按参数查询数据，如果三个参数都为空，则直接返回最近7天数据
	 * @param model
	 * @param keyWord
	 * @param keyDate
	 * @param keyTag
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchByArgus(ModelMap model, @RequestParam("keyWord") String keyWord,
			@RequestParam("keyDate") String keyDate, @RequestParam("keyTag") String keyTag) {
		List<ContentBO> contents = cs.searchByArgus(keyWord, keyDate, keyTag);
		if (contents == null) {
			model.addAttribute("message", "未搜索到内容，请调整日期、关键字或标签分类参数。");
		}
		if (StringUtils.hasText(keyDate))
			model.addAttribute("keyDate", keyDate);
		if (StringUtils.hasText(keyTag))
			model.addAttribute("keyTag", keyTag);
		List<TagBO> tags=cs.listAllTag();
		model.addAttribute("tags",tags);
		model.addAttribute("contents", contents);
		return "fore/index";
	}

	@RequestMapping(value = "/listContent")
	@ResponseBody
	@CrossOrigin
	public  List<ContentDO> listContentBO(){
		return contentDao.listContent();
	}

}
