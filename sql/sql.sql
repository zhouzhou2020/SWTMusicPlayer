create user SWTMusic identified by a;
grant resource,connect to SWTMusic;

create table users(
	userId integer primary key,
	photo blob, --用户头像
	userName varchar2(50) not null,
	password varchar2(40) not null,
	sex char(2) not null
);
create table lyric(
	musicName varchar2(150) primary key,
	lrc blob not null
);
alter table users add mail varchar2(15) null;

select * from user_tables;
create table song(
	songId INTEGER primary key,
	songName varchar2(100) not null, -- 歌曲名称
	artist varchar2(50) not null, --演唱者
	album varchar2(50) not null, --专辑
	totalTime INTEGER not null, --总时长
	music blob not null, --歌曲
	clickCount integer default 0 --点击次数 
);

create table songList(
	userId integer constraint fk_songList_users  references users(userId),	
	songId integer constraint fk_songList_song references song(songId)
);

delete from users;

create sequence seq_users start with 10000000;
create sequence seq_song start with 1000;

insert into USERS values(seq_users.nextval,null,'yc','a','男','2799194073');
insert into USERS values(seq_users.nextval,null,'a','a','女','12345678');
insert into USERS values(seq_users.nextval,null,'b','a','女','12345678');
insert into USERS values(seq_users.nextval,null,'c','a','男','1468437010');
insert into USERS values(seq_users.nextval,null,'d','a','女','1468437010');

alter table users modify mail varchar2(50); 
select * from USERS
select * from USERS where username='yc' and password='6f9b0a55df8ac28564cb9f63a10be8af6ab3f7c2';
UPDATE USERS set password='6f9b0a55df8ac28564cb9f63a10be8af6ab3f7c2';

delete from song;
alter table song add fileName varchar2(50) not null;

select * from song;
commit