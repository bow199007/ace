package com.huacainfo.ace.portal.service.impl;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huacainfo.ace.common.model.UserProp;
import com.huacainfo.ace.common.result.MessageResponse;
import com.huacainfo.ace.common.result.PageResult;
import com.huacainfo.ace.common.result.SingleResult;
import com.huacainfo.ace.common.tools.CommonUtils;
import com.huacainfo.ace.portal.dao.UserinfoDao;
import com.huacainfo.ace.common.model.Userinfo;
import com.huacainfo.ace.portal.service.DataBaseLogService;
import com.huacainfo.ace.portal.service.UserinfoService;
import com.huacainfo.ace.portal.vo.UserinfoVo;
import com.huacainfo.ace.portal.vo.UserinfoQVo;

@Service("userinfoService")
/**
 * @author: 陈晓克
 * @version: 2017-12-29
 * @Description: TODO(微信用户)
 */
public class UserinfoServiceImpl implements UserinfoService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserinfoDao userinfoDao;
    @Autowired
    private DataBaseLogService dataBaseLogService;

    /**
     * @throws
     * @Title:find!{bean.name}List
     * @Description: TODO(微信用户分页查询)
     * @param: @param condition
     * @param: @param start
     * @param: @param limit
     * @param: @param orderBy
     * @param: @throws Exception
     * @return: PageResult<UserinfoVo>
     * @author: 陈晓克
     * @version: 2017-12-29
     */
    @Override
    public PageResult<UserinfoVo> findUserinfoList(UserinfoQVo condition, int start,
                                                   int limit, String orderBy) throws Exception {
        PageResult<UserinfoVo> rst = new PageResult<UserinfoVo>();
        List<UserinfoVo> list = this.userinfoDao.findList(condition,
                start, start + limit, orderBy);
        rst.setRows(list);
        if (start <= 1) {
            int allRows = this.userinfoDao.findCount(condition);
            rst.setTotal(allRows);
        }
        return rst;
    }

    /**
     * @throws
     * @Title:insertUserinfo
     * @Description: TODO(添加微信用户)
     * @param: @param o
     * @param: @param userProp
     * @param: @throws Exception
     * @return: MessageResponse
     * @author: 陈晓克
     * @version: 2017-12-29
     */
    @Override
    public MessageResponse insertUserinfo(Userinfo o, UserProp userProp)
            throws Exception {

        this.userinfoDao.insert(o);
        this.dataBaseLogService.log("添加微信用户", "微信用户", "", o.getNickname(),
                o.getNickname(), userProp);
        return new MessageResponse(0, "添加微信用户完成！");
    }

    /**
     * @throws
     * @Title:updateUserinfo
     * @Description: TODO(更新微信用户)
     * @param: @param o
     * @param: @param userProp
     * @param: @throws Exception
     * @return: MessageResponse
     * @author: 陈晓克
     * @version: 2017-12-29
     */
    @Override
    public MessageResponse updateUserinfo(Userinfo o, UserProp userProp)
            throws Exception {
        this.userinfoDao.updateByPrimaryKey(o);
        this.dataBaseLogService.log("变更微信用户", "微信用户", "", o.getNickname(),
                o.getNickname(), userProp);
        return new MessageResponse(0, "变更微信用户完成！");
    }

    /**
     * @throws
     * @Title:selectUserinfoByPrimaryKey
     * @Description: TODO(获取微信用户)
     * @param: @param id
     * @param: @throws Exception
     * @return: SingleResult<Userinfo>
     * @author: 陈晓克
     * @version: 2017-12-29
     */
    @Override
    public SingleResult<UserinfoVo> selectUserinfoByPrimaryKey(String id) throws Exception {
        SingleResult<UserinfoVo> rst = new SingleResult<UserinfoVo>();
        rst.setValue(this.userinfoDao.selectByPrimaryKey(id));
        return rst;
    }

    /**
     * @throws
     * @Title:deleteUserinfoByUserinfoId
     * @Description: TODO(删除微信用户)
     * @param: @param id
     * @param: @param userProp
     * @param: @throws Exception
     * @return: MessageResponse
     * @author: 陈晓克
     * @version: 2017-12-29
     */
    @Override
    public MessageResponse deleteUserinfoByUserinfoId(String id,
                                                      UserProp userProp) throws Exception {
        this.userinfoDao.deleteByPrimaryKey(id);
        this.dataBaseLogService.log("删除微信用户", "微信用户", String.valueOf(id),
                String.valueOf(id), "微信用户", userProp);
        return new MessageResponse(0, "微信用户删除完成！");
    }
}