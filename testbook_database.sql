create database testbook;
use testbook;

select * from subjects;
select * from questions;
SELECT QuestionsId, Question, Variant1, Variant2, Variant3, Correct_answer FROM questions WHERE SubjectId = 8;

create table subjects (
	SubjectId int not null auto_increment,
    Name varchar(255),
    Test_amount int,
    Duration time,
    primary key(subjectId)
);


insert into subjects(Name, Test_amount, Duration)
values ('Java', 30, '00:30:00'),
('HTML', 50, '00:40:00'),
('Python', 50, '00:40:00'),
('PHP', 20, '00:30:00'),
('C++', 45, '01:00:00'),
('SQL', 15, '00:15:00');

truncate table subjects;


create table questions (
	QuestionsId int not null auto_increment,
    SubjectId int,
    Question text,
    Variant1 varchar(255),
    Variant2 varchar(255),
    Variant3 varchar(255),
    Correct_answer varchar(255),
    primary key (QuestionsId)
);

delete from subjects where SubjectId = 11;
delete from questions where SubjectId = 9;


truncate table questions;

insert into questions(SubjectId, Question, variant1, variant2, variant3, correct_answer)
value (2, 'html nima', 'bilmayman', 'dasturlash tili', 'vs code', 'morkap til'),
	(2, 'br tegning vazifasi nima', 'rasm qoyish', 'button', 'text yozish', 'enter tashlash'),
	(2, 'h1 tegning vazifasi nima', 'kodni korish', 'chiziq chizish', 'hech narsa', 'text yozish'),
    (1, 'java nima', 'bilmayman', 'virtual mashina', 'intelliJ idea', 'dasturlash tili'),
    (1, 'int qanday tipga kiradi', 'bilmayman', 'satr', 'kast', 'butun son'),
    (1, 'JavaFX da nima yaratiladi', 'bilmayman', 'web app', 'mobile app', 'desktop app'),
    (3, 'Pythonni kim yaratgan', 'bilmayman', 'stiv jobs', 'bill gates', 'Guido Rossum'),
    (3, 'Python qanaqa til', 'bilmayman', 'yuqori', 'vs code', 'quyi'),
    (3, 'Python ilon turimi', 'bilmayman', 'hammasi togri', 'yoq', 'ha');


select count(*) from questions where SubjectId = 8;

SELECT Variant1, Variant2, Variant3, Correct_answer FROM questions WHERE SubjectId = 8;




