insert into PUBLIC.db_user (id, username, password) values (1, 'admin', '{noop}!test!');
insert into PUBLIC.db_user (id, username, password) values (2, 'user', '{noop}!user!');

insert into PUBLIC.db_authority (id, name) values ( 1, 'admin' );
insert into PUBLIC.db_authority (id, name) values ( 2, 'user' );

insert into PUBLIC.db_user_x_db_authority (db_user_id, db_authority_id) values ( 1, 1 );
insert into PUBLIC.db_user_x_db_authority (db_user_id, db_authority_id) values ( 1, 2 );
insert into PUBLIC.db_user_x_db_authority (db_user_id, db_authority_id) values ( 2, 2 );

commit;