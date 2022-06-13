create database testbook;
use testbook;

select * from subjects;
select * from questions;

create table subjects (
	SubjectId int not null auto_increment,
    Name varchar(255),
    Test_amount int,
    Duration time,
    primary key(subjectId)
);

insert into subjects(Name, Test_amount, Duration)
value ('HTML', 30, '00:50:00');

drop table questions;

create table questions (
	QuestionsId int not null auto_increment,
    SubjectId int,
    Question text,
    Variant1 varchar(255),
    Variant2 varchar(255),
    Variant3 varchar(255),
    Correct_answer varchar(255),
    primary key (QuestionsId),
    foreign key (SubjectId) references subjects(SubjectId)
);


truncate table questions;

insert into questions(SubjectId, Question, variant1, variant2, variant3, correct_answer)
value (8, 'html nima', 'zor', 'yomon', 'yaxwi', 'morkap til');


SELECT Variant1, Variant2, Variant3, Correct_answer FROM questions WHERE SubjectId = 8;




