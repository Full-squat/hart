package com.hart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hart.domain.CategoryVO;
import com.hart.domain.ProductlistbuttonVO;
import com.hart.domain.ProductsVO;
import com.hart.service.ProductsService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/papi")
@Log4j2
public class ProductRestController {

	@Autowired
	private ProductsService pService;

	@GetMapping(value = "/clist", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, List<CategoryVO>>> getCategory() {

		try {

			Map<String, List<CategoryVO>> result = new HashMap<>();

			List<CategoryVO> categorybar = pService.getcategorybar();

			result.put("result", categorybar);

			return new ResponseEntity<Map<String, List<CategoryVO>>>(result, HttpStatus.OK);

		} catch (Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<Map<String, List<CategoryVO>>>(HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/clistsamll", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, List<ProductsVO>>> getProductsList(@RequestBody Map<String, String> data) {

		try {
			String pcno = data.get("pcno");
			List<ProductsVO> getproductslist = pService.getproductslistajax(Integer.parseInt(pcno));
			// log.info("getproductslist=>>>" + getproductslist);

			Map<String, List<ProductsVO>> result = new HashMap<>();
			result.put("getproductslist", getproductslist);

			return new ResponseEntity<Map<String, List<ProductsVO>>>(result, HttpStatus.OK);

		} catch (Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<Map<String, List<ProductsVO>>>(HttpStatus.BAD_REQUEST);
		}
	}

	// 필터 가격 높은순

	@PostMapping(value = "/fillterbutton", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, List<ProductlistbuttonVO>>> fillterbutton(
			@RequestBody Map<String, String> data) {

		try {
			String type = data.get("type"); // "sales"
			String sort = data.get("sort"); // "2"
			String pcno = data.get("pcno"); // "100571"
			
			
			List<ProductlistbuttonVO> fillter = pService.getproductlisthigh(Integer.parseInt(pcno));
			log.info("fillter=>>>" + fillter);

			Map<String, List<ProductlistbuttonVO>> result = new HashMap<>();
			result.put("fillter", fillter);

			return new ResponseEntity<Map<String, List<ProductlistbuttonVO>>>(result, HttpStatus.OK);

		} catch (Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<Map<String, List<ProductlistbuttonVO>>>(HttpStatus.BAD_REQUEST);
		}
	}

}