package com.ruoyi.tt.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tt.mapper.NoticeMapper;
import com.ruoyi.tt.domain.Notice;
import com.ruoyi.tt.service.INoticeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 关注信息Service业务层处理
 * 
 * @author xxxxxx
 * @date 2022-11-22
 */
@Service
public class NoticeServiceImpl implements INoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 查询关注信息
     * 
     * @param noticeNo 关注信息主键
     * @return 关注信息
     */
    @Override
    public Notice selectNoticeByNoticeNo(String noticeNo)
    {
        return noticeMapper.selectNoticeByNoticeNo(noticeNo);
    }

    /**
     * 查询关注信息列表
     * 
     * @param notice 关注信息
     * @return 关注信息
     */
    @Override
    public List<Notice> selectNoticeList(Notice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 新增关注信息
     * 
     * @param notice 关注信息
     * @return 结果
     */
    @Override
    public int insertNotice(Notice notice)
    {
        notice.setCreateTime(DateUtils.getNowDate());
        return noticeMapper.insertNotice(notice);
    }

    /**
     * 修改关注信息
     * 
     * @param notice 关注信息
     * @return 结果
     */
    @Override
    public int updateNotice(Notice notice)
    {
        notice.setUpdateTime(DateUtils.getNowDate());
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 批量删除关注信息
     * 
     * @param noticeNos 需要删除的关注信息主键
     * @return 结果
     */
    @Override
    public int deleteNoticeByNoticeNos(String noticeNos)
    {
        return noticeMapper.deleteNoticeByNoticeNos(Convert.toStrArray(noticeNos));
    }

    /**
     * 删除关注信息信息
     * 
     * @param noticeNo 关注信息主键
     * @return 结果
     */
    @Override
    public int deleteNoticeByNoticeNo(String noticeNo)
    {
        return noticeMapper.deleteNoticeByNoticeNo(noticeNo);
    }
}
