package com.qk365.datadict.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSource extends AbstractRoutingDataSource {

	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }
    
	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceKey.get();
	}

	
	 public static void clearDbType(){
		 dataSourceKey.remove();
	    }
}
