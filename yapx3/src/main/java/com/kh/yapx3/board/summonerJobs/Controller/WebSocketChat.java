package com.kh.yapx3.board.summonerJobs.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ServerEndpoint( "/yapx3" )
public class WebSocketChat {
	
	@RequestMapping("/chat.do")
	public String WebsocketChat() {
		return "board/summonerJobs/test";	
	}
	
	@OnOpen
	public void handleOpen() {
		System.out.println( " 채팅 시작 " );
	}
	@OnMessage
	public String handleMessage( String message ) {
		System.out.println( " from client : " + message );
		String replymessage = "echo " + message;
		System.out.println( " send client : " + replymessage );
		return replymessage;	
	}
	@OnClose
	public void handleClose() {
		System.out.println( " 채팅 종료 " );
	}
	@OnError
	public void handleError( Throwable t ) {
		t.printStackTrace();	
	}
}
