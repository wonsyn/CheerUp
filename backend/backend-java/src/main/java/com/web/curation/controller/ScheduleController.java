package com.web.curation.controller;

import java.sql.SQLException;
import java.util.HashMap;
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

import com.web.curation.model.dto.ScheduleDto;
import com.web.curation.model.service.JwtService;
import com.web.curation.model.service.ScheduleService;
import com.web.curation.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(tags = "개인 일정")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "개인 일정 생성", 
			notes = "return { 'message' : success,에러메세지' ")
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createSchedule(@RequestBody ScheduleDto scheduleDto, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			scheduleDto.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			if(scheduleService.createSchedule(scheduleDto) == 1) {
				resultMap.put("message", SUCCESS);
			}
			else {
				resultMap.put("message", FAIL);
				status = HttpStatus.INTERNAL_SERVER_ERROR;		
			}
		} 
		catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "개인 일정 수정", 
			notes = "return { 'message' : success,에러메세지, 'scheduleList' : 리스트 } ")
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> updateSchedule(@RequestBody ScheduleDto scheduleDto, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int loginUser = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
			scheduleDto.setUserId(loginUser);
			// 로그인 세션 정보 가져오기
			// 요청 id == 로그인 세션 ?
			if(userService.isSameLoginUserAndRequestId(loginUser, scheduleDto.getUserId())) {
				// 수정 성공
				if(scheduleService.updateSchedule(scheduleDto) == 1) {
					resultMap.put("message", SUCCESS);
					resultMap.put("scheduleDetail", scheduleService.getScheduleDetail(scheduleDto.getScheduleId()));
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
			e.printStackTrace();
		}
		// 수정
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	////////////////////////////////////////////////////
	@ApiOperation(value = "개인 일정 삭제", 
			notes = "return { 'message' : success,에러메세지}")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable int id, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int dbId = scheduleService.getUserIdByScheduleId(id);
			int loginUser = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
			
			if(userService.isSameLoginUserAndRequestId(loginUser, dbId)) {
				// 삭제 성공
				if(scheduleService.deleteSchedule(id) == 1) {
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
	
	@ApiOperation(value = "개인 일정 상세 정보", 
			notes = "return { 'message' : success,에러메세지, 'scheduleDetail' : 리스트 }")
	@GetMapping("/detail/{id}")
	public ResponseEntity<Map<String, Object>> getDetailSchedule(@PathVariable int scheduleId, HttpServletRequest request) {	 
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			ScheduleDto result = scheduleService.getScheduleDetail(scheduleId);
			if(result != null) {
				resultMap.put("scheduleDetail", result);
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
	
	@ApiOperation(value = "이번 달 개인 일정 조회", 
			notes = "return { 'message' : success,에러메세지, 'scheduleList' : 리스트 }")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getScheduleList(@RequestParam String scheduleDate,HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			ScheduleDto schedule = new ScheduleDto();
			schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			schedule.setScheduleDate(scheduleDate);
			
			resultMap.put("message", SUCCESS);
			resultMap.put("scheduleList", scheduleService.getScheduleList(schedule));			
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status); 
	}
	
	@ApiOperation(value = "개인 일정 제목으로 조회", 
			notes = "return { 'message' : success,에러메세지, 'scheduleList' : 리스트 }")
	@GetMapping("/searchByTitle")
	public ResponseEntity<Map<String, Object>> getScheduleListByTitle(@RequestParam String title, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			ScheduleDto schedule = new ScheduleDto();
			schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			schedule.setScheduleTitle(title);
		
			resultMap.put("message", SUCCESS);
			resultMap.put("scheduleList", scheduleService.getScheduleListByTitle(schedule));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "개인 일정 회사명으로 조회", 
			notes = "return { 'message' : success,에러메세지, 'scheduleList' : 리스트 }")
	@GetMapping("/searchByCompany")
	public ResponseEntity<Map<String, Object>> getScheduleListByCompany(@RequestParam String company, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			ScheduleDto schedule = new ScheduleDto();
			schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			schedule.setScheduleCompany(company);
			
			resultMap.put("message", SUCCESS);
			resultMap.put("scheduleList", scheduleService.getScheduleListByCompany(schedule));	
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}	
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "개인 일정 날짜로 조회", 
			notes = "return { 'message' : success,에러메세지, 'scheduleList' : 리스트 }")
	@GetMapping("/searchByDate")
	public ResponseEntity<Map<String, Object>> getScheduleListByDate(@RequestParam String startDate, @RequestParam String endDate, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			ScheduleDto schedule = new ScheduleDto();
			schedule.setStartDate(startDate);
			schedule.setEndDate(endDate);
			schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			
			resultMap.put("message", SUCCESS);
			resultMap.put("scheduleList", scheduleService.getScheduleListByDate(schedule));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "개인 일정 메모 내용으로 조회", 
			notes = "return { 'message' : success,에러메세지, 'scheduleList' : 리스트 }")
	@GetMapping("/searchByMemo")
	public ResponseEntity<Map<String, Object>> getScheduleListByMemo(@RequestParam String memo, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			ScheduleDto schedule = new ScheduleDto();
			schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			schedule.setScheduleMemo(memo);
			
			resultMap.put("message", SUCCESS);
			resultMap.put("scheduleList", scheduleService.getScheduleListByMemo(schedule));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "개인 일정 분류(서류,인성,1차,2차,코테,....)로 조회", 
			notes = "return { 'message' : success,에러메세지, 'scheduleList' : 리스트 }")
	@GetMapping("/searchByCategory")
	public ResponseEntity<Map<String, Object>> getScheduleListByCategory(@RequestParam String category, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			ScheduleDto schedule = new ScheduleDto();
			schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			schedule.setScheduleCategory(category);
			
			resultMap.put("message", SUCCESS);
			resultMap.put("scheduleList", scheduleService.getScheduleListByCategory(schedule));
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);		
	}
	
	@ApiOperation(value = "개인 일정 상태(합,불합)로 조회", 
			notes = "return { 'message' : success,에러메세지, 'scheduleList' : 리스트 }")
	@GetMapping("/searchByStatus")
	public ResponseEntity<Map<String, Object>> getScheduleListByStatus(@RequestParam String scheduleStatus, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			ScheduleDto schedule = new ScheduleDto();
			schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			schedule.setScheduleStatus(scheduleStatus);
			
			resultMap.put("message", SUCCESS);
			resultMap.put("scheduleList", scheduleService.getScheduleListByStatus(schedule));	
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@ApiOperation(value = "개인 일정 다중 조건(회사명 + 분류 + 상태, ...)으로 조회", 
			notes = "return { 'message' : success,에러메세지, 'scheduleList' : 리스트 }")
	@PostMapping("/search")
	public ResponseEntity<Map<String, Object>> getScheduleListByConditions(@RequestBody ScheduleDto conditions, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			conditions.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
			
			resultMap.put("message", SUCCESS);
			resultMap.put("scheduleList", scheduleService.getScheduleListByConditions(conditions));	
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap, status);
	}
}
