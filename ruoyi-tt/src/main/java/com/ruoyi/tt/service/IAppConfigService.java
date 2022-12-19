package com.ruoyi.tt.service;

import java.util.List;
import com.ruoyi.tt.domain.AppConfig;

/**
 * 应用参数配置Service接口
 * 
 * @author xxxxxx
 * @date 2022-12-17
 */
public interface IAppConfigService 
{
    /**
     * 查询应用参数配置
     * 
     * @param deviceId 应用参数配置主键
     * @return 应用参数配置
     */
    List<AppConfig> selectAppConfigByDeviceId(Long deviceId);

    /**
     * 查询应用参数配置
     *
     * @param deviceId 应用参数配置主键
     * @return 应用参数配置
     */
    AppConfig selectAppConfig(Long deviceId, String channelPackage, String appConfigCode);

    /**
     * 查询应用参数配置列表
     * 
     * @param appConfig 应用参数配置
     * @return 应用参数配置集合
     */
    List<AppConfig> selectAppConfigList(AppConfig appConfig);

    /**
     * 新增应用参数配置
     * 
     * @param appConfig 应用参数配置
     * @return 结果
     */
    int insertAppConfig(AppConfig appConfig);

    /**
     * 修改应用参数配置
     * 
     * @param appConfig 应用参数配置
     * @return 结果
     */
    public int updateAppConfig(AppConfig appConfig);

    /**
     * 批量删除应用参数配置
     * 
     * @param deviceIds 需要删除的应用参数配置主键集合
     * @return 结果
     */
    public int deleteAppConfigByDeviceIds(String deviceIds);

    /**
     * 删除应用参数配置信息
     * 
     * @param deviceId 应用参数配置主键
     * @return 结果
     */
    public int deleteAppConfigByDeviceId(Long deviceId);

    /**
     * 获取默认的应用配置参数配置
     * @return
     */
    List<AppConfig> selectDefaultAppConfigs(String channelPackage);

    List<AppConfig> selectDefaultAppConfigs(String channelPackage,Long mechId);

    List<AppConfig> selectAppConfigs(String channelPackage,Long deviceId);

    List<AppConfig> selectAppConfigs(String channelPackage,Long deviceId,Long mechId);


}
