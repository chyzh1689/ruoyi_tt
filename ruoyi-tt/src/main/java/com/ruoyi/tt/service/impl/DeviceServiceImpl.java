package com.ruoyi.tt.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.tt.TTContants;
import com.ruoyi.tt.domain.AppConfig;
import com.ruoyi.tt.domain.Mechant;
import com.ruoyi.tt.enums.ChannelPackage;
import com.ruoyi.tt.enums.MechType;
import com.ruoyi.tt.mapper.MechantMapper;
import com.ruoyi.tt.service.IAppConfigService;
import com.ruoyi.tt.third.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tt.mapper.DeviceMapper;
import com.ruoyi.tt.domain.Device;
import com.ruoyi.tt.service.IDeviceService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备信息Service业务层处理
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private MechantMapper mechantMapper;
    @Autowired
    private ISysConfigService configService;
    /**
     * 查询设备信息
     * 
     * @param deviceId 设备信息主键
     * @return 设备信息
     */
    @Override
    public Device selectDeviceByDeviceId(Long deviceId)
    {
        return deviceMapper.selectDeviceByDeviceId(deviceId);
    }

    /**
     * 查询设备信息列表
     * 
     * @param device 设备信息
     * @return 设备信息
     */
    @Override
    public List<Device> selectDeviceList(Device device)    {
        return deviceMapper.selectDeviceList(device);
    }


    @Autowired
    private IAppConfigService appConfigService;
    /**
     * 新增设备信息
     * 
     * @param device 设备信息
     * @return 结果
     */
    @Override
    public int insertDevice(Device device)    {
        device.setCreateTime(DateUtils.getNowDate());
        Mechant mechant = mechantMapper.selectMechantByMechId(device.getMechOwn());
        if(MechType.MECH.val().equals(mechant.getMechType())){
            device.setMechId(device.getMechOwn());
        }else{
            device.setMechId(mechant.getParentId());
        }
        int cnt = deviceMapper.insertDevice(device);
        //根据商户当前渠道配置参数
        Integer mechChannel = mechant.getMechChannel();
        for (String code : ChannelPackage.codeMap().keySet()) {
            if(ChannelPackage.hasChannel(mechChannel,code)){
                List<AppConfig> appConfigs = appConfigService.selectDefaultAppConfigs(code);
                for (AppConfig appConfig : appConfigs) {
                    appConfig.setDeviceId(device.getDeviceId());
                    appConfig.setMechId(device.getMechId());
                    appConfigService.insertAppConfig(appConfig);
                }
            }
        }
        return cnt;
    }

    /**
     * 修改设备信息
     * 
     * @param device 设备信息
     * @return 结果
     */
    @Override
    public int updateDevice(Device device)  {
        device.setUpdateTime(DateUtils.getNowDate());
        Mechant mechant = mechantMapper.selectMechantByMechId(device.getMechOwn());
        if(MechType.MECH.val().equals(mechant.getMechType())){
            device.setMechId(device.getMechOwn());
        }else{
            device.setMechId(mechant.getParentId());
        }
        //根据商户当前渠道配置参数
        this.updateAppConfigForDevice(device, mechant.getMechChannel());
        return deviceMapper.updateDevice(device);
    }

    @Override
    public void updateAppConfigForDevice(Device device, Integer mechChannel) {
        for (String code : ChannelPackage.codeMap().keySet()) {
            if(ChannelPackage.hasChannel(mechChannel,code)){
                List<AppConfig> appConfigs = appConfigService.selectDefaultAppConfigs(code);
                for (AppConfig appConfig : appConfigs) {
                    AppConfig dataAppConfig = appConfigService.selectAppConfig(device.getDeviceId(),
                            code, appConfig.getAppConfigCode());
                    if(dataAppConfig==null){
                        appConfig.setDeviceId(device.getDeviceId());
                        appConfig.setMechId(device.getMechId());
                        appConfigService.insertAppConfig(appConfig);
                    }
                }
            }
        }
    }

    /**
     * 批量删除设备信息
     * 
     * @param deviceIds 需要删除的设备信息主键
     * @return 结果
     */
    @Override
    public int deleteDeviceByDeviceIds(String deviceIds)
    {
        return deviceMapper.deleteDeviceByDeviceIds(Convert.toStrArray(deviceIds));
    }

    /**
     * 删除设备信息信息
     * 
     * @param deviceId 设备信息主键
     * @return 结果
     */
    @Override
    public int deleteDeviceByDeviceId(Long deviceId)
    {
        return deviceMapper.deleteDeviceByDeviceId(deviceId);
    }
}
