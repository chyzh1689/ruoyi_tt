package com.ruoyi.tt.controller;

import java.util.List;
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
import com.ruoyi.tt.domain.Account;
import com.ruoyi.tt.service.IAccountService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 账号信息Controller
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
@Controller
@RequestMapping("/tt/account")
public class AccountController extends BaseController
{
    private String prefix = "tt/account";

    @Autowired
    private IAccountService accountService;

    @RequiresPermissions("tt:account:view")
    @GetMapping()
    public String account()
    {
        return prefix + "/account";
    }

    /**
     * 查询账号信息列表
     */
    @RequiresPermissions("tt:account:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Account account)
    {
        startPage();
        List<Account> list = accountService.selectAccountList(account);
        return getDataTable(list);
    }

    /**
     * 导出账号信息列表
     */
    @RequiresPermissions("tt:account:export")
    @Log(title = "账号信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Account account)
    {
        List<Account> list = accountService.selectAccountList(account);
        ExcelUtil<Account> util = new ExcelUtil<Account>(Account.class);
        return util.exportExcel(list, "账号信息数据");
    }

    /**
     * 新增账号信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存账号信息
     */
    @RequiresPermissions("tt:account:add")
    @Log(title = "账号信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Account account)
    {
        return toAjax(accountService.insertAccount(account));
    }

    /**
     * 修改账号信息
     */
    @RequiresPermissions("tt:account:edit")
    @GetMapping("/edit/{accountId}")
    public String edit(@PathVariable("accountId") Long accountId, ModelMap mmap)
    {
        Account account = accountService.selectAccountByAccountId(accountId);
        mmap.put("account", account);
        return prefix + "/edit";
    }

    /**
     * 修改保存账号信息
     */
    @RequiresPermissions("tt:account:edit")
    @Log(title = "账号信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Account account)
    {
        return toAjax(accountService.updateAccount(account));
    }

    /**
     * 删除账号信息
     */
    @RequiresPermissions("tt:account:remove")
    @Log(title = "账号信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(accountService.deleteAccountByAccountIds(ids));
    }
}
