package com.ruoyi.tt.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.tt.TTContants;
import com.ruoyi.tt.domain.Mechant;
import com.ruoyi.tt.enums.MechType;
import com.ruoyi.tt.mapper.MechantMapper;
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

    /**
     * 新增设备信息
     * 
     * @param device 设备信息
     * @return 结果
     */
    @Override
    public int insertDevice(Device device)    {
        device.setCreateTime(DateUtils.getNowDate());
        if(device.getMechOwn()!=null){
            Mechant mechant = mechantMapper.selectMechantByMechId(device.getMechOwn());
            if(MechType.MECH.val().equals(mechant.getMechType())){
                device.setMechId(device.getMechOwn());
            }else{
                device.setMechId(mechant.getParentId());
            }
        }

        return deviceMapper.insertDevice(device);
    }

    /**
     * 修改设备信息
     * 
     * @param device 设备信息
     * @return 结果
     */
    @Override
    public int updateDevice(Device device)
    {
        device.setUpdateTime(DateUtils.getNowDate());
        return deviceMapper.updateDevice(device);
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
