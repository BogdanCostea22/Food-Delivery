package com.sd.assignment2;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class NotificationHandler extends AbstractWebSocketHandler {
    private WebSocketSession session1;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        SocketUtil.setSession(session);
        SocketUtil.addSession(session);


        System.out.println("Connection estabilished");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("New Text Message Received"+message.toString());

//        for(WebSocketSession webSocketSession: SocketUtil.getWebScocket())
//            if(webSocketSession.isOpen())
//                webSocketSession.sendMessage(new TextMessage(message.getPayload()));


        session.sendMessage(new TextMessage(message.toString()+"Sugi "));
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received");
        session.sendMessage(message);
    }
}