package com.ruoyi.tt.controller;

import java.util.List;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.tt.domain.Mechant;
import com.ruoyi.tt.service.IMechantService;
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
import com.ruoyi.tt.domain.Device;
import com.ruoyi.tt.service.IDeviceService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备信息Controller
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
@Controller
@RequestMapping("/tt/device")
public class DeviceController extends BaseController{
    private String prefix = "tt/device";

    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IMechantService mechantService;
    @RequiresPermissions("tt:device:view")
    @GetMapping()
    public String device()
    {
        return prefix + "/device";
    }

    /**
     * 查询设备信息列表
     */
    @RequiresPermissions("tt:device:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Device device){
        startPage();
        List<Device> list = deviceService.selectDeviceList(device);
        return getDataTable(list);
    }

    /**
     * 导出设备信息列表
     */
    @RequiresPermissions("tt:device:export")
    @Log(title = "设备信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Device device)
    {
        List<Device> list = deviceService.selectDeviceList(device);
        ExcelUtil<Device> util = new ExcelUtil<Device>(Device.class);
        return util.exportExcel(list, "设备信息数据");
    }

    /**
     * 新增设备信息
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)  {
        Device device  = new Device();
        SysUser sysUser = getSysUser();
        if(sysUser!=null && !UserType.SYS.val().equals(sysUser)){
            Mechant mechant = mechantService.selectMechantByMechId(sysUser.getUserId());
            if(mechant!=null){
                device.setMechOwn(mechant.getMechId());
                if(UserType.EMPL.val().equals(sysUser.getUserType())){
                    device.setMechId(mechant.getParentId());
                }else{
                    device.setMechId(mechant.getMechId());
                }
            }
        }
        mmap.put("device", device);
        return prefix + "/add";
    }

    /**
     * 新增保存设备信息
     */
    @RequiresPermissions("tt:device:add")
    @Log(title = "设备信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Device device) {
        return toAjax(deviceService.insertDevice(device));
    }

    /**
     * 修改设备信息
     */
    @RequiresPermissions("tt:device:edit")
    @GetMapping("/edit/{deviceId}")
    public String edit(@PathVariable("deviceId") Long deviceId, ModelMap mmap)
    {
        Device device = deviceService.selectDeviceByDeviceId(deviceId);
        mmap.put("device", device);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备信息
     */
    @RequiresPermissions("tt:device:edit")
    @Log(title = "设备信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Device device)  {
        return toAjax(deviceService.updateDevice(device));
    }
    /**
     * 设备状态修改
     */
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("tt:device:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Device device){
        return toAjax(deviceService.updateDevice(device));
    }
    /**
     * 删除设备信息
     */
    @RequiresPermissions("tt:device:remove")
    @Log(title = "设备信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)  {
        return toAjax(deviceService.deleteDeviceByDeviceIds(ids));
    }
    /**
     * 加载商户列表树
     */
    @RequiresPermissions("tt:device:list")
    @GetMapping("/mechTreeData")
    @ResponseBody
    public List<Ztree> deptTreeData()  {
        List<Ztree> ztrees = mechantService.selectMechTree(new Mechant());
        return ztrees;
    }
    /**
     * 选择商户树
     *
     * @param mechId 商户ID
     * @param excludeId 排除ID
     */
    @GetMapping(value = { "/selectMechTree/{mechId}", "/selectDeptTree/{mechId}/{excludeId}" })
    public String selectMechTree(@PathVariable("mechId") Long mechId,
                                 @PathVariable(value = "excludeId", required = false) Long excludeId, ModelMap mmap){
        if(mechId.equals(0L)){
            mmap.put("mech", new Mechant());
        }else{
            mmap.put("mech", mechantService.selectMechantByMechId(mechId));
        }
        mmap.put("excludeId", excludeId);
        return prefix + "/mechTree";
    }
}
