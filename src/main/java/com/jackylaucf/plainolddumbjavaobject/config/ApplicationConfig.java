package com.jackylaucf.plainolddumbjavaobject.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ApplicationConfig {

    private static final String DB_CONNECTION_URL = "db.connection.url";
    private static final String DB_CONNECTION_USER = "db.connection.user";
    private static final String DB_CONNECTION_PASSWORD = "db.connection.password";
    private static final String BEAN_ENTITY = "bean.entity";
    private static final String BEAN_POJO = "bean.pojo";
    private static final String BEAN_STRING_ONLY_POJO = "bean.string-only-pojo";
    private static final String BEAN_DB_MAP = "bean.db.map";
    private static final String OUTPUT_ROOT = "output.root";

    private final Properties properties;
    private String outputRoot;
    private String dbConnectionUrl;
    private String dbConnectionUser;
    private String dbConnectionPassword;
    private List<BeanConfig> beanConfig;
    private Map<String, String> databaseToBeanMap;

    public ApplicationConfig(String path) throws IOException {
        File file = new File(path);
        if(!file.exists() || !file.isFile()){
            throw new FileNotFoundException();
        }
        beanConfig = new ArrayList<>();
        databaseToBeanMap = new HashMap<>();
        properties = new Properties();
        properties.load(new FileInputStream(file));
        load();
    }

    private void load(){
        //Database Connection Configuration
        this.dbConnectionUrl = properties.getProperty(DB_CONNECTION_URL);
        this.dbConnectionUser = properties.getProperty(DB_CONNECTION_USER);
        this.dbConnectionPassword = properties.getProperty(DB_CONNECTION_PASSWORD);
        if(dbConnectionUrl==null || dbConnectionUser==null || dbConnectionPassword==null){
            throw new NoSuchElementException("Missing Database Connection Configuration");
        }

        //Bean Configuration
        Enumeration<?> enumeration = properties.propertyNames();
        while(enumeration.hasMoreElements()){
            String propertyName = (String) enumeration.nextElement();

            //Database-to-Bean Mapping
            if(propertyName.contains(BEAN_DB_MAP)){
                databaseToBeanMap.put(propertyName.replace(BEAN_DB_MAP + ".", ""), properties.getProperty(propertyName));
                continue;
            }

            //Package Name & Suffix configuration
            String packageKeyWord = ".package.";
            String suffixKeyWord = ".suffix.";
            if(propertyName.contains(packageKeyWord)){
                String suffixConfig = propertyName.replace(packageKeyWord, suffixKeyWord);
                if(propertyName.contains(BEAN_ENTITY)){
                    String suffix = properties.getProperty(suffixConfig, "");
                    beanConfig.add(new BeanConfig(BeanType.ENTITY, properties.getProperty(propertyName), suffix));
                }else if(propertyName.contains(BEAN_POJO)){
                    String suffix = properties.getProperty(suffixConfig, "Pojo");
                    beanConfig.add(new BeanConfig(BeanType.POJO, properties.getProperty(propertyName), suffix));
                }else if(propertyName.contains(BEAN_STRING_ONLY_POJO)){
                    String suffix = properties.getProperty(suffixConfig, "Pojo");
                    beanConfig.add(new BeanConfig(BeanType.STRING_ONLY_POJO, properties.getProperty(propertyName), suffix));
                }
            }
        }

        //Output Configuration
        this.outputRoot = properties.getProperty(OUTPUT_ROOT, System.getProperty("user.dir"));
    }

    public Map<String, String> getAbsoluteOutputPaths(){
        Map<String, String> absoluteOutputPaths = new HashMap<>();
        for(BeanConfig beanConfig : this.beanConfig){
            File file = new File(outputRoot, beanConfig.getPackageName());
            absoluteOutputPaths.put(beanConfig.getPackageName(), file.getAbsolutePath());
        }
        return absoluteOutputPaths;
    }

    public String getDbConnectionUrl() {
        return dbConnectionUrl;
    }

    public String getDbConnectionUser() {
        return dbConnectionUser;
    }

    public String getDbConnectionPassword() {
        return dbConnectionPassword;
    }

    public List<BeanConfig> getBeanConfig() {
        return beanConfig;
    }

    public Map<String, String> getDatabaseToBeanMap() {
        return databaseToBeanMap;
    }

    public String getOutputRoot() {
        return outputRoot;
    }
}
