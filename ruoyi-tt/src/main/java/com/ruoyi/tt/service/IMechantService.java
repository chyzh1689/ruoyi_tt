package com.ruoyi.tt.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.tt.domain.Mechant;

/**
 * 商户信息Service接口
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
public interface IMechantService 
{
    /**
     * 查询商户信息
     * 
     * @param mechId 商户信息主键
     * @return 商户信息
     */
    public Mechant selectMechantByMechId(Long mechId);

    /**
     * 查询商户信息列表
     * 
     * @param mechant 商户信息
     * @return 商户信息集合
     */
    public List<Mechant> selectMechantList(Mechant mechant);

    /**
     * 新增商户信息
     * 
     * @param mechant 商户信息
     * @return 结果
     */
    public AjaxResult insertMechant(Mechant mechant);

    /**
     * 修改商户信息
     * 
     * @param mechant 商户信息
     * @return 结果
     */
    public int updateMechant(Mechant mechant);

    /**
     * 批量删除商户信息
     * 
     * @param mechIds 需要删除的商户信息主键集合
     * @return 结果
     */
    public int deleteMechantByMechIds(String mechIds);

    /**
     * 删除商户信息信息
     * 
     * @param mechId 商户信息主键
     * @return 结果
     */
    public int deleteMechantByMechId(Long mechId);

    /**
     * 加载商户数据
     * @param mechant
     * @return
     */
    List<Ztree> selectMechTree(Mechant mechant);
}
