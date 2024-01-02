create table if not exists booker
(
    id                              bigserial    not null primary key,
    booker_id                       text         not null unique,
    name                            text
);

create table if not exists phone
(
    id                              bigserial    not null primary key,
    phone_id                        text         not null,
    unit_id                         int,
    make                            text,
    model                           text,
    name                            text         not null,
    available                       boolean,
    unique (phone_id, unit_id)
);

create table if not exists event
(
    id                              bigserial    not null primary key,
    event_id                        text         not null unique,
    phone_id                        bigserial    not null,
    booker_id                       bigint,
    created                         timestamp,
    type                            text,
    constraint fk_event_phone_id foreign key (phone_id) references phone(id),
    constraint fk_event_booker_id foreign key (booker_id) references booker(id)
);

create index if not exists phone_name_idx on phone(name);
create index if not exists phone_phone_id_idx on phone(phone_id);
create index if not exists booker_booker_id_idx on booker(booker_id);

