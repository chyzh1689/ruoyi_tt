package com.ruoyi.tt.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.tt.mapper.MsgMapper;
import com.ruoyi.tt.domain.Msg;
import com.ruoyi.tt.service.IMsgService;
import com.ruoyi.common.core.text.Convert;

/**
 * 消息聊天记录Service业务层处理
 * 
 * @author xxxxxx
 * @date 2022-11-08
 */
@Service
public class MsgServiceImpl implements IMsgService {
    @Autowired
    private MsgMapper msgMapper;

    /**
     * 查询消息聊天记录
     * 
     * @param msgId 消息聊天记录主键
     * @return 消息聊天记录
     */
    @Override
    public Msg selectMsgByMsgId(Long msgId)
    {
        return msgMapper.selectMsgByMsgId(msgId);
    }

    /**
     * 查询消息聊天记录列表
     * 
     * @param msg 消息聊天记录
     * @return 消息聊天记录
     */
    @Override
    public List<Msg> selectMsgList(Msg msg)
    {
        return msgMapper.selectMsgList(msg);
    }

    /**
     * 新增消息聊天记录
     * 
     * @param msg 消息聊天记录
     * @return 结果
     */
    @Override
    public int insertMsg(Msg msg)
    {
        msg.setCreateTime(DateUtils.getNowDate());
        return msgMapper.insertMsg(msg);
    }

    /**
     * 修改消息聊天记录
     * 
     * @param msg 消息聊天记录
     * @return 结果
     */
    @Override
    public int updateMsg(Msg msg)
    {
        return msgMapper.updateMsg(msg);
    }

    /**
     * 批量删除消息聊天记录
     * 
     * @param msgIds 需要删除的消息聊天记录主键
     * @return 结果
     */
    @Override
    public int deleteMsgByMsgIds(String msgIds)
    {
        return msgMapper.deleteMsgByMsgIds(Convert.toStrArray(msgIds));
    }

    /**
     * 删除消息聊天记录信息
     * 
     * @param msgId 消息聊天记录主键
     * @return 结果
     */
    @Override
    public int deleteMsgByMsgId(Long msgId)
    {
        return msgMapper.deleteMsgByMsgId(msgId);
    }
}
