package com.huacainfo.ace.portal.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huacainfo.ace.common.model.PageParamNoChangeSord;
import com.huacainfo.ace.common.result.MessageResponse;
import com.huacainfo.ace.common.result.PageResult;
import com.huacainfo.ace.common.result.SingleResult;
import com.huacainfo.ace.portal.model.WxCfg;
import com.huacainfo.ace.portal.service.WxCfgService;
import com.huacainfo.ace.portal.vo.WxCfgVo;
import com.huacainfo.ace.portal.vo.WxCfgQVo;

@Controller
@RequestMapping("/wxCfg")
public class WxCfgController extends PortalBaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WxCfgService wxCfgService;

	@RequestMapping(value = "/findWxCfgList.do")
	@ResponseBody
	public PageResult<WxCfgVo> findWxCfgList(WxCfgQVo condition,
			PageParamNoChangeSord page) throws Exception {
		PageResult<WxCfgVo> rst = this.wxCfgService
				.findWxCfgList(condition, page.getStart(), page.getLimit(),
						page.getOrderBy());
		if (rst.getTotal() == 0) {
			rst.setTotal(page.getTotalRecord());
		}
	
		return rst;
	}

	@RequestMapping(value = "/insertWxCfg.do")
	@ResponseBody
	public MessageResponse insertWxCfg(String jsons) throws Exception {
		WxCfg obj = JSON.parseObject(jsons, WxCfg.class);
		return this.wxCfgService
				.insertWxCfg(obj, this.getCurUserProp());
	}

	@RequestMapping(value = "/updateWxCfg.do")
	@ResponseBody
	public MessageResponse updateWxCfg(String jsons) throws Exception {
		WxCfg obj = JSON.parseObject(jsons, WxCfg.class);
		return this.wxCfgService
				.updateWxCfg(obj, this.getCurUserProp());
	}

	@RequestMapping(value = "/selectWxCfgByPrimaryKey.do")
	@ResponseBody
	public SingleResult<WxCfgVo> selectWxCfgByPrimaryKey(String id)
			throws Exception {
		return this.wxCfgService.selectWxCfgByPrimaryKey(id);
	}

	@RequestMapping(value = "/deleteWxCfgByWxCfgId.do")
	@ResponseBody
	public MessageResponse deleteWxCfgByWxCfgId(String jsons)
			throws Exception {
		JSONObject json = JSON.parseObject(jsons);
		String id = json.getString("id");
		return this.wxCfgService.deleteWxCfgByWxCfgId(id,
				this.getCurUserProp());
	}
}
