package com.jackylaucf.plainolddumbjavaobject.config;

import java.util.*;

public final class ApplicationConfig {

    static final String DB_CONNECTION_URL = "db.connection.url";
    static final String DB_CONNECTION_USER = "db.connection.user";
    static final String DB_CONNECTION_PASSWORD = "db.connection.password";
    static final String BEAN_PREFIX = "bean.prefix";
    static final String BEAN_SUFFIX = "bean.suffix";
    static final String BEAN_PACKAGE = "bean.package";
    static final String BEAN_DB_MAP = "bean.db.map";
    static final String OUTPUT_ROOT = "output.root";

    private String outputRoot;
    private String dbConnectionUrl;
    private String dbConnectionUser;
    private String dbConnectionPassword;
    private List<BeanConfig> beanConfig;
    private Map<String, String> databaseToBeanMap;

    public ApplicationConfig(){
        this.beanConfig = new ArrayList<>();
        this.databaseToBeanMap = new HashMap<>();
    }

    public String getOutputRoot() {
        return outputRoot;
    }

    public void setOutputRoot(String outputRoot) {
        this.outputRoot = outputRoot;
    }

    public String getDbConnectionUrl() {
        return dbConnectionUrl;
    }

    public void setDbConnectionUrl(String dbConnectionUrl) {
        this.dbConnectionUrl = dbConnectionUrl;
    }

    public String getDbConnectionUser() {
        return dbConnectionUser;
    }

    public void setDbConnectionUser(String dbConnectionUser) {
        this.dbConnectionUser = dbConnectionUser;
    }

    public String getDbConnectionPassword() {
        return dbConnectionPassword;
    }

    public void setDbConnectionPassword(String dbConnectionPassword) {
        this.dbConnectionPassword = dbConnectionPassword;
    }

    public List<BeanConfig> getBeanConfig() {
        return beanConfig;
    }

    public void setBeanConfig(List<BeanConfig> beanConfig) {
        this.beanConfig = beanConfig;
    }

    public Map<String, String> getDatabaseToBeanMap() {
        return databaseToBeanMap;
    }

    public void setDatabaseToBeanMap(Map<String, String> databaseToBeanMap) {
        this.databaseToBeanMap = databaseToBeanMap;
    }
}
