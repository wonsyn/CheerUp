package com.web.curation.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.AlarmDto;
import com.web.curation.model.service.AlarmService;
import com.web.curation.model.service.JwtService;
import com.web.curation.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(tags = "알람")
@RestController
@RequestMapping("/alarm")
public class AlarmController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AlarmService alarmService;
	
	@ApiOperation(value="알람 생성", 
			  notes="1. 누군가 나를 팔로우 했을 때\n2. 내가 팔로우한 사람이 기사를 스크랩 했을 때")
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createAlarm(@RequestBody AlarmDto alarmDto, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			// 로그인 세션 정보 가져오기
			alarmDto.setAlarmReceiverId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			// 알람 생성 성공
			if(alarmService.createAlarm(alarmDto) == 1) {
				resultMap.put("message", SUCCESS);	
			}
			// 알람 생성 실패
			else {
				resultMap.put("message", FAIL);
				status = HttpStatus.INTERNAL_SERVER_ERROR;				
			}			
		} 
		// 서버 에러
		catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);	
	}
	
	@ApiOperation(value="알람 수정", 
			  notes="기존의 알람 내용이 변경될 때 발생")
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateAlarm(@RequestBody AlarmDto alarmDto, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			// 로그인 세션 정보 가져오기
			int loginUser = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
			// 요청 id == 로그인 세션 ?
			if(userService.isSameLoginUserAndRequestId(loginUser, alarmDto.getAlarmReceiverId())) {
				// 수정 성공
				if(alarmService.updateAlarm(alarmDto) == 1) {
					resultMap.put("message", SUCCESS);
					resultMap.put("alarmDetail", alarmService.getAlarmDetail(alarmDto.getAlarmId()));
				}
				// 수정 실패
				else {
					resultMap.put("message", FAIL);
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}
			// 요청id != 로그인 세션
			else {
				resultMap.put("message", FAIL);				
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="알람 삭제", 
			  notes="1. 일정 기간이 지난 확인 알람 삭제\n2. 사용자가 체크해 알람 삭제")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteAlarm(@PathVariable int id, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		int loginUser;
		try {
			// 로그인 세션 정보 가져오기
			loginUser = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
			// @pathvariable의 알람 id를 이용해 user_id 가져오기
			int owner = alarmService.getUserIdByAlarmId(id);
			// 요청 id == 로그인 세션
			if(userService.isSameLoginUserAndRequestId(loginUser, owner)) {
				// 삭제 성공
				if(alarmService.deleteAlarm(id) == 1) {
					resultMap.put("message", SUCCESS);
				}
				// 삭제 실패
				else {
					resultMap.put("message", FAIL);
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}
			// 요청 id != 로그인 세션
			else {
				resultMap.put("message", FAIL);
				status = HttpStatus.INTERNAL_SERVER_ERROR;				
			}
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;				
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="알람 세부 내용 조회", 
			  notes="알람을 클릭 했을 때 알람 세부 내용 출력")
	@GetMapping("/detail/{id}")
	public ResponseEntity<Map<String, Object>> getDetailAlarm(@PathVariable int alarmId, HttpServletRequest request) {	 
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			AlarmDto result = alarmService.getAlarmDetail(alarmId);
			if(result != null) {
				resultMap.put("alarmDetail", result);
				resultMap.put("message", SUCCESS);				
			}
			else {
				resultMap.put("message", FAIL);								
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap,status);
	}
	
	@ApiOperation(value="이번 달 알람 조회", 
			  notes="현재 년,월에 있는 알람 리스트 출력")
	@GetMapping("/listInThisMonth")
	public ResponseEntity<Map<String, Object>> getAlarmListInThisMonth(@RequestParam String thisMonth, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			AlarmDto alarm = new AlarmDto();
			alarm.setAlarmReceiverId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			alarm.setAlarmDateTime(thisMonth);
			
			resultMap.put("message", SUCCESS);
			resultMap.put("alarmList", alarmService.getAlarmListInThisMonth(alarm)); 
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value="보낸 사람 기준 알람 리스트 조회", 
			  notes="알람을 보낸 사람 기준으로 알람 리스트를 조회")
	@GetMapping("/listBySenderId")
	public ResponseEntity<Map<String, Object>> getAlarmListBySenderId(@RequestParam int senderId, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			AlarmDto alarm = new AlarmDto();
			alarm.setAlarmReceiverId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			alarm.setAlarmSenderId(senderId);
			
			resultMap.put("message", SUCCESS);
			resultMap.put("alarmList", alarmService.getAlarmListBySenderId(alarm));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value="알람 타입 기준 알람 리스트 조회", 
			  notes="알람 타입에 따른 알람 리스트 조회")
	@GetMapping("/listByType")
	public ResponseEntity<Map<String, Object>> getAlarmListByType(@RequestParam String type, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			AlarmDto alarm = new AlarmDto();
			alarm.setAlarmReceiverId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			alarm.setAlarmType(type);
			
			resultMap.put("message", SUCCESS);
			resultMap.put("alarmList", alarmService.getAlarmListByType(alarm));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status); 
	}
	
	@ApiOperation(value="알람 내용 기준 알람 리스트 조회", 
			  notes="알람 내용에 따른 알람 리스트 조회")
	@GetMapping("/listByContent")
	public ResponseEntity<Map<String, Object>> getAlarmListByContent(@RequestParam String content, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			AlarmDto alarm = new AlarmDto();
			alarm.setAlarmReceiverId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			alarm.setAlarmContent(content);
			
			resultMap.put("message", SUCCESS);
			resultMap.put("alarmList", alarmService.getAlarmListByContent(alarm));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status); 
	}
	
	@ApiOperation(value="선택 날짜 기준 알람 리스트 조회", 
			  notes="사용자가 선택한 시작~ 끝 날짜 사이에 존재하는 알람 리스트 조회")
	@GetMapping("/listByDate")
	public ResponseEntity<Map<String, Object>> getAlarmListByDate(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			AlarmDto alarm = new AlarmDto();
			int userId = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
//			alarm.setAlarmReceiverId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			
			resultMap.put("message", SUCCESS);
			resultMap.put("alarmList", alarmService.getAlarmListByDate(userId));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} 
		return new ResponseEntity<>(resultMap, status); 
	}
	
	@ApiOperation(value="확인한 알람 리스트 조회", 
			  notes="사용자가 확인한 알람 리스트 조회")
	@GetMapping("/listCheckedAlarm")
	public ResponseEntity<Map<String, Object>> getCheckedAlarm(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			resultMap.put("message", SUCCESS);
			resultMap.put("alaramList", alarmService.getCheckedAlarm(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")))));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} 
		return new ResponseEntity<>(resultMap, status); 
	}
	
	@ApiOperation(value="미확인 알람 리스트 조회", 
			  notes="사용자가 아직 확인하지 못한 알람 리스트 조회")
	@GetMapping("/listUnCheckedAlarm")
	public ResponseEntity<Map<String, Object>> getUnCheckedAlarm(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			resultMap.put("message", SUCCESS);
			resultMap.put("alaramList", alarmService.getUnCheckedAlarm(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")))));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} 
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value="알람 체크", 
			  notes="미확인 알람을 확인 알람으로 바꾸는 기능")
	@PutMapping("/check")
	public ResponseEntity<Map<String, Object>> checkAlarm(@RequestParam int alarmId, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			if(alarmService.checkAlarm(alarmId) == 1)  {
				resultMap.put("message", SUCCESS);
//				resultMap.put("alaramList", alarmService.getUnCheckedAlarm(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")))));
			}
			else {
				resultMap.put("message", FAIL);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value="미확인 알람 리스트 조회", 
			  notes="사용자가 아직 확인하지 못한 알람 리스트 조회")
	@GetMapping("/getlatestalarm")
	public ResponseEntity<Map<String, Object>> getlatestalarm(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			resultMap.put("message", SUCCESS);
			resultMap.put("alarmDetail", alarmService.getLatestAlarm(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")))));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		} 
		return new ResponseEntity<>(resultMap, status);
	}
	
}
