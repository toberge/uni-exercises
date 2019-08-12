DROP TABLE users, lobby, matches;

create table users(
                   user_id integer not null,
                   email varchar(30),
                   password binary(32),
                   salt binary(16),
                   nickname varchar(30),
                   game_code integer,
                   constraint user_id_pk primary key (user_id)
);

create table matches(
                    match_id integer not null,
                    round integer,
                    turn integer,
                    ongoing boolean,
                    constraint match_id_pk primary key (match_id)
);

create table lobby (
                     game_code integer not null,
                     host varchar(25),
                     match_id int,
                     constraint game_code_pk primary key (game_code)
);

alter table lobby
  add constraint match_id_fk foreign key (match_id)
    references matches (match_id);

alter table user
  add constraint game_code_fk foreign key (game_code)
    references lobby (game_code);