# Spring Boot Shopping Backend API Project

## About

<p>This backend api project for practicing <b>Spring Boot</b> + <b>MySQL</b> with role base security features. The goal was to build some basic shopping cart wep APIs.</p>
<br>
<p>Spring Boot (Security,Data JPA, Web, Actuator, Devtool) + MySQL were used.</p>
<br>
<p>There is authentication and authorization functionality with Spring Security. There are two roles; <b>ADMIN</b> and <b>CUSTOMER. </b>
<i>Products</i> and <i>News</i> catalogues can be modified <i>(<b>add, update, delete</b>)</i> only by ADMIN role. Customers with <i>CUSTOMER</i> role can select multiple products and update their <i>cart</i> catalogue. Customers must be login to order. </p>
<br>
<p><i>Products</i> API supports <i>pagination</i>, <i>filter</i> and <i>search</i> features.</p>

## Configuration 

### Database
<p> MY SQL was locally installed and run. Previously a DB with the name "shopdb" was created.</i></p>

* <b>src/resources/application.properties</b> is main configuration file

<p>You can modify your DB settings according to following lines. It can be set a remote DB as well.</p>

    spring.jpa.hibernate.ddl-auto=update
    spring.datasource.url=jdbc:mysql://localhost:3306/shopdb
    spring.datasource.username=root
    spring.datasource.password=123456
    spring.datasource.driver-class-name =com.mysql.jdbc.Driver

* <b>src/resources/data</b> directory has 3 files. 
   <br>
    * <b>user.json :</b> includes users, roles and passwords
    * <b>products.json :</b> includes some mock products for product catalogue
    * <b>news.json :</b> includes some mock news for news catalogue.

<br>

### Users, Roles and Passwords
<p>When application bring up, it loads files under <i>src/resources/data</i> and write them into DB tables at start-up.</p>
<br>
<i>How:</i> All spring services are up, Spring publishes an <i>'ApplicationReadyEvent'</i> and methods in /config/<b>DBConfig.java</b> catch the event and read files. 
<br><br>
<p>There are 6 customer users and one admin users.</p>

<br>

| Users         | Roles         | Passwords     |
| ------------- | ------------- |-------------  | 
| admin         | ADMIN         | adminpass     |
| customer      | CUSTOMER      | customerpass  |
| customer1     | CUSTOMER      | customerpass  |
| customer2     | CUSTOMER      | customerpass  |
| customer3     | CUSTOMER      | customerpass  |
| customer4     | CUSTOMER      | customerpass  |
| customer5     | CUSTOMER      | customerpass  |

![ER Diagram](https://github.com/FerhatAKKOC/Shopping/blob/master/images/ERDiagram.png)

<img src="https://github.com/FerhatAKKOC/Shopping/blob/master/images/ERDiagram.png" alt="ER Diagram" width="200" height="200">