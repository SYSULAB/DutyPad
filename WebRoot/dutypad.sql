drop database if exists dutypad;
create database dutypad;
use dutypad;

/*
#助理信息
s_id(学号),password给默认值防止nullpoint
power：1 普通助理，2 管理员，3 boss
on_job: 1 在职 ，0 离职
*/
drop table if exists assistant;
create table assistant (
	id int not null auto_increment,
	s_id varchar(11) default '000',
	phone varchar(17) default null,
	cornet varchar(9) default null,
	password varchar(35) default '000',
	name varchar(35) default null,
	bankcard varchar(30) default null,
	email varchar(30) default null,
	power tinyint default 1,
	on_job tinyint default 1,
	workhour double(11, 3) default 0.000,
	savehour double(11, 3) default 0.000,
	primary key(id)
)engine=innodb default charset=utf8;


/*
#签到/奖惩记录
record_type: 1 签到 , 2 奖惩
extra_work : 0 不加班 ，1 加班
remarks: 备注
todosth: 待办事项
to_do_state: 0 普通，2 强调 ，1 强调被解决
insert_time:这条记录被插入的时间
reward_punish: 1 奖励，-1 惩罚（只做记录，实际直接影响到hour_nums并且存入）
modify_man:修改人，只有奖惩才有这个属性，谁添加的奖惩记录
a_id:被修改人，或者叫被影响人
*/
drop table if exists workrecord;
create table workrecord (
	id int not null auto_increment,
	record_type tinyint default null,
	record_date date default null,
	start_time time default null,
	end_time time default null,
	classroom char(6) default null,
	extra_work boolean default 0,
	remarks text default null,
	todosth text default null,
	todo_state tinyint default 0,
	insert_time datetime default null,
	hour_nums double(11, 3) default 0.000,
	reward_punish tinyint default 1,
	modify_man varchar(11) default null,
	a_id int default null,
	primary key(id),
	key a_id(a_id),
	foreign key(a_id) references assistant(id)
)engine=innodb default charset=utf8;


/*
#每月工时的索引
insert_time ：插入时间
i_title ：标题 
modify_man ：修改人，一般是boss
*/
drop table if exists salaryindex;
create table salaryindex (
	id int not null auto_increment,
	insert_time datetime default null,
	i_title text default null,
	modify_man varchar(11) default null,
	primary key(id)
)engine=innodb default charset=utf8;


/*
#每月的每人工时记录
i_id:对应的每月工时索引
a_id:对应的助理
old_save ：上月累计
new_save ：本月累计
earn_hour ：本月工时
pay_hour ：本月支付工时
remarks：备注
*/
drop table if exists salaryrecord;
create table salaryrecord (
	id int not null auto_increment,
	i_id int default null,
	a_id int default null,
	old_save double(11, 3) default 0.000,
	new_save double(11, 3) default 0.000,
	earn_hour double(11, 3) default 0.000,
	pay_hour double(11, 3) default 0.000,
	rp_all double(11,3) default 0.000,
	remarks text default null,
	primary key(id),
	key i_id(i_id),
	key a_id(a_id),
	foreign key(a_id) references assistant(id),
	foreign key(i_id) references salaryindex(id)
)engine=innodb default charset=utf8;


/*
#上次盖戳时间
laststamp： 特地开一个table持久化上次盖戳时间
*/
drop table if exists lastwage;
create table lastwage (
	id int not null auto_increment,
	laststamp datetime default null,
	primary key(id)
)engine=innodb default charset=utf8;