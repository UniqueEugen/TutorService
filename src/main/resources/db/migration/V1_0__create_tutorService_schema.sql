create sequence address_seq
    increment by 50;

alter sequence address_seq owner to postgres;

create sequence forum_message_seq
    increment by 50;

alter sequence forum_message_seq owner to postgres;

create sequence order_chat_seq
    increment by 50;

alter sequence order_chat_seq owner to postgres;

create sequence orders_seq
    increment by 50;

alter sequence orders_seq owner to postgres;

create sequence seeker_seq
    increment by 50;

alter sequence seeker_seq owner to postgres;

create sequence tutor_seq
    increment by 50;

alter sequence tutor_seq owner to postgres;

create sequence users_seq
    increment by 50;

alter sequence users_seq owner to postgres;

create table address
(
    id      bigint       not null
        primary key,
    city    varchar(255) not null,
    country varchar(255) not null,
    house   varchar(255) not null,
    office  varchar(255) not null,
    street  varchar(255) not null
);

alter table address
    owner to postgres;

create table order_chat
(
    id      bigint not null
        primary key,
    date    date,
    message varchar(255),
    time    time(6)
);

alter table order_chat
    owner to postgres;

create table seeker
(
    seeker_id   bigint  not null
        primary key,
    age         integer not null,
    description varchar(255)
);

alter table seeker
    owner to postgres;

create table seeker_chats
(
    seeker_seeker_id bigint not null
        constraint fkd8eenqv5uewu3liowhqhnf6ad
            references seeker,
    chats_id         bigint not null
        constraint uk_nbpv0isn5sd7caymqcbkjjmns
            unique
        constraint fk17px2cawo9wp3fomwa2otuw2x
            references order_chat
);

alter table seeker_chats
    owner to postgres;

create table tutor
(
    tutor_id       bigint       not null
        primary key,
    price          real         not null,
    specialisation varchar(255) not null,
    address_id     bigint
        constraint uk_2gctohw93mnnb9gpsa99r7jr4
            unique
        constraint fk9wuvkfmgrk9xqkcja77abb1ma
            references address
);

alter table tutor
    owner to postgres;

create table orders
(
    id           bigint       not null
        primary key,
    date         date         not null,
    status       varchar(255) not null,
    time         time(6)      not null,
    tutor_order  bigint
        constraint fkh11m98vkiva8welhb28bsx1n7
            references tutor,
    seeker_order bigint
        constraint fkos3lmxb50779ajcwgke209t25
            references seeker
);

alter table orders
    owner to postgres;

create table tutor_chats
(
    tutor_tutor_id bigint not null
        constraint fk2qhawcpk6u98oflksh5jfftnr
            references tutor,
    chats_id       bigint not null
        constraint uk_8nbwaq3nqxsaqd3xg33vjbkui
            unique
        constraint fknpqx3wvb2k29arhsgr69hemdj
            references order_chat
);

alter table tutor_chats
    owner to postgres;

create table users
(
    id       bigint       not null
        primary key,
    login    varchar(255) not null,
    password varchar(255) not null
);

alter table users
    owner to postgres;

create table user_data
(
    email       varchar(255) not null,
    name        varchar(255) not null,
    phone       varchar(255) not null,
    second_name varchar(255),
    surname     varchar(255) not null,
    user_id     bigint       not null
        primary key
        constraint fkboeinboxcrb4ilnj0sfyxsbol
            references users,
    seeker_id   bigint
        constraint uk_nb2rqdqwryvqqgyyhxm9482sv
            unique
        constraint fkifhlhujafb301og86785umy8l
            references seeker,
    tutor_id    bigint
        constraint uk_2fk9srkb7rfpiefmoylgsdv1c
            unique
        constraint fk1wuclc2b5ryaty4vihpgs4w9b
            references tutor
);

alter table user_data
    owner to postgres;

create table forum_message
(
    message_id   bigint       not null
        primary key,
    date         date         not null,
    message      varchar(255) not null,
    time         time(6)      not null,
    user_data_id bigint
        constraint fk3uj8adoqp5ti2yp0a3wsbccef
            references user_data
);

alter table forum_message
    owner to postgres;