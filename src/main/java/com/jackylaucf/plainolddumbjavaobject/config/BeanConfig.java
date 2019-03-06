package com.jackylaucf.plainolddumbjavaobject.config;

public class BeanConfig {

    private BeanType type;
    private String packageName;
    private String namePrefix;
    private String nameSuffix;
    private String absolutePath;

    public BeanConfig(BeanType type, String packageName, String namePrefix, String nameSuffix, String absolutePath){
        this.type = type;
        this.packageName = packageName;
        this.namePrefix = namePrefix;
        this.nameSuffix = nameSuffix;
        this.absolutePath = absolutePath;
    }

    public BeanType getType() {
        return type;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}
