package com.ruoyi.tt.mapper;

import java.util.List;
import com.ruoyi.tt.domain.AppConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 应用参数配置Mapper接口
 * 
 * @author xxxxxx
 * @date 2022-12-17
 */
public interface AppConfigMapper 
{
    /**
     * 查询应用参数配置
     * 
     * @param deviceId 应用参数配置主键
     * @return 应用参数配置
     */
    public List<AppConfig> selectAppConfigByDeviceId(Long deviceId);


    public AppConfig selectAppConfig(@Param("deviceId") Long deviceId,
                                     @Param("channelPackage")String channelPackage,
                                     @Param("appConfigCode")String appConfigCode);

    /**
     * 查询应用参数配置列表
     * 
     * @param appConfig 应用参数配置
     * @return 应用参数配置集合
     */
    public List<AppConfig> selectAppConfigList(AppConfig appConfig);

    /**
     * 新增应用参数配置
     * 
     * @param appConfig 应用参数配置
     * @return 结果
     */
    public int insertAppConfig(AppConfig appConfig);

    /**
     * 修改应用参数配置
     * 
     * @param appConfig 应用参数配置
     * @return 结果
     */
    public int updateAppConfig(AppConfig appConfig);

    /**
     * 删除应用参数配置
     * 
     * @param deviceId 应用参数配置主键
     * @return 结果
     */
    public int deleteAppConfigByDeviceId(Long deviceId);

    /**
     * 批量删除应用参数配置
     * 
     * @param deviceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppConfigByDeviceIds(Long[] deviceIds);

    public int deleteAppConfig(@Param("deviceId") Long deviceId,
                               @Param("channelPackage")String channelPackage,
                               @Param("appConfigCode")String appConfigCode);



}
