package net.example.finance.mybank.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class PaginatedDataDto <T> {
	private List<T> data;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean last;
}
