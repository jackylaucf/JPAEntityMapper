package com.jackylaucf.plainolddumbjavaobject.config;

import com.jackylaucf.plainolddumbjavaobject.processor.writer.EntityWriter;
import com.jackylaucf.plainolddumbjavaobject.processor.writer.PojoWriter;
import com.jackylaucf.plainolddumbjavaobject.processor.writer.Writer;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum BeanType {
    ENTITY("entity", new EntityWriter()),
    POJO("pojo", new PojoWriter(false)),
    STRING_ONLY_POJO("string-only-pojo", new PojoWriter(true));

    private final String name;
    private final Writer writer;
    public static Map<String, BeanType> beanTypeMap;

    static{
        beanTypeMap = Arrays.stream(BeanType.values()).collect(Collectors.toMap(BeanType::getName, Function.identity()));
    }

    BeanType(String name, Writer writer){
        this.name = name;
        this.writer = writer;
    }

    private String getName(){
        return this.name;
    }

    public Writer getBeanWriter(){
        return this.writer;
    }

    public static BeanType getTypeByName(String name){
        return beanTypeMap.get(name);
    }
}
