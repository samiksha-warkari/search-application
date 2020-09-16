package com.api.search.service;

import java.util.List;

import com.api.search.valueobjects.ItemVO;

public interface SearchService {

	public List<ItemVO> search(String text);

}
