package com.ruoyi.tt.third;

import com.ruoyi.common.constant.RedisConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.tt.TTContants;
import com.ruoyi.tt.domain.Account;
import com.ruoyi.tt.domain.Device;
import com.ruoyi.tt.domain.Mechant;
import com.ruoyi.tt.enums.AccountStatus;
import com.ruoyi.tt.enums.ChannelPackage;
import com.ruoyi.tt.enums.YesOrNoStatus;
import com.ruoyi.tt.mapper.AccountMapper;
import com.ruoyi.tt.mapper.DeviceMapper;
import com.ruoyi.tt.mapper.MechantMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            case TTScoketConstants.ACTION_FOLLOW: return this.appConfig(ttSocketDto);
            //关注列表
            case TTScoketConstants.ACTION_follow_list: return this.appConfig(ttSocketDto);
            //聊天消息
            case TTScoketConstants.ACTION_CHAT_MSG: return this.appConfig(ttSocketDto);
            //聊天指令
            case TTScoketConstants.ACTION_CHAT_CMD: return this.appConfig(ttSocketDto);
            //同步心跳
            case TTScoketConstants.ACTION_SYNC: return this.appConfig(ttSocketDto);
            //操作指令
            case TTScoketConstants.ACTION_CMD: return this.appConfig(ttSocketDto);
            //获取粉丝列表
            case TTScoketConstants.ACTION_USER_LIST: return this.appConfig(ttSocketDto);
            default:
        }
        return ttSocketDto;
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
        Follow follow = new Follow();
        follow.setNumber(Integer.parseInt(configService.selectConfigByKey(TTContants.CACHE_KEY_TT_FOLLOW_NUMBER)));
        follow.setMinSpeed(Long.parseLong(configService.selectConfigByKey(TTContants.CACHE_KEY_TT_FOLLOW_MINSPEED)));
        follow.setMaxSpeed(Long.parseLong(configService.selectConfigByKey(TTContants.CACHE_KEY_TT_FOLLOW_MAXSPEED)));
        follow.setSleepTime(Long.parseLong(configService.selectConfigByKey(TTContants.CACHE_KEY_TT_FOLLOW_SLEEPTIME)));
        follow.setSleepCount(Integer.parseInt(configService.selectConfigByKey(TTContants.CACHE_KEY_TT_FOLLOW_SLEEPCOUNT)));
        Match match = new Match();
        follow.setMatch(match);
        match.setNickname(configService.selectConfigByKey(TTContants.CACHE_KEY_TT_FOLLOW_NICKNAME));
        match.setSignature(configService.selectConfigByKey(TTContants.CACHE_KEY_TT_FOLLOW_NICKNAME));
        match.setComment(configService.selectConfigByKey(TTContants.CACHE_KEY_TT_FOLLOW_COMMENT));
        return ttSocketDto.ok(follow,"获取配置信息成功！");
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
            account.setAccountName(userInfo.getUserName());
            account.setAccountImgUrl(userInfo.getImageUrl());
            account.setAccountStatus(AccountStatus.LOGIN.val());
            accountMapper.insertAccount(account);
        }else{
            dataAccount.setDeviceId(device.getDeviceId());
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
            return ttSocketDto.fail("设备不存在！");
        }
        if(YesOrNoStatus.NO.equals(device.getDeviceStatus())){
            ttSocketDto.setMsg("设备编码为空!");
            return ttSocketDto.fail("设备已被禁用！");
        }
        if(!DateUtils.ifNowBetween(device.getDeviceStartTime(),device.getDeviceEndTime())){
            ttSocketDto.setMsg("设备编码为空!");
            return ttSocketDto.fail("设备到期或未激活！");
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
        return ttSocketDto.ok(ttSocketDto.getChannelId(),"设备连接成功！");
    }
    /**
     * 设备断开连接
     * @param ttSocketDto
     * @return
     */
    private TTSocketDto actionAccountDisconnect(TTSocketDto ttSocketDto) {
        return ttSocketDto.ok();
    }



}
