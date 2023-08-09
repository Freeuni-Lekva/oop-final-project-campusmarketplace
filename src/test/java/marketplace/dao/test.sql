CREATE DATABASE IF NOT EXISTS test_final_project_db;
USE test_final_project_db;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS test_profiles;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS test_photos;
DROP TABLE IF EXISTS test_chats;
DROP TABLE IF EXISTS test_filters;
DROP TABLE IF EXISTS test_favourites;

SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE IF NOT EXISTS profiles
(
    profile_id    INT PRIMARY KEY AUTO_INCREMENT,
    first_name    VARCHAR(255) NOT NULL,
    surname       VARCHAR(255) NOT NULL,
    phone_number  VARCHAR(20)  NOT NULL,
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    birth_date    DATE
);

CREATE TABLE IF NOT EXISTS posts
(
    profile_id   INT,
    post_id      INT PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(255) NOT NULL,
    price        DECIMAL(10, 2),
    description  MEDIUMTEXT,
    publish_date DATE,
    main_photo   VARCHAR(255) DEFAULT 'images/default.png',
    FOREIGN KEY (profile_id) REFERENCES profiles (profile_id)
);

CREATE TABLE IF NOT EXISTS photos
(
    photo_id  INT PRIMARY KEY AUTO_INCREMENT,
    post_id   INT NOT NULL,
    photo_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts (post_id)
);

CREATE TABLE IF NOT EXISTS chats
(
    message_id  INT PRIMARY KEY AUTO_INCREMENT,
    sender_id   INT NOT NULL,
    receiver_id INT NOT NULL,
    message     VARCHAR(255) NOT NULL,
    send_time   DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES profiles (profile_id),
    FOREIGN KEY (receiver_id) REFERENCES profiles (profile_id)
);

CREATE TABLE IF NOT EXISTS filters
(
    filter_id   INT PRIMARY KEY AUTO_INCREMENT,
    post_id     INT NOT NULL,
    filter_text VARCHAR(255) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts (post_id),
    UNIQUE (post_id, filter_text)
);

CREATE TABLE IF NOT EXISTS favourites
(
    profile_id INT,
    post_id    INT,
    FOREIGN KEY (profile_id) REFERENCES profiles (profile_id),
    FOREIGN KEY (post_id) REFERENCES posts (post_id)
);