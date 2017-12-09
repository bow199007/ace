package com.huacainfo.ace.rvc.service.impl;

import com.huacainfo.ace.common.exception.CustomException;
import com.huacainfo.ace.common.tools.DateUtil;
import com.huacainfo.ace.common.tools.GUIDUtil;
import com.huacainfo.ace.common.tools.JsonUtil;
import com.huacainfo.ace.common.tools.StringUtils;
import com.huacainfo.ace.rvc.dao.RvcBaseUserDao;
import com.huacainfo.ace.rvc.dao.RvcConferenceMapper;
import com.huacainfo.ace.rvc.dao.RvcConferenceMembersMapper;
import com.huacainfo.ace.rvc.kedapi.authorize.AuthorizeApi;
import com.huacainfo.ace.rvc.kedapi.common.constant.VideoConstant;
import com.huacainfo.ace.rvc.kedapi.control.ControlApi;
import com.huacainfo.ace.rvc.kedapi.control.model.RecordReq;
import com.huacainfo.ace.rvc.kedapi.control.model.terminal.TerminalReq;
import com.huacainfo.ace.rvc.kedapi.control.model.terminal.TerminalResp;
import com.huacainfo.ace.rvc.kedapi.manage.ManageApi;
import com.huacainfo.ace.rvc.kedapi.manage.model.create.CreateRequest;
import com.huacainfo.ace.rvc.kedapi.vrs.VRSApi;
import com.huacainfo.ace.rvc.kedapi.vrs.model.LiveRoomResp;
import com.huacainfo.ace.rvc.kedapi.vrs.model.LocalLoginResp;
import com.huacainfo.ace.rvc.model.RvcBaseUser;
import com.huacainfo.ace.rvc.model.RvcConference;
import com.huacainfo.ace.rvc.model.RvcConferenceMembers;
import com.huacainfo.ace.rvc.service.ConferenceService;
import com.huacainfo.ace.rvc.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Arvin on 2017/11/23.
 */
@Service("conferenceService")
public class ConferenceServiceImpl implements ConferenceService {
    private static Logger logger = Logger.getLogger(ConferenceServiceImpl.class);

    @Autowired
    private RvcConferenceMembersMapper rvcConferenceMembersDao;

    @Autowired
    private RvcBaseUserDao rvcBaseUserDao;

    @Autowired
    private RvcConferenceMapper rvcConferenceDao;


    /**
     * 会议签到
     *
     * @param userId       用户ID
     * @param conferenceId 会议ID -- rvc_conference.id
     * @return true/false
     */
    @Override
    public String signIn(String userId, String conferenceId) {

        return null;
    }

    /**
     * 创建会议
     *
     * @param userId     创建人用户ID -- 浪潮ID
     * @param conference 会议参数
     * @return RvcConference
     */
    @Override
    public RvcConference create(String userId, RvcConference conference) {
        RvcBaseUser user = rvcBaseUserDao.getByUserId(userId);
        if (null == user) {
            throw new CustomException("用户信息异常");
        }
        //默认创建人即为主持人
        String conferenceId = GUIDUtil.getGUID();
        conference.setId(conferenceId);
        conference.setEmceeId(userId);
        conference.setEmceeName(user.getUserName());
        conference.setCreateUserId(userId);
        conference.setCreateUserName(user.getUserName());
        conference.setCreateDate(DateUtil.getNowDate());
        conference.setLastModifyDate(DateUtil.getNowDate());
        rvcConferenceDao.insertSelective(conference);
        //添加自己进入与会信息
        addMembers(userId, conferenceId, new String[]{userId});

        return conference;
    }

    /**
     * 添加与会人员 --无接口操作
     *
     * @param userId       操作人用户ID  -- 浪潮ID
     * @param members      与会人员ID数组 -- 浪潮ID
     * @param conferenceId 会议ID -- rvc_conference.id
     * @return List<RvcConferenceMembers>
     */
    @Override
    public List<RvcConferenceMembers> addMembers(String userId, String conferenceId, String[] members) {
        RvcBaseUser operateUser = rvcBaseUserDao.getByUserId(userId);
        if (null == operateUser) {
            throw new CustomException("操作用户信息异常");
        }
        List<RvcConferenceMembers> list = new ArrayList<>();
        RvcBaseUser member;
        RvcConferenceMembers confMember;
        for (String uid : members) {
            member = rvcBaseUserDao.getByUserId(uid);
            if (null == member) {
                throw new CustomException("与会人员用户信息异常[" + uid + "]");
            }
            //与会信息
            confMember = new RvcConferenceMembers();
            confMember.setId(GUIDUtil.getGUID());
            confMember.setConferenceId(conferenceId);
            confMember.setUserId(uid);
            confMember.setUserName(member.getUserName());
            confMember.setUserPosition(member.getPosition());
            confMember.setUserPhoneNumer(member.getPhoneNumer());
            confMember.setKedaAccount(member.getKedaAccount());
            confMember.setKedaAccountType(member.getKedaAccountType());
            confMember.setCreateUserId(userId);
            confMember.setCreateUserName(operateUser.getUserName());
            confMember.setCreateDate(DateUtil.getNowDate());
            confMember.setLastModifyDate(DateUtil.getNowDate());
            rvcConferenceMembersDao.insertSelective(confMember);

            list.add(confMember);
        }

        return list;
    }

