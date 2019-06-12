package com.qk365.datadict.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qk365.datadict.dao.CommonMapper;
import com.qk365.datadict.dto.PaginationSupport;
import com.qk365.datadict.dto.Paging;
import com.qk365.datadict.dto.QueryInfoDto;
import com.qk365.datadict.dto.ResultVO;
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
    @DS("#dbKey")
	public List<Map<String, Object>> findList(QueryInfoDto query,String dbKey) {
		PageHelper.startPage(query.getPageNumber(), query.getPageSize());
		return commonDao.findList(query);
	}

	@Override
    @DS("#dbKey")
	public PaginationSupport queryAjaxList2(QueryInfoDto queryDto,String dbKey) {
        PaginationSupport paginationSupport = new PaginationSupport();
        if (queryDto != null) {
            if (queryDto.getScreen()!= null) {
                queryDto.setPageNumber(0);
            }
        }

        Page<Map<String, Object>> page = PageHelper.offsetPage((queryDto.getPageNumber() - 1) * queryDto.getPageSize(), queryDto.getPageSize());
        List<Map<String, Object>> list = commonDao.findList(queryDto);
        paginationSupport.setTotalCount((int) page.getTotal());
        paginationSupport.setPageSize(queryDto.getPageSize());
        paginationSupport.setPageCount(page.getPages());
        paginationSupport.setCurrentPage(queryDto.getPageNumber());
        paginationSupport.setItems(list);
        return paginationSupport;
	}
    @Override
    @DS("#dbKey")
    public ResultVO<Paging<Map<String, Object>>> queryAjaxList(QueryInfoDto query,String dbKey) {
        ResultVO<Paging<Map<String, Object>>> resultDTO = new ResultVO<Paging<Map<String, Object>>>();

        //设置初始页面1
        if (null == query.getPageNumber()) {
            query.setPageNumber(1);
        }

        if (null == query.getPageSize()) {
            query.setPageSize(20);
        }

        PageHelper.startPage(query.getPageNumber(), query.getPageSize());

        List<Map<String, Object>> list = commonDao.findList(query);

        PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);

        Paging<Map<String, Object>> paging = new Paging<Map<String, Object>>(query.getPageNumber(), query.getPageSize(), page.getTotal(), list);

        resultDTO.setData(paging);

        return resultDTO;
    }
	 
}
