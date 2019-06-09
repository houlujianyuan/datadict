package com.qk365.datadict.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DbRouteDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
//		LOG.info("get key:{}",RouteHolder.getRouteKey());
		return RouteHolder.getRouteKey();
	}

}