    /**
     * 召开会议
     *
     * @param userId       操作人用户ID -- 浪潮
     * @param conferenceId 会议ID -- rvc_conference.id
     * @return 处理结果
     */
    @Override
    public Map<String, Object> start(String userId, String conferenceId) {
        //1.数据校验
        RvcBaseUser operateUser = rvcBaseUserDao.getByUserId(userId);
        if (null == operateUser) {
            throw new CustomException("操作用户信息异常");
        }
        RvcConference conference = rvcConferenceDao.selectByPrimaryKey(conferenceId);
        if (null == conference) {
            throw new CustomException("会议信息异常");
        }
        try {
            //可以放在调度器中去处理
            if (StringUtils.isEmpty(AuthorizeApi.ACCOUNT_TOKEN) || StringUtils.isEmpty(AuthorizeApi.SSO_COOKIE_KEY)) {
                AuthorizeApi.init();
            }
            //2.调用接口开始会议
            String confId = apiCreate(operateUser, conference);//科达接口返回会议ID
            //3.添加自己的本级终端,并指定自己为发言人
            appointDefaultSpeaker(confId, operateUser, conference);
            //4.开启直播功能
            apiLive(confId, operateUser, conference);
            //5.调用录播接口，获取直播地址
            String liveURL = getLiveAddress(conference);
            //6.更新会议状态
            conference.setStatus("1");
            conference.setLiveURL(liveURL);
            conference.setLastModifyUserId(operateUser.getUserId());
            conference.setLastModifyUserName(operateUser.getUserName());
            conference.setLastModifyDate(DateUtil.getNowDate());
            rvcConferenceDao.updateByPrimaryKeySelective(conference);

            return ResultUtil.success(liveURL);
        } catch (Exception e) {
            logger.error("start.error:{}", e);
            throw new CustomException("创建会议失败");
        }
    }

    /***
     *  指定默认发言人
     * @param confId 会议id -- 科达编号
     * @param operateUser 默认发言人会员信息
     * @param conference 会场信息
     */
    private void appointDefaultSpeaker(String confId, RvcBaseUser operateUser, RvcConference conference) {
        TerminalResp terminalResp = apiAddMember(confId, operateUser, conference);
        int mtId = terminalResp.getMts().get(0).getMt_id();
        ControlApi.speaker(confId, mtId, AuthorizeApi.ACCOUNT_TOKEN, AuthorizeApi.SSO_COOKIE_KEY);

        RvcConferenceMembers members = rvcConferenceMembersDao.getByUserId(operateUser.getUserId());
        members.setMtId(mtId + "");
        members.setLastModifyDate(DateUtil.getNowDate());
        members.setLastModifyUserId(operateUser.getUserId());
        members.setLastModifyUserName(operateUser.getUserName());
        rvcConferenceMembersDao.updateByPrimaryKeySelective(members);
    }

    /***
     * 加入会议
     * @param userId 用户ID
     * @param conferenceId 会议ID -- -- rvc_conference.id
     * @return 处理结果
     */
    @Override
    public String join(String userId, String conferenceId) {
        RvcBaseUser user = rvcBaseUserDao.getByUserId(userId);
        if (null == user) {
            throw new CustomException("用户资料异常");
        }
        RvcConference conference = rvcConferenceDao.selectByPrimaryKey(conferenceId);
        if (null == conference) {
            throw new CustomException("会议资料异常");
        }
        if ("1".equals(conference.getStatus()) || StringUtils.isEmpty(conference.getConfId())) {
            throw new CustomException("会议尚未开始");
        }
        //存在科达账户信息，邀请终端，参加会议
        if (!StringUtils.isEmpty(user.getKedaAccount())
                && StringUtils.isEmpty(user.getKedaAccountType() + "")) {
            TerminalResp resp = addMt(conference.getConfId(), user.getKedaAccount(), user.getKedaAccountType());
            int mtId = resp.getMts().get(0).getMt_id();
            //新加入成员新增信息，固有成员更新信息
            RvcConferenceMembers member = rvcConferenceMembersDao.getByUserId(userId);
            if (null == member) {
                member = new RvcConferenceMembers();
                member.setStatus("1");//签到状态
                member.setMtId(mtId + "");
                member.setId(GUIDUtil.getGUID());
                member.setConferenceId(conferenceId);
                member.setUserId(userId);
                member.setUserName(member.getUserName());
                member.setUserPosition(user.getPosition());
                member.setUserPhoneNumer(user.getPhoneNumer());
                member.setKedaAccount(member.getKedaAccount());
                member.setKedaAccountType(member.getKedaAccountType());
                member.setCreateUserId(userId);
                member.setCreateUserName(user.getUserName());
                member.setCreateDate(DateUtil.getNowDate());
                member.setLastModifyDate(DateUtil.getNowDate());
                rvcConferenceMembersDao.insertSelective(member);
            } else {
                member.setStatus("1");//签到状态
                member.setMtId(mtId + "");
                member.setLastModifyDate(DateUtil.getNowDate());
                member.setLastModifyUserId(user.getUserId());
                member.setLastModifyUserName(user.getUserName());
                rvcConferenceMembersDao.updateByPrimaryKeySelective(member);
            }
        }

        return conference.getLiveURL();
    }

