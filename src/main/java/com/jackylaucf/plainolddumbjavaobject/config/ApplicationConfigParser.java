package com.jackylaucf.plainolddumbjavaobject.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static com.jackylaucf.plainolddumbjavaobject.config.ApplicationConfig.*;

public class ApplicationConfigParser {

    private final Properties properties;
    private final ApplicationConfig config;

    public ApplicationConfigParser(String path) throws IOException {
        File file = new File(path);
        if(!file.exists() || !file.isFile()){
            throw new FileNotFoundException();
        }
        this.properties = new Properties();
        this.config = new ApplicationConfig();
        this.properties.load(new FileInputStream(file));
        parse();
    }

    private ApplicationConfig parse(){
        getDatabaseConfig();

        //Database Connection Configuration
        this.dbConnectionUrl = properties.getProperty(DB_CONNECTION_URL);
        this.dbConnectionUser = properties.getProperty(DB_CONNECTION_USER);
        this.dbConnectionPassword = properties.getProperty(DB_CONNECTION_PASSWORD);
        if(dbConnectionUrl==null || dbConnectionUser==null){
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

    private void getDatabaseConfig(){
        if(dbConnectionUrl==null || dbConnectionUser==null){
            throw new NoSuchElementException("Missing Database Connection Configuration");
        }
    }

    public Map<String, String> getAbsoluteOutputPaths(){
        Map<String, String> absoluteOutputPaths = new HashMap<>();
        for(BeanConfig beanConfig : this.beanConfig){
            File file = new File(outputRoot, beanConfig.getPackageName());
            absoluteOutputPaths.put(beanConfig.getPackageName(), file.getAbsolutePath());
        }
        return absoluteOutputPaths;
    }
}
