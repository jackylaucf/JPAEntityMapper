package com.jackylaucf.plainolddumbjavaobject.processor.writer;


import com.jackylaucf.plainolddumbjavaobject.config.BeanConfig;

import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.util.*;

public abstract class Writer {

    public abstract void write(String outputPath, String beanName, List<String> columnNames, List<Integer> columnTypes, BeanConfig beanConfig) throws IOException;

    protected File buildFile(String outputPath, String beanName, String suffix) throws IOException {
        String beanFullName = beanName + suffix + ".java";
        File file = new File(outputPath, beanFullName);
        if(!file.createNewFile()){
            System.out.println(beanFullName + " has already existed. Overwriting the file...");
        }
        return file;
    }

    protected List<String> getSQLTypeStatements(List<Integer> columnTypes){
        List<String> importStatements = new ArrayList<>();
        Set<Integer> databaseColumnType = new HashSet<>(columnTypes);
        for(Iterator<Integer> it = databaseColumnType.iterator(); it.hasNext(); ){
            int type = it.next();
            if(type==Types.BIGINT || type==Types.)
        }
        return importStatements;
    }

    protected List<String> getTypedGetterSetter(){
        List<String> getterSetter = new ArrayList<>();

        return getterSetter;
    }

    protected List<String> getStringOnlyGetterSetter(){
        List<String> getterSetter = new ArrayList<>();

        return getterSetter;
    }
}
