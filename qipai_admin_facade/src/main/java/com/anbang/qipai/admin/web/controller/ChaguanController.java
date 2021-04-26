package com.anbang.qipai.admin.web.controller;

import java.util.HashMap;
import java.util.Map;

import com.anbang.qipai.admin.util.CommonVOUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanApply;
import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanDbo;
import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanShopProduct;
import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.service.chaguanservice.ChaguanApplyService;
import com.anbang.qipai.admin.plan.service.chaguanservice.ChaguanService;
import com.anbang.qipai.admin.plan.service.chaguanservice.ChaguanShopService;
import com.anbang.qipai.admin.plan.service.chaguanservice.ChaguanYushiService;
import com.anbang.qipai.admin.remote.service.QipaiChaguanRemoteService;
import com.anbang.qipai.admin.remote.service.QipaiGameRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.anbang.qipai.admin.web.vo.chaguanvo.ChaguanShopOrderVO;
import com.anbang.qipai.admin.web.vo.chaguanvo.ChaguanYushiRecordDboVO;
import com.highto.framework.web.page.ListPage;

@CrossOrigin
@RestController
@RequestMapping("/chaguan")
public class ChaguanController {

	@Autowired
	private QipaiGameRemoteService qipaiGameRomoteService;

	@Autowired
	private QipaiChaguanRemoteService qipaiChaguanRemoteService;

	@Autowired
	private ChaguanService chaguanService;

	@Autowired
	private ChaguanApplyService chaguanApplyService;

	@Autowired
	private ChaguanShopService chaguanShopService;

	@Autowired
	private ChaguanYushiService chaguanYushiService;

