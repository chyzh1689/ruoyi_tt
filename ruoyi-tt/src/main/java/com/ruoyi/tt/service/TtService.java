package com.ruoyi.tt.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.tt.domain.Mechant;
import com.ruoyi.tt.enums.MechType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考 DictService 实现下拉选择
 */
@Service("tt")
public class TtService {

    @Autowired
    private IMechantService mechantService;

    public List<Mechant> getMechs() {
        SysUser sysUser = ShiroUtils.getSysUser();
        List<Mechant> mechants = new ArrayList<>();
        if (sysUser != null) {
            String userType = sysUser.getUserType();
            if (!UserType.SYS.val().equals(userType)) {//商户或者商户下的员工
                Mechant mechant = mechantService.selectMechantByMechId(sysUser.getUserId());
                //员工情况判断
                if (mechant != null && MechType.EMPL.val().equals(mechant.getMechType())) {
                    mechant = mechantService.selectMechantByMechId(mechant.getParentId());
                }
                if (mechant != null) {
                    mechants.add(mechant);
                }
            } else {//系统用户
                Mechant mechant = new Mechant();
                mechant.setMechType(MechType.MECH.val());
                mechants = mechantService.selectMechantList(mechant);
            }
        }
        for (Mechant mech : mechants) {
            mech.setMechName(mech.getMechNo() + "（" + mech.getMechName() + "）");
        }
        return mechants;
    }

    public List<Mechant> getMechUsers() {
        SysUser sysUser = ShiroUtils.getSysUser();
        List<Mechant> mechants = new ArrayList<>();
        if (sysUser != null) {
            String userType = sysUser.getUserType();
            if (!UserType.SYS.val().equals(userType)) {//商户或者商户下的员工
                Mechant mechant = mechantService.selectMechantByMechId(sysUser.getUserId());
                //员工情况判断
                if (mechant != null && MechType.EMPL.val().equals(mechant.getMechType())) {
                    mechant = mechantService.selectMechantByMechId(mechant.getParentId());
                }else if(mechant!=null){//商户情况
                    mechant = new Mechant();
                    mechant.setMechType(MechType.EMPL.val());
                    mechant.setParentId(sysUser.getUserId());
                    mechants = mechantService.selectMechantList(mechant);
                }
                if (mechant != null) {
                    mechants.add(mechant);
                }
            } else {//系统用户
                Mechant mechant = new Mechant();
                mechant.setMechType(MechType.EMPL.val());
                mechants = mechantService.selectMechantList(mechant);
            }
        }
        for (Mechant mech : mechants) {
            mech.setMechName(mech.getMechNo() + "（" + mech.getMechName() + "）");
        }
        return mechants;
    }
}
