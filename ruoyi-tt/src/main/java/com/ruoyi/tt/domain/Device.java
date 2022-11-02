package com.ruoyi.tt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备信息对象 tt_device
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
public class Device extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 设备Id */
    private Long deviceId;

    /** 所属商户 */
    @Excel(name = "所属商户")
    private Long mechId;

    /** 所属者(商户下某一员工) */
    @Excel(name = "所属者(商户下某一员工)")
    private Long mechOwn;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviveName;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String deviceNo;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String deviceStatus;

    /** 备注1 */
    @Excel(name = "备注1")
    private String deviceMemo1;

    /** 备注2 */
    @Excel(name = "备注2")
    private String deviceMemo2;

    /** 备注3 */
    @Excel(name = "备注3")
    private String deviceMemo3;

    /** 备注4 */
    @Excel(name = "备注4")
    private String deviceMemo4;

    /** 备注5 */
    @Excel(name = "备注5")
    private String deviceMemo5;

    private String mechName;
    private String mechOwnName;

    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }
    public void setMechId(Long mechId) 
    {
        this.mechId = mechId;
    }

    public Long getMechId() 
    {
        return mechId;
    }
    public void setMechOwn(Long mechOwn) 
    {
        this.mechOwn = mechOwn;
    }

    public Long getMechOwn() 
    {
        return mechOwn;
    }
    public void setDeviveName(String deviveName) 
    {
        this.deviveName = deviveName;
    }

    public String getDeviveName() 
    {
        return deviveName;
    }
    public void setDeviceNo(String deviceNo) 
    {
        this.deviceNo = deviceNo;
    }

    public String getDeviceNo() 
    {
        return deviceNo;
    }
    public void setDeviceStatus(String deviceStatus) 
    {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceStatus() 
    {
        return deviceStatus;
    }
    public void setDeviceMemo1(String deviceMemo1) 
    {
        this.deviceMemo1 = deviceMemo1;
    }

    public String getDeviceMemo1() 
    {
        return deviceMemo1;
    }
    public void setDeviceMemo2(String deviceMemo2) 
    {
        this.deviceMemo2 = deviceMemo2;
    }

    public String getDeviceMemo2() 
    {
        return deviceMemo2;
    }
    public void setDeviceMemo3(String deviceMemo3) 
    {
        this.deviceMemo3 = deviceMemo3;
    }

    public String getDeviceMemo3() 
    {
        return deviceMemo3;
    }
    public void setDeviceMemo4(String deviceMemo4) 
    {
        this.deviceMemo4 = deviceMemo4;
    }

    public String getDeviceMemo4() 
    {
        return deviceMemo4;
    }
    public void setDeviceMemo5(String deviceMemo5) 
    {
        this.deviceMemo5 = deviceMemo5;
    }

    public String getDeviceMemo5() 
    {
        return deviceMemo5;
    }

    public String getMechName() {
        return mechName;
    }

    public void setMechName(String mechName) {
        this.mechName = mechName;
    }

    public String getMechOwnName() {
        return mechOwnName;
    }

    public void setMechOwnName(String mechOwnName) {
        this.mechOwnName = mechOwnName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deviceId", getDeviceId())
            .append("mechId", getMechId())
            .append("mechOwn", getMechOwn())
            .append("deviveName", getDeviveName())
            .append("deviceNo", getDeviceNo())
            .append("deviceStatus", getDeviceStatus())
            .append("deviceMemo1", getDeviceMemo1())
            .append("deviceMemo2", getDeviceMemo2())
            .append("deviceMemo3", getDeviceMemo3())
            .append("deviceMemo4", getDeviceMemo4())
            .append("deviceMemo5", getDeviceMemo5())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
