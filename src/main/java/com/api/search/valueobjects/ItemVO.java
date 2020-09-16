package com.api.search.valueobjects;

import java.util.List;

/**
 * @author : Samiksha Warkari
 * @Date : 13-Sep-2020
 */
public class ItemVO {
	private String kind;
	private String title;
	private List<String> authors;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		return "ItemVO [kind=" + kind + ", title=" + title + ", authors=" + authors + "]";
	}

}
