package com.ruoyi.tt.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.tt.domain.Mechant;
import com.ruoyi.tt.enums.ChannelPackage;
import com.ruoyi.tt.enums.MechType;
import com.ruoyi.tt.service.IMechantService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工信息Controller
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
@Controller
@RequestMapping("/tt/employee")
public class EmployeeController extends BaseController
{
    private String prefix = "tt/employee";

    @Autowired
    private IMechantService mechantService;

    @RequiresPermissions("tt:employee:view")
    @GetMapping()
    public String mechant()
    {
        return prefix + "/employee";
    }

    /**
     * 查询员工信息列表
     */
    @RequiresPermissions("tt:employee:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Mechant mechant)  {
        mechant.setMechType(MechType.EMPL.val());
        SysUser sysUser = getSysUser();
        if(sysUser!=null && !UserType.SYS.val().equals(sysUser.getUserType())){
            mechant.setParentId(sysUser.getUserId());
        }
        startPage();
        List<Mechant> list = mechantService.selectMechantList(mechant);
        for (Mechant mech : list) {
            mech.setChannels(ChannelPackage.toChannelStr(mech.getMechChannel()));
        }
        return getDataTable(list);
    }

    /**
     * 导出员工信息列表
     */
    @RequiresPermissions("tt:employee:export")
    @Log(title = "商户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Mechant mechant)    {
        SysUser sysUser = getSysUser();
        if(sysUser!=null && !UserType.SYS.val().equals(sysUser.getUserType())){
            mechant.setParentId(sysUser.getUserId());
        }
        List<Mechant> list = mechantService.selectMechantList(mechant);
        ExcelUtil<Mechant> util = new ExcelUtil<Mechant>(Mechant.class);
        return util.exportExcel(list, "商户信息数据");
    }

    /**
     * 新增  管理员 在此处不能 暂不支持 添加员工信息
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)  {
        Mechant mechant  = new Mechant();
        SysUser sysUser = getSysUser();
        if(sysUser!=null){
            mechant.setParentId(sysUser.getUserId());
            mechant.setParMechName(sysUser.getUserName());
        }
        mmap.put("mechant", mechant);
        return prefix + "/add";
    }

    /**
     * 新增保存员工信息
     */
    @RequiresPermissions("tt:employee:add")
    @Log(title = "商户信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Mechant mechant)    {
        mechant.setCreateBy(getLoginName());
        //设置可用渠道
        mechant.setMechChannel(0);
        String channels = mechant.getChannels();
        if(StringUtils.isNotEmpty(channels)){
            for (String code : channels.split(",")) {
                mechant.setMechChannel(ChannelPackage.addChannel(mechant.getMechChannel(),code));
            }
        }
        return mechantService.insertMechant(mechant);
    }

    /**
     * 修改商户信息
     */
    @RequiresPermissions("tt:employee:edit")
    @GetMapping("/edit/{mechId}")
    public String edit(@PathVariable("mechId") Long mechId, ModelMap mmap)    {
        Mechant mechant = mechantService.selectMechantByMechId(mechId);
        mechant.setChannels(ChannelPackage.toChannelStr(mechant.getMechChannel()));
        mmap.put("mechant", mechant);
        return prefix + "/edit";
    }
    /**
     * 商户状态修改
     */
    @Log(title = "商户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("tt:employee:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Mechant mechant){
        return toAjax(mechantService.updateMechant(mechant));
    }
    /**
     * 修改保存商户信息
     */
    @RequiresPermissions("tt:employee:edit")
    @Log(title = "商户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Mechant mechant)    {
        //设置可用渠道
        mechant.setMechChannel(0);
        String channels = mechant.getChannels();
        if(StringUtils.isNotEmpty(channels)){
            for (String code : channels.split(",")) {
                mechant.setMechChannel(ChannelPackage.addChannel(mechant.getMechChannel(),code));
            }
        }
        return toAjax(mechantService.updateMechant(mechant));
    }

    /**
     * 删除商户信息
     */
    @RequiresPermissions("tt:employee:remove")
    @Log(title = "商户信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)    {
        return toAjax(mechantService.deleteMechantByMechIds(ids));
    }


}
