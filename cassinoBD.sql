CREATE TABLE j2ee.user(
    id serial NOT NULL,
    login character varying(20) NOT NULL,
    senha character(32) NOT NULL,
    nome character varying(40) NOT NULL,
    nascimento date NOT NULL,
    avatar varchar(100),
    carteira integer DEFAULT 0,
    first_login date NOT NULL,
    last_login date NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_login UNIQUE (login)
);

CREATE TABLE j2ee.games(
    id serial NOT NULL,
    nome character(32) NOT NULL,
    multiplicador integer DEFAULT 1,
    CONSTRAINT pk_user PRIMARY KEY (id)
);  

CREATE TABLE j2ee.log(
    id serial NOT NULL,
    user_id serial NOT NULL,
    game_id serial NOT NULL,
    house_gain boolean,
    money integer DEFAULT 0,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE OR REPLACE FUNCTION totalGanhos(_user_id integer,_game_id integer)
RETURNS integer AS $total$
declare
	total integer;
BEGIN
   SELECT count(*) as total FROM log WHERE house_gain IS NULL AND `user_id` = _user_id AND game_id = _game_id;
   RETURN total;
END;
$total$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION totalPerdas(_user_id integer,_game_id integer)
RETURNS integer AS $total$
declare
	total integer;
BEGIN
   SELECT count(*) as total FROM log WHERE house_gain IS NOT NULL AND `user_id` = _user_id AND game_id = _game_id;
   RETURN total;
END;
$total$ LANGUAGE plpgsql;


INSERT INTO j2ee.user VALUES
	(1, 'teste', MD5('1234'), 'Marcos Santos', '04-07-1999', NULL, 125, '04-07-1999', '07-09-1999'),
	(2, 'well', MD5('1234'), 'Wellington Silva', '05-04-1974', NULL, 278, '05-04-1974', '14-10-1999'),
	(7, 'andy', MD5('1234'), 'Anderson Makino', '21-01-1985', NULL, 291, '23-01-1985', '13-10-1999');


INSERT INTO j2ee.games VALUES
	(1, 'blackjack', 2),
	(2, 'slotmachine', 5);
	
INSERT INTO j2ee.log VALUES
	(1, 1, 1, 15,  15),
	(2, 1, 1, -50,  25),
	(3, 1, 2, 5,  5),
	(4, 1, 1, -94,  47),
	(5, 1, 2, -10,  5),
	
	(6, 2, 1, -26,  13),
	(7, 2, 1, 60,  60),
	(8, 2, 2, 5,  5),
	(9, 2, 2, 5,  5),
	(10, 2, 2, 5,  5),
	
	(11, 3, 2, -10,  5),
	(12, 3, 2, -10,  5),
	(13, 3, 2, -10,  5),
	(14, 3, 2, 5,  5),
	(15, 3, 2, 5,  5);