CREATE TABLE beer (
    id integer NOT NULL PRIMARY KEY,
    abv character varying(255),
    add_user integer,
    descript text,
    filepath character varying(255),
    ibu character varying(255),
    last_mod timestamp(6) without time zone,
    name character varying(255),
    srm character varying(255),
    upc character varying(255),
    brewery_id integer,
    style_id integer,
    cat_id integer
);

CREATE TABLE brewery (
    id integer NOT NULL PRIMARY KEY,
    add_user integer,
    address1 character varying(255),
    address2 character varying(255),
    city character varying(255),
    code character varying(255),
    country character varying(255),
    descript text,
    filepath character varying(255),
    last_mod timestamp(6) without time zone,
    name character varying(255),
    phone character varying(255),
    state character varying(255),
    website character varying(255)
);

CREATE TABLE category (
    id integer NOT NULL PRIMARY KEY,
    last_mod timestamp(6) without time zone,
    cat_name character varying(255)
);

CREATE TABLE geocode (
    id integer NOT NULL PRIMARY KEY,
    accuracy character varying(255),
    latitude NUMERIC(17,15),
    longitude NUMERIC(18,15),
    brewery_id integer
);

CREATE TABLE style (
    id integer NOT NULL PRIMARY KEY,
    last_mod timestamp(6) without time zone,
    style_name character varying(255),
    cat_id integer
);

ALTER TABLE ONLY beer
    ADD CONSTRAINT fk_beer_brewery FOREIGN KEY (brewery_id) REFERENCES brewery(id);

ALTER TABLE ONLY beer
    ADD CONSTRAINT fk_beer_style FOREIGN KEY (style_id) REFERENCES style(id);

ALTER TABLE ONLY beer
    ADD CONSTRAINT fk_beer_category FOREIGN KEY (cat_id) REFERENCES category(id);

ALTER TABLE ONLY style
    ADD CONSTRAINT fk_style_category FOREIGN KEY (cat_id) REFERENCES category(id);

ALTER TABLE ONLY geocode
    ADD CONSTRAINT fk_geocode_brewery FOREIGN KEY (brewery_id) REFERENCES brewery(id);

COPY category(id,cat_name,last_mod)
FROM '/docker-entrypoint-initdb.d/categories.csv'
DELIMITER ','
CSV HEADER;

COPY style(id,cat_id,style_name,last_mod)
FROM '/docker-entrypoint-initdb.d/styles.csv'
DELIMITER ','
CSV HEADER;

COPY brewery(id,name,address1,address2,city,state,code,country,phone,website,filepath,descript,add_user,last_mod)
FROM '/docker-entrypoint-initdb.d/breweries.csv'
DELIMITER ','
CSV HEADER;

COPY geocode(id,brewery_id,latitude,longitude,accuracy)
FROM '/docker-entrypoint-initdb.d/geocodes.csv'
DELIMITER ','
CSV HEADER;

COPY beer(id,brewery_id,name,cat_id,style_id,abv,ibu,srm,upc,filepath,descript,add_user,last_mod)
FROM '/docker-entrypoint-initdb.d/beers.csv'
DELIMITER ','
CSV HEADER;