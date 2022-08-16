package com.web.curation.config;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.web.curation.model.dto.AlarmDto;
import com.web.curation.model.dto.UserDto;
import com.web.curation.model.service.AlarmService;
import com.web.curation.model.service.FeedService;
import com.web.curation.model.service.JwtService;
import com.web.curation.model.service.UserService;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
	//로그인 한 인원 전체
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 1:1로 할 경우
	private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();
	
	@Autowired
	FeedService feedService;
	
	@Autowired
	AlarmService alarmService;
	
	@Autowired
	UserService userService;
	
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
		
		//자바스크립트에서 넘어온 Msg
		String msg = message.getPayload();
		
		if (!msg.isBlank()) {
			
			String[] strs = msg.split(",");
			
			if(strs != null && strs.length == 5) {
				for(String s : strs) System.out.println(s);
				// 요청 종류( follow, scrap, comment_like )
				String cmd = strs[0];
				// 보내는 놈
				String senderId = strs[1];
				// 받는 놈
				String receiverId = strs[2];
				// 피드 번호
				String feedId = strs[3];
				// 피드 제목
				String title = "";
				// 댓글 내용 ( comment_like에서만 사용 )
				String comment = strs[4];
				// 프론트 toast 클릭 시 이동할 경로
				String url = "";
				// toast body에 들어갈 내용
				String content = "";
				// 프론트로 전달하는 객체
				TextMessage tmpMsg = null;

				WebSocketSession senderSession = userSessionsMap.get(senderId);
				WebSocketSession receiverSession = userSessionsMap.get(receiverId);
				
				// 팔로우
				if("follow".equals(cmd) && receiverSession != null) {
					
					content = senderId+" 님이 팔로우하였습니다.";
					url = "/profile/"+senderId;
					
//					for(String s : strs) System.out.print(s+"\t");
					System.out.println("follow");
					//replyWriter = 좋아요누른사람 , boardWriter = 게시글작성자
//					TextMessage tmpMsg = new TextMessage(replyWriter + "님이 "
//							+ "<a href='/board/readView?bno=" + bno + "&bgno="+bgno+"'  style=\"color: black\"><strong>"
//							+ title+"</strong> 에 작성한 글을 DEV했습니다!</a>");
//					tmpMsg = new TextMessage("a"+senderId + " 님이 당신을 팔로우하였습니다!"		
//							+ "<a href='/http://localhost:8080/cheerup/user/detail/"+replyWriter +"'");  
//							+ "<a href='/profile/"+senderId +"' data-v-c2383e3c=''>"+ senderId +"</a>");
//							+ "<router-link to = '/profile/"+replyWriter +"'>"+replyWriter + " 님이 당신을 팔로우하였습니다."+"</router-link>");  
					
//					<a href="/calendar" class="nav-link" data-v-c2383e3c="">달력</a>
					
//					replyWriter + " 님이 당신을 팔로우하였습니다." => content
					
//					boardWriterSession.sendMessage(tmpMsg);
					
//					tmpMsg = new TextMessage(senderId+",");
				}
				else {
					
					title = feedService.readFeedById(Integer.parseInt(feedId)).getFeedTitle();
					url = "/detail/" + feedId;
					
					//스크랩
					if("scrap".equals(cmd) && receiverSession != null) {
						content = senderId+" 님이 "+ title + " 을 스크랩하였습니다!";
//						alarmDto.setAlarmContent(senderId+" 님이 "+ title + " 을 스크랩하였습니다!");
						
//						tmpMsg = new TextMessage("b"+senderId + "님이 "
//								+ "<a href='/http://localhost:8080/detail/" + feedId + "'  style=\"color: black; text-decoration: none\"><strong>"
//								+ title+"</strong> 을 스크랩했습니다!</a>");
//					TextMessage tmpMsg = new TextMessage(replyWriter + "님이 "
//							+ "<a href='/board/readView?bno=" + bno + "&bgno="+bgno+"'  style=\"color: black\"><strong>"
//							+ title+"</strong> 에 작성한 글을 스크랩했습니다!</a>");
						
//						boardWriterSession.sendMessage(tmpMsg);
//						user1 님이 팔로우하였습니다.
					}
					
					//좋아요  
					else if("comment_like".equals(cmd) && receiverSession != null) {

						content = senderId+" 님이 "+ title + "에 작성한 " + comment + "댓글을 좋아요 하였습니다!";
						
//						tmpMsg = new TextMessage("c"+senderId + "님이 "
//								+ "<a href='/detail/" + feedId + "'  style=\"color: black; text-decoration: none\"><strong>"
//								+ title+"</strong> 에 작성한 " + content + " 댓글을 좋아요했습니다!</a>");
						
//						<a href="/detail/7" style="color: black"><strong>김부겸 삼성 3년간 총 7만개 청년 일자리창출 기여</strong> 에 작성한 댓글을 좋아요했습니다!</a>
//					TextMessage tmpMsg = new TextMessage(replyWriter + "님이 "
//							+ "<a href='/board/readView?bno=" + bno + "&bgno="+bgno+"'  style=\"color: black\"><strong>"
//							+ title+"</strong> 에 작성한 댓글을 좋아요했습니다!</a>");
						url = "/detail/" + feedId;
						
//						boardWriterSession.sendMessage(tmpMsg);
					}
					
					// 일정 등록
					else if("schedule".equals(cmd) && receiverSession != null) {
						
					}
				}
				
				int receiverUserId = userService.getUserIdById(receiverId);
				int senderUserId = userService.getUserIdById(senderId);
				// 알림이 이미 존재하는지 확인
				// 알람이 있다면 날짜 변경, 확인 시간 = null
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				AlarmDto alarmDto = new AlarmDto(receiverUserId, senderUserId, cmd, content, url,sdf.format(new Date()));
				alarmDto = alarmService.alreadyExist(alarmDto);
				if(alarmDto != null) {
					// 
					System.out.println("yas");
					alarmDto.setAlarmDateTime(sdf.format(new Date()));
					alarmService.updateAlarm(alarmDto);
				} else {					
					System.out.println("nope");
					// 알람이 없다면 새로 생성
					alarmService.createAlarm(new AlarmDto(receiverUserId, senderUserId, cmd, content, url,sdf.format(new Date())));			
				}
				
				// 프론트로 메세지 전달
				tmpMsg = new TextMessage(cmd +","+ senderId +","+ receiverId +","+ title +","+ content +","+ url +"," +sdf.format(new Date()));				
				receiverSession.sendMessage(tmpMsg);
				
				/*
				alarm_content
				
				alarm_type(String), receiver_id(String), url 
				follow, user1, /profile/user
				*/
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
