
drop table if exists "user";
create table "user"(
                       user_id                     serial primary key,
                       phone_number                varchar(50),
                       "name"                      varchar(50),
                       encrypted_password          varchar(50),
                       signup_time                 timestamp default(now()) not null,
                       profile                     text not null,
                       avatar_url                  varchar(150)
);

drop table if exists "topic";
create table "topic"(
                        topic_id                    serial primary key ,
                        title                       varchar(50),
                        content                    text not null,
                        post_time                   timestamp default(now()) not null,
                        poster_user_id              int not null references "user"(user_id),
                        visited_count               int default(0),
                        favorite_count              int default(0)
);

drop table if exists "comment";
create table "comment"(
                          comment_id                  serial primary key ,
                          topic_id                    int not null references topic(topic_id),
                          content                     text not null,
                          comment_user_id             int not null references "user"(user_id),
                          comment_time                timestamp default(now()) not null
);

drop table if exists "message";
create table "message"(
                          message_id                  serial primary key ,
                          content                     text not null,
                          sent_user_id                int not null references "user"(user_id),
                          receive_user_id             int not null references "user"(user_id),
                          sent_time                   timestamp default(now()) not null,
                          is_looked                   bool default false
);

drop table if exists "favorite_topic_list";
create table "favorite_topic_list"(
                                      favorite_topic_list_id      serial primary key ,
                                      owner_user_id               int not null references "user"(user_id),
                                      topic_id                    int not null references "user"(user_id)
);
