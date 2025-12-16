package com.medical.controller.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebSocketController {
/*

    @Autowired
    private PrivateChatService privateChatService;
    @Autowired
    private ChatRoomService chatRoomService;


    //广播发送功能 适用于系统公告 大厅消息等
    @MessageMapping("/system/notice") // 接收前端发送的消息（"/app/chat"）
    @SendTo("/topic/messages") // 广播到 "/topic/messages"
    public String handleChatMessage(String message) {
        log.info("收到消息: {}", message);
        JSONObject jsonObject = JSONUtil.parseObj(message);
        log.info("收到解析后的数据:{}", JSONUtil.toJsonStr(jsonObject));
        return "服务器返回: " + message;
    }



    //私聊发送功能
    @MessageMapping("/chat/private")
    public void sendPrivateMessage(@Payload PrivateChatSendReq req, Principal principal) {
        if (principal == null) {
            log.warn("WebSocket 发送失败：Principal 为空，消息体：{}", req);
            return;
        }

        req.setSendId(Long.valueOf(principal.getName()));
        log.info("{}-发送给了-{}-消息:{}", principal.getName(), req.getReceiveId(), req.getContent());

        PrivateChat privateChat = BeanUtil.toBean(req, PrivateChat.class);
        privateChatService.add(privateChat);
    }
*/



}

