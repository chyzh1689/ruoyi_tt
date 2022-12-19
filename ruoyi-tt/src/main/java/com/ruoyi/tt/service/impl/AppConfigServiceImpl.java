package com.ruoyi.tt.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.tt.TTContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tt.mapper.AppConfigMapper;
import com.ruoyi.tt.domain.AppConfig;
import com.ruoyi.tt.service.IAppConfigService;
import com.ruoyi.common.core.text.Convert;

/**
 * 应用参数配置Service业务层处理
 * 
 * @author xxxxxx
 * @date 2022-12-17
 */
@Service
public class AppConfigServiceImpl implements IAppConfigService 
{
    @Autowired
    private AppConfigMapper appConfigMapper;

    /**
     * 查询应用参数配置
     * 
     * @param deviceId 应用参数配置主键
     * @return 应用参数配置
     */
    @Override
    public List<AppConfig> selectAppConfigByDeviceId(Long deviceId)
    {
        return appConfigMapper.selectAppConfigByDeviceId(deviceId);
    }

    @Override
    public AppConfig selectAppConfig(Long deviceId, String channelPackage, String appConfigCode) {
        return appConfigMapper.selectAppConfig(deviceId,channelPackage,appConfigCode);
    }
    @Override
    public List<AppConfig> selectDefaultAppConfigs(String channelPackage) {
        return selectAppConfigs(channelPackage, TTContants.VAL_PARENT_ID,TTContants.VAL_PARENT_ID);
    }

    @Override
    public List<AppConfig> selectDefaultAppConfigs(String channelPackage, Long mechId) {
        return selectAppConfigs(channelPackage, TTContants.VAL_PARENT_ID,mechId);
    }

    @Override
    public List<AppConfig> selectAppConfigs(String channelPackage, Long deviceId) {
        return appConfigMapper.selectAppConfigsByDeviceId(channelPackage,deviceId);
    }

    @Override
    public List<AppConfig> selectAppConfigs(String channelPackage, Long deviceId, Long mechId) {
        return appConfigMapper.selectAppConfigs(channelPackage,deviceId,mechId);
    }

    /**
     * 查询应用参数配置列表
     * 
     * @param appConfig 应用参数配置
     * @return 应用参数配置
     */
    @Override
    public List<AppConfig> selectAppConfigList(AppConfig appConfig)
    {
        return appConfigMapper.selectAppConfigList(appConfig);
    }

    /**
     * 新增应用参数配置
     * 
     * @param appConfig 应用参数配置
     * @return 结果
     */
    @Override
    public int insertAppConfig(AppConfig appConfig)
    {
        appConfig.setCreateTime(DateUtils.getNowDate());
        return appConfigMapper.insertAppConfig(appConfig);
    }

    /**
     * 修改应用参数配置
     * 
     * @param appConfig 应用参数配置
     * @return 结果
     */
    @Override
    public int updateAppConfig(AppConfig appConfig)
    {
        appConfig.setUpdateTime(DateUtils.getNowDate());
        return appConfigMapper.updateAppConfig(appConfig);
    }

    /**
     * 批量删除应用参数配置
     * 
     * @param deviceIds 需要删除的应用参数配置主键
     * @return 结果
     */
    @Override
    public int deleteAppConfigByDeviceIds(String deviceIds)
    {
        return appConfigMapper.deleteAppConfigByDeviceIds(Convert.toLongArray(deviceIds));
    }

    /**
     * 删除应用参数配置信息
     * 
     * @param deviceId 应用参数配置主键
     * @return 结果
     */
    @Override
    public int deleteAppConfigByDeviceId(Long deviceId)
    {
        return appConfigMapper.deleteAppConfigByDeviceId(deviceId);
    }


}
