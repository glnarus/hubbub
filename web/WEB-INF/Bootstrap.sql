--remove foreign links so we can drop the tables 
Alter Table JAVAUSER.PROFILES DROP userid;
Alter Table JAVAUSER.USERS DROP profileId;

DROP TABLE Posts;
DROP TABLE Profiles;
DROP TABLE Users;

--must create users first since you don't have to specify a profileId
CREATE TABLE Users (
    username VARCHAR(12) NOT NULL UNIQUE,
    password VARCHAR(15) NOT NULL,
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profileId INT
);

--create profiles second, then after created put the key in the user dbase row
CREATE TABLE Profiles (
    joindate DATE DEFAULT CURRENT_DATE,
    firstname VARCHAR(20) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    email VARCHAR(100) NOT NULL,
    zip VARCHAR(10) NOT NULL,
    userid INT NOT NULL,
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY
);

Alter Table JAVAUSER.PROFILES
Add FOREIGN KEY (USERID)
References JAVAUSER.USERS (id);  

--setup foreign key relationship now that Profiles exists
Alter Table JAVAUSER.USERS
Add FOREIGN KEY (profileId)
References JAVAUSER.Profiles (id);

CREATE TABLE Posts (
    content VARCHAR(140) NOT NULL,
    authorid INT NOT NULL,
    postdate DATE DEFAULT CURRENT_DATE,
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY
);

Alter Table JAVAUSER.Posts
Add FOREIGN KEY (authorid)
References JAVAUSER.USERS (id);

INSERT INTO Users (username, password) VALUES
    ('johndoe', 'password'),
    ('jilljack', 'password');

INSERT INTO Profiles (joindate, firstname, lastname, email, zip, userid) VALUES
    ('2013-05-09', 'John', 'Doe', 'jd@example.com', '98008',1),
    ('2013-10-31', 'Jill', 'Jack', 'jj@nowhere.com', '24201',2);

UPDATE USERS SET profileId=1 WHERE id=1;
UPDATE USERS SET profileId=2 WHERE id=2;

INSERT INTO Posts (content, authorid, postdate) VALUES
    ('I''m a white-hat hacking my wonky Twonky server.', 1, '2013-05-09'),
    ('My wonky Twonky server conked out.', 1, '2014-06-23'),
    ('I see good reason not to configure Twonky.', 2, '2013-11-01');
