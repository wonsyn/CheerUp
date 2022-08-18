package com.web.curation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer{

	private final WebSocketHandler webScoketHandler;
	
	public WebsocketConfig(WebSocketHandler webScoketHandler) {
		super();
		this.webScoketHandler = webScoketHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("@@@@@@@@@@@@@@");
		registry.addHandler(webScoketHandler, "/ws").setAllowedOrigins("*");
		
	}

}