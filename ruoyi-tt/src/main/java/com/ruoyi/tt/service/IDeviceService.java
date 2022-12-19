package com.ruoyi.tt.service;

import java.util.List;
import com.ruoyi.tt.domain.Device;

/**
 * 设备信息Service接口
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
public interface IDeviceService 
{
    /**
     * 查询设备信息
     * 
     * @param deviceId 设备信息主键
     * @return 设备信息
     */
    public Device selectDeviceByDeviceId(Long deviceId);

    /**
     * 查询设备信息列表
     * 
     * @param device 设备信息
     * @return 设备信息集合
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 新增设备信息
     * 
     * @param device 设备信息
     * @return 结果
     */
    public int insertDevice(Device device);

    /**
     * 修改设备信息
     * 
     * @param device 设备信息
     * @return 结果
     */
    public int updateDevice(Device device);

    void updateAppConfigForDevice(Device device, Integer mechChannel);

    /**
     * 批量删除设备信息
     * 
     * @param deviceIds 需要删除的设备信息主键集合
     * @return 结果
     */
    public int deleteDeviceByDeviceIds(String deviceIds);

    /**
     * 删除设备信息信息
     * 
     * @param deviceId 设备信息主键
     * @return 结果
     */
    public int deleteDeviceByDeviceId(Long deviceId);
}
