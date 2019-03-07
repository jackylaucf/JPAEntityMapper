package com.jackylaucf.plainolddumbjavaobject.config;

import com.jackylaucf.plainolddumbjavaobject.processor.writer.EntityWriter;
import com.jackylaucf.plainolddumbjavaobject.processor.writer.PojoWriter;
import com.jackylaucf.plainolddumbjavaobject.processor.writer.Writer;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum BeanType {
    ENTITY("entity", EntityWriter::new),
    POJO("pojo", () -> new PojoWriter(false)),
    STRING_ONLY_POJO("string-only-pojo", () -> new PojoWriter(true));

    private final String name;
    private final Supplier<Writer> writerSupplier;
    public static Map<String, BeanType> beanTypeMap;

    static{
        beanTypeMap = Arrays.stream(BeanType.values()).collect(Collectors.toMap(BeanType::getName, Function.identity()));
    }

    BeanType(String name, Supplier<Writer> writerSupplier){
        this.name = name;
        this.writerSupplier = writerSupplier;
    }

    private String getName(){
        return this.name;
    }

    public Writer getBeanWriter(){
        return this.writerSupplier.get();
    }

    public static BeanType getTypeByName(String name){
        return beanTypeMap.get(name);
    }
}
