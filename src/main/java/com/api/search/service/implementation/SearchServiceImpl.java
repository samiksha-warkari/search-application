package com.api.search.service.implementation;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.search.configuration.ApplicationProperties;
import com.api.search.exception.DataNotFoundException;
import com.api.search.exception.InValidRequestParameterException;
import com.api.search.service.SearchClientService;
import com.api.search.service.SearchService;
import com.api.search.valueobjects.ItemVO;

/**
 * @author : Samiksha Warkari
 * @Date : 13-Sep-2020
 */
@Service
public class SearchServiceImpl implements SearchService {
	private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	@Autowired
	SearchClientService searchClientService;
	@Autowired
	ApplicationProperties properties;

	@Override
	public List<ItemVO> search(String text) {
		logger.info("Inside search method");
		List<ItemVO> sortedData = null;

		if (text != null) {
			text = text.trim();
			if (!text.isEmpty()) {
				Long maxResults = properties.getMaxResults();
				Long limit = properties.getLimit();
				logger.info("MaxResults Value :: " + maxResults);
				logger.info("Limit Value :: " + limit);
				/* Since books Api of google returns maximum 40 records */
				if (maxResults > 40 || maxResults <= 0 ) {
					logger.error(
							"Values must be within the range of 1-40",
							sortedData);
					throw new InValidRequestParameterException(
							"Values must be within the range of 1-40");
				} else {
					CompletableFuture<List<ItemVO>> books = searchClientService.searchBooks(text,
							maxResults);
					CompletableFuture<List<ItemVO>> albums = searchClientService.searchAlbums(text,
							limit);
					if (books != null || albums != null) {
						try {
							/*
							 * Merge the books and albums list into a single
							 * list in an alphabetically sorted order
							 */
							sortedData = Stream.concat(books.get().stream(), albums.get().stream())
									.sorted((book, album) -> book.getTitle().compareTo(album.getTitle()))
									.collect(Collectors.toList());
							if (sortedData.size() <= 0) {
								logger.error("Data not found", sortedData);
								throw new DataNotFoundException("Data not found");
							}
						} catch (InterruptedException e) {
							logger.error("InterruptedException OCCURED :: " + e.getMessage());
						} catch (ExecutionException e) {
							logger.error("ExecutionException OCCURED :: " + e.getMessage());
						}
						logger.info("Data has been retrieved successfully", sortedData);
					} else {
						logger.error("Data not found", sortedData);
						throw new DataNotFoundException("Data not found");
					}
				}

			} else {
				logger.error("Invalid input", sortedData);
				throw new InValidRequestParameterException("Invalid input");
			}
		}
		return sortedData;
	}
}
