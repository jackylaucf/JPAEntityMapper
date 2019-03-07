package com.jackylaucf.plainolddumbjavaobject.processor.writer;


import com.jackylaucf.plainolddumbjavaobject.config.BeanConfig;
import com.jackylaucf.plainolddumbjavaobject.config.DataType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Writer {

    BufferedWriter bufferedWriter;

    public abstract void write(String outputPath, String tableName, String beanName, List<String> rawColumnNames, List<Integer> columnTypes, BeanConfig beanConfig) throws IOException;

    void initBufferedWriter(String outputPath, String beanName, String prefix, String suffix) throws IOException {
        String beanFullName = prefix + beanName + suffix + ".java";
        File file = new File(outputPath, beanFullName);
        if(!file.createNewFile()){
            System.out.println(beanFullName + " has already existed. Overwriting the file...");
        }else{
            System.out.println("Writing " + beanFullName + "...");
        }
        bufferedWriter = new BufferedWriter(new FileWriter(file));
    }

    void writePackageStatement(String packageName) throws IOException{
        bufferedWriter.write("package ");
        bufferedWriter.write(packageName);
        bufferedWriter.write(";");
        writeNewLines(2, 0);
    }

    void writeTypeImportStatements(List<Integer> columnTypes) throws IOException{
        Set<Integer> databaseColumnType = new HashSet<>(columnTypes);
        for (Integer integer : databaseColumnType) {
            String dependency = DataType.getDataType(integer).getDependency();
            if (dependency != null) {
                bufferedWriter.write("import ");
                bufferedWriter.write(dependency);
                bufferedWriter.write(";");
                writeNewLines(1, 0);
            }
        }
        writeNewLines(1, 0);
    }

    void writeClassOpening(String prefix, String beanName, String suffix) throws IOException{
        bufferedWriter.write("public class ");
        bufferedWriter.write(prefix + beanName + suffix);
        bufferedWriter.write("{");
        writeNewLines(2, 1);
    }

    void writeFieldDeclaration(boolean stringOnly, int columnType, String fieldName) throws IOException{
        if(stringOnly){
            bufferedWriter.write("private String ");
            bufferedWriter.write(fieldName);
        }else{
            bufferedWriter.write("private ");
            bufferedWriter.write(DataType.getDataType(columnType).getTypeString());
            bufferedWriter.write(" ");
            bufferedWriter.write(fieldName);
            bufferedWriter.write(";");
        }
        writeNewLines(1, 1);
    }

    void writeGetterSetters(boolean stringOnly, List<Integer> columnTypes, List<String> fieldNames) throws IOException{
        for(int i=0; i<fieldNames.size(); i++){
            writeNewLines(1, 1);
            //prepare required parameters
            String fieldName = fieldNames.get(i);
            String capitalizedFieldName;
            if(fieldName.length()>1 && Character.isUpperCase(fieldName.charAt(1))){
                capitalizedFieldName = fieldName;
            }else{
                capitalizedFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            }
            String columnTypeString = DataType.getDataType(columnTypes.get(i)).getTypeString();

            //getter
            if(stringOnly){
                bufferedWriter.write("public String get" + capitalizedFieldName + "()");
            }else{
                bufferedWriter.write("public " + columnTypeString + " get" + capitalizedFieldName + "()");
            }
            bufferedWriter.write("{");
            writeNewLines(1, 2);
            bufferedWriter.write("return this." + fieldName + ";");
            writeNewLines(1, 1);
            bufferedWriter.write("}");
            writeNewLines(2, 1);

            //setter
            bufferedWriter.write("public void set" + capitalizedFieldName);
            if(stringOnly){
                bufferedWriter.write("(String " + fieldNames.get(i) + ")");
            }else{
                bufferedWriter.write("(" + DataType.getDataType(columnTypes.get(i)).getTypeString() + " " + fieldNames.get(i) + ")");
            }
            bufferedWriter.write("{");
            writeNewLines(1, 2);
            bufferedWriter.write("this." + fieldNames.get(i) + " = " + fieldNames.get(i) + ";");
            writeNewLines(1, 1);
            bufferedWriter.write("}");
            writeNewLines(1, 0);
        }
    }

    void writeClassClosing() throws IOException{
        bufferedWriter.write("}");
    }

    void writeNewLines(int nLines, int nIndentChar) throws IOException{
        for(int i=0; i<nLines; i++){
            bufferedWriter.newLine();
        }
        for(int i=0; i<nIndentChar*4; i++){
            bufferedWriter.append(" ");
        }
    }

    List<String> camelizeFields(List<String> rawColumnNames){
        List<String> camelizedFieldNames = new ArrayList<>();
        if(rawColumnNames!=null){
            StringBuilder builder = new StringBuilder();
            for(String rawName : rawColumnNames){
                builder.setLength(0);
                Pattern symbolRegex = Pattern.compile("[#$_]+");
                Matcher symbolMatcher = symbolRegex.matcher(rawName);
                if(symbolMatcher.find()){
                    String[] nameArr = rawName.toLowerCase().split("[#$_]+");
                    for(int i=0; i<nameArr.length; i++){
                        if(i==0){
                            builder.append(nameArr[0]);
                        }else{
                            builder.append(Character.toUpperCase(nameArr[i].charAt(0)));
                            builder.append(nameArr[i].substring(1));
                        }
                    }
                }else{
                    char[] charArr = rawName.toCharArray();
                    for(int i=0; i<charArr.length; i++){
                        if(i==0 || i==charArr.length-1) {
                            charArr[i] = Character.toLowerCase(charArr[i]);
                        }else{
                            if(Character.isLowerCase(charArr[i-1]) && Character.isUpperCase(charArr[i+1])){
                                charArr[i] = Character.toLowerCase(charArr[i]);
                            }
                        }
                    }
                    builder.append(charArr);
                }
                camelizedFieldNames.add(builder.toString());
            }
        }
        return camelizedFieldNames;
    }
}
