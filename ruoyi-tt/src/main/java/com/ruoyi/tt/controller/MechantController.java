package com.ruoyi.tt.controller;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.tt.domain.Device;
import com.ruoyi.tt.enums.ChannelPackage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.tt.domain.Mechant;
import com.ruoyi.tt.service.IMechantService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商户信息Controller
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
@Controller
@RequestMapping("/tt/mechant")
public class MechantController extends BaseController
{
    private String prefix = "tt/mechant";

    @Autowired
    private IMechantService mechantService;

    @RequiresPermissions("tt:mechant:view")
    @GetMapping()
    public String mechant()
    {
        return prefix + "/mechant";
    }

    /**
     * 查询商户信息列表
     */
    @RequiresPermissions("tt:mechant:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Mechant mechant)  {
        SysUser sysUser = getSysUser();
        if(sysUser!=null && !UserType.SYS.val().equals(sysUser.getUserType())){
            mechant.setMechId(sysUser.getUserId());
        }
        startPage();
        List<Mechant> list = mechantService.selectMechantList(mechant);
        for (Mechant mech : list) {
            mech.setChannels(ChannelPackage.toChannelStr(mech.getMechChannel()));
        }
        return getDataTable(list);
    }

    /**
     * 导出商户信息列表
     */
    @RequiresPermissions("tt:mechant:export")
    @Log(title = "商户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Mechant mechant)    {
        SysUser sysUser = getSysUser();
        if(sysUser!=null && !UserType.SYS.val().equals(sysUser.getUserType())){
            mechant.setMechId(sysUser.getUserId());
        }
        List<Mechant> list = mechantService.selectMechantList(mechant);
        ExcelUtil<Mechant> util = new ExcelUtil<Mechant>(Mechant.class);
        return util.exportExcel(list, "商户信息数据");
    }

    /**
     * 新增
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap)  {
        Mechant mechant  = new Mechant();
        mechant.setParentId(parentId);
        if (0L != parentId){
            Mechant mechantData = mechantService.selectMechantByMechId(parentId);
            mechant.setParMechName(mechantData.getMechName());
        }

        mmap.put("mechant", mechant);
        return prefix + "/add";
    }

    /**
     * 新增保存商户信息
     */
    @RequiresPermissions("tt:mechant:add")
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
    @RequiresPermissions("tt:mechant:edit")
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
    @RequiresPermissions("tt:mechant:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Mechant mechant){
        return toAjax(mechantService.updateMechant(mechant));
    }
    /**
     * 修改保存商户信息
     */
    @RequiresPermissions("tt:mechant:edit")
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
    @RequiresPermissions("tt:mechant:remove")
    @Log(title = "商户信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(mechantService.deleteMechantByMechIds(ids));
    }


}
