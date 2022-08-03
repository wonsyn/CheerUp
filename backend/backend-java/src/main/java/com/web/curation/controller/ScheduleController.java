package com.web.curation.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping("/create")
	public ResponseEntity<Void> createSchedule(@RequestBody ScheduleDto scheduleDto, HttpServletRequest request) throws SQLException{
		
		scheduleDto.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		System.out.println(scheduleDto);
		if(scheduleService.createSchedule(scheduleDto) == 1) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Void> updateSchedule(@RequestBody ScheduleDto scheduleDto, HttpServletRequest request) throws SQLException {
		
		scheduleDto.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		// 수정
		if(scheduleService.updateSchedule(scheduleDto) == 1) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteBoard(@PathVariable int id) throws SQLException{
		
		if(scheduleService.deleteSchedule(id) == 1) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<Map<String, Object>> getDetailSchedule(@PathVariable int scheduleId, HttpServletRequest request) {	 
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			ScheduleDto result = scheduleService.getScheduleDetail(scheduleId);
			if(result != null) {
				resultMap.put("scheduleInfo", result);
				resultMap.put("message", SUCCESS);				
			}
			else {
				resultMap.put("message", FAIL);								
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap,status);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<ScheduleDto>> getScheduleList(@RequestParam String scheduleDate,HttpServletRequest request) throws SQLException{
		
		ScheduleDto schedule = new ScheduleDto();
		schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		schedule.setScheduleDate(scheduleDate);
		return new ResponseEntity<List<ScheduleDto>>(scheduleService.getScheduleList(schedule),HttpStatus.OK); 
	}
	
	@GetMapping("/searchByTitle")
	public ResponseEntity<List<ScheduleDto>> getScheduleListByTitle(@RequestParam String title, HttpServletRequest request) throws SQLException{
		ScheduleDto schedule = new ScheduleDto();
		schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		schedule.setScheduleTitle(title);
		
		System.out.println("title: "+ title);
		
		return new ResponseEntity<List<ScheduleDto>>( scheduleService.getScheduleListByTitle(schedule), HttpStatus.OK);
	}
	
	@GetMapping("/searchByCompany")
	public ResponseEntity<List<ScheduleDto>> getScheduleListByCompany(@RequestParam String company, HttpServletRequest request) throws SQLException{
		ScheduleDto schedule = new ScheduleDto();
		schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		schedule.setScheduleCompany(company);
		
		System.out.println("company: "+company);
		
		return new ResponseEntity<List<ScheduleDto>>( scheduleService.getScheduleListByCompany(schedule), HttpStatus.OK);
	}
	
	@GetMapping("/searchByDate")
	public ResponseEntity<List<ScheduleDto>> getScheduleListByDate(@RequestParam String startDate, @RequestParam String endDate, HttpServletRequest request) throws SQLException{
		ScheduleDto schedule = new ScheduleDto();
		schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		schedule.setStartDate(startDate);
		schedule.setEndDate(endDate);
		
		System.out.println("[start,end]: "+ startDate +"," + endDate+"]");
		
		return new ResponseEntity<List<ScheduleDto>>( scheduleService.getScheduleListByDate(schedule), HttpStatus.OK);

	}
	
	@GetMapping("/searchByMemo")
	public ResponseEntity<List<ScheduleDto>> getScheduleListByMemo(@RequestParam String memo, HttpServletRequest request) throws SQLException{
		ScheduleDto schedule = new ScheduleDto();
		schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		schedule.setScheduleMemo(memo);
		
		System.out.println("memo: "+memo);
		
		return new ResponseEntity<List<ScheduleDto>>( scheduleService.getScheduleListByMemo(schedule), HttpStatus.OK);
		
	}
	
	@GetMapping("/searchByCategory")
	public ResponseEntity<List<ScheduleDto>> getScheduleListByCategory(@RequestParam String category, HttpServletRequest request) throws SQLException{
		ScheduleDto schedule = new ScheduleDto();
		schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		schedule.setScheduleCategory(category);
		
		System.out.println("category: "+category);
		
		return new ResponseEntity<List<ScheduleDto>>( scheduleService.getScheduleListByCategory(schedule), HttpStatus.OK);
	}
	
	@GetMapping("/searchByStatus")
	public ResponseEntity<List<ScheduleDto>> getScheduleListByStatus(@RequestParam String status, HttpServletRequest request) throws SQLException{
		ScheduleDto schedule = new ScheduleDto();
		schedule.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		schedule.setScheduleStatus(status);
		
		System.out.println("status: "+status);
		
		return new ResponseEntity<List<ScheduleDto>>( scheduleService.getScheduleListByStatus(schedule), HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<ScheduleDto>> getScheduleListByConditions(@RequestBody ScheduleDto conditions, HttpServletRequest request) throws SQLException{
		conditions.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		System.out.println("conditions: "+conditions);
		
		return new ResponseEntity<List<ScheduleDto>>( scheduleService.getScheduleListByConditions(conditions), HttpStatus.OK);
	}
}
