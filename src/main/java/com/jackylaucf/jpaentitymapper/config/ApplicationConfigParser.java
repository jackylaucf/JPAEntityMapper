package com.jackylaucf.jpaentitymapper.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jackylaucf.jpaentitymapper.config.ApplicationConfig.*;

public class ApplicationConfigParser {

    private static Properties properties;
    private static ApplicationConfig config;

    static{
        properties = new Properties();
        config = ApplicationConfig.getConfig();
    }

    public static void parse(String path) throws IOException{
        final File file = new File(path);
        if(!file.exists() || !file.isFile()){
            throw new FileNotFoundException();
        }
        properties.load(new FileInputStream(file));
        setDatabaseConfig();
        setBeanConfig(getPackagePropertyKeys());
        setDatabaseBeanMapConfig();
        setDatabaseIdMapConfig();
        config.setLoaded();
    }

    /*Database connection configuration*/
    private static void setDatabaseConfig(){
        if(properties.getProperty(DB_CONNECTION_URL)==null || properties.getProperty(DB_CONNECTION_USER)==null){
            throw new NoSuchElementException("Missing Database Connection Configuration");
        }else{
            config.setDbConnectionUrl(properties.getProperty(DB_CONNECTION_URL));
            config.setDbConnectionUser(properties.getProperty(DB_CONNECTION_USER));
            config.setDbConnectionPassword(properties.getProperty(DB_CONNECTION_PASSWORD));
            config.setDbConnectionSchema(properties.getProperty(DB_CONNECTION_SCHEMA));
        }
    }

    /*Java bean properties configuration*/
    private static void setBeanConfig(Set<String> packagePropertyKeys){
        final List<BeanConfig> beanConfigs = new ArrayList<>();
        for(String key : packagePropertyKeys){
            final String beanTypeName = getBeanTypeNameByPackageKey(key);
            final String prefix = getPrefixByPackageKey(key);
            final String suffix = getSuffixByPackageKey(key);
            final String packageName = properties.getProperty(key);
            final String absolutePath = getAbsolutePathByPackageKey(key);
            beanConfigs.add(new BeanConfig(BeanType.getTypeByName(beanTypeName), packageName, prefix, suffix, absolutePath));
        }
        config.setBeanConfig(beanConfigs);
    }

    /*Database table properties configuration
     *Possible to refactor if the number of supported config properties increase in the future*/
    private static void setDatabaseBeanMapConfig(){
        final Map<String, String> databaseBeanMap = new HashMap<>();
        final Set<String> propertiesNameSet = properties.stringPropertyNames();
        propertiesNameSet.removeIf(name -> !name.startsWith(BEAN_DB_MAP));
        for(String beanDbMapPropertyKey : propertiesNameSet){
            databaseBeanMap.put(beanDbMapPropertyKey.replace(BEAN_DB_MAP, ""), properties.getProperty(beanDbMapPropertyKey));
        }
        config.setDatabaseToBeanMap(databaseBeanMap);
    }

    private static void setDatabaseIdMapConfig(){
        final Map<String, String> databaseIdMap = new HashMap<>();
        final Set<String> propertiesNameSet = properties.stringPropertyNames();
        propertiesNameSet.removeIf(name -> !name.startsWith(BEAN_DB_ID));
        for(String beanDbIdPropertyKey : propertiesNameSet){
            databaseIdMap.put(beanDbIdPropertyKey.replace(BEAN_DB_ID, ""), properties.getProperty(beanDbIdPropertyKey));
        }
        config.setDatabaseIdMap(databaseIdMap);
    }

    /*Helper function*/
    private static Set<String> getPackagePropertyKeys(){
        final Set<String> propertiesNameSet = properties.stringPropertyNames();
        propertiesNameSet.removeIf(name -> !name.startsWith(BEAN_PACKAGE));
        if(propertiesNameSet.isEmpty()){
            throw new NoSuchElementException("Missing Package Configuration");
        }
        return propertiesNameSet;
    }

    private static String getBeanTypeNameByPackageKey(String packagePropertyKey){
        final Pattern packagePattern = Pattern.compile(BEAN_PACKAGE + "(.+)\\.\\d+");
        final Matcher packagePatternMatcher = packagePattern.matcher(packagePropertyKey);
        if(packagePatternMatcher.matches()){
            return packagePatternMatcher.group(1);
        }else{
            throw new NoSuchElementException("Invalid Bean Type in Package Configuration: " + packagePropertyKey);
        }
    }

    private static String getPrefixByPackageKey(String packagePropertyKey){
        final String prefixPropertyKey = packagePropertyKey.replaceFirst(BEAN_PACKAGE, BEAN_PREFIX);
        return properties.getProperty(prefixPropertyKey, "");
    }

    private static String getSuffixByPackageKey(String packagePropertyKey){
        final String suffixPropertyKey = packagePropertyKey.replaceFirst(BEAN_PACKAGE, BEAN_SUFFIX);
        return properties.getProperty(suffixPropertyKey, "");
    }

    private static String getAbsolutePathByPackageKey(String packagePropertyKey){
        final String rootPath = properties.getProperty(OUTPUT_ROOT, System.getProperty("user.dir"));
        final String childPath = properties.getProperty(packagePropertyKey).replace(".", "/");
        final File file = new File(rootPath, childPath);
        return file.getAbsolutePath();
    }
}
