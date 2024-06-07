create database if not exists poollearn;
use poollearn;
create table if not exists random_strings(
  id bigint not null auto_increment,
  value varchar(255),
  primary key(`id`)
) engine = InnoDB;
