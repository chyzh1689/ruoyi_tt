package com.ruoyi.tt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.beans.Transient;

/**
 * 商户信息对象 tt_mechant
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
public class Mechant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键Id */
    private Long mechId;

    /** 父级Id */
    @Excel(name = "父级Id")
    private Long parentId;

    /** 商户名称 */
    @Excel(name = "商户名称")
    private String mechName;

    /** 商户编号 */
    @Excel(name = "商户编号")
    private String mechNo;

    /** 商户手机 */
    @Excel(name = "商户手机")
    private String mechPhone;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String mechMemo;

    /** 商户状态 */
    @Excel(name = "商户状态")
    private String mechStatus;

    /** 商户类型 0-商户 1-员工 */
    @Excel(name = "商户类型 0-商户 1-员工")
    private String mechType;

    /** 备注1 */
    @Excel(name = "备注1")
    private String mechMemo1;

    /** 备注2 */
    @Excel(name = "备注2")
    private String mechMemo2;

    /** 备注3 */
    @Excel(name = "备注3")
    private String mechMemo3;

    /** 备注4 */
    @Excel(name = "备注4")
    private String mechMemo4;

    /** 备注5 */
    @Excel(name = "备注5")
    private String mechMemo5;

    /** 备注5 */
    @Excel(name = "商户名称")
    private String parMechName;

    public void setMechId(Long mechId) 
    {
        this.mechId = mechId;
    }

    public Long getMechId() 
    {
        return mechId;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setMechName(String mechName) 
    {
        this.mechName = mechName;
    }

    public String getMechName() 
    {
        return mechName;
    }
    public void setMechNo(String mechNo) 
    {
        this.mechNo = mechNo;
    }

    public String getMechNo() 
    {
        return mechNo;
    }
    public void setMechPhone(String mechPhone) 
    {
        this.mechPhone = mechPhone;
    }

    public String getMechPhone() 
    {
        return mechPhone;
    }
    public void setMechMemo(String mechMemo) 
    {
        this.mechMemo = mechMemo;
    }

    public String getMechMemo() 
    {
        return mechMemo;
    }
    public void setMechStatus(String mechStatus) 
    {
        this.mechStatus = mechStatus;
    }

    public String getMechStatus() 
    {
        return mechStatus;
    }
    public void setMechType(String mechType) 
    {
        this.mechType = mechType;
    }

    public String getMechType() 
    {
        return mechType;
    }
    public void setMechMemo1(String mechMemo1) 
    {
        this.mechMemo1 = mechMemo1;
    }

    public String getMechMemo1() 
    {
        return mechMemo1;
    }
    public void setMechMemo2(String mechMemo2) 
    {
        this.mechMemo2 = mechMemo2;
    }

    public String getMechMemo2() 
    {
        return mechMemo2;
    }
    public void setMechMemo3(String mechMemo3) 
    {
        this.mechMemo3 = mechMemo3;
    }

    public String getMechMemo3() 
    {
        return mechMemo3;
    }
    public void setMechMemo4(String mechMemo4) 
    {
        this.mechMemo4 = mechMemo4;
    }

    public String getMechMemo4() 
    {
        return mechMemo4;
    }
    public void setMechMemo5(String mechMemo5) 
    {
        this.mechMemo5 = mechMemo5;
    }

    public String getMechMemo5() 
    {
        return mechMemo5;
    }

    public String getParMechName() {
        return parMechName;
    }

    public void setParMechName(String parMechName) {
        this.parMechName = parMechName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mechId", getMechId())
            .append("parentId", getParentId())
            .append("mechName", getMechName())
            .append("mechNo", getMechNo())
            .append("mechPhone", getMechPhone())
            .append("mechMemo", getMechMemo())
            .append("mechStatus", getMechStatus())
            .append("mechType", getMechType())
            .append("mechMemo1", getMechMemo1())
            .append("mechMemo2", getMechMemo2())
            .append("mechMemo3", getMechMemo3())
            .append("mechMemo4", getMechMemo4())
            .append("mechMemo5", getMechMemo5())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
