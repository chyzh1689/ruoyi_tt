package com.ruoyi.tt.third;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.RedisConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.tt.TTContants;
import com.ruoyi.tt.domain.Account;
import com.ruoyi.tt.domain.Device;
import com.ruoyi.tt.domain.Mechant;
import com.ruoyi.tt.domain.Notice;
import com.ruoyi.tt.enums.AccountStatus;
import com.ruoyi.tt.enums.ChannelPackage;
import com.ruoyi.tt.enums.NoticeFlag;
import com.ruoyi.tt.enums.YesOrNoStatus;
import com.ruoyi.tt.mapper.AccountMapper;
import com.ruoyi.tt.mapper.DeviceMapper;
import com.ruoyi.tt.mapper.MechantMapper;
import com.ruoyi.tt.mapper.NoticeMapper;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class TtSocketService {
    @Autowired
    private RedisCache redisCache;
    /**处理消息**/
    TTSocketDto handle(TTSocketDto ttSocketDto){
        String action = ttSocketDto.getAction();
        switch (action){
            //设备初始化
            case TTScoketConstants.ACTION_DEVICE_LOGIN: return this.actionDeviceLogin(ttSocketDto);
            //断开连接
            case TTScoketConstants.ACTION_ACCOUNT_DISCONNECT: return this.actionAccountDisconnect(ttSocketDto);
            //账号登录
            case TTScoketConstants.ACTION_ACCOUNT_LOGIN: return this.accountLogin(ttSocketDto);
            //获取配置
            case TTScoketConstants.ACTION_APP_CONFIG: return this.appConfig(ttSocketDto);
            //是否关注
            case TTScoketConstants.ACTION_FOLLOW: return this.follow(ttSocketDto);
            //关注列表
            case TTScoketConstants.ACTION_follow_list: return this.followList(ttSocketDto);
            //聊天消息
            case TTScoketConstants.ACTION_CHAT_MSG: return this.appConfig(ttSocketDto);
            //聊天指令
            case TTScoketConstants.ACTION_CHAT_CMD: return this.appConfig(ttSocketDto);
            //同步心跳
            case TTScoketConstants.ACTION_SYNC: return this.sync(ttSocketDto);
            //操作指令
            case TTScoketConstants.ACTION_CMD: return this.appConfig(ttSocketDto);
            //获取粉丝列表
            case TTScoketConstants.ACTION_USER_LIST: return this.appConfig(ttSocketDto);
            //进入房间
            case TTScoketConstants.ACTION_ENTER_ROOM: return  this.enterRoom(ttSocketDto);
            //查询
            case TTScoketConstants.ACTION_QUERY_ROOM: return this.queryRoom(ttSocketDto);
            default:
        }
        return ttSocketDto;
    }

    /***
     * @param ttSocketDto
     * @return
     */
    private TTSocketDto sync(TTSocketDto ttSocketDto) {
        return ttSocketDto.ok();
    }

    /**
     *
     * @param ttSocketDto
     * @return
     */
    private TTSocketDto queryRoom(TTSocketDto ttSocketDto) {
        JSONObject obj = (JSONObject) ttSocketDto.getData();
        String userId = (String) obj.get("userId");
        obj = redisCache.getCacheMapValue(TTContants.cache_key_tt_room_user_data, userId);
        //obj = JSONObject.parseObject(data);
        return ttSocketDto.ok(obj);
    }

    /**
     *
     * @param ttSocketDto
     * @return
     */
    private TTSocketDto enterRoom(TTSocketDto ttSocketDto) {
        JSONObject data = (JSONObject) ttSocketDto.getData();
        String userId = (String) data.get("userId");
        redisCache.setCacheMapValue(TTContants.cache_key_tt_room_user_data,userId,data);
        return ttSocketDto.ok();
    }

    /**
     * 询问结果
     * @param ttSocketDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Synchronized
    public TTSocketDto follow(TTSocketDto ttSocketDto) {
        UserInfo userInfo = ttSocketDto.getUserInfo();
        if(userInfo==null||StringUtils.isEmpty(userInfo.getUserId())){
            return ttSocketDto.fail("用户信息为空！");
        }
        String ownNo = userInfo.getUserId();
        String deviceNo = redisCache.getCacheMapValue(RedisConstants.TT_CHANNEL_DEVICE,
                ttSocketDto.getChannelId());
        Device device = deviceMapper.selectDeviceByDeviceNo(deviceNo);
        if(device==null||device.getMechId()==null){
            if(device==null){
                return ttSocketDto.fail(TTScoketConstants.CODE_UNATIVE,"设备不存在！");
            }
        }
        JSONObject data = (JSONObject) ttSocketDto.getData();
        if(data==null){
            return ttSocketDto.fail("要关注对象为空！");
        }
        UserFollowInfo followInfo = data.toJavaObject(UserFollowInfo.class);
        String status = followInfo.getStatus();
        if(StringUtils.isEmpty(status)){
            return ttSocketDto.fail("要关注对象状态为空！");
        }
        Integer noticeFlag = Integer.parseInt(status);
        String noticeNo = followInfo.getUserId();
        Notice notice = noticeMapper.selectNoticeForFollow(device.getMechId(),
                ttSocketDto.getPackageName(),noticeNo);
        String dateStr = DateUtils.dateTime();
        String key = userInfo.getUserId()+"@"+device.getMechId()+"@"+ttSocketDto.getPackageName();
        Integer oldNoticeFllag = NoticeFlag.free.val();;
        if(notice==null){
            notice = new Notice();
            notice.setNoticeNo(noticeNo);
            notice.setOwnId(device.getMechOwn());
            notice.setOwnNo(userInfo.getUserId());
            notice.setOwnName(userInfo.getUserName());
            notice.setNoticeFlag(noticeFlag);
            notice.setDeviceId(device.getDeviceId());
            notice.setMechantId(device.getMechId());
            notice.setChannelPackage(ttSocketDto.getPackageName());
            notice.setCreateBy("admin");
            notice.setCreateTime(new Date());
            notice.setNoticeName(followInfo.getUserName());
            notice.setNoticeLocation(followInfo.getLocation());
            notice.setNoticeImgRul(followInfo.getImageUrl());
            noticeMapper.insertNotice(notice);
        }else if(NoticeFlag.free.val().equals(notice.getNoticeFlag()) ||
                (!noticeFlag.equals(notice.getNoticeFlag()) && ownNo.equals(notice.getOwnNo()))){
            oldNoticeFllag = notice.getNoticeFlag();
            notice.setDeviceId(device.getDeviceId());
            notice.setNoticeFlag(noticeFlag);
            notice.setUpdateBy("admin");
            notice.setUpdateTime(new Date());
            notice.setNoticeName(followInfo.getUserName());
            notice.setNoticeLocation(followInfo.getLocation());
            notice.setNoticeImgRul(followInfo.getImageUrl());
            noticeMapper.updateNotice(notice);
        }else{
            return ttSocketDto.ok("关注状态异常！");
        }
        this.updateNumForNoticeFlag(noticeFlag, dateStr, key,oldNoticeFllag);
        return ttSocketDto.ok();
    }

    private void updateNumForNoticeFlag(Integer noticeFlag, String dateStr, String key,Integer oldNoticeFllag) {
        if(NoticeFlag.free.val().equals(oldNoticeFllag) &&
                NoticeFlag.apply.val().equals(noticeFlag)){
            Integer applyNum = redisCache.getCacheMapValue(dateStr + TTContants.cache_key_tt_day_apply_number, key);
            if(applyNum!=null){
                applyNum++;
            }else{
                applyNum=1;
            }
            redisCache.setCacheMapValue(dateStr + TTContants.cache_key_tt_day_apply_number, key,applyNum);
        }else if(NoticeFlag.apply.val().equals(oldNoticeFllag) &&
                (NoticeFlag.notice.val().equals(noticeFlag) || NoticeFlag.back.val().equals(noticeFlag))){
            Integer followNum = redisCache.getCacheMapValue(dateStr + TTContants.cache_key_tt_day_follow_number, key);
            if(followNum!=null){
                followNum++;
            }else{
                followNum=1;
            }
            redisCache.setCacheMapValue(dateStr + TTContants.cache_key_tt_day_follow_number, key,followNum);

            Integer applyNum = redisCache.getCacheMapValue(dateStr + TTContants.cache_key_tt_day_apply_number, key);
            if(applyNum!=null){
                applyNum--;
            }else{
                applyNum=0;
            }
            if(applyNum<0){
                applyNum=0;
            }
            redisCache.setCacheMapValue(dateStr + TTContants.cache_key_tt_day_apply_number, key,applyNum);
        }
    }

    @Autowired
    private NoticeMapper noticeMapper;
    /**
     * 询问关注
     * @param ttSocketDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Synchronized
    public TTSocketDto followList(TTSocketDto ttSocketDto) {
        UserInfo userInfo = ttSocketDto.getUserInfo();
        if(userInfo==null){
            return ttSocketDto.fail("用户信息为空！");
        }
        String deviceNo = redisCache.getCacheMapValue(RedisConstants.TT_CHANNEL_DEVICE,
                ttSocketDto.getChannelId());
        Device device = deviceMapper.selectDeviceByDeviceNo(deviceNo);
        if(device==null||device.getMechId()==null){
            if(device==null){
                return ttSocketDto.fail(TTScoketConstants.CODE_UNATIVE,"设备不存在！");
            }
        }
        JSONArray datas = (JSONArray) ttSocketDto.getData();
        if(datas==null || datas.size()==0){
            return ttSocketDto.fail("询问关注账号为空！");
        }
        Account account = new Account();
        account.setMechantId(device.getMechId());
        account.setAccountChannel(ttSocketDto.getPackageName());
        account.setAccountNo(userInfo.getUserId());
        account = accountMapper.selectAccount(account);
        if(account==null){
            return ttSocketDto.fail("账号信息不存在！");
        }
        Integer remainNum = account.getFollowNumber();
        String dateStr = DateUtils.dateTime();
        String key = account.getAccountNo()+"@"+account.getMechantId()+"@"+account.getAccountChannel();
        //当天正在关注数量
        Integer applyNum = redisCache.getCacheMapValue(dateStr + TTContants.cache_key_tt_day_apply_number,key);
        if(applyNum!=null){
            remainNum-=applyNum;
        }
        //当天关注成功数量
        Integer followNum = redisCache.getCacheMapValue(dateStr + TTContants.cache_key_tt_day_follow_number,key);
        if(followNum!=null){
            remainNum-=followNum;
        }
        if(remainNum<=0){
            return ttSocketDto.fail("剩余关注数量不足够，请加大配置数量！");
        }
        String[] noticeNos = new String[datas.size()];
        int idx = 0;
        for (Object data : datas) {
            JSONObject obj = (JSONObject) data;
            noticeNos[idx++] = (String) obj.get("userId");
        }
        List<Notice> notices = noticeMapper.selectNoticeForFollowList(device.getMechId(),
                ttSocketDto.getPackageName(),noticeNos);
        HashMap<String,Notice> maps = new HashMap<>();
        for (Notice notice : notices) {
            maps.put(notice.getNoticeNo(),notice);
        }
        JSONArray ress = new JSONArray();
        for (Object one : datas) {
            JSONObject jo = (JSONObject) one;
            String noticeNo = jo.getString(TTScoketConstants.param_user_id);
            Notice notice = maps.get(noticeNo);
            if(notice==null){
                notice = new Notice();
                notice.setOwnId(device.getMechOwn());
                notice.setOwnNo(userInfo.getUserId());
                notice.setOwnName(userInfo.getUserName());
                notice.setNoticeNo(noticeNo);
                notice.setNoticeFlag(NoticeFlag.apply.val());
                notice.setDeviceId(device.getDeviceId());
                notice.setMechantId(device.getMechId());
                notice.setChannelPackage(ttSocketDto.getPackageName());
                notice.setNoticeName(jo.getString(TTScoketConstants.param_user_name));
                notice.setNoticeImgRul(jo.getString(TTScoketConstants.param_image_url));
                notice.setNoticeLocation(jo.getString(TTScoketConstants.param_location));
                notice.setCreateBy("admin");
                notice.setCreateTime(new Date());
                noticeMapper.insertNotice(notice);
                remainNum--;
                ress.add(one);
            }else if(NoticeFlag.free.val().equals(notice.getNoticeFlag())){
                notice.setOwnId(device.getMechOwn());
                notice.setOwnNo(userInfo.getUserId());
                notice.setOwnName(userInfo.getUserName());
                notice.setDeviceId(device.getDeviceId());
                notice.setNoticeFlag(NoticeFlag.apply.val());
                notice.setNoticeName(jo.getString(TTScoketConstants.param_user_name));
                notice.setNoticeImgRul(jo.getString(TTScoketConstants.param_image_url));
                notice.setNoticeLocation(jo.getString(TTScoketConstants.param_location));
                notice.setUpdateBy("admin");
                notice.setUpdateTime(new Date());
                noticeMapper.updateNotice(notice);
                remainNum--;
                ress.add(one);
            }
            if(remainNum==0){
                break;
            }
        }
        //更新正在关注数量
        if(applyNum == null){
            applyNum = ress.size();
        }else{
            applyNum+=ress.size();
        }
        redisCache.setCacheMapValue(dateStr + TTContants.cache_key_tt_day_apply_number,key,applyNum);
        return ttSocketDto.ok(ress,"返回成功！");
    }

    @Autowired
    private ISysConfigService configService;
    /**
     * 配置信息
     * @param ttSocketDto
     * @return
     */
    private TTSocketDto appConfig(TTSocketDto ttSocketDto) {
        UserInfo userInfo = ttSocketDto.getUserInfo();
        if(userInfo==null){
            return ttSocketDto.fail("用户信息为空！");
        }

        String deviceNo = redisCache.getCacheMapValue(RedisConstants.TT_CHANNEL_DEVICE,ttSocketDto.getChannelId());
        Device device = deviceMapper.selectDeviceByDeviceNo(deviceNo);
        if(device==null){
            if(device==null){
                return ttSocketDto.fail(TTScoketConstants.CODE_UNATIVE,"设备不存在！");
            }
        }
        Account account = new Account();
        account.setMechantId(device.getMechId());
        account.setAccountChannel(ttSocketDto.getPackageName());
        account.setAccountNo(userInfo.getUserId());
        account = accountMapper.selectAccount(account);
        if(account==null){
            return ttSocketDto.fail("账号信息不存在！");
        }
        JSONObject obj = getAppConfig(account,device);
        return ttSocketDto.ok(obj,"获取配置信息成功！");
    }

    public JSONObject getAppConfig(Account account,Device device) {
        Follow follow = new Follow();
        Integer remainNum = account.getFollowNumber();
        String dateStr = DateUtils.dateTime();
        String key = account.getAccountNo()+"@"+ account.getMechantId()+"@"+ account.getAccountChannel();
        //当天正在关注数量
        Integer applyNum = redisCache.getCacheMapValue(dateStr + TTContants.cache_key_tt_day_apply_number,key);
        if(applyNum!=null){
            remainNum-=applyNum;
        }
        //当天关注成功数量
        Integer followNum = redisCache.getCacheMapValue(dateStr + TTContants.cache_key_tt_day_follow_number,key);
        if(followNum!=null){
            remainNum-=followNum;
        }
        //设置每日关注数量
        follow.setNumber(remainNum);
        follow.setSex(device.getFollowSex());
        follow.setMinAge(device.getFollowMinAge());
        follow.setMaxAge(device.getFollowMaxAge());
        follow.setMinSpeed(1000L * device.getFollowMinSpeed());
        follow.setMaxSpeed(1000L * device.getFollowMaxSpeed());
        follow.setSleepTime(1000L * device.getFollowSleepTime());
        follow.setSleepCount(device.getFollowSleepCount());
        Match match = new Match();
        follow.setMatch(match);
        match.setNickname(device.getFollowMatchNickname());
        match.setSignature(device.getFollowMatchSignature());
        match.setComment(device.getFollowMatchComment());
        JSONObject obj = new JSONObject();
        obj.put("follow",follow);
        return obj;
    }


    @Autowired
    private AccountMapper accountMapper;
    /**
     * 账号登录
     * @param ttSocketDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TTSocketDto accountLogin(TTSocketDto ttSocketDto) {
        UserInfo userInfo = ttSocketDto.getUserInfo();
        if(userInfo ==null){
            return ttSocketDto.fail("账号信息为空!");
        }
        if(StringUtils.isEmpty(userInfo.getUserName()) || StringUtils.isEmpty(userInfo.getUserId())){
            return ttSocketDto.fail("账号昵称为空");
        }
        Account account = new Account();
        String deviceNo = redisCache.getCacheMapValue(RedisConstants.TT_CHANNEL_DEVICE,ttSocketDto.getChannelId());
        Device device = deviceMapper.selectDeviceByDeviceNo(deviceNo);
        if(device==null){
            if(device==null){
                return ttSocketDto.fail("设备不存在！");
            }
        }

        Mechant mechant = mechantMapper.selectMechantByMechId(device.getMechId());
        if(mechant==null){
            return ttSocketDto.fail("商户不存在！");
        }
        if(YesOrNoStatus.NO.equals(mechant.getMechStatus())){
            return ttSocketDto.fail("商户被禁用！");
        }
        if(!ChannelPackage.hasChannel(mechant.getMechChannel(),ttSocketDto.getPackageName())){
            return ttSocketDto.fail("当前商户未开通相关渠道！");
        }
        account.setMechantId(device.getMechId());
        account.setAccountChannel(ttSocketDto.getPackageName());
        account.setAccountNo(userInfo.getUserId());
        Account dataAccount = accountMapper.selectAccount(account);
        if(dataAccount==null){
            account.setDeviceId(device.getDeviceId());
            account.setOwnId(device.getMechOwn());
            account.setAccountName(userInfo.getUserName());
            account.setAccountImgUrl(userInfo.getImageUrl());
            account.setAccountStatus(AccountStatus.LOGIN.val());
            account.setFollowNumber(device.getFollowNumber());
            accountMapper.insertAccount(account);
        }else{
            dataAccount.setDeviceId(device.getDeviceId());
            account.setOwnId(device.getMechOwn());
            dataAccount.setAccountName(userInfo.getUserName());
            account.setAccountImgUrl(userInfo.getImageUrl());
            account.setAccountStatus(AccountStatus.LOGIN.val());
            accountMapper.updateAccount(dataAccount);
        }
        return ttSocketDto.ok();
    }

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private MechantMapper mechantMapper;
    /**
     * 设备连接初始化
     * @param ttSocketDto
     * @return
     */
    private TTSocketDto actionDeviceLogin(TTSocketDto ttSocketDto) {
        String deviceNo = ttSocketDto.getAndroidId();
        if(deviceNo==null){
            return ttSocketDto.fail("设备编码为空！");
        }
        Device device = deviceMapper.selectDeviceByDeviceNo(deviceNo);
        if(device==null){
            return ttSocketDto.fail(TTScoketConstants.CODE_UNATIVE,"设备不存在！");
        }
        if(YesOrNoStatus.NO.equals(device.getDeviceStatus())){
            ttSocketDto.setMsg("设备编码为空!");
            return ttSocketDto.fail(TTScoketConstants.CODE_UNATIVE,"设备已被禁用！");
        }
        if(!DateUtils.ifNowBetween(device.getDeviceStartTime(),device.getDeviceEndTime())){
            ttSocketDto.setMsg("设备编码为空!");
            return ttSocketDto.fail(TTScoketConstants.CODE_UNATIVE,"设备到期或未激活！");
        }
        Mechant mechant = mechantMapper.selectMechantByMechId(device.getMechId());
        if(mechant==null){
            ttSocketDto.setMsg("设备编码为空!");
            return ttSocketDto.fail("商户不存在！");
        }
        if(YesOrNoStatus.NO.equals(mechant.getMechStatus())){
            ttSocketDto.setMsg("设备编码为空!");
            return ttSocketDto.fail("商户被禁用！");
        }
//        if(mechant.getMechChannel()==0){
//            return R.fail("当前商户未开通相关渠道！");
//        }
        redisCache.setCacheMapValue(RedisConstants.TT_CHANNEL_DEVICE,ttSocketDto.getChannelId(),ttSocketDto.getAndroidId());
        redisCache.setCacheMapValue(RedisConstants.TT_DEVICE_CHANNEL,ttSocketDto.getAndroidId(),ttSocketDto.getChannelId());
        return ttSocketDto.ok(ttSocketDto.getChannelId(),TTScoketConstants.CODE_ACTIVE,"设备连接成功！");
    }
    /**
     * 设备断开连接
     * @param ttSocketDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public TTSocketDto actionAccountDisconnect(TTSocketDto ttSocketDto) {
        String deviceNo = ttSocketDto.getAndroidId();
        if(deviceNo==null){
            return ttSocketDto.fail("设备编码为空！");
        }
        Device device = deviceMapper.selectDeviceByDeviceNo(deviceNo);
        if(device==null){
            return ttSocketDto.fail(TTScoketConstants.CODE_UNATIVE,"设备不存在！");
        }
        UserInfo userInfo = ttSocketDto.getUserInfo();
        if(userInfo ==null){
            return ttSocketDto.fail("账号信息为空!");
        }
        Account account = new Account();
        account.setMechantId(device.getMechId());
        account.setAccountChannel(ttSocketDto.getPackageName());
        account.setAccountNo(userInfo.getUserId());
        account = accountMapper.selectAccount(account);
        if(account==null){
            return ttSocketDto.fail("账号信息不存在！");
        }
        account.setAccountStatus(AccountStatus.UNLOGIN.val());
        accountMapper.updateAccount(account);
        return ttSocketDto.ok();
    }



}
