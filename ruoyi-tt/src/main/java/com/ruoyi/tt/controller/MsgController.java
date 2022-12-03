package com.ruoyi.tt.controller;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.UserType;
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
import com.ruoyi.tt.domain.Msg;
import com.ruoyi.tt.service.IMsgService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 消息聊天记录Controller
 * 
 * @author xxxxxx
 * @date 2022-11-08
 */
@Controller
@RequestMapping("/tt/msg")
public class MsgController extends BaseController
{
    private String prefix = "tt/msg";

    @Autowired
    private IMsgService msgService;

    @RequiresPermissions("tt:msg:view")
    @GetMapping()
    public String msg()
    {
        return prefix + "/msg";
    }

    /**
     * 查询消息聊天记录列表
     */
    @RequiresPermissions("tt:msg:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Msg msg)    {
        SysUser sysUser = getSysUser();
        if(sysUser!=null && !UserType.SYS.val().equals(sysUser.getUserType())){
            if(UserType.MECH.val().equals(sysUser.getUserType())){
                msg.setMechId(sysUser.getUserId());
            }else if(UserType.EMPL.val().equals(sysUser.getUserType())){
                msg.setOwnId(sysUser.getUserId());
            }
        }
        startPage();
        List<Msg> list = msgService.selectMsgList(msg);
        return getDataTable(list);
    }

    /**
     * 导出消息聊天记录列表
     */
    @RequiresPermissions("tt:msg:export")
    @Log(title = "消息聊天记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Msg msg)    {
        SysUser sysUser = getSysUser();
        if(sysUser!=null && !UserType.SYS.val().equals(sysUser.getUserType())){
            if(UserType.MECH.val().equals(sysUser.getUserType())){
                msg.setMechId(sysUser.getUserId());
            }else if(UserType.EMPL.val().equals(sysUser.getUserType())){
                msg.setOwnId(sysUser.getUserId());
            }
        }
        List<Msg> list = msgService.selectMsgList(msg);
        ExcelUtil<Msg> util = new ExcelUtil<Msg>(Msg.class);
        return util.exportExcel(list, "消息聊天记录数据");
    }

    /**
     * 新增消息聊天记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存消息聊天记录
     */
    @RequiresPermissions("tt:msg:add")
    @Log(title = "消息聊天记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Msg msg)
    {
        return toAjax(msgService.insertMsg(msg));
    }

    /**
     * 修改消息聊天记录
     */
    @RequiresPermissions("tt:msg:edit")
    @GetMapping("/edit/{msgId}")
    public String edit(@PathVariable("msgId") Long msgId, ModelMap mmap)
    {
        Msg msg = msgService.selectMsgByMsgId(msgId);
        mmap.put("msg", msg);
        return prefix + "/edit";
    }

    /**
     * 修改保存消息聊天记录
     */
    @RequiresPermissions("tt:msg:edit")
    @Log(title = "消息聊天记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Msg msg)
    {
        return toAjax(msgService.updateMsg(msg));
    }

    /**
     * 删除消息聊天记录
     */
    @RequiresPermissions("tt:msg:remove")
    @Log(title = "消息聊天记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(msgService.deleteMsgByMsgIds(ids));
    }
}
