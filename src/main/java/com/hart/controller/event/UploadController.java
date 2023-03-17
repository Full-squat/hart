package com.hart.controller.event;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hart.domain.ProductsVO;
import com.hart.service.event.EventService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/event/api")
public class UploadController {

	@Autowired
	private EventService eventService;

	@PostMapping("/uploadAjax")
	public void uploadFile(MultipartFile[] uploadFiles) {

		log.info(uploadFiles);
		for (MultipartFile i : uploadFiles) {
			// [IE] 실제 파일 이름 전체 경로가 들어오므로
			// String fileName =
			// originalName.substring(originalName.lastIndexOf("\\"+1));
			String originalName = i.getOriginalFilename();
			log.info("fileName :" + originalName);
		} // end for

	}// end void

	@GetMapping("/search")
	public ResponseEntity<List<ProductsVO>> searchProduct(String keyword) throws SQLException {
		return ResponseEntity.ok(eventService.getList(keyword));
	}
}// end class
