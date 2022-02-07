CREATE TABLE city (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE candidate (
   id SERIAL PRIMARY KEY,
   name TEXT,
   city_id int REFERENCES city(id)
);