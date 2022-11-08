package com.ruoyi.tt.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关注信息对象 tt_notice
 * 
 * @author xxxxxx
 * @date 2022-11-08
 */
@Data
public class Notice extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 被关注者账号 */
    private String noticeNo;

    /** 所属商户Id */
    private Long mechantId;

    /** 来源渠道 */
    private String channelPackage;

    /** 被关注者昵称 */
    @Excel(name = "被关注者昵称")
    private String noticeName;

    /** 被关注者头像 */
    @Excel(name = "被关注者头像")
    private String noticeImgRul;

    /** 被关注者区域 */
    @Excel(name = "被关注者区域")
    private String noticeLocation;

    /** 0--释放是否被关注或者回关 */
    @Excel(name = "0--释放是否被关注或者回关")
    private Long noticeFlag;

    /** 所属账号 */
    @Excel(name = "所属账号")
    private String ownNo;

    /** 所属昵称 */
    @Excel(name = "所属昵称")
    private String ownName;

    /** 所属头像 */
    @Excel(name = "所属头像")
    private String ownImgUrl;

    /** 设备id */
    @Excel(name = "设备id")
    private Long deviceId;

}
