# TeachMeSkills_C27_Lesson_45_HW
Homework for lesson #45

1. **Task #1**

A "Spring Boot REST" project has been developed to simulate the operation of Internet banking.
Project contains: 
- Utility class "PostgresUtil" - component for managing connection to a PostgreSQL database;
- Account data model class **"Account"**. Contains account information such as ID, account number, balance, client and list of cards;
- Card data model class **"Card"**. Contains card information such as ID, card number, account and client;
- Client data model class **"Client"**. Contains client information such as ID, name, email, list of accounts and list of cards;
- Data Transfer Object (DTO) class **"TransferDTO"** for transferring amounts between cards. This class encapsulates the details required for the translation operation;
- Validator class **"GenericValidator"** with **"isClientExists"**, **"isCardExistsByID"**, **"isAccountExists"**, **"isCardExistsByCardNumber"**, **"isIDExists"** and **"isCardNumberExists"** methods for checking the existence of various entities in the database;
- Service class **"AmountTransferService"** with **"transferAmount"** method for transferring funds between accounts using card numbers;
- Service class **"CardService"** with **"getCardByID"** and **"getCardsByAccountID"** methods working with cards;
- Service class **"ClientService"** with **"getClientByID"** and **"getClients"** methods for working with clients;
- Controller class **"CardController"** for working with cards;
- Controller class **"ClientController"** for working with clients;
- Controller class **"TransferController"** for transferring funds between cards;
- Exception handler class **"ModelExceptionHandler"** - global exception handler for "REST API" controllers.

- Resources "TMS_C27_account_card_client.sql" and "TMS_C27_account_card_client.txt" files contain a script for creating the "account", "card" and "client" tables in the "TMS_C27 database".
