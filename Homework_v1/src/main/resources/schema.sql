
CREATE TABLE if not exists Weather_Data(
                              id INT AUTO_INCREMENT  PRIMARY KEY,
                              name VARCHAR(100) NOT NULL,
                              phenomenon VARCHAR(250),
                              wmo_code int not null,
                              wind_speed double,
                              air_temp double,
                              timestamp timestamp not null
);