CREATE DATABASE IF NOT EXISTS final_project_db;


-- Use the database
USE final_project_db;
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS photos;
DROP TABLE IF EXISTS chats;
DROP TABLE IF EXISTS filters;
SET FOREIGN_KEY_CHECKS=1;

-- Create the profile table
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

-- Create the posts table
CREATE TABLE IF NOT EXISTS posts
(
    profile_id   INT,
    post_id      INT PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(255) NOT NULL,
    price        DECIMAL(10, 2),
    description  mediumTEXT,
    publish_date DATE,
    main_photo  varchar(255) DEFAULT '/images/default.png',
    FOREIGN KEY (profile_id) REFERENCES profiles (profile_id)
    );

-- Create the photos table
CREATE TABLE IF NOT EXISTS photos
(
    photo_id  INT PRIMARY KEY AUTO_INCREMENT,
    post_id   INT not null,
    photo_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts (post_id)
    );

-- Create the chat table
CREATE TABLE IF NOT EXISTS chats
(
    message_id  INT PRIMARY KEY AUTO_INCREMENT,
    sender_id   INT  NOT NULL,
    receiver_id INT  NOT NULL,
    message     VARCHAR(255) NOT NULL,
    send_time   DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES profiles (profile_id),
    FOREIGN KEY (receiver_id) REFERENCES profiles (profile_id)
    );

CREATE TABLE IF NOT EXISTS filters
(
  filter_id int primary key auto_increment,
  post_id int not null,
  filter_text varchar(255) not null,
  foreign key (post_id) references posts(post_id),
  Unique(post_id, filter_text)

);

