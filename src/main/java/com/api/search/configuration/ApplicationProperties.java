package com.api.search.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description : ApplicationProperties mapped automatically with the properties
 * in the application.properties file by using
 * annotation @ConfigurationProperties which helps to access the properties
 * value by getter methods
 * 
 * @author : Samiksha Warkari
 * @Date : 14-Sep-2020
 */
@Configuration
@ConfigurationProperties
public class ApplicationProperties {
	private String book;
	private String album;
	private Long maxResults;
	private Long limit;

	public String getBook() {
		return book;
	}

	public String getAlbum() {
		return album;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public Long getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Long maxResults) {
		this.maxResults = maxResults;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

}
