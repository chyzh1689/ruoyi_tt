package com.ruoyi.tt.service.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.RedisConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.tt.domain.Device;
import com.ruoyi.tt.mapper.DeviceMapper;
import com.ruoyi.tt.third.SocketServerHandler;
import com.ruoyi.tt.third.TTScoketConstants;
import com.ruoyi.tt.third.TTSocketDto;
import com.ruoyi.tt.third.TtSocketService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tt.mapper.AccountMapper;
import com.ruoyi.tt.domain.Account;
import com.ruoyi.tt.service.IAccountService;
import com.ruoyi.common.core.text.Convert;

/**
 * 账号信息Service业务层处理
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
@Service
public class AccountServiceImpl implements IAccountService 
{
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 查询账号信息
     * 
     * @param accountId 账号信息主键
     * @return 账号信息
     */
    @Override
    public Account selectAccountByAccountId(Long accountId)
    {
        return accountMapper.selectAccountByAccountId(accountId);
    }

    /**
     * 查询账号信息列表
     * 
     * @param account 账号信息
     * @return 账号信息
     */
    @Override
    public List<Account> selectAccountList(Account account)
    {
        return accountMapper.selectAccountList(account);
    }

    /**
     * 新增账号信息
     * 
     * @param account 账号信息
     * @return 结果
     */
    @Override
    public int insertAccount(Account account)
    {
        account.setCreateTime(DateUtils.getNowDate());
        return accountMapper.insertAccount(account);
    }

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private TtSocketService ttSocketService;
    /**
     * 修改账号信息
     * 
     * @param account 账号信息
     * @return 结果
     */
    @Override
    public int updateAccount(Account account){
        account.setUpdateTime(DateUtils.getNowDate());
        Account dataAccount = accountMapper.selectAccountByAccountId(account.getAccountId());
        int count = accountMapper.updateAccount(account);
        if(dataAccount!=null && account.getFollowNumber()!=null &&
                !account.getFollowNumber().equals(dataAccount.getFollowNumber())){
            account.setAccountChannel(dataAccount.getAccountChannel());
            account.setMechantId(dataAccount.getMechantId());
            Device device = deviceMapper.selectDeviceByDeviceId(dataAccount.getDeviceId());
            if(device!=null){
                String  channelId = redisCache.getCacheMapValue(RedisConstants.TT_DEVICE_CHANNEL,
                        device.getDeviceNo());
                if(channelId==null){
                    return count;
                }
                Channel channel = SocketServerHandler.getChannelMap().get(channelId);
                if(channel!=null){
                    JSONObject appConfig = ttSocketService.getAppConfig(account);
                    TTSocketDto ttSocketDto = new TTSocketDto();
                    ttSocketDto.setAction(TTScoketConstants.ACTION_APP_CONFIG);
                    ttSocketDto.setPackageName(TTScoketConstants.getChannelMap().get(account.getAccountChannel()));
                    ttSocketDto.setAndroidId(device.getDeviceNo());
                    ttSocketDto.setData(appConfig);
                    channel.writeAndFlush(JSONObject.toJSONString(ttSocketDto));
                }

            }
        }
        return count;
    }

    /**
     * 批量删除账号信息
     * 
     * @param accountIds 需要删除的账号信息主键
     * @return 结果
     */
    @Override
    public int deleteAccountByAccountIds(String accountIds)
    {
        return accountMapper.deleteAccountByAccountIds(Convert.toStrArray(accountIds));
    }

    /**
     * 删除账号信息信息
     * 
     * @param accountId 账号信息主键
     * @return 结果
     */
    @Override
    public int deleteAccountByAccountId(Long accountId)
    {
        return accountMapper.deleteAccountByAccountId(accountId);
    }
}
