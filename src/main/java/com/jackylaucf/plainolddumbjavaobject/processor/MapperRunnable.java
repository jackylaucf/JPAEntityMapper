package com.jackylaucf.plainolddumbjavaobject.processor;

import com.jackylaucf.plainolddumbjavaobject.config.ApplicationConfig;
import com.jackylaucf.plainolddumbjavaobject.config.BeanConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MapperRunnable implements Runnable{

    private Connection connection;
    private String tableName;
    private String beanName;
    private ApplicationConfig config;

    public MapperRunnable(Connection connection, String tableName, String beanName, ApplicationConfig config){
        this.connection = connection;
        this.tableName = tableName;
        this.beanName = beanName;
        this.config = config;
    }

    @Override
    public void run() {
        try {
            List<String> columnNames = new ArrayList<>();
            List<Integer> columnTypes = new ArrayList<>();
            ResultSet meta = connection.getMetaData().getColumns(null, null, tableName, "%");
            while(meta.next()){
                columnNames.add(meta.getString("COLUMN_NAME"));
                columnTypes.add(meta.getInt("DATA_TYPE"));
            }
            for(BeanConfig beanConfig : config.getBeanConfig()){
                String outputPath = config.getDatabaseToBeanMap().get(beanConfig.getPackageName());
                beanConfig.getType().getBeanWriter().write(outputPath, beanName, columnNames, columnTypes, beanConfig);
            }
        } catch (SQLException | IOException e) {
            System.err.print("Error encountered: ");
            System.err.println(e.toString());
        }
    }

}
