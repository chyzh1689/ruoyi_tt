package com.ruoyi.tt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.UserType;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.tt.domain.Notice;
import com.ruoyi.tt.service.INoticeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 关注信息Controller
 * 
 * @author xxxxxx
 * @date 2022-11-22
 */
@Controller
@RequestMapping("/tt/notice")
public class NoticeController extends BaseController
{
    private String prefix = "tt/notice";

    @Autowired
    private INoticeService noticeService;

    @RequiresPermissions("tt:notice:view")
    @GetMapping()
    public String notice()
    {
        return prefix + "/notice";
    }

    /**
     * 查询关注信息列表
     */
    @RequiresPermissions("tt:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Notice notice)    {
        SysUser sysUser = getSysUser();
        if(sysUser!=null && !UserType.SYS.val().equals(sysUser.getUserType())){
            if(UserType.MECH.val().equals(sysUser.getUserType())){
                notice.setMechantId(sysUser.getUserId());
            }else if(UserType.EMPL.val().equals(sysUser.getUserType())){
                notice.setOwnId(sysUser.getUserId());
            }
        }
        HttpServletRequest request = ServletUtils.getRequest();
        String orderByCol = request.getParameter(TableSupport.ORDER_BY_COLUMN);
        if(StringUtils.isEmpty(orderByCol)){
            request.setAttribute(TableSupport.ORDER_BY_COLUMN,"updateTime");
            request.setAttribute(TableSupport.IS_ASC,"desc");
        }
        startPage();
        List<Notice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 导出关注信息列表
     */
    @RequiresPermissions("tt:notice:export")
    @Log(title = "关注信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Notice notice)    {
        SysUser sysUser = getSysUser();
        if(sysUser!=null && !UserType.SYS.val().equals(sysUser.getUserType())){
            if(UserType.MECH.val().equals(sysUser.getUserType())){
                notice.setMechantId(sysUser.getUserId());
            }else if(UserType.EMPL.val().equals(sysUser.getUserType())){
                notice.setOwnId(sysUser.getUserId());
            }
        }
        List<Notice> list = noticeService.selectNoticeList(notice);
        ExcelUtil<Notice> util = new ExcelUtil<Notice>(Notice.class);
        return util.exportExcel(list, "关注信息数据");
    }

    /**
     * 新增关注信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存关注信息
     */
    @RequiresPermissions("tt:notice:add")
    @Log(title = "关注信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Notice notice)
    {
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改关注信息
     */
    @RequiresPermissions("tt:notice:edit")
    @GetMapping("/edit/{noticeNo}")
    public String edit(@PathVariable("noticeNo") String noticeNo, ModelMap mmap)
    {
        Notice notice = noticeService.selectNoticeByNoticeNo(noticeNo);
        mmap.put("notice", notice);
        return prefix + "/edit";
    }

    /**
     * 修改保存关注信息
     */
    @RequiresPermissions("tt:notice:edit")
    @Log(title = "关注信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Notice notice)
    {
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除关注信息
     */
    @RequiresPermissions("tt:notice:remove")
    @Log(title = "关注信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(noticeService.deleteNoticeByNoticeNos(ids));
    }
}
