## **Introduction**
This project immitates the electric bill generation process at a very basic level. In real scenario possibly the whole process relies on AS400 or any mainframe system.This project tries to explore all the possibilities of doing the same process while elevating microservies and cloud based approach.Irrespective of whatever has been used in this project the possibilities are endless.

###  **Objective**
To create a resilient cloud model to find another way of electric bill generation for WBSEDCL .

>**ElectricBillProcessing**: processes the data through<br>**ElectricBillDataLoadTask**: Loads daat

### **Technologies Used**
<ul>
<li>Spring Data JPA</li>
<li>Spring cloud data flow server</li>
<li>Spring cloud Task</li>
<li>Spring Batch</li>
<li>MySQL</li>
</ul>

### **Future scope**
<ul>
<li>To use api gateways and use the inbuilt rest api endpoints to control the task execution in the server</li>
<li>Use of Kafka for real time processing and bill generation</li>
<li> use of mongo db for analytics</li>
<li> use of redis for caching</li>
</ul>

### *Environment preparation for execution*
---
 ### **Server Intialization:**
 1. execute the below script:
> version used for Spring Cloud Data Flow Server: 2.10.0
```
@echo off
timeout 3 > nul

set PATH=.;C:\Program Files\Java\Jdk1.8\jdk1.8.0_281\bin
echo "Setting necessary environment varibales===================>"

set SPRING_CLOUD_DATAFLOW_FEATURES_STREAMS_ENABLED=false
set SPRING_CLOUD_DATAFLOW_FEATURES_SCHEDULES_ENABLED=false
set SPRING_CLOUD_DATAFLOW_FEATURES_TASKS_ENABLED=true
set spring_datasource_url=jdbc:mariadb://localhost:3306/CYOLASBCOM
set spring_datasource_username=<your db id>
set spring_datasource_password=<your db pwd>
set spring_datasource_driverClassName=org.mariadb.jdbc.Driver
set spring_datasource_initialization_mode=always
set spring_flyway_enabled=false

timeout 3 > nul

start java -jar spring-cloud-dataflow-server-2.10.0.jar
```
> Download jars: 
2. package creation and installation: mvn clean compile package install
3. package deployment:
4. deploymeny properties:
5. ```
deployer.*.cpu=1<br>
deployer.*.disk=100<br>
deployer.*.local.cpu=1<br>
deployer.*.local.disk=100<br>
deployer.*.local.java-opts=-Xms512m -Xmx1024m<br>
deployer.*.local.memory=100<br>
deployer.*.local.working-directories-root=D:\Program <br>Files\Spring\Spring Data Flow<br>
deployer.*.memory=100<br>
deployer.*.spring.cloud.dataflow.features.<br>schedules-enabled=false<br>
deployer.*.spring.cloud.dataflow.features.streams-enabled=false<br>
deployer.*.spring.cloud.dataflow.features.tasks-enabled=true<br>
deployer.bill-load-task.cpu=1<br>
deployer.bill-load-task.disk=100<br>
deployer.bill-load-task.local.cpu=1<br>
deployer.bill-load-task.local.disk=100<br>
deployer.bill-load-task.local.java-opts=-Xms512m -Xmx1024m<br>
deployer.bill-load-task.local.memory=100<br>
deployer.bill-load-task.local.working-directories-root=D:\Program Files\Spring\Spring Data Flow<br>
deployer.bill-load-task.memory=200<br>
deployer.bill-load-task.spring.cloud.dataflow.features.schedules-enabled=false<br>
deployer.bill-load-task.spring.cloud.dataflow.features.streams-enabled=false<br>
deployer.bill-load-task.spring.cloud.dataflow.features.tasks-enabled=true<br>
deployer.bill-processing-task.cpu=1<br>
deployer.bill-processing-task.disk=100<br>
deployer.bill-processing-task.local.cpu=1<br>
deployer.bill-processing-task.local.disk=100<br>
deployer.bill-processing-task.local.java-opts=-Xms512m -Xmx1024m<br>
deployer.bill-processing-task.local.memory=100<br>
deployer.bill-processing-task.local.working-directories-root=D:\Program Files\Spring\Spring Data Flow<br>
deployer.bill-processing-task.memory=200<br>
deployer.bill-processing-task.spring.cloud.dataflow.features.schedules-enabled=false<br>
deployer.bill-processing-task.spring.cloud.dataflow.features.streams-enabled=false<br>
deployer.bill-processing-task.spring.cloud.dataflow.features.tasks-enabled=true<br>
spring.cloud.dataflow.task.platformName=default<br>
```

References:
Docs:  https://dataflow.spring.io/docs/installation/local/manual/
```
wget https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-server/2.10.2/spring-cloud-dataflow-server-2.10.2.jar


wget https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-shell/2.10.2/spring-cloud-dataflow-shell-2.10.2.jar```

<p>
![Pipeline](https://user-images.githubusercontent.com/69466192/230708407-8871f20f-89be-43c4-9b27-b33a3b5aadff.png)
</p>
<p>
![execution](https://user-images.githubusercontent.com/69466192/230708517-ef2b447b-b3e5-4395-a258-ad39800d5281.png)
</p>
<p>
![Information](https://user-images.githubusercontent.com/69466192/230708521-5c009691-117e-4153-9e36-f76b19607553.png)
</P>
<p>
![logs](https://user-images.githubusercontent.com/69466192/230708541-9a5a8a80-9936-4e36-8d7b-ba7c1c8e01e2.png)
</p>
<p>
![Database_Desing](https://user-images.githubusercontent.com/69466192/230708563-21dd4e58-b756-4301-92fd-6641095d1efa.png)
</p>

