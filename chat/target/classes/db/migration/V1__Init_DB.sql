CREATE TABLE hibernate_sequence (
  next_val BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (next_val)
) engine=InnoDB;
INSERT INTO hibernate_sequence(next_val) VALUES (2);
create table message (id integer not null, filename varchar(255), text_message varchar(2550), user_id integer, primary key (id)) engine=InnoDB;
create table user_role (user_id integer not null, roles varchar(255)) engine=InnoDB;
create table usr (id integer not null, activation_code varchar(255), active bit not null, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
alter table message add constraint message_user_fk foreign key (user_id) references usr (id);
alter table user_role add constraint user_role_user_fk foreign key (user_id) references usr (id);