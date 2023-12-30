create table booker
(
    id                              bigserial    not null primary key,
    booker_id                       text         not null unique,
    name                            text
);

create table phone
(
    id                              bigserial    not null primary key,
    phone_id                        text         not null,
    booker_id                       bigserial    unique,
    make                            text,
    model                           text,
    name                            text         not null,
    date_of_booking                 timestamp,
    available                       boolean,
    quantity                        numeric(2,0),
    constraint fk_phone_booker_id foreign key (booker_id) references booker(id)
);

create table event
(
    id                              bigserial    not null primary key,
    event_id                        text         not null unique,
    phone_id                        bigserial    not null unique,
    created                         timestamp,
    type                            text,
    constraint fk_event_phone_id foreign key (phone_id) references phone(id)
);

create
    index concurrently if not exists phone_name_index
    on phone(name);

