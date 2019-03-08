# PlainOldDumbJavaObject
A simple java application for those who hates building POJOs and mapping the database schema with the JPA Entity class.

## How it works

The program currently supports three types (<b>\<TYPE\></b>) of bean production

| \<TYPE\> | Description |
| -------- | ----------- |
| entity | JPA Entity Class |
| pojo | Plain Old Java Object |
| string-only-pojo | Plain Old Java Object, all member fields are set as String type |

## Supported properties
Java `.properties` file is required and should be supplied to the program by users.

### Database Connection Configuration
| Property | Description | Mandatory |
| -------- | ----------- | --------- |
| db.connection.url | A database url of the form jdbc:subprotocol:subname | Y |
| db.connection.user | The database user on whose behalf the connection is being made | Y | 
| db.connection.password | The user's password | N |

### Output File Path Configuration
| Property | Description | Mandatory |
| -------- | ----------- | --------- |
| output.root | Absolute path of the root directory of the output file. Default path is your current working directory | N |

### Bean Properties Configuration
| Properties | Description |
| ---------- | ----------- |
| bean.package.\<TYPE\>.X | Package name of the generated java source file. <br> The name will also determine the path of the generated source file. <br> For example: <b>com.jackylaucf.packagename</b> <br> The output file will be placed under <b>output.root\com\jackylaucf\packagename</b> |

## Import the project
The project is built with Gradle. Contributors can import the project via the Gradle option in your IDE
