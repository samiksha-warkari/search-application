package com.api.search.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.search.service.SearchService;
import com.api.search.valueobjects.ItemVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Description : SearchController is the Rest Api end point for searching the books and albums from the 3rd party apis.
 * 
 * @author : Samiksha Warkari
 * @Date : 13-Sep-2020
 */
@Api(value = "SearchController")
@RestController
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	@Autowired
	SearchService searchService;
	/**
	 * Description : Search method helps to search the albums and books information and returns the list of data with alphabetically sorted order.
	 * @ApiOperation and  @ApiResponses are the swagger annotations used for the documentation. 
	 * 
	 * @author : Samiksha Warkari
	 * @Date : 13-Sep-2020
	 */
	@ApiOperation(httpMethod = "GET", value = "Search books and albums", response = ResponseEntity.class, responseContainer = "ResponseEntity")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid parameters provided"),
			@ApiResponse(code = 200, message = "Data has been retrieved successfully"),
			@ApiResponse(code = 404, message = "Books and albums data not found"),
			@ApiResponse(code = 500, message = "Books and albums data could not be fetched") })
	@GetMapping("/search")
	public ResponseEntity<Object> search(@RequestParam(required = true) String text) {
		logger.info("Inside search method");
		List<ItemVO> response = searchService.search(text);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
