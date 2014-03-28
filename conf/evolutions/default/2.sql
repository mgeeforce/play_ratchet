# --- Sample dataset

# --- !Ups

Insert into dcs.user (`email`,`name`,`password`) values ('mike.gee@mac.com','Mike','adeptus');


INSERT INTO `dcs`.`parent` (`id`,`name`,`status`,`date`,`created_by_email`) VALUES (1,'Kimberely',2,'2014-03-14','mike.gee@mac.com');
INSERT INTO `dcs`.`parent` (`id`,`name`,`status`,`date`,`created_by_email`) VALUES (2,'Red Mountain',0,'2014-03-14','mike.gee@mac.com');
INSERT INTO `dcs`.`parent` (`id`,`name`,`status`,`date`,`created_by_email`) VALUES (3,'Revelstoke',1,'2014-03-14','mike.gee@mac.com');
INSERT INTO `dcs`.`parent` (`id`,`name`,`status`,`date`,`created_by_email`) VALUES (4,'Whistler',0,'2014-03-14','mike.gee@mac.com');
INSERT INTO `dcs`.`parent` (`id`,`name`,`status`,`date`,`created_by_email`) VALUES (5,'Schweitzer',2,'2014-03-14','mike.gee@mac.com');
INSERT INTO `dcs`.`parent` (`id`,`name`,`status`,`date`,`created_by_email`) VALUES (6,'Nakiska',2,'2014-03-14','mike.gee@mac.com');


INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (1, 'Fuel', '2014-03-01', 100.00, 1, 2, 'gassed up at COP', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (2, 'Fuel', '2014-03-01', 100.00, 2, 2, 'gassed up at COP', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (3, 'Fuel', '2014-03-01', 100.00, 3, 2, 'gassed up at COP', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (4, 'Fuel', '2014-03-01', 100.00, 4, 2, 'gassed up at COP', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (5, 'Fuel', '2014-03-01', 100.00, 5, 2, 'gassed up at COP', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (6, 'Fuel', '2014-03-01', 100.00, 6, 2, 'gassed up at COP', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (7, 'Meals', '2014-03-01', 100.00, 1, 3, 'team dinner', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (8, 'Meals', '2014-03-01', 100.00, 2, 3, 'coaches meals', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (9, 'Meals', '2014-03-01', 100.00, 3, 3, 'groceries', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (10, 'Meals', '2014-03-01', 100.00, 1, 3, 'team dinner', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (11, 'Meals', '2014-03-01', 100.00, 2, 3, 'coaches meals', '2014-03-14');
INSERT INTO detail (id, name, date, amount, parent_id, category, description, created) VALUES (12, 'Meals', '2014-03-01', 100.00, 3, 3, 'groceries', '2014-03-14');
# --- !Downs

DELETE FROM DETAIL;
DELETE FROM PARENT;
DELETE FROM USER;