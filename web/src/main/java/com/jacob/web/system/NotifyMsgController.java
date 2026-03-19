package com.jacob.web.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.user.entity.SysUserNotifyMsg;
import com.jacob.common.utils.JwtUtil;
import com.jacob.service.user.SysUserNotifyMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notify")
public class NotifyMsgController {

    @Autowired
    private SysUserNotifyMsgService sysUserNotifyMsgService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/list")
    public ResponseVO<List<SysUserNotifyMsg>> list(){
        String userId = jwtUtil.getCurrentUserId();
        List<SysUserNotifyMsg> list = sysUserNotifyMsgService.list(new LambdaQueryWrapper<SysUserNotifyMsg>().eq(SysUserNotifyMsg::getUserId, userId));
        return ResponseVO.success(list);
    }

    @PostMapping("/read/{id}")
    public ResponseVO<?> read(@PathVariable("id") String ids){
        List<String> idList = List.of(ids.split(","));
        if(!idList.isEmpty()){
            sysUserNotifyMsgService.update(
                    new LambdaUpdateWrapper<SysUserNotifyMsg>()
                            .in(SysUserNotifyMsg::getMsgId, idList)
                            .set(SysUserNotifyMsg::getStatus, 1)
            );
        }
        return ResponseVO.success();
    }

}