	/**
	 * 设置茶馆基本信息
	 */
	@RequestMapping("/chaguan_info_set")
	public CommonVO chaguan_info_set(String chaguanId, String name, String desc) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiChaguanRemoteService.chaguan_update_rpc(chaguanId, name, desc);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		vo.setData(rvo.getData());
		return vo;
	}

	/**
	 * 移除茶馆成员
	 */
	@RequestMapping("/chaguan_member_remove")
	public CommonVO chaguan_member_remove(String chaguanId, String memberId) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiChaguanRemoteService.chaguan_member_remove_rpc(chaguanId, memberId);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		vo.setData(rvo.getData());
		return vo;
	}

	/**
	 * 查询茶馆申请
	 */
	@RequestMapping("/apply_query")
	public CommonVO chaguanApplyQuery(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, ChaguanApply apply) {
		CommonVO vo = new CommonVO();
		apply.setStatus("APPLYING");
		ListPage listPage = chaguanApplyService.findByStatus(page, size, apply);
		Map data = new HashMap<>();
		data.put("listPage", listPage);
		vo.setData(data);
		return vo;
	}

	/**
	 * 通过推广员开通茶馆申请
	 */
	@RequestMapping("/apply_pass")
	public CommonVO chaguanApplyPass(String applyId) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiChaguanRemoteService.agentchaguan_apply_pass(applyId);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		vo.setData(rvo.getData());
		return vo;
	}

	/**
	 * 拒绝推广员开通茶馆申请
	 */
	@RequestMapping("/apply_refuse")
	public CommonVO chaguanApplyRefuse(String applyId) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiChaguanRemoteService.agentchaguan_apply_refuse(applyId);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		vo.setData(rvo.getData());
		return vo;
	}

	/**
	 * 移除茶馆
	 */
	@RequestMapping("/chaguan_remove")
	public CommonVO chaguan_remove(String chaguanId) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiChaguanRemoteService.chaguan_chaguan_remove(chaguanId);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		vo.setData(rvo.getData());
		return vo;
	}

	/**
	 * 查询商品
	 */
	@RequestMapping("/product_query")
	public CommonVO product_query(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {
		CommonVO vo = new CommonVO();
		ListPage listPage = chaguanShopService.findChaguanShopProductByConditions(page, size);
		vo.setMsg("product list");
		Map data = new HashMap<>();
		vo.setData(data);
		data.put("listPage", listPage);
		return vo;
	}

	/**
	 * 添加商品
	 */
	@RequestMapping("/product_add")
	public CommonVO product_add(ChaguanShopProduct product) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiChaguanRemoteService.shop_product_add(product);
		vo.setMsg(rvo.getMsg());
		vo.setData(rvo.getData());
		return vo;
	}

	/**
	 * 修改商品
	 */
	@RequestMapping("/product_update")
	public CommonVO product_update(ChaguanShopProduct product) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiChaguanRemoteService.shop_product_update(product);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		vo.setData(rvo.getData());
		return vo;
	}

	/**
	 * 删除商品
	 */
	@RequestMapping("/product_remove")
	public CommonVO product_remove(@RequestParam(name = "productIds") String[] productIds) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiChaguanRemoteService.shop_product_remove(productIds);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		vo.setData(rvo.getData());
		return vo;
	}

	/**
	 * 查询茶馆
	 */
	@RequestMapping("/query_chaguan")
	public CommonVO query_chaguan(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,String agentId) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		vo.setData(data);
		ListPage listPage = chaguanService.findChaguanDbo(page, size,agentId);
		data.put("listPage", listPage);
		return vo;
	}

	/**
	 * 查询茶馆基本信息
	 */
	@RequestMapping("/query_chaguan_info")
	public CommonVO query_chaguan_info(String chaguanId) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		vo.setData(data);
		ChaguanDbo chaguan = chaguanService.findByChaguanId(chaguanId);
		data.put("chaguan", chaguan);
		return vo;
	}

	/**
	 * 查询茶馆成员
	 */
	@RequestMapping("/query_chaguanmember")
	public CommonVO query_chaguanmember(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, String chaguanId) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		vo.setData(data);
		ListPage listPage = chaguanService.findChaguanMemberDbo(page, size, chaguanId);
		data.put("listPage", listPage);
		return vo;
	}

	/**
	 * 查询购买记录
	 */
	@RequestMapping("/query_order")
	public CommonVO query_order(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			ChaguanShopOrderVO order) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		vo.setData(data);
		ListPage listPage = chaguanShopService.findOrderByConditions(page, size, order);
		data.put("listPage", listPage);
		return vo;
	}

	/**
	 * 查询玉石充值记录
	 */
	@RequestMapping("/query_yushi_record")
	public CommonVO query_yushi_record(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, ChaguanYushiRecordDboVO record) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		vo.setData(data);
		ListPage listPage = chaguanYushiService.findRecordByConditions(page, size, record,"玩家");
		data.put("listPage", listPage);
		return vo;
	}

	/**
	 * 房间管理-查询
	 * 
	 * @param playerId
	 * @param roomNo
	 * @return
	 */
	@RequestMapping(value = "/queryroom", method = RequestMethod.POST)
	public CommonVO queryRoom(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, String playerId, String roomNo) {
		CommonVO vo = new CommonVO();
		ListPage listPage = chaguanService.queryRoomList(page, size, playerId, roomNo);
		vo.setSuccess(true);
		vo.setMsg("query roomlist success");
		Map data=new HashMap();
		data.put("listPage",listPage);
		vo.setData(data);
		return vo;
	}

	/**
	 * 房间管理-查看
	 * 
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value = "/queryroomdetail", method = RequestMethod.POST)
	public CommonVO queryRoomDetail(String gameId) {
		CommonVO vo = new CommonVO();
		Map map = chaguanService.queryRoomDetail(gameId);
		vo.setSuccess(true);
		vo.setMsg("query roomdetail success");
		vo.setData(map);
		return vo;
	}

	/**
	 * 获取茶馆回放码
	 */
	@RequestMapping(value = "/get_backcode", method = RequestMethod.POST)
	public CommonVO getBackcode(Game game, String gameId, int panNo) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO remoteVO = qipaiGameRomoteService.getBackcode(game, gameId, panNo);
		vo.setSuccess(true);
		vo.setMsg("query roomdetail success");
		vo.setData(remoteVO.getData());
		return vo;
	}

	/**
	 * 赠送馆主大赢家卡
	 */
	@RequestMapping(value = "/get_dayingjiaka", method = RequestMethod.POST)
	public CommonVO getDayingjiaka(String agentId, int amount,String textSummary){
		if (StringUtils.isBlank(agentId) || amount == 0 || StringUtils.isBlank(textSummary)) {
			return CommonVOUtil.lackParameter();
		}
		CommonRemoteVO remoteVO = qipaiChaguanRemoteService.giveDayingjiakatoagent(agentId, amount, textSummary);
		if (remoteVO != null && remoteVO.isSuccess()){
			return CommonVOUtil.success("success");
		}
		return CommonVOUtil.systemException();
	}


	/**
	 * 赠送馆主茶馆玉石
	 */
	@RequestMapping(value = "/get_chaguanyushi", method = RequestMethod.POST)
	public CommonVO getChaguanyushi(String agentId, int amount,String textSummary) {
		if (StringUtils.isBlank(agentId) || amount == 0 || StringUtils.isBlank(textSummary)) {
			return CommonVOUtil.lackParameter();
		}
		CommonRemoteVO remoteVO = qipaiChaguanRemoteService.givechaguanyushitoagent(agentId, amount, textSummary);
		if (remoteVO != null && remoteVO.isSuccess()){
			return CommonVOUtil.success("success");
		}
		return CommonVOUtil.systemException();
	}

}
