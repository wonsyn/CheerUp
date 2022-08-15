package com.web.curation.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.web.curation.model.dto.UserDto;
import com.web.curation.model.service.FeedService;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
	//로그인 한 인원 전체
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 1:1로 할 경우
	private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();
	
	@Autowired
	FeedService feedService;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {//클라이언트와 서버가 연결
		logger.info("Socket 연결");
		String loginUserId = session.getUri().toString().split("id=")[1];
		sessions.add(session);
		logger.info(currentUserName(session));//현재 접속한 사람
		String senderId = currentUserName(session);
		userSessionsMap.put(loginUserId,session);
	}
	
	private String currentUserName(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		UserDto loginUser = (UserDto)httpSession.get("id");
		if(loginUser == null) {
			String mid = session.getId();
			return mid;
		}
		String mid = loginUser.getId();
		return mid;
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {// 메시지
		logger.info("ssesion"+currentUserName(session));
		String msg = message.getPayload();//자바스크립트에서 넘어온 Msg
		logger.info("msg="+msg);
		
		if (!msg.isBlank()) {
			logger.info("if문 들어옴?");
			String[] strs = msg.split(",");
			if(strs != null && strs.length == 5) {
				for(String s : strs) System.out.println(s);
				String cmd = strs[0];
				// 보내는 놈
				String replyWriter = strs[1];
				// 받는 놈
				String boardWriter = strs[2];
				// 피드 번호
				String bno = strs[3];
				// 피드 제목
//				String title = strs[4];
//				String bgno = strs[5];
				logger.info("length 성공?"+cmd);
				
				WebSocketSession replyWriterSession = userSessionsMap.get(replyWriter);
				WebSocketSession boardWriterSession = userSessionsMap.get(boardWriter);
				logger.info("boardWriterSession="+userSessionsMap.get(boardWriter));
				logger.info("boardWirterSession"+boardWriterSession);
				
				// 팔로우
				if("follow".equals(cmd) && boardWriterSession != null) {
//					for(String s : strs) System.out.print(s+"\t");
					System.out.println("follow");
					//replyWriter = 좋아요누른사람 , boardWriter = 게시글작성자
//					TextMessage tmpMsg = new TextMessage(replyWriter + "님이 "
//							+ "<a href='/board/readView?bno=" + bno + "&bgno="+bgno+"'  style=\"color: black\"><strong>"
//							+ title+"</strong> 에 작성한 글을 DEV했습니다!</a>");
					TextMessage tmpMsg = new TextMessage("a"+replyWriter + " 님이 당신을 팔로우하였습니다."		
//							+ "<a href='/http://localhost:8080/cheerup/user/detail/"+replyWriter +"'");  
							+ "<a href='/profile/"+replyWriter +"'");  
					
					boardWriterSession.sendMessage(tmpMsg);
				}
				else {
					String title = feedService.readFeedById(Integer.parseInt(bno)).getFeedTitle();
					String content = strs[4];
					//스크랩
					if("scrap".equals(cmd) && boardWriterSession != null) {
						//replyWriter = 스크랩누른사람 , boardWriter = 게시글작성자
						TextMessage tmpMsg = new TextMessage("b"+replyWriter + "님이 "
								+ "<a href='/http://localhost:8080/detail/" + bno + "'  style=\"color: black; text-decoration: none\"><strong>"
								+ title+"</strong> 을 스크랩했습니다!</a>");
//					TextMessage tmpMsg = new TextMessage(replyWriter + "님이 "
//							+ "<a href='/board/readView?bno=" + bno + "&bgno="+bgno+"'  style=\"color: black\"><strong>"
//							+ title+"</strong> 에 작성한 글을 스크랩했습니다!</a>");
						
						boardWriterSession.sendMessage(tmpMsg);
					}
					
					//좋아요  
					else if("comment_like".equals(cmd) && boardWriterSession != null) {
						//replyWriter = 좋아요누른사람 , boardWriter = 게시글작성자
						System.out.println("*************");
						TextMessage tmpMsg = new TextMessage("c"+replyWriter + "님이 "
								+ "<a href='/detail/" + bno + "'  style=\"color: black; text-decoration: none\"><strong>"
								+ title+"</strong> 에 작성한 " + content + " 댓글을 좋아요했습니다!</a>");
						
//						<a href="/detail/7" style="color: black"><strong>김부겸 삼성 3년간 총 7만개 청년 일자리창출 기여</strong> 에 작성한 댓글을 좋아요했습니다!</a>
//					TextMessage tmpMsg = new TextMessage(replyWriter + "님이 "
//							+ "<a href='/board/readView?bno=" + bno + "&bgno="+bgno+"'  style=\"color: black\"><strong>"
//							+ title+"</strong> 에 작성한 댓글을 좋아요했습니다!</a>");
						
						boardWriterSession.sendMessage(tmpMsg);
					}
				}
				
				
				
				
				
				/*
				//댓글채택
				else if("questionCheck".equals(cmd) && replyWriterSession != null) {
					//replyWriter = 댓글작성자 , boardWriter = 글작성자
					TextMessage tmpMsg = new TextMessage(boardWriter + "님이 "
							+ "<a href='/board/readView?bno=" + bno + "&bgno="+bgno+"'  style=\"color: black\"><strong>"
							+ title+"</strong> 에 작성한 댓글을 채택했습니다!</a>");

					replyWriterSession.sendMessage(tmpMsg);
				}
				
				//댓글좋아요
				else if("commentLike".equals(cmd) && replyWriterSession != null) {
					logger.info("좋아요onmessage되나?");
					logger.info("result=board="+boardWriter+"//"+replyWriter+"//"+bno+"//"+bgno+"//"+title);
					//replyWriter=댓글작성자 , boardWriter=좋아요누른사람 
					TextMessage tmpMsg = new TextMessage(boardWriter + "님이 "
							+ "<a href='/board/readView?bno=" + bno + "&bgno="+bgno+"'  style=\"color: black\"><strong>"
							+ title+"</strong> 에 작성한 댓글을 추천했습니다!</a>");

					replyWriterSession.sendMessage(tmpMsg);
				}
				
				
				//댓글DEV
				else if("commentDev".equals(cmd) && replyWriterSession != null) {
					logger.info("좋아요onmessage되나?");
					logger.info("result=board="+boardWriter+"//"+replyWriter+"//"+bno+"//"+bgno+"//"+title);
					//replyWriter=댓글작성자 , boardWriter=좋아요누른사람 
					TextMessage tmpMsg = new TextMessage(boardWriter + "님이 "
							+ "<a href='/board/readView?bno=" + bno + "&bgno="+bgno+"'  style=\"color: black\"><strong>"
							+ title+"</strong> 에 작성한 댓글을 DEV했습니다!</a>");

					replyWriterSession.sendMessage(tmpMsg);
				}
				
				
				*/
				
			}
			
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {//연결 해제
		// TODO Auto-generated method stub
		logger.info("Socket 끊음");
		sessions.remove(session);
		userSessionsMap.remove(currentUserName(session),session);
	}
}
