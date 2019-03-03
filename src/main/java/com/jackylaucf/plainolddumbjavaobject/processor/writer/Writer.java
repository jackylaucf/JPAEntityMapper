package com.jackylaucf.plainolddumbjavaobject.processor.writer;


import com.jackylaucf.plainolddumbjavaobject.config.BeanConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Types;
import java.util.*;

public abstract class Writer {

    BufferedWriter bufferedWriter;

    public abstract void write(String outputPath, String beanName, List<String> columnNames, List<Integer> columnTypes, BeanConfig beanConfig) throws IOException;

    void initBufferedWriter(String outputPath, String beanName, String suffix) throws IOException {
        String beanFullName = beanName + suffix + ".java";
        File file = new File(outputPath, beanFullName);
        if(!file.createNewFile()){
            System.out.println(beanFullName + " has already existed. Overwriting the file...");
        }
        bufferedWriter = new BufferedWriter(new FileWriter(file));
    }

    void writePackageStatement(String packageName) throws IOException{
        if(bufferedWriter!=null){
            bufferedWriter.write("package ");
            bufferedWriter.write(packageName);
            writeNewLines(2);
            bufferedWriter.flush();
        }
    }

    protected void writeTypeImportStatements(List<Integer> columnTypes){
        Set<Integer> databaseColumnType = new HashSet<>(columnTypes);
        for(Iterator<Integer> it = databaseColumnType.iterator(); it.hasNext(); ){
            int type = it.next();

        }
    }

    protected List<String> getTypedGetterSetter(){
        List<String> getterSetter = new ArrayList<>();

        return getterSetter;
    }

    protected List<String> getStringOnlyGetterSetter(){
        List<String> getterSetter = new ArrayList<>();

        return getterSetter;
    }

    protected void writeNewLines(int nLines) throws IOException{
        if(bufferedWriter!=null){
            for(int i=0; i<nLines; i++){
                bufferedWriter.newLine();
            }
        }
    }
}
