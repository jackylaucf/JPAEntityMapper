package com.jackylaucf.plainolddumbjavaobject.config;

public class BeanConfig {

    private BeanType type;
    private String packageName;
    private String nameSuffix;

    public BeanConfig(BeanType type, String packageName, String nameSuffix){
        this.type = type;
        this.packageName = packageName;
        this.nameSuffix = nameSuffix;
    }

    public BeanType getType() {
        return type;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }
}
