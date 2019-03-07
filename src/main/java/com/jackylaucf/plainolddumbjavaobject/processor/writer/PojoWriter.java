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
    public void write(String outputPath, String tableName, String beanName, List<String> rawColumnNames, List<Integer> columnTypes, BeanConfig beanConfig) throws IOException {
        initBufferedWriter(outputPath, beanName, beanConfig.getNamePrefix(), beanConfig.getNameSuffix());
        if(bufferedWriter!=null){
            List<String> camelizedFieldNames = camelizeFields(rawColumnNames);
            writePackageStatement(beanConfig.getPackageName());
            if(!isStringOnly){
                writeTypeImportStatements(columnTypes);
            }
            writeClassOpening(beanConfig.getNamePrefix(), beanName, beanConfig.getNameSuffix());
            for(int i=0; i<camelizedFieldNames.size(); i++){
                writeFieldDeclaration(isStringOnly, columnTypes.get(i), camelizedFieldNames.get(i));
            }
            writeGetterSetters(isStringOnly, columnTypes, camelizedFieldNames);
            writeClassClosing();
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }
}
