package com.jackylaucf.plainolddumbjavaobject.processor.writer;

import com.jackylaucf.plainolddumbjavaobject.config.BeanConfig;

import java.io.IOException;
import java.util.List;

public class EntityWriter extends Writer{

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
