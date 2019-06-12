package com.qk365.datadict.dao;


import com.qk365.datadict.dto.QueryInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface CommonMapper {
	
	/**
	 * 通用list 查询
	 * @param query
	 * @return
	 */
	List<Map<String,Object>> findList(@Param("query") QueryInfoDto query);
	
}