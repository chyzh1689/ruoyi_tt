package com.ruoyi.tt.mapper;

import java.util.List;
import com.ruoyi.tt.domain.Account;

/**
 * 账号信息Mapper接口
 * 
 * @author xxxxxx
 * @date 2022-10-22
 */
public interface AccountMapper 
{
    /**
     * 查询账号信息
     * 
     * @param accountId 账号信息主键
     * @return 账号信息
     */
    public Account selectAccountByAccountId(Long accountId);

    /**
     * 查询账号信息列表
     * 
     * @param account 账号信息
     * @return 账号信息集合
     */
    public List<Account> selectAccountList(Account account);

    /**
     * 新增账号信息
     * 
     * @param account 账号信息
     * @return 结果
     */
    public int insertAccount(Account account);

    /**
     * 修改账号信息
     * 
     * @param account 账号信息
     * @return 结果
     */
    public int updateAccount(Account account);

    /**
     * 删除账号信息
     * 
     * @param accountId 账号信息主键
     * @return 结果
     */
    public int deleteAccountByAccountId(Long accountId);

    /**
     * 批量删除账号信息
     * 
     * @param accountIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAccountByAccountIds(String[] accountIds);
}
