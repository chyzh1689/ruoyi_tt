package com.ruoyi.tt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 账号信息对象 tt_account
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
public class Account extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 账号Id */
    private Long accountId;

    /** 设备Id */
    @Excel(name = "设备Id")
    private Long deviceId;

    /** 账号 */
    @Excel(name = "账号")
    private String accountNo;

    /** 账号昵称 */
    @Excel(name = "账号昵称")
    private String accountName;

    /** 账号类型 */
    @Excel(name = "账号类型")
    private String accountType;

    /** 账号状态 */
    @Excel(name = "账号状态")
    private String accountStatus;

    /** 备注1 */
    @Excel(name = "备注1")
    private String accountMemo1;

    /** 备注2 */
    @Excel(name = "备注2")
    private String accountMemo2;

    /** 备注3 */
    @Excel(name = "备注3")
    private String accountMemo3;

    /** 备注4 */
    @Excel(name = "备注4")
    private String accountMemo4;

    /** 备注5 */
    @Excel(name = "备注5")
    private String accountMemo5;

    public void setAccountId(Long accountId) 
    {
        this.accountId = accountId;
    }

    public Long getAccountId() 
    {
        return accountId;
    }
    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }
    public void setAccountNo(String accountNo) 
    {
        this.accountNo = accountNo;
    }

    public String getAccountNo() 
    {
        return accountNo;
    }
    public void setAccountName(String accountName) 
    {
        this.accountName = accountName;
    }

    public String getAccountName() 
    {
        return accountName;
    }
    public void setAccountType(String accountType) 
    {
        this.accountType = accountType;
    }

    public String getAccountType() 
    {
        return accountType;
    }
    public void setAccountStatus(String accountStatus) 
    {
        this.accountStatus = accountStatus;
    }

    public String getAccountStatus() 
    {
        return accountStatus;
    }
    public void setAccountMemo1(String accountMemo1) 
    {
        this.accountMemo1 = accountMemo1;
    }

    public String getAccountMemo1() 
    {
        return accountMemo1;
    }
    public void setAccountMemo2(String accountMemo2) 
    {
        this.accountMemo2 = accountMemo2;
    }

    public String getAccountMemo2() 
    {
        return accountMemo2;
    }
    public void setAccountMemo3(String accountMemo3) 
    {
        this.accountMemo3 = accountMemo3;
    }

    public String getAccountMemo3() 
    {
        return accountMemo3;
    }
    public void setAccountMemo4(String accountMemo4) 
    {
        this.accountMemo4 = accountMemo4;
    }

    public String getAccountMemo4() 
    {
        return accountMemo4;
    }
    public void setAccountMemo5(String accountMemo5) 
    {
        this.accountMemo5 = accountMemo5;
    }

    public String getAccountMemo5() 
    {
        return accountMemo5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("accountId", getAccountId())
            .append("deviceId", getDeviceId())
            .append("accountNo", getAccountNo())
            .append("accountName", getAccountName())
            .append("accountType", getAccountType())
            .append("accountStatus", getAccountStatus())
            .append("accountMemo1", getAccountMemo1())
            .append("accountMemo2", getAccountMemo2())
            .append("accountMemo3", getAccountMemo3())
            .append("accountMemo4", getAccountMemo4())
            .append("accountMemo5", getAccountMemo5())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
