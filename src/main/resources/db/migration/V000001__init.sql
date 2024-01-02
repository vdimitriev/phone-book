create table if not exists booker
(
    id                              bigserial    not null primary key,
    booker_id                       text         not null unique,
    name                            text
);

create table if not exists device_info
(
    id                              bigserial    not null primary key,
    device_info_id                  text,
    device_name                     text,
    technology                      text,
    bands_2g                        text,
    bands_3g                        text,
    bands_4g                        text
);

create table if not exists phone
(
    id                              bigserial    not null primary key,
    phone_id                        text         not null,
    device_info_id                  bigint,
    unit_id                         int,
    brand                           text,
    model                           text,
    name                            text         not null,
    available                       boolean,
    unique (phone_id, unit_id),
    constraint fk_phone_device_info_id foreign key (device_info_id) references device_info(id)
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

