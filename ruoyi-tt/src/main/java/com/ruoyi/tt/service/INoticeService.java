package com.ruoyi.tt.service;

import java.util.List;
import com.ruoyi.tt.domain.Notice;

/**
 * 关注信息Service接口
 * 
 * @author xxxxxx
 * @date 2022-11-22
 */
public interface INoticeService 
{
    /**
     * 查询关注信息
     * 
     * @param noticeNo 关注信息主键
     * @return 关注信息
     */
    public Notice selectNoticeByNoticeNo(String noticeNo);

    /**
     * 查询关注信息列表
     * 
     * @param notice 关注信息
     * @return 关注信息集合
     */
    public List<Notice> selectNoticeList(Notice notice);

    /**
     * 新增关注信息
     * 
     * @param notice 关注信息
     * @return 结果
     */
    public int insertNotice(Notice notice);

    /**
     * 修改关注信息
     * 
     * @param notice 关注信息
     * @return 结果
     */
    public int updateNotice(Notice notice);

    /**
     * 批量删除关注信息
     * 
     * @param noticeNos 需要删除的关注信息主键集合
     * @return 结果
     */
    public int deleteNoticeByNoticeNos(String noticeNos);

    /**
     * 删除关注信息信息
     * 
     * @param noticeNo 关注信息主键
     * @return 结果
     */
    public int deleteNoticeByNoticeNo(String noticeNo);
}
