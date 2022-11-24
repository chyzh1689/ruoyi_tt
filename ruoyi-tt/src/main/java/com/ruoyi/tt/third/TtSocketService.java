package com.ruoyi.tt.third;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.tt.domain.Account;
import com.ruoyi.tt.domain.Device;
import com.ruoyi.tt.domain.Mechant;
import com.ruoyi.tt.enums.AccountStatus;
import com.ruoyi.tt.enums.ChannelPackage;
import com.ruoyi.tt.enums.YesOrNoStatus;
import com.ruoyi.tt.mapper.AccountMapper;
import com.ruoyi.tt.mapper.DeviceMapper;
import com.ruoyi.tt.mapper.MechantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TtSocketService {
    /**处理消息**/
    R handle(Object msg,String channelId){
        if(msg instanceof String){
            TTSocketDto ttSocketDto = JSON.parseObject((String) msg, TTSocketDto.class);
            ttSocketDto.setChannelId(channelId);
            String action = ttSocketDto.getAction();
            String packageName = ttSocketDto.getPackageName();
            if(TTScoketConstants.PACKAGE_NAME_ADMIN.equals(packageName)){
                switch (action){
                    //设备初始化
                    case TTScoketConstants.ACTION_CLIENT_INIT: return this.actionClientInit(ttSocketDto);
                    case TTScoketConstants.ACTION_CLIENT_DISCONNECT: return this.actionClientDisconnect(ttSocketDto);
                    case TTScoketConstants.ACTION_CLIENT_MESSAGE: return this.actionClientMessage(ttSocketDto);
                    default:
                }
            }else if(TTScoketConstants.PACKAGE_NAME_TT.equals(packageName)){
                ttSocketDto.setChannelPackage(ChannelPackage.TT.name());
                return this.accountLogin(ttSocketDto);
            }
        }
        return R.ok();
    }

    /**
     * 消息处理
     * @param ttSocketDto
     * @return
     */
    private R actionClientMessage(TTSocketDto ttSocketDto) {
        Message message = ttSocketDto.getMessage();
        if(message==null){
            return R.fail("消息处理对象为空！");
        }
        String method = message.getMethod();
        if(StringUtils.isEmpty(method)){
            return R.fail("要处理的消息为空！");
        }
        switch (method){
            //设备初始化
            case TTScoketConstants.METHOD_APP_CONFIG: return this.methodAppConfig(ttSocketDto);
            default:
        }
        return R.ok();
    }

    private R methodAppConfig(TTSocketDto ttSocketDto) {

        return R.ok();
    }

    @Autowired
    private AccountMapper accountMapper;
    /**
     * 账号登录
     * @param ttSocketDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public R accountLogin(TTSocketDto ttSocketDto) {
        UserInfo userInfo = ttSocketDto.getUserInfo();
        if(userInfo ==null){
            return R.fail("账号信息为空!");
        }
        if(StringUtils.isEmpty(userInfo.getUserName()) || StringUtils.isEmpty(userInfo.getUserId())){
            return R.fail("账号昵称为空");
        }
        Account account = new Account();
        String deviceNo = SocketServerHandler.getChannelToDevice().get(ttSocketDto.getChannelId());
        Device device = deviceMapper.selectDeviceByDeviceNo(deviceNo);
        if(device==null){
            if(device==null){
                return R.fail("设备不存在！");
            }
        }

        Mechant mechant = mechantMapper.selectMechantByMechId(device.getMechId());
        if(mechant==null){
            return R.fail("商户不存在！");
        }
        if(YesOrNoStatus.NO.equals(mechant.getMechStatus())){
            return R.fail("商户被禁用！");
        }
        if(!ChannelPackage.hasChannel(mechant.getMechChannel(),ttSocketDto.getChannelPackage())){
            return R.fail("当前商户未开通相关渠道！");
        }
        account.setMechantId(device.getMechId());
        account.setAccountChannel(ttSocketDto.getChannelPackage());
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
        return R.ok();
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
    private R actionClientInit(TTSocketDto ttSocketDto) {
        String deviceNo = ttSocketDto.getAndroidId();
        if(deviceNo==null){
            return R.fail("设备编码为空!");
        }
        Device device = deviceMapper.selectDeviceByDeviceNo(deviceNo);
        if(device==null){
            return R.fail("设备不存在！");
        }
        if(YesOrNoStatus.NO.equals(device.getDeviceStatus())){
            return R.fail("设备已被禁用！");
        }
        if(!DateUtils.ifNowBetween(device.getDeviceStartTime(),device.getDeviceEndTime())){
            return R.fail("设备到期或未激活！");
        }
        Mechant mechant = mechantMapper.selectMechantByMechId(device.getMechId());
        if(mechant==null){
            return R.fail("商户不存在！");
        }
        if(YesOrNoStatus.NO.equals(mechant.getMechStatus())){
            return R.fail("商户被禁用！");
        }
//        if(mechant.getMechChannel()==0){
//            return R.fail("当前商户未开通相关渠道！");
//        }
        SocketServerHandler.getDeviceToChannel().put(ttSocketDto.getAndroidId(),ttSocketDto.getChannelId());
        SocketServerHandler.getChannelToDevice().put(ttSocketDto.getChannelId(),ttSocketDto.getAndroidId());
        return R.ok(ttSocketDto.getChannelId(),"设备连接成功！");
    }
    /**
     * 设备断开连接
     * @param ttSocketDto
     * @return
     */
    private R actionClientDisconnect(TTSocketDto ttSocketDto) {

        return R.ok();
    }



}
