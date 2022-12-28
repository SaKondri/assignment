# Getting Started

### Reference Documentation
In order to run the application follow these steps

####1) Authenticate and get token for call API
Call https://localhost:8080/authenticate by Post method and in body set this parameter 
{
"username" : "admin",
"password":"123"
} then you can get token in response like this
{
"jwttoken": "ey...."
} and you should put jwttoken value in other api Authorization's header and add "Bearer " at first token. like this  "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." 

####2) Add product 
Call https://localhost:8080/domain/springresttest/addProduct by Post method and in body set this parameter 
{"prdname" : "your value"}
and add Authorization header in request like this "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9"

####3) Get all products 
Call https://localhost:8080/domain/springresttest/prids by Get method and add Authorization header in request like this "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9"

####4) Edit Product

Call https://localhost:8080/domain/springresttest/editProduct by Put method and in body set this parameter
{
"prid": 1,
"prdname": "Windows 11"
}
and add Authorization header in request like this "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9"

####4) Delete Product

Call https://localhost:8080/domain/springresttest/deleteProduct/1 by Delete method and add Authorization header in request like this "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9"

####5) Get Product by Id

Call https://localhost:8080/domain/springresttest/prid/1 by Get method and add Authorization header in request like this "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9"



