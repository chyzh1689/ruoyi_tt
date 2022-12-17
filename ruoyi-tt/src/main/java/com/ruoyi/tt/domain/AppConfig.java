package com.ruoyi.tt.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 应用参数配置对象 tt_app_config
 * 
 * @author xxxxxx
 * @date 2022-12-17
 */
@Data
public class AppConfig extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 设备Id */
    private Long deviceId;

    /** 商户Id */
    private Long mechId;

    /** 来源渠道 */
    private String channelPackage;

    /** 代码 */
    private String appConfigCode;

    /** 值 */
    @Excel(name = "值")
    private String appConfigValue;

    /** 标签 */
    @Excel(name = "标签")
    private String appConfigLabel;

    /** 顺序 */
    @Excel(name = "顺序")
    private Long appConfigSort;

    @Excel(name = "设备")
    private String deviceShow;

    @Excel(name = "商户")
    private String mechShow;
}
