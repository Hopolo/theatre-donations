create table donation_alerts_users
(
    id                      bigserial not null primary key,
    name                    varchar(50),
    donation_alerts_id      integer,
    code                    varchar(50),
    is_active               integer,
    avatar                  varchar(50),
    email                   varchar(50),
    language                varchar(50),
    socket_connection_token varchar(150)
);
