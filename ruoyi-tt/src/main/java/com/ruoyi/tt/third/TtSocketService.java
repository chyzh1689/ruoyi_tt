package com.ruoyi.tt.third;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.R;
import org.springframework.stereotype.Service;

@Service
public class TtSocketService {
    /**处理消息**/
    R handle(Object msg){
        if(msg instanceof String){
            TTSocketDto ttSocketDto = JSON.parseObject((String) msg, TTSocketDto.class);
        }
        return R.ok();
    }

}
