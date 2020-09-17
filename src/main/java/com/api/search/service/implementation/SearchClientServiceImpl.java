package com.api.search.service.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.api.search.configuration.AppConfig;
import com.api.search.configuration.ApplicationProperties;
import com.api.search.service.SearchClientService;
import com.api.search.valueobjects.ItemVO;

/**
 * Description : SearchClientServiceImpl contains the implementation of calling
 * the 3rd party api for the data.
 * 
 * @author : Samiksha Warkari
 * @Date : 13-Sep-2020
 */
@Service
public class SearchClientServiceImpl implements SearchClientService {
	private static final Logger logger = LoggerFactory.getLogger(SearchClientServiceImpl.class);
	@Autowired
	AppConfig config;

	@Autowired
	ApplicationProperties properties;

	/**
	 * Description : SearcBooks method returns the list of books from 3rd party
	 * API. @Async is used, since this call is an independent call which can
	 * execute independently without waiting for the result of other method
	 * call.CompletableFuture isthe type need to use while using Async
	 * annotation, so that it will return the data once the execution of the
	 * method completes.
	 * 
	 * @author : Samiksha Warkari
	 * @Date : 13-Sep-2020
	 */
	@Async
	@Override
	public CompletableFuture<List<ItemVO>> searchBooks(String text, Long maxResults) {
		logger.info("Inside searchBooks method");
		List<ItemVO> itemsVO = new ArrayList<ItemVO>();
		String url = properties.getBook() + text + "&maxResults=" + maxResults;
		logger.info("Books URL :: " + url);
		Object object = config.restTemplate.getForObject(url, Object.class);
		/*
		 * default json deserialize picks linkedhashmap for the non-identifiable
		 * objects during deserialize process.
		 */
		@SuppressWarnings("unchecked")
		HashMap<String, Object> jsonObject = (HashMap<String, Object>) object;
		for (Entry<String, Object> mapEntry : jsonObject.entrySet()) {
			String key = mapEntry.getKey();
			if (key.equalsIgnoreCase("items")) {

				@SuppressWarnings("unchecked")
				List<String> items = (List<String>) mapEntry.getValue();
				Iterator<String> itemsIterator = items.iterator();
				while (itemsIterator.hasNext()) {
					ItemVO itemVO = new ItemVO();
					Object itemJson = itemsIterator.next();
					/*
					 * default json deserialize picks linkedhashmap for the
					 * non-identifiable objects during deserialize process.
					 */
					@SuppressWarnings("unchecked")
					HashMap<String, Object> itemJsonValue = (HashMap<String, Object>) itemJson;
					for (Entry<String, Object> itemJsonMapEntry : itemJsonValue.entrySet()) {
						String itemKey = itemJsonMapEntry.getKey();
						if (itemKey.equalsIgnoreCase("kind")) {
							itemVO.setKind("Book");
						} else if (itemKey.equalsIgnoreCase("volumeInfo")) {
							/*
							 * default json deserialize picks linkedhashmap for
							 * the non-identifiable objects during deserialize
							 * process.
							 */
							@SuppressWarnings("unchecked")
							HashMap<String, Object> itemJsonVolumeInfo = (HashMap<String, Object>) itemJsonMapEntry
									.getValue();
							for (Entry<String, Object> volumeInfoMapEntry : itemJsonVolumeInfo.entrySet()) {
								String volumeInfoKey = volumeInfoMapEntry.getKey();
								if (volumeInfoKey.equalsIgnoreCase("title")) {
									String title = volumeInfoMapEntry.getValue().toString();
									title = (title.isEmpty()) ? "-" : title;
									itemVO.setTitle(title);
								} else if (volumeInfoKey.equalsIgnoreCase("authors")) {
									@SuppressWarnings("unchecked")
									List<String> authors = (List<String>) volumeInfoMapEntry.getValue();
									itemVO.setAuthors(authors);
								}
								/*
								 * Break the loop once we get the required field
								 * values to reducethe excecution time
								 */
								if (itemVO.getKind() != null && itemVO.getTitle() != null
										&& itemVO.getAuthors() != null) {
									break;
								}
							}
						}
						/*
						 * Break the loop once we get the required field values
						 * to reducethe excecution time
						 */
						if (itemVO.getKind() != null && itemVO.getTitle() != null && itemVO.getAuthors() != null) {
							break;
						}
					}
					itemsVO.add(itemVO);
				}
			}
		}
		return CompletableFuture.completedFuture(itemsVO);
	}

	/**
	 * Description : SearcAlbums method returns the list of albums from 3rd
	 * party API. @Async is used, since this call is an independent call which
	 * can execute independently without waiting for the result of other method
	 * call.CompletableFuture isthe type need to use while using Async
	 * annotation, so that it will return the data once the execution of the
	 * method completes.
	 * 
	 * @author : Samiksha Warkari
	 * @Date : 13-Sep-2020
	 */
	@Async
	@Override
	public CompletableFuture<List<ItemVO>> searchAlbums(String text, Long limit) {
		logger.info("Inside searchAlbums method");
		List<ItemVO> itemsVO = new ArrayList<ItemVO>();
		String url = properties.getAlbum() + text + "&limit=" + limit;
		logger.info("Album URL :: " + url);
		Object object = config.restTemplate.getForObject(url, Object.class);
		/*
		 * default json deserialize picks linkedhashmap for the non-identifiable
		 * objects during deserialize process.
		 */
		@SuppressWarnings("unchecked")
		HashMap<String, Object> jsonObject = (HashMap<String, Object>) object;
		for (Entry<String, Object> mapEntry : jsonObject.entrySet()) {
			String key = mapEntry.getKey();
			if (key.equalsIgnoreCase("results")) {

				@SuppressWarnings("unchecked")
				List<String> items = (List<String>) mapEntry.getValue();
				Iterator<String> itemsIterator = items.iterator();
				while (itemsIterator.hasNext()) {
					ItemVO itemVO = new ItemVO();
					Object itemJson = itemsIterator.next();
					/*
					 * default json deserialize picks linkedhashmap for the
					 * non-identifiable objects during deserialize process.
					 */
					@SuppressWarnings("unchecked")
					HashMap<String, Object> itemJsonValue = (HashMap<String, Object>) itemJson;
					for (Entry<String, Object> itemJsonMapEntry : itemJsonValue.entrySet()) {
						String itemKey = itemJsonMapEntry.getKey();
						if (itemKey.equalsIgnoreCase("collectionType")) {
							itemVO.setKind(itemJsonMapEntry.getValue().toString());
						} else if (itemKey.equalsIgnoreCase("collectionName")) {
							itemVO.setTitle(itemJsonMapEntry.getValue().toString());
						} else if (itemKey.equalsIgnoreCase("artistName")) {
							itemVO.setAuthors(Arrays.asList(itemJsonMapEntry.getValue().toString()));
						}
						/*
						 * Break the loop once we get the required field values
						 * to reduce the excecution time
						 */
						if (itemVO.getKind() != null && itemVO.getTitle() != null && itemVO.getAuthors() != null) {
							break;
						}
					}
					itemsVO.add(itemVO);
				}
			}
		}
		return CompletableFuture.completedFuture(itemsVO);
	}
}
