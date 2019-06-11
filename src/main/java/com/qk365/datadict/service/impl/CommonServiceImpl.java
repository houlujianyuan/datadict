package com.qk365.datadict.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qk365.datadict.dao.CommonMapper;
import com.qk365.datadict.dto.PaginationSupport;
import com.qk365.datadict.dto.QueryInfoDto;
import com.qk365.datadict.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CommonServiceImpl implements CommonService {
	
    @Autowired
    private CommonMapper commonDao;
	
	@Override
	public List<Map<String, Object>> findList(QueryInfoDto query) {
		PageHelper.startPage(query.getPageNumber(), query.getPageSize());
		return commonDao.findList(query);
	}

	@Override
	public PaginationSupport queryAjaxList(QueryInfoDto queryDto) {
        PaginationSupport paginationSupport = new PaginationSupport();
        if (queryDto != null) {
            if (queryDto.getScreen()!= null) {
                queryDto.setPageNumber(0);
            }
        }

        Page<Map<String, Object>> page = PageHelper.offsetPage((queryDto.getPageNumber() - 1) * queryDto.getPageSize(), queryDto.getPageSize());
        commonDao.findList(queryDto);
        paginationSupport.setTotalCount((int) page.getTotal());
        paginationSupport.setPageSize(queryDto.getPageSize());
        paginationSupport.setPageCount(page.getPages());
        paginationSupport.setCurrentPage(queryDto.getPageNumber());
        paginationSupport.setItems(page.getResult());
        return paginationSupport;
	}

	 
}
