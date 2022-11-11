package com.ruoyi.tt.domain;

import lombok.Data;
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
@Data
public class Mechant extends BaseEntity{
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

    /** 商户可用渠道 */
    @Excel(name = "商户可用渠道")
    private Integer mechChannel;

    /** 备注1 */
    @Excel(name = "备注1")
    private String mechMemo1;

    /** 备注2 */
    @Excel(name = "备注2")
    private String mechMemo2;

    /** 备注5 */
    @Excel(name = "商户名称")
    private String parMechName;

    /**用于界面**/
    private String channels;

}
