package com.api.search.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.api.search.valueobjects.ItemVO;

public interface SearchClientService {
	public CompletableFuture<List<ItemVO>> searchBooks(String text);
	public CompletableFuture<List<ItemVO>> searchAlbums(String text);
}
