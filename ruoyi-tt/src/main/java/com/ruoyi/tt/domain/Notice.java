package com.ruoyi.tt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关注信息对象 tt_notice
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
public class Notice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 账号Id */
    private Long accountId;

    /** 关注者Id */
    private String noticeId;

    /** 关注者账号 */
    @Excel(name = "关注者账号")
    private String noticeNo;

    /** 关注者昵称 */
    @Excel(name = "关注者昵称")
    private String noticeName;

    public void setAccountId(Long accountId) 
    {
        this.accountId = accountId;
    }

    public Long getAccountId() 
    {
        return accountId;
    }
    public void setNoticeId(String noticeId) 
    {
        this.noticeId = noticeId;
    }

    public String getNoticeId() 
    {
        return noticeId;
    }
    public void setNoticeNo(String noticeNo) 
    {
        this.noticeNo = noticeNo;
    }

    public String getNoticeNo() 
    {
        return noticeNo;
    }
    public void setNoticeName(String noticeName) 
    {
        this.noticeName = noticeName;
    }

    public String getNoticeName() 
    {
        return noticeName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("accountId", getAccountId())
            .append("noticeId", getNoticeId())
            .append("noticeNo", getNoticeNo())
            .append("noticeName", getNoticeName())
            .append("createTime", getCreateTime())
            .toString();
    }
}
