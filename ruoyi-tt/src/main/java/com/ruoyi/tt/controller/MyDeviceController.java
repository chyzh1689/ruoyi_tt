package com.ruoyi.tt.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.tt.domain.Device;
import com.ruoyi.tt.service.IDeviceService;
import com.ruoyi.tt.service.IMechantService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的设备信息Controller
 *
 * @author xxxxxx
 * @date 2022-10-22
 */
@Controller
@RequestMapping("/tt/myDevice")
public class MyDeviceController extends BaseController{
    private String prefix = "tt/myDevice";

    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IMechantService mechantService;
    @RequiresPermissions("tt:myDevice:view")
    @GetMapping()
    public String myDevice(ModelMap mmap) {
        Device device = new Device();
        SysUser sysUser = getSysUser();
        if(sysUser!=null){
            if(!UserType.SYS.val().equals(sysUser.getUserType())){
                device.setMechOwn(sysUser.getUserId());
            }
            List<Device> list = deviceService.selectDeviceList(device);
            mmap.put("devices",new ArrayList<>());
        }else{
            mmap.put("devices",new ArrayList<>());
        }

        return prefix + "/myDevice";
    }



}
