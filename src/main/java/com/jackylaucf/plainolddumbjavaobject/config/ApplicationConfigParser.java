package com.jackylaucf.plainolddumbjavaobject.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jackylaucf.plainolddumbjavaobject.config.ApplicationConfig.*;

public class ApplicationConfigParser {

    private final Properties properties;
    private final ApplicationConfig config;

    public ApplicationConfigParser(String path) throws IOException {
        final File file = new File(path);
        if(!file.exists() || !file.isFile()){
            throw new FileNotFoundException();
        }
        this.properties = new Properties();
        this.config = new ApplicationConfig();
        this.properties.load(new FileInputStream(file));
    }

    public ApplicationConfig parse(){
        setOutputRootConfig();
        setDatabaseConfig();
        setBeanConfig(getPackagePropertyKeys());
        setDatabaseBeanMapConfig();
        return this.config;
    }

    private void setOutputRootConfig(){
        config.setOutputRoot(properties.getProperty(OUTPUT_ROOT, System.getProperty("user.dir")));
    }

    private void setDatabaseConfig(){
        if(properties.getProperty(DB_CONNECTION_URL)==null || properties.getProperty(DB_CONNECTION_USER)==null){
            throw new NoSuchElementException("Missing Database Connection Configuration");
        }else{
            config.setDbConnectionUrl(properties.getProperty(DB_CONNECTION_URL));
            config.setDbConnectionUser(properties.getProperty(DB_CONNECTION_USER));
            config.setDbConnectionPassword(properties.getProperty(DB_CONNECTION_PASSWORD));
        }
    }

    private void setBeanConfig(Set<String> packagePropertyKeys){
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

    private void setDatabaseBeanMapConfig(){
        final Map<String, String> databaseBeanMap = new HashMap<>();
        final Set<String> propertiesNameSet = properties.stringPropertyNames();
        propertiesNameSet.removeIf(name -> !name.startsWith(BEAN_DB_MAP));
        for(String beanDbMapPropertyKey : propertiesNameSet){
            databaseBeanMap.put(beanDbMapPropertyKey.replace(BEAN_DB_MAP, ""), properties.getProperty(beanDbMapPropertyKey));
        }
        config.setDatabaseToBeanMap(databaseBeanMap);
    }

    private Set<String> getPackagePropertyKeys(){
        final Set<String> propertiesNameSet = properties.stringPropertyNames();
        propertiesNameSet.removeIf(name -> !name.startsWith(BEAN_PACKAGE));
        if(propertiesNameSet.isEmpty()){
            throw new NoSuchElementException("Missing Package Configuration");
        }
        return propertiesNameSet;
    }

    private String getBeanTypeNameByPackageKey(String packagePropertyKey){
        final Pattern packagePattern = Pattern.compile(BEAN_PACKAGE + "(.+)\\.\\d+");
        final Matcher packagePatternMatcher = packagePattern.matcher(packagePropertyKey);
        if(packagePatternMatcher.matches()){
            return packagePatternMatcher.group(1);
        }else{
            throw new NoSuchElementException("Invalid Bean Type in Package Configuration: " + packagePropertyKey);
        }
    }

    private String getPrefixByPackageKey(String packagePropertyKey){
        final String prefixPropertyKey = packagePropertyKey.replaceFirst(BEAN_PACKAGE, BEAN_PREFIX);
        return properties.getProperty(prefixPropertyKey, "");
    }

    private String getSuffixByPackageKey(String packagePropertyKey){
        final String suffixPropertyKey = packagePropertyKey.replaceFirst(BEAN_PACKAGE, BEAN_SUFFIX);
        return properties.getProperty(suffixPropertyKey, "");
    }

    private String getAbsolutePathByPackageKey(String packagePropertyKey){
        final String rootPath = properties.getProperty(OUTPUT_ROOT, System.getProperty("user.dir"));
        final String childPath = properties.getProperty(packagePropertyKey).replace(".", "/");
        final File file = new File(rootPath, childPath);
        return file.getAbsolutePath();
    }
}
