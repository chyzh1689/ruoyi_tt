package com.ruoyi.tt.mapper;

import java.util.List;
import com.ruoyi.tt.domain.Mechant;

/**
 * 商户信息Mapper接口
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
public interface MechantMapper 
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
    public int insertMechant(Mechant mechant);

    /**
     * 修改商户信息
     * 
     * @param mechant 商户信息
     * @return 结果
     */
    public int updateMechant(Mechant mechant);

    /**
     * 删除商户信息
     * 
     * @param mechId 商户信息主键
     * @return 结果
     */
    public int deleteMechantByMechId(Long mechId);

    /**
     * 批量删除商户信息
     * 
     * @param mechIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMechantByMechIds(String[] mechIds);
}
