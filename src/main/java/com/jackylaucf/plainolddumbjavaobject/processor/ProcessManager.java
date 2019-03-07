package com.jackylaucf.plainolddumbjavaobject.processor;

import com.jackylaucf.plainolddumbjavaobject.config.ApplicationConfig;
import com.jackylaucf.plainolddumbjavaobject.config.ApplicationConfigParser;
import com.jackylaucf.plainolddumbjavaobject.config.BeanConfig;
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
    private ApplicationConfig config;
    private BasicDataSource dataSource;
    
    public ProcessManager(String configPath) throws IOException{
        ApplicationConfigParser.parse(configPath);
        this.config = ApplicationConfig.getConfig();
        this.timeout = config.getDatabaseToBeanMap().size() * config.getBeanConfig().size() * 20;
        this.dataSource = DatabaseConnectionPool.getDataSource(config.getDbConnectionUrl(), config.getDbConnectionUser(), config.getDbConnectionPassword());
    }

    public void start() throws IOException, SQLException{
        if(prepareDirectory()){
            ExecutorService executor = Executors.newFixedThreadPool(20);
            for(Map.Entry<String, String> entry : config.getDatabaseToBeanMap().entrySet()){
                try {
                    executor.submit(new MapperRunnable(dataSource.getConnection(), entry.getKey(), entry.getValue()));
                } catch (SQLException e) {
                    shutdownAndAwaitTermination(executor);
                    throw e;                }
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
        for(BeanConfig beanConfig : config.getBeanConfig()){
            File directory = new File(beanConfig.getAbsolutePath());
            if(!directory.exists()){
                if(!directory.mkdirs()){
                    return false;
                }
            }
        }
        return true;
    }
}
