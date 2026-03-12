package com.medical.config.websocket;

import com.medical.entity.Admin;
import com.medical.pojo.resp.player.PlayerTokenResp;
import com.medical.service.EhcacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.http.server.ServerHttpRequest;

import java.security.Principal;
import java.util.Map;
@Slf4j
public class CustomPrincipalHandshakeHandler extends DefaultHandshakeHandler {
    private final EhcacheService ehcacheService;

    public CustomPrincipalHandshakeHandler(EhcacheService ehcacheService) {
        this.ehcacheService = ehcacheService;
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 从 attributes 中取出 Token
        String token = (String) attributes.get("token-id");
        log.info("请求头token-id={}", token);
        String type = (String) attributes.get("type");
        log.info("请求头type={}", type);

        // 如果没有 token，可返回 null 或做其它处理
        if (token == null || type == null) {
            return null;
        }

        String username = null;
        if(type.equals("user")){
            PlayerTokenResp playerTokenResp = ehcacheService.playerTokenCache().get(token);
            log.info("握手中提取 token-id={}, playerTokenResp={}", token, playerTokenResp);
            if (playerTokenResp == null){
                return null;
            }
            username = playerTokenResp.getUsername();
        }
        if(type.equals("admin")){
            Admin admin = ehcacheService.getAdminTokenCache().get(token);
            if(admin == null){
                return null;
            }
            username = admin.getAccount();
        }

        if(StringUtils.isBlank(username)){
            return null;
        }

        // 用 token 构造自定义 Principal
        return new StompPrincipal(username);
    }

}
