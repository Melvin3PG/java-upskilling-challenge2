package net.example.finance.mybank.model.dto;

import java.util.List;

import lombok.Data;

/**
 * Data transfer object to return data paginated with all details.
 * 
 * @author jesus.quintero
 *
 * @param <T>
 */
@Data
public class PaginatedDataDto <T> {
	
	/**
	 * List of records paginated 
	 */
	private List<T> data;
	
	/**
	 * Page number
	 */
	private int pageNo;
	
	/**
	 * Page size of records
	 */
	private int pageSize;
	
	/**
	 * Total of all records including the records that are not in data list.
	 */
	private long totalElements;
	
	/**
	 * Total of pages available calculated on page size and total of records.
	 */
	private int totalPages;
	
	/**
	 * Indicates is last page
	 */
	private boolean lastPage;
}
