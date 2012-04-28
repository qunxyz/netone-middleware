package com.jl.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DynamicDataSource extends AbstractRoutingDataSource {   
	  
	static Logger log = Logger.getLogger(DynamicDataSource.class);
    protected Object determineCurrentLookupKey() {   
        return DbContextHolder.getDbType();   
    }   
  
}  
