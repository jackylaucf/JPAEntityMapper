package com.jackylaucf.plainolddumbjavaobject.config;

import java.sql.Types;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DataType {
    ARRAY(Types.ARRAY, "java.sql.Array", "Array"),
    BIGINT(Types.BIGINT, null, "Float"),
    BINARY(Types.BINARY, null, "byte[]"),
    BIT(Types.BIT, null, "Boolean"),
    BLOB(Types.BLOB, "java.sql.Blob", "Blob"),
    BOOLEAN(Types.BOOLEAN, null, "Boolean"),
    CHAR(Types.CHAR, null, "String"),
    CLOB(Types.CLOB, "java.sql.Clob", "Clob"),
    DATALINK(Types.DATALINK, "java.net.URL", "URL"),
    DATE(Types.DATE, "java.time.LocalDate", "LocalDate"),
    DECIMAL(Types.DECIMAL, "java.math.BigDecimal", "BigDecimal"),
    DOUBLE(Types.DOUBLE, null, "Double"),
    FLOAT(Types.FLOAT, null, "Double"),
    INTEGER(Types.INTEGER, null, "Integer"),
    LONGNVARCHAR(Types.LONGNVARCHAR, null, "String"),
    LONGVARBINARY(Types.LONGVARBINARY, "null", "byte[]"),
    LONGVARCHAR(Types.LONGVARCHAR, null, "String"),
    NCHAR(Types.NCHAR, null, "String"),
    NCLOB(Types.NCLOB, "java.sql.NClob", "NClob"),
    NUMERIC(Types.NUMERIC, "java.math.BigDecimal", "BigDecimal"),
    NVARCHAR(Types.NVARCHAR, null, "String"),
    REAL(Types.REAL, null, "Float"),
    REF(Types.REF, "java.sql.Ref", "Ref"),
    ROWID(Types.ROWID, "java.sql.RowId", "RowId"),
    SMALLINT(Types.SMALLINT, null, "Short"),
    SQLXML(Types.SQLXML, "java.sql.SQLXML", "SQLXML"),
    STRUCT(Types.STRUCT, "java.sql.Struct", "Struct"),
    TIME(Types.TIME, "java.time.LocalTime", "LocalTime"),
    TIME_WITH_TIMEZONE(Types.TIME_WITH_TIMEZONE, "java.time.OffsetTime", "OffsetTime"),
    TIMESTAMP(Types.TIMESTAMP, "java.time.LocalDateTime", "LocalDateTime"),
    TIMESTAMP_WITH_TIMEZONE(Types.TIMESTAMP_WITH_TIMEZONE, "java.time.OffsetDateTime", "OffsetDateTime"),
    TINYINT(Types.TINYINT, null, "Byte"),
    VARBINARY(Types.VARBINARY, null, "byte[]"),
    VARCHAR(Types.VARCHAR, null, "String");

    private int sqlType;
    private String dependency;
    private String typeString;
    private static Map<Integer, DataType> dataTypeMap;

    static{
        dataTypeMap = Arrays.stream(DataType.values()).collect(Collectors.toMap(DataType::getSqlType, Function.identity()));
    }

    DataType(int sqlType, String dependency, String typeString){
        this.sqlType = sqlType;
        this.dependency = dependency;
        this.typeString = typeString;
    }

    public static DataType getDataType(int sqlType){
        return dataTypeMap.get(sqlType);
    }

    private int getSqlType() {
        return this.sqlType;
    }

    public String getDependency(){
        return this.dependency;
    }
}
