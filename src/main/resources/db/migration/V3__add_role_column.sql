alter table users add role varchar(255);
update users set role ='USER'where role is null;