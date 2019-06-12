package com.qk365.datadict.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.qk365.datadict.dto.PaginationSupport;
import com.qk365.datadict.dto.Paging;
import com.qk365.datadict.dto.QueryInfoDto;
import com.qk365.datadict.dto.ResultVO;

import java.util.List;
import java.util.Map;


public interface CommonService {
	 
	List<Map<String,Object>> findList(QueryInfoDto query,String dbKey);

	 PaginationSupport queryAjaxList2(QueryInfoDto queryDto,String dbKey);
	ResultVO<Paging<Map<String, Object>>> queryAjaxList(QueryInfoDto query,String dbKey);
}
