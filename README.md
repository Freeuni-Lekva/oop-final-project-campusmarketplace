# Campus Marketplace

## Overview
### Final project for Free University OOP class
Welcome to the Campus Marketplace Java Web Application! This application allows users to create, manage, and interact with posts related to various products and services. Below are the instructions to get started with the application.

## Requirements

To ensure proper functionality of the application, make sure you have the following software and tools installed:

- Java 20+
- MySQL 8.0.25+
- Apache Tomcat 7
- Apache Maven 3
  
## Getting Started

1. **Database Setup**
   - Locate the `mySQLdb.sql` file in the `src/main/resources` directory.
   - Run the SQL script in your MySQL database to create the necessary database schema.

2. **Database Configuration**
   - Open `src/main/java/marketplace/constants/DatabaseConstants.java`.
   - Set the `DATABASE_USERNAME` and `DATABASE_PASSWORD` constants with your MySQL database credentials.

3. **Application Features**
   - Users can register and log in to the application to access its features.
   - Users can create, edit, and delete posts. Each post includes a title, description, price, and multiple photos.
   - Users can initiate and participate in chat conversations with other users.
   - The search functionality allows users to search for posts by title, author, description, or apply filters based on categories.
   - Users can mark specific posts as favorites.
   - Users can view each others' profiles.

## Building and Running

1. **Using IntelliJ:**
   - This repository is integrated with IntelliJ IDEA, making it easy to build and run.
   - Simply open the project in IntelliJ and utilize the provided configurations to run the application.

2. **Using Maven:**
   - Open a terminal or command prompt in the project root directory.
   - Run the following Maven command to start the server:
     ```
     mvn sql:execute tomcat7:run
     ```
   
## Usage

1. **Registration and Login**
   - Launch the application and navigate to the registration page to create an account.
   - Once registered, log in with your credentials to access the application's features.

2. **Creating Posts**
   - After logging in, users can create new posts by providing a title, description, price, and uploading multiple (possibly zero) photos.

3. **Managing Posts**
   - Users can view their own posts and edit or delete them as needed.

4. **Chat**
   - Users can initiate chat conversations with other users and engage in real-time messaging.

5. **Favorites**
   - Users can mark posts as favorites to easily access them later.

6. **Searching and Filtering**
   - The search functionality allows users to find posts by various criteria, such as title, author, description, or filters.

7. **View Profiles:**
   - Users can view each other's profiles to learn more about fellow users.

**Note:** Some features can be accessed without registering or logging in, including viewing posts, searching, using filters, viewing profiles, and marking posts as favorites.
---
© Free University of Tbilisi | 2023
