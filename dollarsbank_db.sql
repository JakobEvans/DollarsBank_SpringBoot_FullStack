drop database if exists dollarsbank_db;

create database dollarsbank_db;
use dollarsbank_db;



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
(1,  '112 Place dr',0, 77, 'Billy Bob','password', '111-111-1111','username1');
 
 
 
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
(2,  '333 Other dr',0, 77, 'Other Guy','password', '111-111-1111','username2');
 

