
create table variable(
	name VARCHAR(20),
	type VARCHAR(10),
	template VARCHAR(100),
	persistent bool default  true
)


create table variable_value(
	key VARCHAR(20) primary key,
	value VARCHAR(20)
)

insert into variable values('associate_commission','DOUBLE','associate_commission.${associate_id}',false);




create table staging_order(
	batch_number int,
	associate_id int,
	customer_id int,
	purchase_date date,
	item_id int,
	item_price double precision
)







CREATE SEQUENCE associate_id_seq START 101;
CREATE SEQUENCE customer_id_seq START 101;
CREATE SEQUENCE item_id_seq START 101;


insert into staging_order values(1,nextval('associate_id_seq'),nextval('customer_id_seq'),'1-Jan-2016',nextval('item_id_seq'),0);


random()*(b-a)+a;
update staging_order set item_price=random() * 1500 + 500


create table invoice(
	customer_id int,
	item_id int,
	item_price double precision
);
