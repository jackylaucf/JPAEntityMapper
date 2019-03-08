package com.jackylaucf.plainolddumbjavaobject.processor.writer;

import com.jackylaucf.plainolddumbjavaobject.config.ApplicationConfig;
import com.jackylaucf.plainolddumbjavaobject.config.BeanConfig;

import java.io.IOException;
import java.util.List;

public class EntityWriter extends Writer{

    private static final String JPA_DEPENDENCY = "javax.persistence.*";

    private enum JPAEntity{
        Entity,
        Table,
        Id,
        Column
    }

    @Override
    public void write(String outputPath, String tableName, String beanName, List<String> rawColumnNames, List<Integer> columnTypes, BeanConfig beanConfig) throws IOException {
        initBufferedWriter(outputPath, beanName, beanConfig.getNamePrefix(), beanConfig.getNameSuffix());
        if(bufferedWriter!=null){
            final List<String> camelizedFieldNames = camelizeFields(rawColumnNames);
            final String id = ApplicationConfig.getConfig().getDatabaseIdMap().get(tableName);
            writePackageStatement(beanConfig.getPackageName());
            writeJpaDependencyImportStatement();
            writeTypeImportStatements(columnTypes);
            writeEntityAnnotation(JPAEntity.Entity, null);
            writeEntityAnnotation(JPAEntity.Table, tableName);
            writeClassOpening(beanConfig.getNamePrefix(), beanName, beanConfig.getNameSuffix());
            for(int i=0; i<camelizedFieldNames.size(); i++){
                if(id!=null && id.equalsIgnoreCase(rawColumnNames.get(i))){
                    writeEntityAnnotation(JPAEntity.Id, null);
                }
                writeEntityAnnotation(JPAEntity.Column, rawColumnNames.get(i));
                writeFieldDeclaration(false, columnTypes.get(i), camelizedFieldNames.get(i));
                writeNewLines(1, 1);
            }
            writeGetterSetters(false, columnTypes, camelizedFieldNames);
            writeClassClosing();
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    private void writeJpaDependencyImportStatement() throws IOException{
        bufferedWriter.write("import " + JPA_DEPENDENCY);
        writeNewLines(1, 0);
    }

    private void writeEntityAnnotation(JPAEntity annotation, String value) throws IOException{
        if(annotation!=null){
            switch(annotation){
                case Entity:
                    bufferedWriter.write("@" + annotation.name());
                    writeNewLines(1, 0);
                    break;
                case Table:
                    bufferedWriter.write("@" + annotation.name());
                    bufferedWriter.write("(name=\"" + value + "\")");
                    writeNewLines(1,0);
                    break;
                case Id:
                    bufferedWriter.write("@" + annotation.name());
                    writeNewLines(1, 1);
                    break;
                case Column:
                    bufferedWriter.write("@" + annotation.name());
                    bufferedWriter.write("(name=\"" + value + "\")");
                    writeNewLines(1,1);
                    break;
            }
        }
    }
}
