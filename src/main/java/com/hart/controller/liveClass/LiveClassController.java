package com.hart.controller.liveClass;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hart.domain.liveClass.LiveClassDetailInfoDTO;
import com.hart.domain.liveClass.LiveClassListDTO;
import com.hart.domain.liveClass.LiveClassVideoDTO;
import com.hart.domain.liveClass.MyLiveClassInfoDTO;
import com.hart.service.liveClass.LiveClassService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 3. 15.
 * @FileName: LiveClassController.java
 * @author : 함세강
 * @설명 : LiveClass 관련 요청 처리 컨트롤러
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 15.     함세강       LiveClassController 구현
 *     </pre>
 */
@Controller
@RequestMapping("/class")
@RequiredArgsConstructor
@Log4j2
public class LiveClassController {

	private final LiveClassService service;
	
	@GetMapping
	public String getLiveClassList(Model model) {
		log.info("getLiveClassList 컨트롤러 호출");
		List<LiveClassListDTO> list = service.getList();
		model.addAttribute("liveClassList",list);
		return "liveClass/liveClassList";
	}
	
	@GetMapping("/detail/{lcid}")
	public String getLiveClassListDetail(Model model, @PathVariable String lcid) {
		log.info("getLiveClassListDetail 컨트롤러 호출");
		LiveClassDetailInfoDTO dto = service.getClassDetail(lcid);
		model.addAttribute("liveClass",dto);
		log.info(model);
		return "liveClass/liveClassDetail";
	}
	
	@GetMapping("/video/{lcid}")
	public String getVideoDetail(@PathVariable String lcid, Model model) {
		log.info("videoDetail 컨트롤러 호출");
		LiveClassVideoDTO videoInfo =  service.getClassVideo(lcid);
		log.info(videoInfo);
		model.addAttribute("videoInfo",videoInfo);
		return "liveClass/liveClassVideo";
	}
	
	@GetMapping("/mypage")
	public String myPageTest(Principal pr,Model model) {
		log.info("myPage 컨트롤러 호출");
		String mid = pr.getName();
		List<MyLiveClassInfoDTO> list =  service.getMyClassInfo(mid);
		log.info(list);
		model.addAttribute("classList",list);
		return "liveClass/liveClassMypage";
	}
	
	
	
	
//	@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@테스트용
	@GetMapping("/testchat")
	public String streamingTestChat() {
		log.info("streamingTest 컨트롤러 호출");
		
		return "liveClass/liveClassChatTestStream";
	}
	
	@GetMapping("/testdetail/{lcid}")
	public String liveClassDetailTest(Model model, @PathVariable String lcid) {
		LiveClassDetailInfoDTO dto = service.getClassDetail(lcid);
		model.addAttribute("liveClass",dto);
		log.info(model);
		
		return "liveClass/testdetail";
	}
	
	
}
