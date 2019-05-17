# PlainOldDumbJavaObject
A simple java application for automating Pojo and JPA Entity code generation.

## How it works

The Java program aims to speed up the pojo and entity creation process with the help of the JDBC API. By providing appropriate properties file, the program will generate the designated java source code accordingly.
<br>
The program currently supports three types (<b>\<TYPE\></b>) of bean production

| \<TYPE\> | Description |
| -------- | ----------- |
| entity | JPA Entity Class |
| pojo | Plain Old Java Object |
| string-only-pojo | Plain Old Java Object, all member fields are set as String type |

To run the program, 
<br>

 + Build a jar with your JDBC database driver.
 + Prepare a `config.properties` file 
 + Run `java -jar <YOUR_JAR_FILE> <YOUR_CONFIG_FILE>` in your terminal.

## Supported properties
Java `.properties` file is required and should be supplied to the program by users.

### Database Connection Configuration
| Property | Description | Mandatory |
| -------- | ----------- | --------- |
| db.connection.url | A database url of the form jdbc:subprotocol:subname | Y |
| db.connection.user | The database user on whose behalf the connection is being made | Y | 
| db.connection.password | The user's password | N |

<b>Remarks:</b> To prevent `No suitable driver found` error, please pack the database driver jar with the compiled source code if you are going to create a portable jar.

### Output File Path Configuration
| Property | Description | Mandatory |
| -------- | ----------- | --------- |
| output.root | Absolute path of the root directory of the output file. Default path is your current working directory | N |


### Bean Properties Configuration
| Properties | Description |
| ---------- | ----------- |
| bean.package.\<TYPE\>.X | Package name of the generated java source file. <br> The name will also determine the path of the generated source file. <br> For example: <b>com.jackylaucf.packagename</b> <br> The output file will be placed under <b>output.root\com\jackylaucf\packagename</b> |
| bean.prefix.\<TYPE\>.X | Prefix of the bean name |
| bean.suffix.\<TYPE\>.X | Suffix of the bean name |

<b>Remarks: </b> 
<br> (i) \<TYPE\> refers to the supported types (i.e. either `entity` or `pojo` or `string-only-pojo`)
<br> (ii) X refers to the identifier for linking the properties of the same bean together.
<br>&nbsp;&nbsp;&nbsp;  e.g. bean.package.entity.1, bean.prefix.entity.1 and bean.suffix.entity.1 refer to the configuration of the same entity bean. 

### Database-Bean Mapping Configuration
| Properties | Description |
| ---------- | ----------- |
| bean.db.map.\<TABLE_NAME\> | One-to-One mapping of the database table name to Java Bean name. <br> The name of the bean product will be \<bean.prefix\>\<BEAN_NAME\>\<bean.suffix\>.java
| bean.db.id.\<TABLE_NAME\> | One-to-One mapping of the database table name to its corresponding Id field, which will be annotated with @Id in entity class according to JPA specification. Note that the value of the key-value pair should be the COLUMN_NAME in database, not the field name in entity class. |

## JPA Annotation Support
| Annotation | Attribute(s) |
| ---------- | ------------ |
| @Entity | nil |
| @Table | name |
| @Id | nil |
| @Column | name |

## Default Datababase Type to Java Type Mapping
The design is based on <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/Types.html">java.sql.Types</href></a>
<br>

| java.sql.Types | Java data type |
| -------------- | -------------- |
| ARRAY | java.sql.Array |
| BIGINT | java.lang.Long |
| BINARY | byte[] |
| BIT | java.lang.Boolean |
| BLOB | java.sql.Blob |
| BOOLEAN | java.lang.Boolean |
| CHAR | java.lang.String |
| CLOB | java.sql.Clob |
| DATALINK | java.net.URL |
| DATE | java.time.LocalDate |
| DECIMAL | java.math.BigDecimal |
| DOUBLE | java.lang.Double |
| FLOAT | java.lang.Double |
| INTEGER | java.lang.Integer |
| LONGNVARCHAR | java.lang.String |
| LONGVARBINARY | byte[] |
| LONGVARCHAR | java.lang.String |
| NCHAR | java.lang.String |
| NCLOB | java.sql.NClob |
| NUMERIC | java.math.BigDecimal |
| NVARCHAR | java.lang.String |
| REAL | java.lang.Float |
| REF | java.sql.Ref |
| ROWID | java.sql.RowId |
| SMALLINT | java.lang.Short |
| SQLXML | java.sql.SQLXML |
| STRUCT | java.sql.Struct |
| TIME | java.time.LocalTime |
| TIME_WITH_TIMEZONE | java.time.OffsetTime |
| TIMESTAMP | java.time.LocalDateTime |
| TIMESTAMP_WITH_TIMEZONE | java.time.OffsetDateTime |
| TINYINT | java.lang.Byte |
| VARBINARY | byte[] |
| VARCHAR | java.lang.String |

## Import the project
The project is built with Gradle. Contributors can import the project via the Gradle option in your IDE


