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
values ('Java', 30, '00:30:00'),
('Python', 50, '00:40:00'),
('PHP', 20, '00:30:00'),
('C++', 45, '01:00:00'),
('SQL', 15, '00:15:00');

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
value (8, 'html nima', 'bilmayman', 'dasturlash tili', 'vs code', 'morkap til'),
	(8, 'br tegning vazifasi nima', 'rasm qoyish', 'button', 'text yozish', 'enter tashlash'),
	(8, 'h1 tegning vazifasi nima', 'kodni korish', 'chiziq chizish', 'hech narsa', 'text yozish'),
    (1, 'java nima', 'bilmayman', 'virtual mashina', 'intelliJ idea', 'dasturlash tili'),
    (1, 'int qanday tipga kiradi', 'bilmayman', 'satr', 'kast', 'butun son'),
    (1, 'JavaFX da nima yaratiladi', 'bilmayman', 'web app', 'mobile app', 'desktop app'),
    (2, 'Pythonni kim yaratgan', 'bilmayman', 'stiv jobs', 'bill gates', 'Guido Rossum'),
    (2, 'Python qanaqa til', 'bilmayman', 'yuqori', 'vs code', 'quyi'),
    (2, 'Python ilon turimi', 'bilmayman', 'hammasi togri', 'yoq', 'ha');


select count(*) from questions where SubjectId = 8;

SELECT Variant1, Variant2, Variant3, Correct_answer FROM questions WHERE SubjectId = 8;




