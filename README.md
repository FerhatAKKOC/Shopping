# Spring Boot Shopping Backend API Project

## About

<p>This backend api project for practicing <b>Spring Boot</b> + <b>MySQL</b> with role base security features. The goal was to build some basic shopping cart wep APIs.</p>
<br>
<p>Spring Boot (Security, Data JPA, Web, Actuator, Devtool) + MySQL were used.</p>
<br>
<p>There is authentication and authorization functionality with Spring Security. There are two roles; <b>ADMIN</b> and <b>CUSTOMER. </b>
<i>Products</i> and <i>News</i> catalogues can be modified <i>(<b>add, update, delete</b>)</i> only by ADMIN role. Customers with <i>CUSTOMER</i> role can select multiple products and update their <i>cart</i> catalogues. Customers must be login to order. </p>
<br>
<p><i>Product</i> APIs support <i>pagination</i>, <i>filter</i> and <i>search</i> features.</p>

## Configuration 

<p>Spring Boot : 2.5.4</p>
<p>My SQL : 8.0</p>
<p>Java : 11.0.11</p>

### Database
<p> MY SQL was locally installed and run. Previously a DB with the name "shopdb" was created.</i></p>

* <b>src/resources/application.properties</b> is main configuration file.

<p>You can modify your DB settings according to following lines. A remote DB can be set as well.</p>

    spring.jpa.hibernate.ddl-auto=update
    spring.datasource.url=jdbc:mysql://localhost:3306/shopdb
    spring.datasource.username=root
    spring.datasource.password=123456
    spring.datasource.driver-class-name =com.mysql.jdbc.Driver

* <b>src/resources/data</b> directory has 3 files. 
   <br>
    * <b>user.json :</b> includes users, roles and passwords
    * <b>products.json :</b> includes some mock products for product catalogue
    * <b>news.json :</b> includes some mock news for news catalogue

<br>

### Users, Roles and Passwords
<p>When application bring up, it loads files under <i>src/resources/data</i> and write them into DB tables at start-up. DB schema is built by Data JPA.</p>
<br>
<i>How:</i> All spring services are up, Spring publishes an <i>'ApplicationReadyEvent'</i> and methods in /config/<b>DBConfig.java</b> catch the event and read files. 
<br><br>
<p>There are 6 customer users and one admin user.</p>

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

<br> <br>
![ER Diagram](https://github.com/FerhatAKKOC/Shopping/blob/master/images/ERDiagram.png)

## How to Run

<p>Firstly, clone or download the project.</p>
<p>Open a command line window (cmd) in "Shopping" folder (Windows).</p>

There are many ways to build and run application, I will show the simplest way.

Before running, be sure that My SQL configurations are done properly and it is running. Do not forget to create a schema, <i>'shopdb'</i>.

Spring Boot serves server.port=8080 as default. It can be configured in application.properties.

    server.port=8080

Run the following command. It builds and runs the application.

```sh
gradlew bootRun
```

<i>Note</i> : be sure that DB tables are filled in with mock data. Checks DB, if an issue arises, checks logs.

Later, you can send some REST requests to check if it is running properly.

```sh
http://localhost:8080/products/
http://localhost:8080/news/
http://localhost:8080/actuator

http://localhost:8080/swagger-ui.html
```

## Roles and login-logout
<p>Home page returns current user and her/him roles.</p>
    
    http://localhost:8080/

<p>If user role is either <i>ROLE_ADMIN</i> or <i>ROLE_CUSTOMER</i>, you can logout</p>

    http://localhost:8080/logout

<p>If current role is <b>ROLE_ANONYMOUS</b>, you can login one of the following roles. Be sure that, previously be in ROLE_ANONYMOUS.</p>

    http://localhost:8080/admin
    http://localhost:8080/customer


### Products entity

<p>This APIs support pagination, filter and search functionalities. You can use swagger-ui.html or try the following ones. Searching is made according to <i>name</i>, <i>price</i> and <i>dicount</i> attributes.

        http://localhost:8080/products/
        http://localhost:8080/products/3
        http://localhost:8080/products/?page=2
        http://localhost:8080/products/?page=2&size=3
        http://localhost:8080/products/?name=Toy
        http://localhost:8080/products/?price=10000
        http://localhost:8080/products/?discount=20

<p><b>add/delete/update</b> APIs require <i>ADMIN</i> role.</p>
<p>Note : Content-Type : <i> application/json</i>,  method: <i>POST/PUT/DELETE</i>  role: <i>ADMIN</i> If you use <i>postman</i>, set method types properly.</p>

APIs

    http://localhost:8080/products/add        (POST)
    http://localhost:8080/products/add/all    (POST)
    http://localhost:8080/products/update/2   (PUT)
    http://localhost:8080/products/delete/2   (DELETE)

Body    
```sh
{
    "id": 2,
    "name": "New product",
    "price": 210.0,
    "description": "New product api adding test ",
    "discount": 3.0,
    "image": "NewProduct.png"
}
```

### News entity

<p>This APIs reqire ADMIN role except read operations. Pagination is not supported.</p>

APIs

    http://localhost:8080/news/
    http://localhost:8080/news/1
    http://localhost:8080/news/add              (POST)
    http://localhost:8080/news/add/all
    http://localhost:8080/news/update/2         (PUT)
    http://localhost:8080/news/delete/2         
    http://localhost:8080/news/delete/all       (DELETE)

Body
```sh
{
    "id": 2,
    "head": "Test",
    "description": "Testing News api",
    "image": "test.png"
}
```

### Cart entity

<p>This APIs reqire CUSTOMER role.</p>

API's

    http://localhost:8080/carts/
    http://localhost:8080/carts/add/2       (Add productid:2, quantity:1)
    http://localhost:8080/carts/update/2/3  (update product:2, quantity:3)
    http://localhost:8080/carts/delete/2    
    http://localhost:8080/carts/orderSummary

Output
```sh
{
    "cartModelList": [
        {
            "product": {
                "id": 6,
                "name": "Product3",
                "price": 1500,
                "description": "Product3 Description ",
                "discount": 10,
                "image": "Product3.png"
            },
            "quantity": 1,
            "price": 1500
        },
        {
            "product": {
                "id": 2,
                "name": "Hp Laptop",
                "price": 7000,
                "description": "Hp Laptop Ultra Fast Gaming Display",
                "discount": 12,
                "image": "HP7800.png"
            },
            "quantity": 3,
            "price": 21000
        }
    ],
    "orderSummary": 22500
}
```
<br>

## To Do

* Banner Api's will be developed and improved
* Unit tests will be written
* Pagination/Search/Filter will be implemented in for <i>News</i> and <i>Cart</i> entities
* Globally both Rest&Application exceptions handling will centralized with <b>@ControllerAdvice</b>
* Bean validation will be developed
* View models and Entities separation
* Logs managament will be centralized by ShopLogger
* Login&Logout pages will be developed
* Beans will be separated according to profiles; <i>dev</i>, <i>prod</i>
    


