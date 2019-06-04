package com.sd.assignment2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;

public class SocketUtil {

    private static WebSocketSession webSession;

    private static ArrayList<WebSocketSession> sessions = new ArrayList<>();


    public static void sendData(String data) throws IOException {
        webSession.sendMessage(new TextMessage(data));

    }

    public static void addSession(WebSocketSession webSession){
        sessions.add(webSession);
    }

    public static  ArrayList<WebSocketSession> getWebScocket(){
        return  sessions;
    }

    public static void setSession(WebSocketSession session)
    {
        webSession = session;
    }
}
