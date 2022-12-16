package com.ruoyi.tt.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.constant.CfgConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.tt.TTContants;
import com.ruoyi.tt.enums.ChannelPackage;
import com.ruoyi.tt.enums.MechType;
import com.ruoyi.tt.enums.YesOrNoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tt.mapper.MechantMapper;
import com.ruoyi.tt.domain.Mechant;
import com.ruoyi.tt.service.IMechantService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商户信息Service业务层处理
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
@Service
public class MechantServiceImpl implements IMechantService {
    @Autowired
    private MechantMapper mechantMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private SysPasswordService passwordService;
    /**
     * 查询商户信息
     * 
     * @param mechId 商户信息主键
     * @return 商户信息
     */
    @Override
    public Mechant selectMechantByMechId(Long mechId)  {
        return mechantMapper.selectMechantByMechId(mechId);
    }

    /**
     * 查询商户信息列表
     * 
     * @param mechant 商户信息
     * @return 商户信息
     */
    @Override
    public List<Mechant> selectMechantList(Mechant mechant)    {
        return mechantMapper.selectMechantList(mechant);
    }

    /**
     * 新增商户信息
     * 
     * @param mechant 商户信息
     * @return 结果
     */
    @Override
    public AjaxResult insertMechant(Mechant mechant)    {
        SysUser user = userMapper.selectUserByLoginName(mechant.getMechNo());
        if(user!=null){
            return AjaxResult.error("商户号已经存在!");
        }
        user = new SysUser();
        String initPassword = configService.selectConfigByKey(CfgConstants.SYS_USER_INITPASSWORD);
        if(StringUtils.isEmpty(initPassword)){
            initPassword="123456";
        }
        Long[] roleIds = new Long[1];
        if(mechant.getParentId()==null||mechant.getParentId().equals(TTContants.VAL_PARENT_ID)){
            mechant.setParentId(TTContants.VAL_PARENT_ID);
            mechant.setMechType(MechType.MECH.val());
            user.setUserType(UserType.MECH.val());
            roleIds[0]=TTContants.VAL_ROLE_MECH_ID;
        }else{
            mechant.setMechType(MechType.EMPL.val());
            user.setUserType(UserType.EMPL.val());
            roleIds[0]=TTContants.VAL_ROLE_EMPL_ID;
        }
        user.setRoleIds(roleIds);
        user.setPhonenumber(mechant.getMechPhone());
        user.setSalt(ShiroUtils.randomSalt());
        user.setLoginName(mechant.getMechNo());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), initPassword, user.getSalt()));
        user.setUserName(mechant.getMechName());
        user.setCreateBy(mechant.getCreateBy());
        userService.insertUser(user);

        mechant.setCreateTime(DateUtils.getNowDate());
        mechant.setMechId(user.getUserId());
        mechantMapper.insertMechant(mechant);
        return AjaxResult.success();
    }

    /**
     * 修改商户信息
     * 
     * @param mechant 商户信息
     * @return 结果
     */
    @Override
    public int updateMechant(Mechant mechant){
        mechant.setUpdateTime(DateUtils.getNowDate());
        //设置可用渠道
        mechant.setMechChannel(0);
        String channels = mechant.getChannels();
        if(StringUtils.isNotEmpty(channels)){
            for (String code : channels.split(",")) {
                mechant.setMechChannel(ChannelPackage.addChannel(mechant.getMechChannel(),code));
            }
        }
        SysUser user = userMapper.selectUserById(mechant.getMechId());
        user.setUpdateTime(mechant.getUpdateTime());
        user.setPhonenumber(mechant.getMechPhone());
        user.setUserName(mechant.getMechName());
        userMapper.updateUser(user);
        return mechantMapper.updateMechant(mechant);
    }

    /**
     * 批量删除商户信息
     * 
     * @param mechIds 需要删除的商户信息主键
     * @return 结果
     */
    @Override
    public int deleteMechantByMechIds(String mechIds)  {
        userService.deleteUserByIds(mechIds);
        return mechantMapper.deleteMechantByMechIds(Convert.toStrArray(mechIds));
    }

    /**
     * 删除商户信息信息
     * 
     * @param mechId 商户信息主键
     * @return 结果
     */
    @Override
    public int deleteMechantByMechId(Long mechId){
        userService.deleteUserById(mechId);
        return mechantMapper.deleteMechantByMechId(mechId);
    }

    @Override
    public List<Ztree> selectMechTree(Mechant mechant) {
        SysUser sysUser = ShiroUtils.getSysUser();
        String userType = sysUser.getUserType();
        if (!UserType.SYS.val().equals(userType)) {//商户或者商户下的员工
            if(UserType.MECH.equals(userType)){
                mechant.setParentId(sysUser.getUserId());
            }else{
                mechant.setMechId(sysUser.getUserId());
            }
        }
        List<Mechant> mechList = mechantMapper.selectMechantList(mechant);
        if(!UserType.SYS.val().equals(userType)){
            if(UserType.MECH.equals(userType)){
                mechList.add(mechantMapper.selectMechantByMechId(sysUser.getUserId()));
            }else{
                if(mechList.size()>0){
                    mechList.add(mechantMapper.selectMechantByMechId(mechList.get(0).getParentId()));
                }
            }
        }
        List<Ztree> ztrees = initZtree(mechList);
        return ztrees;
    }
    /**
     * 对象转部门树
     *
     * @param mechList 商户列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Mechant> mechList)    {
        return initZtree(mechList, null);
    }

    /**
     * 对象转部门树
     *
     * @param mechList 商户列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Mechant> mechList, List<String> roleDeptList) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (Mechant mech : mechList){
            if (YesOrNoStatus.YES.val().equals(mech.getMechStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(mech.getMechId());
                ztree.setpId(mech.getParentId());
                ztree.setName(mech.getMechName());
                ztree.setTitle(mech.getMechName());
                if (isCheck)  {
                    ztree.setChecked(roleDeptList.contains(mech.getMechId() + mech.getMechName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }
}