    /***
     *  结束会议
     *
     * @param userId  用户ID
     * @param conferenceId 会议ID -- -- rvc_conference.id
     * @return 处理结果
     */
    @Override
    public String end(String userId, String conferenceId) {
        RvcBaseUser user = rvcBaseUserDao.getByUserId(userId);
        if (null == user) {
            throw new CustomException("用户资料异常");
        }
        RvcConference conference = rvcConferenceDao.selectByPrimaryKey(conferenceId);
        if (null == conference) {
            throw new CustomException("会议资料异常");
        }

        return ManageApi.delete(conference.getConfId(), AuthorizeApi.ACCOUNT_TOKEN, AuthorizeApi.SSO_COOKIE_KEY);
    }

    /**
     * 获取直播地址
     *
     * @param conference 会议资料
     * @return 直播地址
     */
    private String getLiveAddress(RvcConference conference) {
        LocalLoginResp loginResp = VRSApi.localLogin(AuthorizeApi.ACCOUNT_TOKEN, AuthorizeApi.VRS_ACCOUNT, AuthorizeApi.VRS_PWD);
        String cookies = "SSO_COOKIE_KEY=" + loginResp.getToken();
        LiveRoomResp liveRoom = VRSApi.getLiveRoomList(AuthorizeApi.ACCOUNT_TOKEN, conference.getTitle(), cookies);

        return VRSApi.getLiveURL(liveRoom);
    }


    /**
     * 添加单个本级终端
     *
     * @param confId      科达会议id
     * @param operateUser 操作人信息
     * @param conference  会议资料
     * @return
     */
    private TerminalResp apiAddMember(String confId, RvcBaseUser operateUser, RvcConference conference) {
        return addMt(confId, operateUser.getKedaAccount(), operateUser.getKedaAccountType());
    }

    /**
     * 开启直播功能
     *
     * @param confId      科达会议ID
     * @param operateUser 操作人
     * @param conference  会议资料
     */
    private void apiLive(String confId, RvcBaseUser operateUser, RvcConference conference) {
        RecordReq req = new RecordReq(conference.getTitle(), VideoConstant.RecordMode.LIVE);
        ControlApi.record(confId, req, AuthorizeApi.ACCOUNT_TOKEN, AuthorizeApi.SSO_COOKIE_KEY);
    }

    /**
     * 调用接口开始会议
     *
     * @param operateUser 操作人信息
     * @param conference  预会议信息
     */
    private String apiCreate(RvcBaseUser operateUser, RvcConference conference) {
//        Speaker speaker = new Speaker(operateUser.getUserName(), operateUser.getKedaAccount(), operateUser.getKedaAccountType());
//        Chairman chairman = new Chairman(operateUser.getUserName(), operateUser.getKedaAccount(), operateUser.getKedaAccountType());

        CreateRequest request = new CreateRequest(conference.getTitle());
        String confId = ManageApi.create(AuthorizeApi.ACCOUNT_TOKEN, AuthorizeApi.SSO_COOKIE_KEY, request);
        conference.setConfId(confId);
        return confId;
    }


    /**
     * 接口添加单个本级终端
     *
     * @param confId      会议ID
     * @param account     账号
     * @param accountType 账号类型
     * @return 接口返回值
     */
    private TerminalResp addMt(String confId, String account, int accountType) {
        //添加本级终端
        List<TerminalReq> terminals = new ArrayList<>();
        TerminalReq t1 = new TerminalReq(account, accountType);
        terminals.add(t1);
        Map<String, List<TerminalReq>> params = new HashMap<>();
        params.put("mts", terminals);

        return ControlApi.addMts(confId, JsonUtil.toJson(params), AuthorizeApi.ACCOUNT_TOKEN, AuthorizeApi.SSO_COOKIE_KEY);
    }
}