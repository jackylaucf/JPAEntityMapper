package com.jackylaucf.plainolddumbjavaobject.processor.writer;

import com.jackylaucf.plainolddumbjavaobject.config.BeanConfig;

import java.io.IOException;
import java.util.List;

public class PojoWriter extends Writer{

    private boolean isStringOnly;

    public PojoWriter(boolean isStringOnly){
        this.isStringOnly = isStringOnly;
    }

    @Override
    public void write(String outputPath, String beanName, List<String> columnNames, List<String> columnTypes, BeanConfig beanConfig) throws IOException {
        buildFile(outputPath, beanName, beanConfig.getNameSuffix());

    }
}
