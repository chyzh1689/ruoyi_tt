package com.ruoyi.tt.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 设备信息对象 tt_device
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
@Data
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
    private String deviceName;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String deviceNo;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String deviceStatus;

    /** 设备激活日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "设备激活日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deviceStartTime;

    /** 设备到期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "设备到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deviceEndTime;

    /** 备注1 */
    @Excel(name = "备注1")
    private String deviceMemo1;

    /** 备注2 */
    @Excel(name = "备注2")
    private String deviceMemo2;

    private String mechName;
    private String mechOwnName;
    private Integer mechChannel;
    private String channels;

}
