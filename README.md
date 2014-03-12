FileUploadAndDownload
=====================

java/jsp/servlet/fileupload/download

This is an sample j2ee code for file upload and download.
In order to use it, you can follow below steps:
1, download the entire file into local.
2, use sql server database for file store. need to find a sql server engine, and restore the database.
   -- in order to restore database, first thing, you can put the content(db_file_Data.MDF,db_file_Log.LDF) 
      in database folder into sql server data folder.
   -- Then in sql server manage studio, right click the database, and select "attach", 
      then you can select the db_file_Data.MDF in its data folder.

    Change database connection information in file com.duanchao.file.toolsbean.DB
    
3, import the project into eclipse, and deploy it into tomcat,you can run or debug it in eclipse.

another option, you can simply deploy the FileUploadAndDownload.war in deploy folder into tomcat, and run tomcat
(pre-requiste is: change db connection information in file com.duanchao.file.toolsbean.DB)