-- Add profiles
-- email: test@test.com     password: test
INSERT INTO profiles (first_name, surname, phone_number, email, password_hash, birth_date)
VALUES ('test', 'test', '0123456789', 'test@test.com', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3', '2000-09-11');
-- email: johndoe@example.com        password: password123
INSERT INTO profiles (first_name, surname, phone_number, email, password_hash, birth_date)
VALUES ('John', 'Doe', '123456789', 'johndoe@example.com', 'cbfdac6008f9cab4083784cbd1874f76618d2a97', '1990-01-01');
-- email: janesmith@example.com      password: password456
INSERT INTO profiles (first_name, surname, phone_number, email, password_hash, birth_date)
VALUES ('Jane', 'Smith', '987654321', 'janesmith@example.com', '0c6f6845bb8c62b778e9147c272ac4b5bdb9ae71', '1995-05-10');
-- email: alicejohnson@example.com   password: password789
INSERT INTO profiles (first_name, surname, phone_number, email, password_hash, birth_date)
VALUES ('Alice', 'Johnson', '555555555', 'alicejohnson@example.com', '7f6d5eea1bcef5ca6209d33b28e3aaeb3db26f24', '1988-12-15');
-- email: michaelbrown@example.com   password: passwordabc
INSERT INTO profiles (first_name, surname, phone_number, email, password_hash, birth_date)
VALUES ('Michael', 'Brown', '444444444', 'michaelbrown@example.com', '963c8b37b3615f3c7f88cbb0f6becff1ffe726f4', '1992-07-20');

-- Add posts
-- Post 1
INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
VALUES (1, 'test', 123.45, 'test test test', '2023-11-05', '/images/1P1.png');
-- Post 2
INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
VALUES (2, 'Lenovo Flex 14 Touchscreen', 1450.00, 'Lenovo touchscreen laptop with 360° conversion. On the Ryzen 5 processor, with 12 gigabytes of RAM and the fastest Samsung M2 SSD.',  '2023-01-15', '/images/2P1.png');
-- Post 3
INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
VALUES (2, 'iPhone 11 Pro Max 256/512GB', 1075.00, 'არის სრულიად ახალივით.იდეალურ მდგომარეობაში.',  '2023-02-20', '/images/3P1.png');
-- Post 4
INSERT INTO posts (profile_id, title, price, description,  publish_date, main_photo)
VALUES (3, 'მისაღები ოთახის კომპლექტი', 6500.00, 'სასწრაფოდ! იყიდება სულ ახალი მისაღების კომპლექტი რაც ხედავთ სურათზე ყველაფერი 6500. მაგიდას მოყვება 7 სკამი!', '2022-03-10', '/images/4P1.png');
-- Post 5
INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
VALUES (4, 'Ibanez RG370DX, Indonesia 2008', 1500.00, 'ინდონეზიური Ibanez RG370DX. 15 წელია რაც ჩემს ხელშია. არის სრულად დასეტაპებული, ახალი სიმებით.', '2023-04-05', '/images/5P1.png');
-- Post 6
INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
VALUES (5, 'ირლანდიური სეტერი', 200.00, 'სხვისდება ირლანდიური სეტერის ლეკვები, ძალიან კარგად მოვლილი და საუკეთესო ვიზუალის მქონე მშობლები საბუთებით, საგვარეულო ნუსხით.', '2023-05-20', '/images/6P1.png');
-- Post 7
INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
VALUES (5, 'HUBLOT F1', 550.99, 'იყიდება მამაკაცის ელექტრონული საათი "HUBLOT -FORMULA 1" იაპონური მექანიზმით.',  '2023-05-20', '/images/7P1.png');
-- Post 8
INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
VALUES (5, 'იყიდება ქართველი ავტორების წიგნები', 45.00, 'იყიდება ქართველი ავტორების წიგნები. წიგნები არის ნორმალურ მდგომარეობაში. სულ არის 10 წიგნი, იყიდება ყველა წიგნი ერთად, ფასი 45 ლარი.', '2023-05-20', '/images/8P1.png');


-- Add images
-- Image 1
INSERT INTO photos (post_id, photo_url)
VALUES (1, '/images/1P1.png');
-- Image 2
INSERT INTO photos (post_id, photo_url)
VALUES (2, '/images/2P1.png');
-- Image 3
INSERT INTO photos (post_id, photo_url)
VALUES (2, '/images/2P2.png');
-- Image 4
INSERT INTO photos (post_id, photo_url)
VALUES (3, '/images/3P1.png');
-- Image 5
INSERT INTO photos (post_id, photo_url)
VALUES (3, '/images/3P2.png');
-- Image 6
INSERT INTO photos (post_id, photo_url)
VALUES (4, '/images/4P1.png');
-- Image 7
INSERT INTO photos (post_id, photo_url)
VALUES (5, '/images/5P1.png');
-- Image 8
INSERT INTO photos (post_id, photo_url)
VALUES (6, '/images/6P1.png');
-- Image 9
INSERT INTO photos (post_id, photo_url)
VALUES (7, '/images/7P1.png');
-- Image 10
INSERT INTO photos (post_id, photo_url)
VALUES (7, '/images/7P2.png');
-- Image 11
INSERT INTO photos (post_id, photo_url)
VALUES (8, '/images/8P1.png');
-- Image 12
INSERT INTO photos (post_id, photo_url)
VALUES (8, '/images/8P2.png');
-- Image 13
INSERT INTO photos (post_id, photo_url)
VALUES (8, '/images/8P3.png');


-- Add filters
-- Filter 1
INSERT INTO filters (post_id, filter_text)
VALUES (1, 'test');
-- Filter 2
INSERT INTO filters (post_id, filter_text)
VALUES (2, 'tech');
-- Filter 3
INSERT INTO filters (post_id, filter_text)
VALUES (3, 'tech');
-- Filter 4
INSERT INTO filters (post_id, filter_text)
VALUES (4, 'furniture');
-- Filter 5
INSERT INTO filters (post_id, filter_text)
VALUES (4, 'home');
-- Filter 6
INSERT INTO filters (post_id, filter_text)
VALUES (5, 'music');
-- Filter 7
INSERT INTO filters (post_id, filter_text)
VALUES (5, 'entertainment');
-- Filter 8
INSERT INTO filters (post_id, filter_text)
VALUES (6, 'animals');
-- Filter 9
INSERT INTO filters (post_id, filter_text)
VALUES (7, 'fashion');
-- Filter 10
INSERT INTO filters (post_id, filter_text)
VALUES (7, 'tech');
-- Filter 11
INSERT INTO filters (post_id, filter_text)
VALUES (8, 'education');
-- Filter 12
INSERT INTO filters (post_id, filter_text)
VALUES (8, 'books');
