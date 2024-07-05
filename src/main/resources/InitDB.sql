CREATE TABLE IF NOT EXISTS Users (Id SERIAL primary key ,
                                userName varchar,
                                fio varchar);
CREATE TABLE IF NOT EXISTS Logins (Id SERIAL primary key  ,
                                    access_date timestamp,
                                    user_id integer references users(id),
                                    application varchar);