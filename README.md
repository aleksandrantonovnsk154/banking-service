# Сервис банка

### Документация по взаимодействию с Api

#### Swagger-ui:

* [http://localhost:9191/swagger-ui/index.html](http://localhost:9191/swagger-ui/index.html)
  
#### H2db:

Driver class: org.h2.Driver

URL: jdbc:h2:mem:testdb

User Name: aa

Pass: aa
* [http://localhost:9191/h2-console/](http://localhost:9191/h2-console/)


POST METHODS:
* ====================== 
### Создать новый аккаунт

POST http://localhost:9191/api/accounts

        {
      "beneficiaryName": "TestUser1",
      "pinCode": "0000"
        }

response:

        {
      "id": "233c77d0-ac7e-42b0-8a4c-97e33afdc506",
      "beneficiaryName": "TestUser1",
      "balance": 0.00,
      "created": "2024-03-18T12:14:55.750158123"
        }

* ======================

### Внести деньги

POST http://localhost:9191/api/transactions/deposit

        {
      "amount": "100",
      "toAccount": "233c77d0-ac7e-42b0-8a4c-97e33afdc506"
        }

response:

        {
      "id": "75d3433f-ca12-45b7-bfc7-55ee9579002d",
      "fromAccount": null,
      "toAccount": "233c77d0-ac7e-42b0-8a4c-97e33afdc506",
      "amount": 100,
      "transactionType": "DEPOSIT",
      "created": "2024-03-18T12:18:16.475090596"
        }

* ======================

### Перевод денег

POST http://localhost:9191/api/transactions/transfer

        {
      "amount": "50",
      "fromAccount": "233c77d0-ac7e-42b0-8a4c-97e33afdc506",
      "pinCode": "0000",
      "toAccount": "81de20c2-7288-44d0-9720-85236d69790d"
        }

response:

        {
      "id": "1ab7432c-a888-4efe-8e58-e3100ec0e2c1",
      "fromAccount": "233c77d0-ac7e-42b0-8a4c-97e33afdc506",
      "toAccount": "81de20c2-7288-44d0-9720-85236d69790d",
      "amount": 50,
      "transactionType": "TRANSFER",
      "created": "2024-03-18T12:19:54.188378644"
        }	

* ======================

### Снять деньги

POST http://localhost:9191/api/transactions/withdraw

        {
      "fromAccount": "233c77d0-ac7e-42b0-8a4c-97e33afdc506",
      "amount": 30,
      "pinCode": "0000"
        }

response:

        {
      "id": "8dbfb0e4-e137-4729-8763-5d7401009496",
      "fromAccount": "233c77d0-ac7e-42b0-8a4c-97e33afdc506",
      "toAccount": null,
      "amount": 30,
      "transactionType": "WITHDRAW",
      "created": "2024-03-18T12:20:50.582394585"
        }

* ======================

GET METHODS:
### Получить все учетные записи
GET http://localhost:9191/api/accounts - Получить все учетные записи с разбивкой по страницам

http://localhost:9191/api/accounts?page=0&size=3&sort=balance,desc

response example:

        {
      "id": "233c77d0-ac7e-42b0-8a4c-97e33afdc506",
      "beneficiaryName": "TestUser1",
      "balance": 50.00,
      "created": "2024-03-18T12:14:55.750158"
        },
        {
      "id": "81de20c2-7288-44d0-9720-85236d69790d",
      "beneficiaryName": "TestUser1",
      "balance": 20.00,
      "created": "0024-03-20T12:46:20.827463"
        }

* ======================

### Получить все операции по счету
GET http://localhost:9191/api/accounts - Получить все операции с разбивкой по страницам для данного идентификатора учетной записи

http://localhost:9191/api/transactions/233c77d0-ac7e-42b0-8a4c-97e33afdc506?page=0&size=10&sort=created%2Cdesc

response example:

        }  
      "id": "1ab7432c-a888-4efe-8e58-e3100ec0e2c1",
      "fromAccount": "233c77d0-ac7e-42b0-8a4c-97e33afdc506",
      "toAccount": "81de20c2-7288-44d0-9720-85236d69790d",
      "amount": 50.00,
      "transactionType": "TRANSFER",
      "created": "2024-03-18T12:19:54.188379"
        },
        {
      "id": "75d3433f-ca12-45b7-bfc7-55ee9579002d",
      "fromAccount": null,
      "toAccount": "233c77d0-ac7e-42b0-8a4c-97e33afdc506",
      "amount": 100.00,
      "transactionType": "DEPOSIT",
      "created": "2024-03-18T12:18:16.475091"
        }
