package com.jackylaucf.plainolddumbjavaobject.config;

import com.jackylaucf.plainolddumbjavaobject.processor.writer.EntityWriter;
import com.jackylaucf.plainolddumbjavaobject.processor.writer.PojoWriter;
import com.jackylaucf.plainolddumbjavaobject.processor.writer.Writer;

public enum BeanType {
    ENTITY("entity", new EntityWriter()),
    POJO("pojo", new PojoWriter(false)),
    STRING_ONLY_POJO("pojo", new PojoWriter(true));

    private final String value;
    private final Writer writer;

    BeanType(String value, Writer writer){
        this.value = value;
        this.writer = writer;
    }

    public String getValue(){
        return this.value;
    }

    public Writer getBeanWriter(){
        return this.writer;
    }
}
