package com.jackylaucf.plainolddumbjavaobject.processor.writer;

import com.jackylaucf.plainolddumbjavaobject.config.BeanConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PojoWriter extends Writer{

    private boolean isStringOnly;

    public PojoWriter(boolean isStringOnly){
        this.isStringOnly = isStringOnly;
    }

    @Override
    public void write(String outputPath, String beanName, List<String> columnNames, List<Integer> columnTypes, BeanConfig beanConfig) throws IOException {
        initBufferedWriter(outputPath, beanName, beanConfig.getNameSuffix());
        if(bufferedWriter!=null){
            writePackageStatement(beanConfig.getPackageName());
            writeTypeImportStatements(columnTypes);
            //to-do
            bufferedWriter.close();
        }



    }
}
