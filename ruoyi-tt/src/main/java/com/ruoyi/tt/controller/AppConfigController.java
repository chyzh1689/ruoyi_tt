package com.ruoyi.tt.controller;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.tt.TTContants;
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
import com.ruoyi.tt.domain.AppConfig;
import com.ruoyi.tt.service.IAppConfigService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 应用参数配置Controller
 * 
 * @author xxxxxx
 * @date 2022-12-17
 */
@Controller
@RequestMapping("/tt/config")
public class AppConfigController extends BaseController
{
    private String prefix = "tt/config";

    @Autowired
    private IAppConfigService appConfigService;

    @RequiresPermissions("tt:config:view")
    @GetMapping()
    public String config()
    {
        return prefix + "/config";
    }

    /**
     * 查询应用参数配置列表
     */
    @RequiresPermissions("tt:config:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AppConfig appConfig) {
        SysUser sysUser = getSysUser();
        if(sysUser!=null && !UserType.SYS.val().equals(sysUser.getUserType())){
            appConfig.setMechId(sysUser.getUserId());
            if(UserType.EMPL.val().equals(sysUser.getUserType())){
                //appConfig.setMechOwn(sysUser.getUserId());
            }
        }
        startPage();
        List<AppConfig> list = appConfigService.selectAppConfigList(appConfig);
        return getDataTable(list);
    }

    /**
     * 导出应用参数配置列表
     */
    @RequiresPermissions("tt:config:export")
    @Log(title = "应用参数配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AppConfig appConfig)
    {
        List<AppConfig> list = appConfigService.selectAppConfigList(appConfig);
        ExcelUtil<AppConfig> util = new ExcelUtil<AppConfig>(AppConfig.class);
        return util.exportExcel(list, "应用参数配置数据");
    }

    /**
     * 新增应用参数配置
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存应用参数配置
     */
    @RequiresPermissions("tt:config:add")
    @Log(title = "应用参数配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AppConfig appConfig)    {
        appConfig.setMechId(TTContants.VAL_PARENT_ID);
        appConfig.setDeviceId(TTContants.VAL_PARENT_ID);
        return toAjax(appConfigService.insertAppConfig(appConfig));
    }

    /**
     * 修改应用参数配置
     */
    @RequiresPermissions("tt:config:edit")
    @GetMapping("/edit/{appConfigId}")
    public String edit(@PathVariable("appConfigId") String appConfigId,ModelMap mmap) {
        String[] ids = appConfigId.split(",");
        AppConfig appConfig = appConfigService.selectAppConfig(Long.parseLong(ids[0]),ids[1],ids[2]);
        if(appConfig.getMechId().equals(TTContants.VAL_PARENT_ID)){
            appConfig.setMechShow("默认商户");
        }
        if(appConfig.getDeviceId().equals(TTContants.VAL_PARENT_ID)){
            appConfig.setDeviceShow("默认设备");
        }
        mmap.put("appConfig", appConfig);
        return prefix + "/edit";
    }

    /**
     * 修改保存应用参数配置
     */
    @RequiresPermissions("tt:config:edit")
    @Log(title = "应用参数配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AppConfig appConfig) {
        return toAjax(appConfigService.updateAppConfig(appConfig));
    }

    /**
     * 删除应用参数配置
     */
    @RequiresPermissions("tt:config:remove")
    @Log(title = "应用参数配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(appConfigService.deleteAppConfigByDeviceIds(ids));
    }
}
