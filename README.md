# ProcessLogFileDemo

 *Steps to run the applications*

1. Import the application in eclipse
2. Navigate to the src/main/java/com/creditsuisse/processlogfile/ then right click on the ProcessLogFileDemoApplication.java file -> click on Run As -> click on Maven install
3. Above step will build the application to application right click on the ProcessLogFileDemoApplication.java file -> click on Run As -> click on Java Application
  
  Above step will help to run the application
  
 *Steps to test applications*
  
  1. The application is exposing the below rest endpoint to process the Log event the file. Below are the details:
    Rest endpoint: http://localhost:8080/api/processlogfile?logfilePath=<path of the log file>
  
     example: 
     http://localhost:8080/api/processlogfile?logfilePath=C://Users//Dell//Desktop//demo//ProcessLogFileDemo//log.txt
     
     Note: 1. If Your log file is present at C:\Desktop\demo\ProcessLogFileDemo\log.txt then in rest endpoint you have give filepath 'C://Desktop//demo//ProcessLogFileDemo//log.txt'
           2. You can create the log event by using rest endpoint: http://localhost:8080/api/createlogfile/{numberOfEvents}
              then copy the response of this endpoint to any .txt file and use this file for testing purpose.
           3. Please refer the /ProcessLogFileDemo/log.txt file for sample log file.
  
  
