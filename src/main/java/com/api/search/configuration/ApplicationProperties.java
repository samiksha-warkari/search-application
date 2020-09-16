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
@ConfigurationProperties(prefix = "baseurl")
public class ApplicationProperties {
	private String book;
	private String album;

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

}
