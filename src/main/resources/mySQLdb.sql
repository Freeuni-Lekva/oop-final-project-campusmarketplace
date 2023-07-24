	DROP DATABASE If EXISTS final_project_db;
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
        main_photo  varchar(255) DEFAULT 'images/default.png',
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
    VALUES (1, 'test', 123.45, 'test test test', '2023-11-05', 'images/1P1.png');
    -- Post 2
    INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
    VALUES (2, 'Lenovo Flex 14 Touchscreen', 1450.00, 'Lenovo touchscreen laptop with 360° conversion. On the Ryzen 5 processor, with 12 gigabytes of RAM and the fastest Samsung M2 SSD.',  '2023-01-15', 'images/2P1.png');
    -- Post 3
    INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
    VALUES (2, 'iPhone 11 Pro Max 256/512GB', 1075.00, 'არის სრულიად ახალივით.იდეალურ მდგომარეობაში.',  '2023-02-20', 'images/3P1.png');
    -- Post 4
    INSERT INTO posts (profile_id, title, price, description,  publish_date, main_photo)
    VALUES (3, 'მისაღები ოთახის კომპლექტი', 6500.00, 'სასწრაფოდ! იყიდება სულ ახალი მისაღების კომპლექტი რაც ხედავთ სურათზე ყველაფერი 6500. მაგიდას მოყვება 7 სკამი!', '2022-03-10', 'images/4P1.png');
    -- Post 5
    INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
    VALUES (4, 'Ibanez RG370DX, Indonesia 2008', 1500.00, 'ინდონეზიური Ibanez RG370DX. 15 წელია რაც ჩემს ხელშია. არის სრულად დასეტაპებული, ახალი სიმებით.', '2023-04-05', 'images/5P1.png');
    -- Post 6
    INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
    VALUES (5, 'ირლანდიური სეტერი', 200.00, 'სხვისდება ირლანდიური სეტერის ლეკვები, ძალიან კარგად მოვლილი და საუკეთესო ვიზუალის მქონე მშობლები საბუთებით, საგვარეულო ნუსხით.', '2023-05-20', 'images/6P1.png');
    -- Post 7
    INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
    VALUES (5, 'HUBLOT F1', 550.99, 'იყიდება მამაკაცის ელექტრონული საათი "HUBLOT -FORMULA 1" იაპონური მექანიზმით.',  '2023-05-20', 'images/7P1.png');
    -- Post 8
    INSERT INTO posts (profile_id, title, price, description, publish_date, main_photo)
    VALUES (5, 'იყიდება ქართველი ავტორების წიგნები', 45.00, 'იყიდება ქართველი ავტორების წიგნები. წიგნები არის ნორმალურ მდგომარეობაში. სულ არის 10 წიგნი, იყიდება ყველა წიგნი ერთად, ფასი 45 ლარი.', '2023-05-20', 'images/8P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Bedford Fallsის კონცერტის ბილეთი', 123.45,'ბედფორდ ფოლსის კონცერტზე ერთი ბილეთის გაყიდვა აღარ შემიძლია ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'კაიაკატას ლაივ შოუს ბილეთი', 123.45,'კაიაკატას ლაივ შოუს ერთი ბილეთის გაყიდვა ვეღარ ვესწრები გონივრულ ფასად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'Killages კონცერტის ბილეთი ხელმისაწვდომია', 123.45,'Killagesის კონცერტზე ერთი ბილეთის გაყიდვა სამწუხაროდ აღარ შემიძლია ღიაა შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'Mellow Band Performance Ticket', 123.45,'მელოუს ჯგუფის სპექტაკლზე ერთი ბილეთის გაყიდვა აღარ შემიძლია ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'არას კონცერტის ბილეთი იყიდება', 123.45,'არას კონცერტზე ერთი ბილეთის გაყიდვა აღარ შემიძლია სამართლიან ფასად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, '4D Monster Lobsters ლაივ შოუს ბილეთი', 123.45,'ერთი ბილეთის გაყიდვა 4D Monster Lobstersის ლაივ შოუზე სამწუხაროდ ვეღარ წავა ღიაა შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება მეორადი ლეპტოპი', 123.45,'იყიდება მეორადი ლეპტოპი ჯერ კიდევ კარგ მდგომარეობაში იდეალურია ბიუჯეტის მქონე სტუდენტებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ხელმისაწვდომ ფასად', 123.45,'ვყიდი ტელეფონს იაფად მუშაობს იდეალურად შესანიშნავია მათთვის ვისაც სჭირდება საიმედო მოწყობილობა ბანკის დარღვევის გარეშე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ლეპტოპი კარგ მდგომარეობაში', 123.45,'იყიდება ლეპტოპი კარგ მდგომარეობაში გამოდგება ყოველდღიური გამოყენებისთვის ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელმისაწვდომია ბიუჯეტური მეგობრული ტელეფონი', 123.45,'იყიდება ბიუჯეტური ტელეფონი იდეალურია ძირითადი ამოცანებისა და კომუნიკაციისთვის გონივრულ ფასად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'მეორადი ლეპტოპი მუშა მდგომარეობაში', 123.45,'იყიდება მეორადი ლეპტოპი რომელიც არის მუშა მდგომარეობაში იდეალურია მათთვის ვისაც სჭირდება საიმედო მოწყობილობა ზედმეტი დახარჯვის გარეშე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელმისაწვდომი ტელეფონი შესანიშნავი მდგომარეობაში', 123.45,'ვყიდი ხელმისაწვდომ ტელეფონს რომელიც არის იდეალურ მდგომარეობაში შესაფერისია მათთვის ვისაც მჭიდრო ბიუჯეტი აქვს', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ლეპტოპი  კარგი გარიგება', 123.45,'ვყიდი ლეპტოპს კარგ ფასად ისევ კარგად მუშაობს ღიაა მოლაპარაკებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება გონივრულ ფასად ტელეფონი', 123.45,'ვყიდი გონივრულ ფასად ტელეფონს მუშაობს იდეალურად შესანიშნავია მათთვის ვისაც არ სურს ზედმეტი დახარჯოს', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Apple MacBook Pro', 123.45,'იყიდება მეორადი Apple MacBook Pro კარგ მდგომარეობაში იდეალურია სტუდენტებისთვის ან პროფესიონალებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელმისაწვდომია Dell XPS ლეპტოპი', 123.45,'იყიდება Dell XPS ლეპტოპი ჯერ კიდევ შესანიშნავ ფორმაში იდეალურია სათამაშო ან სამუშაო მიზნებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება HP Pavilion ლეპტოპი', 123.45,'იყიდება HP Pavilion ლეპტოპი იდეალურ მდგომარეობაში გამოდგება ყოველდღიური გამოყენებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელმისაწვდომია Lenovo ThinkPad ლეპტოპი', 123.45,'ვყიდი Lenovo ThinkPad ლეპტოპს იდეალურია ბიზნეს პროფესიონალებისთვის ან სტუდენტებისთვის ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ASUS ROG სათამაშო ლეპტოპი', 123.45,'იყიდება ASUS ROG სათამაშო ლეპტოპი იდეალურ მდგომარეობაში იდეალურია გეიმერებისთვის ან გრაფიკული დიზაინერებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'Acer Predator Helios ლეპტოპი ხელმისაწვდომია', 123.45,'ვყიდი Acer Predator Helios ლეპტოპს იდეალურია თამაშების მოყვარულთათვის ღია შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Microsoft Surface Laptop', 123.45,'იყიდება Microsoft Surface ლეპტოპი ჯერ კიდევ კარგ მდგომარეობაში შესანიშნავი პროდუქტიულობისა და პორტაბელურობისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'Alienware სათამაშო ლეპტოპი ხელმისაწვდომია', 123.45,'იყიდება Alienware სათამაშო ლეპტოპი იდეალურ მდგომარეობაში შესაფერისია ჰარდკორ გეიმერებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება HP Envy ლეპტოპი', 123.45,'ვყიდი HP Envy ლეპტოპს იდეალურია მულტიმედია და გასართობად ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელმისაწვდომია Lenovo Yoga ლეპტოპი', 123.45,'იყიდება Lenovo Yoga ლეპტოპი იდეალურ მდგომარეობაში სტუდენტებისთვის ან პროფესიონალებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Dell Inspiron ლეპტოპი', 123.45,'იყიდება Dell Inspiron ლეპტოპი ჯერ კიდევ კარგ მდგომარეობაში შესანიშნავია ყოველდღიური გამოყენებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ASUS ZenBook ლეპტოპი ხელმისაწვდომია', 123.45,'იყიდება ASUS ZenBook ლეპტოპი იდეალურია პროფესიონალებისა და სტუდენტებისთვის ღია შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება HP Spectre ლეპტოპი', 123.45,'იყიდება HP Spectre ლეპტოპი იდეალურ მდგომარეობაში შესაფერისია მაღალი ხარისხის დავალებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'Microsoft Surface Book ხელმისაწვდომია', 123.45,'იყიდება Microsoft Surface Book ჯერ კიდევ კარგ მდგომარეობაში შესანიშნავია შემოქმედებითობისა და პროდუქტიულობისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Acer Swift ლეპტოპი', 123.45,'იყიდება Acer Swift ლეპტოპი იდეალურია პორტაბელურობისა და ყოველდღიური გამოყენებისთვის ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელმისაწვდომია Lenovo Legion სათამაშო ლეპტოპი', 123.45,'იყიდება Lenovo Legion სათამაშო ლეპტოპი იდეალურ მდგომარეობაში იდეალურია გეიმერებისთვის ან კონტენტის შემქმნელებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Dell XPS  ლეპტოპი', 123.45,'იყიდება Dell XPS  ლეპტოპი ჯერ კიდევ კარგ მდგომარეობაში შესანიშნავი პროდუქტიულობისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ASUS VivoBook ლეპტოპი ხელმისაწვდომია', 123.45,'იყიდება ASUS VivoBook ლეპტოპი იდეალურია სტუდენტებისთვის ან ჩვეულებრივი მომხმარებლებისთვის ღია შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება HP Omen Gaming Laptop', 123.45,'იყიდება HP Omen სათამაშო ლეპტოპი იდეალურ მდგომარეობაში შესაფერისია ინტენსიური სათამაშო სესიებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელმისაწვდომია Lenovo IdeaPad ლეპტოპი', 123.45,'ვყიდი Lenovo IdeaPad ლეპტოპს იდეალურია ყოველდღიური დავალებების და მულტიმედიური სამუშაოებისთვის ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Dell Precision Laptop', 123.45,'იყიდება Dell Precision ლეპტოპი ჯერ კიდევ კარგ მდგომარეობაში შესანიშნავია პროფესიონალური მუშაობისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ASUS TUF Gaming Laptop ხელმისაწვდომია', 123.45,'იყიდება ASUS TUF სათამაშო ლეპტოპი შესანიშნავ ფორმაში იდეალურია ბიუჯეტის მცოდნე მოთამაშეებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება HP ProBook ლეპტოპი', 123.45,'ვყიდი HP ProBook ლეპტოპს იდეალურია ბიზნეს პროფესიონალებისთვის ან სტუდენტებისთვის ღია შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელმისაწვდომია Lenovo Flex ლეპტოპი', 123.45,'იყიდება Lenovo Flex ლეპტოპი იდეალურ მდგომარეობაში ვარგისია მრავალფეროვნებისა და მოქნილობისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Dell Latitude ლეპტოპი', 123.45,'იყიდება Dell Latitude ლეპტოპი ჯერ კიდევ კარგ მდგომარეობაში შესანიშნავია საქმიანი ან საგანმანათლებლო გამოყენებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ASUS Chromebook ხელმისაწვდომია', 123.45,'ვყიდი ASUS Chromebookს იდეალურია ვებდათვალიერებისთვის და ძირითადი ამოცანები ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება HP EliteBook ლეპტოპი', 123.45,'ვყიდი HP EliteBook ლეპტოპს იდეალურ მდგომარეობაში პროფესიონალი ან ძლიერი მომხმარებლებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელმისაწვდომია Lenovo Chromebook', 123.45,'იყიდება Lenovo Chromebook იდეალურია სტუდენტებისთვის ან ჩვეულებრივი მომხმარებლებისთვის ღია შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Dell Vostro ლეპტოპი', 123.45,'იყიდება Dell Vostro ლეპტოპი იდეალურ მდგომარეობაში განკუთვნილია მცირე ბიზნესის მფლობელებისთვის ან მეწარმეებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ASUS Transformer Book ხელმისაწვდომია', 123.45,'იყიდება ASUS Transformer Book ჯერ კიდევ კარგ მდგომარეობაში შესანიშნავია პორტაბელურობისა და მრავალფეროვნებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება მუსიკალური ფესტივალის საშვი', 123.45,'ვყიდი ერთი მუსიკალური ფესტივალის საშვი აღარ შემიძლია დასწრება ღიაა შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება კომედი შოუს ბილეთი', 123.45,'იუმორისტული შოუს ერთი ბილეთის გაყიდვა სამწუხაროდ ვეღარ წავა ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'თეატრალური სპექტაკლის ბილეთი ხელმისაწვდომია', 123.45,'თეატრის სპექტაკლზე ერთი ბილეთის გაყიდვა აღარ შემიძლია სამართლიან ფასად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება სპორტული ღონისძიების ბილეთი', 123.45,'სპორტულ ღონისძიებაზე ერთი ბილეთის გაყიდვა სამწუხაროდ აღარ შეიძლება ღიაა შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელოვნების გამოფენის საშვი ხელმისაწვდომია', 123.45,'ერთი საშვის გაყიდვა ხელოვნების გამოფენაზე აღარ შემიძლია ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება საცეკვაო სპექტაკლის ბილეთი', 123.45,'საცეკვაო სპექტაკლზე ერთი ბილეთის გაყიდვა სამწუხაროდ ვეღარ ვესწრები ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ფილმის ჩვენების ბილეთი ხელმისაწვდომია', 123.45,'ფილმის ჩვენებაზე ერთი ბილეთის გაყიდვა აღარ შემიძლია გონივრულ ფასად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება სტენდაპ კომედი შოუს ბილეთი', 123.45,'ვყიდი სთენდაპ კომედი შოუს ერთი ბილეთს სამწუხაროდ ვეღარ  ღიაა შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Open Airის  დღიანი ბილეთი', 123.45,'იყიდება ოუფენ ეარის  დღიანი ბილეთი ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება Open Airის მეორე დღის ბილეთი', 123.45,'ოუფენის მეორე დღის ბილეთი საკმაოდ იაფად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ოუფენ ეარი პირველი დღის ბილეთი', 123.45,'Open Air პირველი დღის ბილეთი დაბალ ფასებში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'Open Air მესამე დღის ბილეთი', 123.45,'ოუფენის ეარი  მესამე დღის ბილეთი საკმაოდ იაფად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'Open Air პირველი დღის ბილეთი', 123.45,'Open Airის პირველი დღის ბილეთი შეღავათიან ფასად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'კომედი შოუს ბილეთი იყიდება', 123.45,'იუმორისტული შოუს ერთი ბილეთის გაყიდვა სამწუხაროდ ვეღარ წავა ღიაა შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'თეატრალური სპექტაკლის ბილეთი ხელმისაწვდომია', 123.45,'თეატრის სპექტაკლზე ერთი ბილეთის გაყიდვა აღარ შემიძლია ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება სპორტული ღონისძიების ბილეთი', 123.45,'სპორტულ ღონისძიებაზე ერთი ბილეთის გაყიდვა სამწუხაროდ აღარ შეიძლება ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელოვნების გამოფენის საშვი ხელმისაწვდომია', 123.45,'ერთი საშვის გაყიდვა ხელოვნების გამოფენაზე აღარ შემიძლია გონივრულ ფასად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება საცეკვაო სპექტაკლის ბილეთი', 123.45,'საცეკვაო სპექტაკლზე ერთი ბილეთის გაყიდვა სამწუხაროდ ვეღარ ვესწრები ღიაა შეთავაზებებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ფილმის ჩვენების ბილეთი ხელმისაწვდომია', 123.45,'ფილმის ჩვენებაზე ერთი ბილეთის გაყიდვა აღარ შემიძლია ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება სტენდაპ კომედი შოუს ბილეთი', 123.45,'ვყიდი სტენდაპ კომედი შოუს ერთი ბილეთს სამწუხაროდ ვეღარ ვესწრები ფასი შეთანხმებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სასწავლო სახელმძღვანელოები იყიდება', 123.45,'იყიდება სხვადასხვა საგნის მეორადი სახელმძღვანელოები კარგ მდგომარეობაში და მისაღებ ფასებში იდეალურია სტუდენტებისთვის რომლებიც ეძებენ ფულის დაზოგვას კურსის მასალებზე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ლეპტოპი იყიდება', 123.45,'იყიდება საიმედო ლეპტოპი შესაფერისი ყოველდღიური გამოყენებისა და სკოლის დავალებისთვის მოყვება ყველა საჭირო აქსესუარი და კარგ მდგომარეობაში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ბილეთები კონცერტზე', 123.45,'იყიდება პოპულარულ კონცერტზე ორი ბილეთი შესანიშნავი ადგილები და ფასდაკლებულ ფასად არ გამოტოვოთ ეს საოცარი შესაძლებლობა', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ველოსიპედი', 123.45,'იყიდება ნაზად გამოყენებული ველოსიპედი იდეალურია მგზავრობისთვის ან დასასვენებლად მოყვება საკეტი და ჩაფხუტი დამატებითი მოხერხებულობისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იძებნება ოთახის მეგობარი', 123.45,'ვეძებთ მეგობრულ და პასუხისმგებელ მეზობელს ფართო ბინის გასაზიარებლად ქირავდება ხელმისაწვდომ ფასად და კომუნალური მომსახურება შედის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'გიტარის გაკვეთილები', 123.45,'გთავაზობთ გიტარის გაკვეთილებს დამწყებთათვის არ არის საჭირო წინასწარი გამოცდილება ისწავლეთ თქვენი საყვარელი სიმღერების დაკვრა უმოკლეს დროში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'დამრიგებლური მომსახურება', 123.45,'გთავაზობთ რეპეტიტორულ მომსახურებას სხვადასხვა საგნებზე თქვენს საჭიროებებზე მორგებული გააუმჯობესე შენი ქულები და მოიპოვე ნდობა სწავლაში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ფიტნეს აღჭურვილობა', 123.45,'იყიდება ჰანტელების ნაკრები იდეალურია სახლში ვარჯიშისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ხატვის მასალები', 123.45,'იყიდება ხელოვნების მასალების კოლექცია მათ შორის საღებავები ფუნჯები და ტილოები იდეალურია დამწყები მხატვრებისთვის ან ჰობისტებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'კალკულუსის მასწავლებელი', 123.45,'ჩავატარებ ალგებრის აზმათის კალკულუსის გაკვეთილებს მოქნილი საათები და გონივრული ტარიფები', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ტელეფონის დამტენი', 123.45,'იყიდება ტელეფონის სრულიად ახალი დამტენი თავსებადი სხვადასხვა მოდელებთან აღარასოდეს დასრულდეს ბატარეა', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სასწავლო ჯგუფი', 123.45,'სასწავლო ჯგუფის შექმნა რთული კურსისთვის ითანამშრომლეთ თანატოლებთან და ერთად გაიარეთ გამოცდები', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ქირავდება ოთახი', 123.45,'ქირავდება ფართო ოთახი სტუდენტურ უბანში კამპუსთან და ყველა კეთილმოწყობასთან ახლოს', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იოგას გაკვეთილები', 123.45,'გთავაზობთ დამწყებთათვის იოგას გაკვეთილებს იდეალურია დასვენებისა და სტრესის შესამსუბუქებლად გააუმჯობესეთ თქვენი მოქნილობა და საერთო კეთილდღეობა', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება კომპიუტერის მონიტორი', 123.45,'იყიდება მაღალი ხარისხის კომპიუტერის მონიტორი იდეალურ მდგომარეობაში გაზარდეთ თქვენი პროდუქტიულობა უფრო დიდი ეკრანით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'გრაფიკული დიზაინის სერვისები', 123.45,'გთავაზობთ პროფესიონალური გრაფიკული დიზაინის სერვისებს ლოგოების პლაკატებისა და სოციალური მედიის შინაარსისთვის ხელმისაწვდომი ტარიფები და სწრაფი შემობრუნების დრო', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება სასწავლო მაგიდა', 123.45,'იყიდება მტკიცე სასწავლო მაგიდა იდეალურია სტუდენტებისთვის კომპაქტური და ფუნქციონალური დიზაინი დიდი შენახვის ადგილით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ძაღლების გასეირნების სერვისები', 123.45,'შესაძლებელია თქვენი ბეწვიანი მეგობრის გასეირნება სანამ დაკავებული ხართ სანდო და გამოცდილი ძაღლების მოსიარულე გთავაზობთ ხელმისაწვდომ ფასებს', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'კულინარიის გაკვეთილები', 123.45,'გთავაზობთ კულინარიის გაკვეთილებს დამწყებთათვის ისწავლეთ გემრიელი კერძების მომზადება ბიუჯეტში შთაბეჭდილება მოახდინეთ თქვენს მეგობრებზე და ოჯახის წევრებზე თქვენი კულინარიული უნარებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ყურსასმენები', 123.45,'იყიდება მაღალი ხარისხის ყურსასმენები იდეალურია მუსიკის მოსასმენად ან მშვიდად სწავლისთვის ხმის შესანიშნავი ხარისხი ხელმისაწვდომ ფასად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ფოტოგრაფიის სერვისები', 123.45,'გთავაზობთ პროფესიონალურ ფოტოგრაფიის სერვისებს ღონისძიებებისა და პორტრეტებისთვის აღბეჭდეთ თქვენი განსაკუთრებული მომენტები მაღალი ხარისხის ფოტოებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება მცენარე', 123.45,'ვყიდი დაბალი მოვლაპატრონობის შიდა ქარხანას იდეალურია შენს საცხოვრებელ სივრცეში გამწვანების დასამატებლად მოყვება მოვლის ინსტრუქცია', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ფიტნეს კლასები', 123.45,'გთავაზობთ ჯგუფურ ფიტნეს კლასებს შესაფერისი ყველა ფიტნეს დონისთვის ჩადექით ფორმაში და გაერთეთ დამხმარე გარემოში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება მიკროტალღური ღუმელი', 123.45,'იყიდება კომპაქტური მიკროტალღური ღუმელი იდეალურია კერძების ან საჭმლის გასათბობად კარგ მუშა მდგომარეობაში და მისაღებ ფასად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'წერითი მომსახურება', 123.45,'ესეების და დავალებების პროფესიონალური წერის სერვისების შეთავაზება გააუმჯობესეთ თქვენი წერის უნარი და მიიღეთ უკეთესი შეფასებები', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სასწავლო შენიშვნები იყიდება', 123.45,'გაყიდვა ყოვლისმომცველი სასწავლო ჩანაწერები სხვადასხვა საგნებისთვის შედგენილი საუკეთესო სტუდენტის მიერ გაიარეთ გამოცდები ამ სასარგებლო რესურსებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ფიტნეს ტრეკერი საათი', 123.45,'იყიდება ფიტნეს ტრეკერი საათი აკონტროლებს ნაბიჯებს დაწვულ კალორიებს და ძილის რეჟიმს იყავი მოტივირებული და მიაღწიე ფიტნეს მიზნებს', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'კალკულუსის საშინაო დავალების დახმარება', 123.45,'გთავაზობთ მათემატიკის აზმათის კალკულუსის დისკრეტულის საშინაო დავალების დახმარებას', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება პორტატული დინამიკი', 123.45,'იყიდება პორტატული დინამიკი იდეალურია მუსიკის მოსასმენად კომპაქტური და ხმის შესანიშნავი ხარისხით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'რეზიუმეს წერის სერვისები', 123.45,'გთავაზობთ პროფესიონალურ რეზიუმეს წერის სერვისებს მორგებული თქვენს უნარებსა და გამოცდილებაზე გამოირჩეოდე კონკურენციიდან და მიიღე შენი ოცნების სამუშაო', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'მცენარეთა მოვლის სერვისები', 123.45,'ხელმისაწვდომია თქვენი მცენარეების მოვლისთვის სანამ არ ხართ მორწყვა გასხვლა და მათი აყვავების უზრუნველყოფა თქვენს არყოფნაში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სასწავლო ოთახის დაჯავშნა', 123.45,'ქირავდება კერძო სასწავლო ოთახი ბიბლიოთეკაში რომელიც აღჭურვილია მერხით და კომფორტული ჯდომით იდეალურია ორიენტირებული სწავლისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ფიტნეს ტანსაცმელი', 123.45,'იყიდება ნაზად გამოყენებული ფიტნეს ტანსაცმლის კოლექცია მათ შორის სავარჯიშო ტანსაცმელი და სპორტული ფეხსაცმელი იყავით ელეგანტური და იყავით აქტიური', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ენის სწავლება', 123.45,'გთავაზობთ ენის სწავლების სერვისებს დამწყებთათვის საშუალო და მოწინავე დონეზე გაიუმჯობესეთ თქვენი ენის ცოდნა და მოიპოვეთ სრულყოფილება', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'გრაფიკული დიზაინის კომისია', 123.45,'გთავაზობთ მორგებული გრაფიკული დიზაინის სერვისებს ლოგოების პლაკატებისა და ბრენდინგისთვის გააცოცხლეთ თქვენი ხედვა პროფესიონალური დიზაინით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სახელმძღვანელოების გაცვლა', 123.45,'ვეძებ სახელმძღვანელოების გაცვლას თანაკლასელებთან დაზოგეთ ფული შემდეგი სემესტრის წიგნების გაცვლით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ტელეფონების შეკეთების სერვისები', 123.45,'გთავაზობთ ტელეფონის შეკეთების სერვისებს დაბზარული ეკრანებისთვის ბატარეის გამოცვლისა და პროგრამული უზრუნველყოფის პრობლემებისთვის სწრაფი და საიმედო მომსახურება მისაღებ ფასებში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სამხატვრო კომისია', 123.45,'გთავაზობთ საბაჟო ხელოვნების კომისიებს მათ შორის პორტრეტებს და ილუსტრაციებს დააფიქსირეთ თქვენი მოგონებები ან გააცოცხლეთ თქვენი იდეები', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სტანდარტიზებული ტესტების დამრიგებლობა', 123.45,'გთავაზობთ რეპეტიტორულ სერვისებს სტანდარტიზებული ტესტებისთვის როგორიცაა SAT ACT და GRE გააუმჯობესე შენი ქულები და გაზარდე დაშვების შანსები', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ავეჯი', 123.45,'იყიდება სხვადასხვა სახის ავეჯი მათ შორის მაგიდა სკამი და წიგნების თარო ხელმისაწვდომი ფასები და კარგ მდგომარეობაში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ძაღლების მომზადების სერვისები', 123.45,'გამოცდილი ძაღლების მწვრთნელი გთავაზობთ მორჩილების ვარჯიშს და ქცევის მოდიფიკაციას დაეხმარეთ თქვენს ბეწვიან მეგობარს გახდეს კარგად მოქცეული კომპანიონი', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სწავლის საზღვარგარეთ შესაძლებლობები', 123.45,'ინფორმაციისა და ხელმძღვანელობის მიწოდება საზღვარგარეთ სწავლის პროგრამებზე გამოიკვლიეთ ახალი კულტურები და გააფართოვეთ თქვენი ჰორიზონტები', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'კომპიუტერის შეკეთების სერვისი', 123.45,'გთავაზობთ კომპიუტერის შეკეთების სერვისებს ტექნიკისა და პროგრამული უზრუნველყოფის პრობლემებისთვის განაახლეთ თქვენი კომპიუტერი და შეუფერხებლად იმუშაოთ', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ფიტნეს ბუტკემპი', 123.45,'შეუერთდით მაღალი ინტენსივობის ფიტნეს ბუტკემპს რათა გამოწვეთ საკუთარი თავი და მიაღწიოთ ფიტნეს მიზნებს სერტიფიცირებული ტრენერების ხელმძღვანელობით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ოთახის დეკორი', 123.45,'იყიდება ოთახის დეკორაციის სხვადასხვა ნივთები მათ შორის პლაკატები ზღაპრული განათებები და კედლის მხატვრობა მოახდინეთ თქვენი საცხოვრებელი ფართის პერსონალიზაცია ბიუჯეტით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სასწავლო სახელმძღვანელოები იყიდება', 123.45,'იყიდება სხვადასხვა საგნის მეორადი სახელმძღვანელოები კარგ მდგომარეობაში ხელმისაწვდომი ფასები თანაკლასელებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ლეპტოპი იყიდება', 123.45,'იყიდება საიმედო ლეპტოპი იდეალურია სასწავლო და ყოველდღიური გამოყენებისთვის მოყვება დამტენი და არის კარგ მდგომარეობაში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ველოსიპედი', 123.45,'იყიდება მეორადი ველოსიპედი რომელიც შესაფერისია მგზავრობისთვის ან დასასვენებლად კარგ მდგომარეობაში რეგულირებადი სავარძლით და მუშა მუხრუჭებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ტუტორინგის მომსახურება', 123.45,'ინდივიდუალური საჭიროებებზე მორგებული რეპეტიტორული სერვისების შეთავაზება სხვადასხვა საგნებზე როგორიცაა მათემატიკა', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'გიტარის გაკვეთილები', 123.45,'გთავაზობთ გიტარის გაკვეთილებს დამწყებთათვის არ არის საჭირო წინასწარი გამოცდილება მომთმენი და მგზნებარე ინსტრუქტორი მოქნილი განრიგი', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება სამაგიდო სკამი', 123.45,'იყიდება კომფორტული სამაგიდო სკამი იდეალურია ხანგრძლივი სასწავლო სესიებისთვის რეგულირებადი სიმაღლე და კარგ მდგომარეობაში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება მათემატიკის სახელმძღვანელო', 123.45,'იყიდება მეორადი მათემატიკის სახელმძღვანელო შესაფერისი კოლეჯის დონის კურსებისთვის კარგ მდგომარეობაში ხელმისაწვდომი ფასი', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'გრაფიკული დიზაინის სერვისები', 123.45,'გთავაზობთ გრაფიკული დიზაინის სერვისებს პროექტებისა და დავალებების შესასრულებლად კრეატიული და პროფესიონალური დიზაინი სწრაფი შემობრუნება', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება პრინტერი', 123.45,'იყიდება პრინტერი იდეალურია დავალებების და დოკუმენტების დასაბეჭდად კარგ მდგომარეობაში მოყვება მელნის კარტრიჯები', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ინგლისური სწავლება', 123.45,'გთავაზობთ ინგლისური ენის სწავლებას არა მშობლიურ ენაზე მომთმენი და გამოცდილი დამრიგებელი ფოკუსირება გრამატიკისა და საუბრის უნარებზე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ყავის მადუღარა', 123.45,'იყიდება ყავის მადუღარა იდეალურია დილით ადრე სასწავლო სესიებისთვის კარგ მუშა მდგომარეობაში მარტივი გამოსაყენებელი', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ფიტნეს აღჭურვილობა', 123.45,'იყიდება სხვადასხვა ფიტნეს აღჭურვილობა იდეალურია სახლში ვარჯიშისთვის ხელმისაწვდომი ფასები', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ხელოვნების გაკვეთილები', 123.45,'გთავაზობთ ხელოვნების გაკვეთილებს დამწყებთათვის არ არის საჭირო წინასწარი გამოცდილება მომთმენი და მგზნებარე ინსტრუქტორი მოქნილი განრიგი', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება მინი მაცივარი', 123.45,'იყიდება მინი მაცივარი იდეალურია საერთო საცხოვრებლის ან პატარა ფართებისთვის კარგ მუშა მდგომარეობაში კომპაქტური ზომებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ესპანური ენის რეპეტიტორობა', 123.45,'გთავაზობთ ესპანურის სწავლებას დამწყებთათვის მომთმენი და გამოცდილი დამრიგებელი ფოკუსირება ლექსიკაზე და საუბრის უნარებზე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება მაგიდა', 123.45,'იყიდება გამძლე მაგიდა იდეალურია სწავლისა და ორგანიზებისთვის კარგ მდგომარეობაში ფართო ზედაპირით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სამეცნიერო სახელმძღვანელოები იყიდება', 123.45,'იყიდება მეორადი საბუნებისმეტყველო სახელმძღვანელოები სხვადასხვა საგნისთვის კარგ მდგომარეობაში ხელმისაწვდომი ფასები თანაკლასელებისთვის', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ფოტოგრაფიის სერვისები', 123.45,'გთავაზობთ ფოტოგრაფიის სერვისებს ღონისძიებებისა და პორტრეტებისთვის კრეატიული და პროფესიონალური კადრები ხელმისაწვდომი ფასები', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ტოსტერი', 123.45,'იყიდება ტოსტერი იდეალურია სწრაფი საუზმისთვის კარგ მუშა მდგომარეობაში მარტივი გამოსაყენებელი', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება სამაგიდო ნათურა', 123.45,'იყიდება მაგიდის ნათურა იდეალურია გვიან ღამით სწავლისთვის კარგ მუშა მდგომარეობაში რეგულირებადი სიკაშკაშე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ქიმიის რეპეტიტორობა', 123.45,'გთავაზობთ ქიმიის სწავლებას საშუალო სკოლისა და კოლეჯის სტუდენტებისთვის პაციენტი და გამოცდილი დამრიგებელი ფოკუსირება ცნებებზე და პრობლემის გადაჭრაზე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'პუფი იყიდება', 123.45,'იყიდება კომფორტული პუფები იდეალურია სტუმრების დასასვენებლად და მასპინძლობისთვის კარგ მდგომარეობაში', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება კომპიუტერის მონიტორი', 123.45,'იყიდება კომპიუტერის მონიტორი იდეალურია ორმაგი ეკრანის დასაყენებლად კარგ მუშა მდგომარეობაში თავსებადია უმეტეს კომპიუტერებთან', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'მუსიკის გაკვეთილები', 123.45,'მუსიკის გაკვეთილების შეთავაზება დამწყებთათვის არ არის საჭირო წინასწარი გამოცდილება მომთმენი და მგზნებარე ინსტრუქტორი მოქნილი განრიგი', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება სასწავლო მაგიდა', 123.45,'იყიდება სასწავლო მაგიდა იდეალურია ორიენტირებული სამუშაო სესიებისთვის კარგ მდგომარეობაში სათავსოებით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ფიზიკის რეპეტიტორობა', 123.45,'გთავაზობთ ფიზიკის სწავლებას საშუალო სკოლისა და კოლეჯის სტუდენტებისთვის პაციენტი და გამოცდილი დამრიგებელი ფოკუსირება ცნებებზე და პრობლემის გადაჭრაზე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება თეთრი დაფა', 123.45,'იყიდება დაფა იდეალურია იდეების ვიზუალიზაციისთვის და სწავლისთვის კარგ მდგომარეობაში მოყვება მარკერები და საშლელი', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება იოგას ხალიჩა', 123.45,'იყიდება იოგას ხალიჩა იდეალურია სახლში ვარჯიშისთვის და დასვენებისთვის კარგ მდგომარეობაში კომფორტული და არ მოცურების ზედაპირი', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ფრანგული რეპეტიტორობა', 123.45,'გთავაზობთ ფრანგულის სწავლებას დამწყებთათვის მომთმენი და გამოცდილი დამრიგებელი ფოკუსირება ლექსიკაზე და საუბრის უნარებზე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება პორტატული დინამიკი', 123.45,'იყიდება პორტატული დინამიკი იდეალურია მუსიკის მოსასმენად კარგ მუშა მდგომარეობაში თავსებადია უმეტეს მოწყობილობებთან', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'წერის რედაქტირების სერვისები', 123.45,'გთავაზობთ წერილობითი რედაქტირების სერვისებს ესეებისა და ნაშრომებისთვის დეტალებისადმი ყურადღება და კონსტრუქციული გამოხმაურება', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება სამზარეულოს ჭურჭელი', 123.45,'იყიდება სამზარეულოს ჭურჭლის ნაკრები იდეალურია სამზარეულოსთვის და გამოცხობისთვის კარგ მდგომარეობაში მოყვება სხვადასხვა იარაღები', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ძაღლების გასეირნების სერვისები', 123.45,'გთავაზობთ ძაღლების გასეირნების მომსახურებას დაკავებული სტუდენტებისთვის სანდო და მზრუნველი მოქნილი განრიგი', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება პორტატული დამტენი', 123.45,'იყიდება პორტატული დამტენი იდეალურია მოწყობილობების დასატენად მოძრაობაში კარგ მუშა მდგომარეობაში თავსებადია უმეტეს მოწყობილობებთან', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ისტორიის რეპეტიტორობა', 123.45,'გთავაზობთ ისტორიის სწავლებას საშუალო სკოლისა და კოლეჯის სტუდენტებისთვის პაციენტი და გამოცდილი დამრიგებელი ფოკუსირება ძირითად მოვლენებსა და კონცეფციებზე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება მაგიდის ორგანიზატორი', 123.45,'იყიდება მაგიდის ორგანიზატორი იდეალურია სასწავლო მასალისა და საკანცელარიო ნივთების ორგანიზებაში შესანახად კარგ მდგომარეობაში რამდენიმე კუპე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'სასწავლო ოთახი ხელმისაწვდომია', 123.45,'ქირავდება სასწავლო ოთახი ინდივიდუალური ან ჯგუფური გამოყენებისთვის მყუდრო და ხელსაყრელი გარემო აღჭურვილი საჭირო ტექნიკით', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება პორტატული მყარი დისკი', 123.45,'იყიდება პორტატული მყარი დისკი იდეალურია ფაილების შესანახად და გადასატანად კარგ მუშა მდგომარეობაში დიდი ტევადობა', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'კულინარიის გაკვეთილები', 123.45,'გთავაზობთ სამზარეულოს გაკვეთილებს დამწყებთათვის ისწავლეთ ძირითადი რეცეპტები და ტექნიკა პრაქტიკული გამოცდილება', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება სასწავლო ნათურა', 123.45,'იყიდება სასწავლო ნათურა იდეალურია გვიან ღამით სწავლისთვის კარგ მუშა მდგომარეობაში რეგულირებადი სიკაშკაშე და კუთხე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'ეკონომიკის რეპეტიტორობა', 123.45,'გთავაზობთ ეკონომიკის სწავლებას საშუალო სკოლისა და კოლეჯის სტუდენტებისთვის პაციენტი და გამოცდილი დამრიგებელი ფოკუსირება ძირითად ცნებებზე და პრობლემის გადაჭრაზე', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'იყიდება ავეჯის კომპლექტი', 123.45,'იყიდება ავეჯის კომპლექტი საწოლის ჩარჩოს ლეიბის და კომოდის ჩათვლით კარგ მდგომარეობაში სტუდენტებისთვის ხელმისაწვდომ ფასად', '2023-11-05', 'images/1P1.png');

    INSERT INTO posts (profile_id, title, price, description , publish_date, main_photo)
    VALUES (1, 'რეზიუმეს წერის სერვისები', 123.45,'გთავაზობთ სამუშაოს მაძიებლებს რეზიუმეების დაწერის სერვისებს პროფესიონალური და მორგებული რეზიუმეები ხაზს უსვამს უნარებსა და გამოცდილებას', '2023-11-05', 'images/1P1.png');

    -- Add images
    -- Image 1
    INSERT INTO photos (post_id, photo_url)
    VALUES (1, 'images/1P1.png');
    -- Image 2
    INSERT INTO photos (post_id, photo_url)
    VALUES (2, 'images/2P1.png');
    -- Image 3
    INSERT INTO photos (post_id, photo_url)
    VALUES (2, 'images/2P2.png');
    -- Image 4
    INSERT INTO photos (post_id, photo_url)
    VALUES (3, 'images/3P1.png');
    -- Image 5
    INSERT INTO photos (post_id, photo_url)
    VALUES (3, 'images/3P2.png');
    -- Image 6
    INSERT INTO photos (post_id, photo_url)
    VALUES (4, 'images/4P1.png');
    -- Image 7
    INSERT INTO photos (post_id, photo_url)
    VALUES (5, 'images/5P1.png');
    -- Image 8
    INSERT INTO photos (post_id, photo_url)
    VALUES (6, 'images/6P1.png');
    -- Image 9
    INSERT INTO photos (post_id, photo_url)
    VALUES (7, 'images/7P1.png');
    -- Image 10
    INSERT INTO photos (post_id, photo_url)
    VALUES (7, 'images/7P2.png');
    -- Image 11
    INSERT INTO photos (post_id, photo_url)
    VALUES (8, 'images/8P1.png');
    -- Image 12
    INSERT INTO photos (post_id, photo_url)
    VALUES (8, 'images/8P2.png');
    -- Image 13
    INSERT INTO photos (post_id, photo_url)
    VALUES (8, 'images/8P3.png');
    
    INSERT INTO photos (post_id, photo_url)
    VALUES (9, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (10, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (11, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (12, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (13, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (14, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (15, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (16, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (17, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (18, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (19, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (20, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (21, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (22, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (23, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (24, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (25, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (26, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (27, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (28, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (29, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (30, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (31, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (32, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (33, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (34, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (35, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (36, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (37, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (38, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (39, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (40, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (41, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (42, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (43, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (44, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (45, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (46, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (47, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (48, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (49, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (50, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (51, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (52, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (53, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (54, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (55, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (56, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (57, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (58, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (59, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (60, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (61, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (62, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (63, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (64, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (65, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (66, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (67, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (68, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (69, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (70, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (71, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (72, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (73, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (74, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (75, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (76, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (77, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (78, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (79, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (80, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (81, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (82, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (83, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (84, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (85, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (86, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (87, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (88, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (89, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (90, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (91, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (92, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (93, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (94, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (95, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (96, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (97, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (98, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (99, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (100, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (101, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (102, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (103, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (104, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (105, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (106, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (107, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (108, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (109, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (110, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (111, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (112, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (113, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (114, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (115, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (116, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (117, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (118, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (119, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (120, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (121, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (122, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (123, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (124, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (125, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (126, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (127, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (128, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (129, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (130, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (131, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (132, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (133, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (134, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (135, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (136, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (137, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (138, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (139, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (140, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (141, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (142, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (143, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (144, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (145, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (146, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (147, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (148, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (149, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (150, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (151, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (152, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (153, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (154, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (155, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (156, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (157, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (158, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (159, 'images/1P1.png');

    INSERT INTO photos (post_id, photo_url)
    VALUES (160, 'images/1P1.png');


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
    INSERT INTO filters (post_id, filter_text)
    VALUES (9, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (10, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (11, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (12, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (13, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (14, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (15, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (16, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (17, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (18, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (19, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (20, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (21, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (22, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (23, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (24, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (25, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (26, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (27, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (28, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (29, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (30, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (31, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (32, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (33, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (34, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (35, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (36, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (37, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (38, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (39, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (40, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (41, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (42, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (43, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (44, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (45, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (46, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (47, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (48, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (49, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (50, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (51, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (52, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (53, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (54, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (55, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (56, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (57, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (58, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (59, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (60, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (61, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (62, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (63, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (64, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (65, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (66, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (67, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (68, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (69, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (70, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (71, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (72, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (73, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (74, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (75, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (76, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (77, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (78, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (79, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (80, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (81, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (82, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (83, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (84, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (85, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (86, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (87, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (88, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (89, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (90, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (91, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (92, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (93, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (94, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (95, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (96, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (97, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (98, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (99, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (100, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (101, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (102, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (103, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (104, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (105, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (106, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (107, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (108, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (109, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (110, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (111, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (112, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (113, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (114, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (115, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (116, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (117, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (118, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (119, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (120, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (121, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (122, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (123, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (124, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (125, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (126, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (127, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (128, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (129, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (130, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (131, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (132, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (133, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (134, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (135, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (136, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (137, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (138, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (139, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (140, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (141, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (142, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (143, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (144, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (145, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (146, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (147, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (148, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (149, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (150, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (151, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (152, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (153, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (154, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (155, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (156, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (157, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (158, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (159, 'tech');

    INSERT INTO filters (post_id, filter_text)
    VALUES (160, 'tech');
