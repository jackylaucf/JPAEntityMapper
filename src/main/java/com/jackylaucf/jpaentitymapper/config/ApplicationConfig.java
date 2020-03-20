package com.jackylaucf.jpaentitymapper.config;

import java.util.*;

public final class ApplicationConfig {

    static final String DB_CONNECTION_URL = "db.connection.url";
    static final String DB_CONNECTION_USER = "db.connection.user";
    static final String DB_CONNECTION_PASSWORD = "db.connection.password";
    static final String DB_CONNECTION_SCHEMA = "db.connection.schema"; //support single schema only
    static final String BEAN_PREFIX = "bean.prefix.";
    static final String BEAN_SUFFIX = "bean.suffix.";
    static final String BEAN_PACKAGE = "bean.package.";
    static final String BEAN_DB_MAP = "bean.db.map.";
    static final String BEAN_DB_ID = "bean.db.id.";
    static final String OUTPUT_ROOT = "output.root";

    private static boolean isLoaded;

    private String dbConnectionUrl;
    private String dbConnectionUser;
    private String dbConnectionPassword;
    private String dbConnectionSchema;
    private List<BeanConfig> beanConfig;
    private Map<String, String> databaseToBeanMap;
    private Map<String, String> databaseIdMap;

    private static ApplicationConfig config;

    static{
        config = new ApplicationConfig();
        isLoaded = false;
    }

    private ApplicationConfig(){
        this.beanConfig = new ArrayList<>();
        this.databaseToBeanMap = new HashMap<>();
    }

    public static ApplicationConfig getConfig(){
        return config;
    }

    public String getDbConnectionUrl() {
        if(isLoaded){
            return dbConnectionUrl;
        }else{
            throw new IllegalStateException("Configuration should be loaded before read");
        }
    }

    void setDbConnectionUrl(String dbConnectionUrl) {
        this.dbConnectionUrl = dbConnectionUrl;
    }

    public String getDbConnectionUser() {
        if(isLoaded){
            return dbConnectionUser;
        }else{
            throw new IllegalStateException("Configuration should be loaded before read");
        }
    }

    void setDbConnectionUser(String dbConnectionUser) {
        this.dbConnectionUser = dbConnectionUser;
    }

    public String getDbConnectionPassword() {
        if(isLoaded){
            return dbConnectionPassword;
        }else{
            throw new IllegalStateException("Configuration should be loaded before read");
        }
    }

    void setDbConnectionPassword(String dbConnectionPassword) {
        this.dbConnectionPassword = dbConnectionPassword;
    }

    public String getDbConnectionSchema() {
        if(isLoaded){
            return dbConnectionSchema;
        }else{
            throw new IllegalStateException("Configuration should be loaded before read");
        }
    }

    void setDbConnectionSchema(String dbConnectionSchema) {
        this.dbConnectionSchema = dbConnectionSchema;
    }

    public List<BeanConfig> getBeanConfig() {
        if(isLoaded){
            return beanConfig;
        }else{
            throw new IllegalStateException("Configuration should be loaded before read");
        }
    }

    void setBeanConfig(List<BeanConfig> beanConfig) {
        this.beanConfig = beanConfig;
    }

    public Map<String, String> getDatabaseToBeanMap() {
        if(isLoaded){
            return databaseToBeanMap;
        }else{
            throw new IllegalStateException("Configuration should be loaded before read");
        }
    }

    void setDatabaseToBeanMap(Map<String, String> databaseToBeanMap) {
        this.databaseToBeanMap = databaseToBeanMap;
    }

    public Map<String, String> getDatabaseIdMap() {
        if(isLoaded){
            return databaseIdMap;
        }else{
            throw new IllegalStateException("Configuration should be loaded before read");
        }
    }

    void setDatabaseIdMap(Map<String, String> databaseIdMap) {
        this.databaseIdMap = databaseIdMap;
    }

    void setLoaded(){
        isLoaded = true;
    }
}
