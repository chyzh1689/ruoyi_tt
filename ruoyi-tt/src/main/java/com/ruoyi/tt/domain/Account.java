package com.ruoyi.tt.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 账号信息对象 tt_account
 * 
 * @author xxxxxx
 * @date 2022-11-08
 */
@Data
public class Account extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /** 账号Id */
    private Long accountId;

    /** 设备Id */
    @Excel(name = "设备Id")
    private Long deviceId;

    /** 商户Id */
    @Excel(name = "商户Id")
    private Long mechantId;
    /** 员工Id */
    @Excel(name = "员工Id")
    private Long ownId;
    /** 账号 */
    @Excel(name = "账号")
    private String accountNo;

    /**每日关注数量**/
    @Excel(name="每日关注数量")
    private Integer followNumber;

    /** 账号昵称 */
    @Excel(name = "账号昵称")
    private String accountName;

    /** 账号头像 */
    @Excel(name = "账号头像")
    private String accountImgUrl;

    /** 账号渠道 */
    @Excel(name = "账号渠道")
    private String accountChannel;

    /** 账号状态 */
    @Excel(name = "账号状态")
    private String accountStatus;

    /** 备注1 */
    @Excel(name = "备注1")
    private String accountMemo1;

    /** 备注2 */
    @Excel(name = "备注2")
    private String accountMemo2;

    private String deviceShow;

    private String mechShow;

    /**当天申请数量**/
    private Integer dayApplyNum;
    /**当天关注数量**/
    private Integer dayFollowNum;

}
