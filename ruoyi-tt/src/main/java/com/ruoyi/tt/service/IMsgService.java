package com.ruoyi.tt.service;

import java.util.List;
import com.ruoyi.tt.domain.Msg;

/**
 * 消息聊天记录Service接口
 * 
 * @author xxxxxx
 * @date 2022-11-08
 */
public interface IMsgService {
    /**
     * 查询消息聊天记录
     * 
     * @param msgId 消息聊天记录主键
     * @return 消息聊天记录
     */
    public Msg selectMsgByMsgId(Long msgId);

    /**
     * 查询消息聊天记录列表
     * 
     * @param msg 消息聊天记录
     * @return 消息聊天记录集合
     */
    public List<Msg> selectMsgList(Msg msg);

    /**
     * 新增消息聊天记录
     * 
     * @param msg 消息聊天记录
     * @return 结果
     */
    public int insertMsg(Msg msg);

    /**
     * 修改消息聊天记录
     * 
     * @param msg 消息聊天记录
     * @return 结果
     */
    public int updateMsg(Msg msg);

    /**
     * 批量删除消息聊天记录
     * 
     * @param msgIds 需要删除的消息聊天记录主键集合
     * @return 结果
     */
    public int deleteMsgByMsgIds(String msgIds);

    /**
     * 删除消息聊天记录信息
     * 
     * @param msgId 消息聊天记录主键
     * @return 结果
     */
    public int deleteMsgByMsgId(Long msgId);
}
