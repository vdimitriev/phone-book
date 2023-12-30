create database phonebook;
create user phonebook with encrypted password 'phonebook';
grant all privileges on database phonebook to phonebook;
alter database phonebook owner to phonebook;