# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table attachment (
  id                        bigint auto_increment not null,
  filename                  varchar(255),
  path                      varchar(255),
  content_type              varchar(255),
  created                   datetime not null,
  constraint pk_attachment primary key (id))
;

create table detail (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  date                      datetime,
  amount                    decimal(38),
  parent_id                 bigint,
  category                  integer,
  description               varchar(255),
  attachment_id             bigint,
  created                   datetime not null,
  constraint ck_detail_category check (category in (0,1,2,3,4)),
  constraint pk_detail primary key (id))
;

create table parent (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  status                    integer,
  date                      datetime,
  created_by_email          varchar(255),
  constraint ck_parent_status check (status in (0,1,2,3)),
  constraint pk_parent primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

alter table detail add constraint fk_detail_parent_1 foreign key (parent_id) references parent (id) on delete restrict on update restrict;
create index ix_detail_parent_1 on detail (parent_id);
alter table detail add constraint fk_detail_attachment_2 foreign key (attachment_id) references attachment (id) on delete restrict on update restrict;
create index ix_detail_attachment_2 on detail (attachment_id);
alter table parent add constraint fk_parent_createdBy_3 foreign key (created_by_email) references user (email) on delete restrict on update restrict;
create index ix_parent_createdBy_3 on parent (created_by_email);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table attachment;

drop table detail;

drop table parent;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

