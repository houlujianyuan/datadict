package com.qk365.datadict.service;

import com.qk365.datadict.dto.PaginationSupport;
import com.qk365.datadict.dto.QueryInfoDto;
import com.qk365.datadict.dto.ResultVO;

import java.util.List;
import java.util.Map;


public interface CommonService {
	 
	List<Map<String,Object>> findList(QueryInfoDto query);

	 PaginationSupport queryAjaxList(QueryInfoDto queryDto);
}
