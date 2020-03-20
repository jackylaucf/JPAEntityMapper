package com.jackylaucf.jpaentitymapper.persistence;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConnectionPool{
    private static BasicDataSource dataSource;

    private DatabaseConnectionPool(){}

    public static BasicDataSource getDataSource(String url, String username, String password){
        if(dataSource==null){
            dataSource = new BasicDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
        }
        return dataSource;
    }
}
