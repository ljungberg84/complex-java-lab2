--------------------------------------------------------------

# base url: localhost:8080/school

subject:
  {
    "title": "string"
  }

student:
  {
    "firstName":"String",
    "lastName": "String",
    "email": "String"
  }

teacher:
  {
    "firstName":"String",
    "lastName": "String",
    "email": "String"
  }

# add student, subject or teacher:

POST:
  * /students
  * /subjects
  * /teachers


# add student or teacher to subject:

PUT(no body):
  * /subjects/{title}/student/{email}

  * /subjects/{title}/teacher/{email}


GET:
  * /students
  * /subjects
  * /teachers
  
--------------------------------------------------------

# Schoolproject

Small project for teaching purposes.

* Wildfly
* JEE 8 
* Java 11
* Git
* Maven
* MySQL

## Wildfly configuration

Install any Wildfly release you want. I use 18.

Add a user, and place the school.cli script under the bin folder.<br>
Create database school. The script will need a mysql connector under `C:\Users`
to work. 

The script is predefined for `mysql.connector-java-8.0.12.jar`. Change location and version for your own liking.

Start Wildfly, and once running, open a new prompt, and go to the bin folder.<br>
Write `jboss-cli -c --file=school.cli`

It should say outcome success. Write `jboss-cli -c --command=:reload` to restart the server.



 
