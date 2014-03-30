use Evoting

create table Users
(
	id int primary key identity(1,1),
	fbId varchar(100) unique
);

create table Elections
(
	id int primary key identity(1,1),
	name varchar(50) not null,
	descript varchar(512),
	openState tinyint,   
	creatorId int foreign key references Users(id),
	publicKey varchar(max) 
); 

create table ElectionTrustees
(
	id int primary key identity(1,1),
	electId int foreign key references Elections(id),
	trusteeId int,
	email varchar(50),
	isGenerated tinyint,
	publicKey varchar(max),
	token varchar(50)
);

create table ElectionAnswers
(
	electId int foreign key references Elections(id),
	answerId int not null,
	answer varchar(50) not null
);

create table ElectionVoters
(
	electId int foreign key references Elections(id),
	voterType tinyint,
	voterId varchar(100)
);

create table ElectionVotes
(
	electId int foreign key references Elections(id),
	userId int foreign key references Users(id),
	votedBallot tinyint,
	decodedSequence varchar(1024),
	encodedSequence varchar(1024),
	answerId int
);

create table TempTrustees
(
	id int primary key identity(1,1),
	electId int foreign key references Elections(id),
	email varchar(50)
);

create table UserGroups
(
	userId int foreign key references Users(id),
	groupId varchar(100)
);


drop table TempTrustees
drop table ElectionVoters
drop table ElectionAnswers
drop table ElectionTrustees
drop table Elections
drop table Users

insert into Users(fbId) values ('123')

insert into ElectionTrustees (electId,trusteeId,email,isGenerated) values(2,'','aaa',0)

select * from Users   
select * from ElectionTrustees
select * from UserGroups

select * from Elections
update Elections 
set openState = 1, descript = 'this is election description'
where id = 2


select * from ElectionTrustees

select * from ElectionVotes

select publicKey 
from ElectionTrustees 
where electId = 3

alter table Elections
add publicKey varchar(max)