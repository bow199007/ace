package com.huacainfo.ace.portal.service.impl;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.huacainfo.ace.common.result.MessageResponse;
import com.huacainfo.ace.common.tools.CommonBeanUtils;
import com.huacainfo.ace.common.model.WxUser;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huacainfo.ace.common.result.SingleResult;
import com.huacainfo.ace.common.tools.CommonUtils;
import com.huacainfo.ace.common.tools.Encryptor;
import com.huacainfo.ace.common.tools.HttpUtils;
import com.huacainfo.ace.portal.service.AuthorityService;
import com.huacainfo.ace.portal.dao.WxUserDao;

/**
 * Created by chenxiaoke on 2017/5/18.
 */

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {
	private static final Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);

	@Autowired
	private RedisOperations<String, Object> redisTemplate;
	@Autowired
	private WxUserDao wxUserDao;



	private String access_token;
	//获取到的access_token
	private int  expires_in; //有效时间（两个小时，7200s）

	public SingleResult<Map<String, String>> authority(String appid, String appsecret, String code,
			String encryptedData, String iv,String latitude,String longitude) throws Exception {
		// appid wxa09a5be5fd228680
		// appsecret d520d29f8c26c7e3885d80b1812a8d91
		SingleResult<Map<String, String>> rst = new SingleResult<Map<String, String>>(0, "OK");
		StringBuffer url = new StringBuffer("");
		url.append("https://api.weixin.qq.com");
		url.append("/sns/jscode2session?");
		url.append("appid=");
		url.append(appid);
		url.append("&secret=");
		url.append(appsecret);
		url.append("&js_code=");
		url.append(code);
		url.append("&grant_type=authorization_code");
		//logger.info("url -> {}", url.toString());
		String res = HttpUtils.httpGet(url.toString());
		logger.info("res -> {}", res);
		JSONObject json = JSON.parseObject(res);
		if (CommonUtils.isNotBlank(json.getString("errcode"))) {
			return new SingleResult<Map<String, String>>(1, json.getString("errmsg"));
		}
		String session_key = json.getString("session_key");
		String openid = json.getString("openid");
		String expires_in = json.getString("expires_in");
        JSONObject userinfo = this.getUserInfo(encryptedData,session_key,iv);


		logger.info("session_key -> {} openid -> {} expires_in -> {} userinfo ->{}", session_key, openid, expires_in,
				userinfo);
        String _3rd_session =userinfo.getString("unionId");
        if(CommonUtils.isBlank(_3rd_session)){
			_3rd_session=userinfo.getString("openId");
			userinfo.put("unionId",_3rd_session);
		}
		WxUser user=this.wxUserDao.selectByPrimaryKey(_3rd_session);
		if(CommonUtils.isNotEmpty(user)){
			userinfo.put("areaCode",user.getAreaCode());
			userinfo.put("category",user.getCategory());
			userinfo.put("party",user.getParty());
			userinfo.put("role",user.getRole());
			if(CommonUtils.isNotEmpty(user)){
				if(CommonUtils.isNotEmpty(user.getRole())){
					if(user.getRole().equals("admin")){

						userinfo.put("category","");
						userinfo.put("party","");
						logger.info("admin in login");
					}
				}

			}
		}
        Map<String, String> o = new HashMap<String, String>();
        o.put("session_key", session_key);
        o.put("openid", openid);
        o.put("expires_in", expires_in);
        o.put("3rd_session", _3rd_session);
        redisTemplate.opsForValue().set(_3rd_session + "openid", openid);
        redisTemplate.opsForValue().set(_3rd_session + "session_key", session_key);
		redisTemplate.opsForValue().set(_3rd_session, userinfo);
		WxUser wxUser= JSON.parseObject(userinfo.toString(),WxUser.class);
		if(CommonUtils.isNotEmpty(latitude)){
			wxUser.setLatitude(new java.math.BigDecimal(latitude));
		}
		if(CommonUtils.isNotEmpty(longitude)){
			wxUser.setLongitude(new java.math.BigDecimal(longitude));
		}
		this.saveOrUpdateWxUser(wxUser);
		rst.setValue(o);
		return rst;
	}
	private void saveOrUpdateWxUser(WxUser o){
		int t=this.wxUserDao.isExit(o);
		if(t>0){
			wxUserDao.updateByPrimaryKey(o);
		}else{
			wxUserDao.insert(o);
		}
	}
	public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) throws Exception {
        logger.info("encryptedData: {} sessionKey: {} iv:{}",encryptedData,sessionKey,iv);
		// 被加密的数据
		byte[] dataByte = Base64.decodeBase64(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64.decodeBase64(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.decodeBase64(iv);
		// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
		int base = 16;
		if (keyByte.length % base != 0) {
			int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
			keyByte = temp;
		}
		// 初始化
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
		SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
		AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
		parameters.init(new IvParameterSpec(ivByte));
		cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
		byte[] resultByte = cipher.doFinal(dataByte);
		if (null != resultByte && resultByte.length > 0) {
			String result = new String(resultByte, "UTF-8");
			return JSON.parseObject(result);
		}

		return null;
	}



	public  SingleResult<WxUser> reg(com.huacainfo.ace.common.model.WxUser wxUser) throws Exception{

		Map<String,Object> p=this.wxUserDao.selectPersonageByMobile(wxUser.getMobile());
		if(p==null||p.isEmpty()){
			return new SingleResult(1,"手机号不正确，还未被统战人士信息绑定。");
		}
		if(!((String)p.get("name")).equals(wxUser.getName())){
			return new SingleResult(1,"姓名在统战人士信息中不存在。");
		}

		wxUser.setAreaCode((String) p.get("areaCode"));
		wxUser.setCategory((String) p.get("category"));
		wxUser.setParty((String) p.get("party"));
		wxUser.setDeptId((String) p.get("deptId"));
		int t=this.wxUserDao.updateReg(wxUser);
		WxUser o=this.wxUserDao.selectByPrimaryKey(wxUser.getUnionId());
		logger.info("==============>WxUser=====>{}",o);
		SingleResult<WxUser> rst=new SingleResult(0,"成功。");
		rst.setValue(o);
		return rst;
	}
	public  SingleResult<Map<String,String>> getPhoneNumber(String appid,String appsecret,String code,String encryptedData,String iv) throws Exception{
		SingleResult<Map<String, String>> rst = new SingleResult<Map<String, String>>(0, "OK");
		StringBuffer url = new StringBuffer("");
		url.append("https://api.weixin.qq.com");
		url.append("/sns/jscode2session?");
		url.append("appid=");
		url.append(appid);
		url.append("&secret=");
		url.append(appsecret);
		url.append("&js_code=");
		url.append(code);
		url.append("&grant_type=authorization_code");
		//logger.info("url -> {}", url.toString());
		String res = HttpUtils.httpGet(url.toString());
		logger.info("res -> {}", res);
		JSONObject json = JSON.parseObject(res);
		if (CommonUtils.isNotBlank(json.getString("errcode"))) {
			return new SingleResult<Map<String, String>>(1, json.getString("errmsg"));
		}
		String session_key = json.getString("session_key");
		String openid = json.getString("openid");
		String expires_in = json.getString("expires_in");
		JSONObject phoneNumber = this.getUserInfo(encryptedData,session_key,iv);
		Map<String, String> e=new HashMap<String, String>();
		e.put("phoneNumber",phoneNumber.getString("phoneNumber"));
		e.put("purePhoneNumber",phoneNumber.getString("purePhoneNumber"));
		e.put("countryCode",phoneNumber.getString("countryCode"));
		logger.info("session_key -> {} openid -> {} expires_in -> {} userinfo ->{}", session_key, openid, expires_in,
				phoneNumber);
		rst.setValue(e);
		return rst;
	}

	public static void main(String args[]) throws Exception {
		String  encryptedData="ZKXxLGHy1CUHN4I97dXN47ozaVSVoYf83QXelurq956orVrLxyzXF10haIwgskFYT2mQ4h5ugIUhR1OFGl+uuUOmjh5JH0BGOKweUPpfmRew9lWEPXqHGILraeVRUWwdeGfei1Yx78YK1MyKEtRWP7FfgiEAkAl3Ue1lOpL/Dz0fv28I3e2+rAjHX/hhYO+9qyuAGftgIGZgut2XkdCEwlJX1dSTTPQrOcTxh8hToKEM+4ddnX+DBUALNRrIpn6NxHcrFgWNkOuo4CHBPc5098POfzRuI8bG6mIrVNNEjfIEa6WO4KPvfSqdENEjAGUDvTcBOz2S0uADq7CF/J5yMzc+FGB7WRaGcLyw7YnF5lG3GNHv3hXoTOW73ilvs6jJq/lt4JgOv5B3/gheRImziL7I/SRYcVznk/sQ6UAeneGVBGiJc8zlygLLCBArbFpEBFW0CywikoYF5ef9e1/D9BgAaSYcKbdIjfMaReM7Tis=";
		String sessionKey="YOv6rGoBAjb\\/2Z3RQWqmMw==";
        String iv="R3eMNcQVlXJS82s1RQE0lw==";
        AuthorityServiceImpl o=new AuthorityServiceImpl();
        JSONObject userinfo= o.getUserInfo(encryptedData,sessionKey,iv);
		System.out.println(userinfo.toString());
	}

	public  MessageResponse updateForExperienceUser(String id,String name) throws Exception{
		WxUser wxUser=this.wxUserDao.selectByPrimaryKey(id);
		wxUser.setAreaCode("430702");
		wxUser.setCategory("01");
		wxUser.setParty("");
		wxUser.setDeptId("");
		wxUser.setRole("admin");
		wxUser.setName(name);
		if(CommonUtils.isBlank(name)){
			wxUser.setName("体验者");
		}
		wxUser.setMobile("13922861673");
		int t=this.wxUserDao.updateReg(wxUser);
		return new MessageResponse(0,"成功。");
	}
	public  SingleResult<WxUser> selectForExperienceUser(String id) throws Exception{
		WxUser o=this.wxUserDao.selectByPrimaryKey(id);
		logger.info("==============>WxUser=====>{}",o);
		SingleResult<WxUser> rst=new SingleResult(0,"成功。");
		rst.setValue(o);
		return rst;
	}
}
