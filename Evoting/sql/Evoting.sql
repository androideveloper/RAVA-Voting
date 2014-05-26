use Evoting

create table Users
(
	id int primary key identity(1,1),
	fbId varchar(100) unique,
	email varchar(50),
);

create table UserGroups
(
	userId int foreign key references Users(id),
	groupId varchar(100),
	name varchar(1024)
);

create table Elections
(
	id int primary key identity(1,1),
	name varchar(512) not null,
	descript varchar(4096),
	openState tinyint,   
	creatorId int foreign key references Users(id),
	publicKey varchar(max),
	mixStage int 
); 

create table ElectionTrustees
(
	id int primary key identity(1,1),
	electId int foreign key references Elections(id),
	userId int,
	email varchar(50),
	isGenerated bit,
	publicKey varchar(max),
	token varchar(50),
	isDecoded bit, 
	mixDone bit,
	mixServer int
);

create table ElectionAnswers
(
	electId int foreign key references Elections(id),
	answerId int not null,
	answer varchar(100) not null,
	numberOfVotes int default(0)
);

create table ElectionVoters
(
	electId int foreign key references Elections(id),
	voterType tinyint,
	voterId varchar(100)
);

create table ElectionVotes
(
	id  int primary key identity(1,1),
	electId int foreign key references Elections(id),
	userId int foreign key references Users(id),
	auditBallot tinyint,
	auditSequence varchar(256),
	encoded1 varchar(1024),--collate SQL_Latin1_General_CP850_CS_AS,
	encoded2 varchar(1024),-- collate SQL_Latin1_General_CP850_CS_AS,
	answerId int,
	chaumPedersen varchar(max)
);

create table CutVotes
(
	id int primary key identity(1,1),
	electId int foreign key references Elections(id),
	answersSequence varchar(1024),
	answerId int,
	mixStage int 
);


drop table ElectionVoters
drop table ElectionVotes
drop table ElectionAnswers
drop table ElectionTrustees
drop table CutVotes
drop table Elections
drop table UserGroups
drop table Users



insert into Users(fbId) values ('123')

insert into ElectionTrustees (electId,trusteeId,email,isGenerated) values(2,'','aaa',0)

select * from Users   
select * from ElectionTrusteesinch
select * from UserGroups

select * from Elections
update Elections 
set openState = 1, descript = 'this is election description'
where id = 2


select * from ElectionTrustees

select * from ElectionAnswers

select * from ElectionVotes 
select * from CutVotes

select publicKey 
from ElectionTrustees 
where electId = 3

alter table Elections
add publicKey varchar(max)

delete from ElectionVotes

select * from CutVotes
