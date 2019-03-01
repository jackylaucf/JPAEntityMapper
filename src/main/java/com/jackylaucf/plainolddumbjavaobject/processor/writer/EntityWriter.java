package com.jackylaucf.plainolddumbjavaobject.processor.writer;

import com.jackylaucf.plainolddumbjavaobject.config.BeanConfig;

import java.io.IOException;
import java.util.List;

public class EntityWriter extends Writer{

    @Override
    public void write(String outputPath, String beanName, List<String> columnNames, List<String> columnTypes, BeanConfig beanConfig) throws IOException {
        buildFile(outputPath, beanName, beanConfig.getNameSuffix());

    }
}
