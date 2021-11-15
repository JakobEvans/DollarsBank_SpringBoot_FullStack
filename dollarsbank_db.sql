drop database if exists dollarsbank_db;

create database dollarsbank_db;
use dollarsbank_db;

select * from customer;
select * from transaction;

INSERT INTO `dollarsbank_db`.`customer`
(`customer_id`,
`address`,
`currentbalance`,
`inititaldeposit`,
`name`,
`password`,
`phonenumber`,
`username`)
VALUES
(1,  '112 Place dr',77, 77, 'Billy Bob','password', '111-111-1111','username1');
 
 
 
INSERT INTO `dollarsbank_db`.`customer`
(`customer_id`,
`address`,
`currentbalance`,
`inititaldeposit`,
`name`,
`password`,
`phonenumber`,
`username`)
VALUES
(2,  '333 Other dr',77, 77, 'Other Guy','password', '111-111-1111','username2');
 
 
 
 INSERT INTO `dollarsbank_db`.`transaction`
(
`id`,
`amount`,
`balance_after`,
`balance_before`,
`date`,
`status_message`,
`customer_id`)
VALUES
(1,  33,100,77,now(), 'status', 2);


 INSERT INTO `dollarsbank_db`.`transaction`
(
`id`,
`amount`,
`balance_after`,
`balance_before`,
`date`,
`status_message`,
`customer_id`)
VALUES
(2,  15,115,100,now(), 'status', 2);
 
 


