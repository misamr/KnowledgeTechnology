# KnowledgeTechnology

Welcome to xGP! With this project, we aimed to create an 
expert program that can help GPs to analyze the range 
and severity of the symptoms and to suggest the most 
suitable specialist(s) for the patient, which might save time and reduce uncertainty. 
To reduce the complexity of our system, we focused the model on specialist referral for GPs for adults.

This project was created using Java Spring application.

## Getting started

Please install at least Java 8. You can follow the instructions [here](https://www.java.com/en/download/help/download_options.xml)

We have used [IntelliJ](https://www.jetbrains.com/idea/) IDE for this project. If you want to use to run our code from there, 
just open it as a project. Otherwise, it should also work with other IDEs.

In order to run the jar file, type in your command line:
```
java -jar ai-kb-0.1.0.jar 
```
You will then have to open your browser and run the following:
```
http://localhost:8080/
```

Alternatively, you can run the program from the cloud by typing the following in your browser:
```
https://xgp.herokuapp.com
```
##Project structure
* __.idea__: The IntelliJ project specific settings file.
* __.settings__: The Eclipse project specific settings file.
* __.vscode__: The Virtual Studio Code project specific settings file.
* __src__: The application source files. You can find here the Java files divided into 3 folders: 
domainmodel, rulemodel and utils. There are also elements of UI in __resources__ folder along with the file 
with the questions named `survey`

##Authors
Liya Yeshmagambetova (s3360571), Luke van den Wittenboer (s3480569) and Michaela Mrazkova (s3372219)



