CREATE DATABASE devskillspec;
USE devskillspec;
go

CREATE TABLE skills (
id int not null auto_increment,
skill_name varchar(30) not null,
status varchar(10) not null default 'ACTIVE',
primary key(id),
UNIQUE(skill_name)
);
go

CREATE TABLE  specialties (
id int not null auto_increment,
specialty_name varchar(30) not null,
status varchar(10) not null default 'ACTIVE',
primary key(id), 
UNIQUE(specialty_name)
);
go

CREATE TABLE  developers(
id int not null auto_increment,
firstName varchar(20) not null,
lastName varchar(20) not null,
specialty varchar(20) not null,
skills varchar(200) not null,
status varchar(10) not null default 'ACTIVE',
primary key(id)
);
 
