package com.hsia.weblog.config;

import com.hsia.weblog.exception.GlobalException;
import com.hsia.weblog.util.SessionUtil;
import com.hsia.weblog.vo.SessionInfo;
import com.hsia.weblog.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.AbstractHandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * @author : hsiachyikwok
 * @version : 1.0
 * @class_name : WebsocketConfig
 * @description : websocket
 * @create_date : 2019/8/28
 * @create_time : 14:19
 */
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebsocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    //    直接暴露end point
    //    @Bean
    //    public ServerEndpointExporter serverEndpointExporter() {
    //        return new ServerEndpointExporter();
    //    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(
                "/topic",   //广播消息前缀
                "/queue"    //点对点消息前缀
        );
        //设置客户端接收点对点消息地址的前缀，默认为 /user
        registry.setUserDestinationPrefix("/user");
        //设置客户端向服务器发送消息的地址前缀（可不设置）
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket") //注册一个 /websocket 的 websocket 节点
                .addInterceptors(myHandshakeInterceptor())  //添加 websocket握手拦截器
                .setHandshakeHandler(myDefaultHandshakeHandler())   //添加 websocket握手处理器
                .setAllowedOrigins("*") //设置允许可跨域的域名
                .withSockJS();  //指定使用SockJS协议
    }

    /**
     * WebSocket 握手拦截器
     * 可做一些用户认证拦截处理
     */
    private HandshakeInterceptor myHandshakeInterceptor() {
        return new HandshakeInterceptor() {

            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                ServletServerHttpRequest req = (ServletServerHttpRequest) request;
                HttpServletRequest httpServletRequest = req.getServletRequest();
                //通过url的query参数获取认证参数
                SessionInfo sessionInfo = SessionUtil.getSessionInfo(httpServletRequest.getSession(false));
                String token = req.getServletRequest().getParameter("token");
                //根据token认证用户，不通过返回拒绝握手
                Principal user = authenticate(sessionInfo, token);
                if (user == null) {
                    return false;
                }
                //保存认证用户
                attributes.put("user", user);
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

            }
        };
    }


    //WebSocket 握手处理器
    private DefaultHandshakeHandler myDefaultHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                //设置认证通过的用户到当前会话中
                return (Principal) attributes.get("user");
            }
        };
    }

    /**
     * 根据token认证授权
     *
     * @param token
     */
    private Principal authenticate(SessionInfo sessionInfo, String token) {
        log.info("**************token:{}******************", token);
        // 实现用户的认证并返回用户信息，如果认证失败返回 null
        // 用户信息需继承 Principal 并实现 getName() 方法，返回全局唯一值
        //验证token
        if (!sessionInfo.getToken().equals(token)) {
            throw GlobalException.SESSION_OUT_OF_DATE;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setName(sessionInfo.getUsername());
        return userInfo;
    }
}
