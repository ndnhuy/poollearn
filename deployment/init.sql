create database if not exists poollearn;
use poollearn;
create table if not exists orders(
  id bigint not null auto_increment,
  name varchar(255),
  status varchar(255),
  delivery_id varchar(36),
  primary key(`id`)
) engine = InnoDB;
