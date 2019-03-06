package com.jackylaucf.plainolddumbjavaobject.processor;

import com.jackylaucf.plainolddumbjavaobject.config.ApplicationConfig;
import com.jackylaucf.plainolddumbjavaobject.config.ApplicationConfigParser;
import com.jackylaucf.plainolddumbjavaobject.persistence.DatabaseConnectionPool;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProcessManager {

    private int timeout;
    private ApplicationConfigParser config;
    private BasicDataSource dataSource;
    
    public ProcessManager(ApplicationConfigParser parser){
        this.config = config;
        this.timeout = config.getDatabaseToBeanMap().size() * config.getBeanConfig().size() * 20;
        dataSource = DatabaseConnectionPool.getDataSource(config.getDbConnectionUrl(), config.getDbConnectionUser(), config.getDbConnectionPassword());
    }

    public void start() throws IOException, SQLException{
        if(prepareDirectory()){
            ExecutorService executor = Executors.newFixedThreadPool(20);
            for(Map.Entry<String, String> entry : config.getDatabaseToBeanMap().entrySet()){
                try {
                    executor.submit(new MapperRunnable(dataSource.getConnection(), entry.getKey(), entry.getValue(), config));
                } catch (SQLException e) {
                    shutdownAndAwaitTermination(executor);
                    throw e;
                }
            }
            shutdownAndAwaitTermination(executor);
        }else{
            throw new IOException("Error: Invalid file output path");
        }
    }

    private void shutdownAndAwaitTermination(ExecutorService threadPool){
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(timeout, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private boolean prepareDirectory(){
        for(Map.Entry<String, String> outputPath : config.getAbsoluteOutputPaths().entrySet()){
            File directory = new File(outputPath.getValue());
            if(!directory.exists()){
                if(!directory.mkdir()){
                    return false;
                }
            }
        }
        return true;
    }
}
