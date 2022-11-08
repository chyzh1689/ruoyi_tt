package com.ruoyi.tt.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 消息聊天记录对象 tt_msg
 * 
 * @author xxxxxx
 * @date 2022-11-08
 */
@Data
public class Msg extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 消息Id */
    private Long msgId;

    /** 商户Id */
    @Excel(name = "商户Id")
    private Long mechId;

    /** 设备Id */
    @Excel(name = "设备Id")
    private Long deviceId;

    /** 员工Id */
    @Excel(name = "员工Id")
    private Long ownId;

    /** 消息渠道 */
    @Excel(name = "消息渠道")
    private String msgChannel;

    /** 关注者账号 */
    @Excel(name = "关注者账号")
    private String accountNo;

    /** 关注者昵称 */
    @Excel(name = "关注者昵称")
    private String accountName;

    /** 被关注着账号 */
    @Excel(name = "被关注着账号")
    private String noticeNo;

    /** 被关注昵称 */
    @Excel(name = "被关注昵称")
    private String noticeName;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String msgContent;

}
