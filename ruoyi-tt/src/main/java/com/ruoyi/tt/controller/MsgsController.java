package com.ruoyi.tt.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.tt.domain.Device;
import com.ruoyi.tt.service.IDeviceService;
import com.ruoyi.tt.service.IMechantService;
import com.ruoyi.tt.service.SocketServerHandler;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 通讯记录信息Controller
 *
 * @author xxxxxx
 * @date 2022-10-22
 */
@Controller
@RequestMapping("/tt/msgs")
public class MsgsController extends BaseController {
    private String prefix = "tt/msgs";

    @Autowired
    private IDeviceService deviceService;
    @RequiresPermissions("tt:msgs:view")
    @GetMapping()
    public String device()
    {
        return prefix + "/msgs";
    }

    /**
     * 查询设备信息列表
     */
    @RequiresPermissions("tt:msgs:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        return getDataTable(SocketServerHandler.msgs);
    }
}
