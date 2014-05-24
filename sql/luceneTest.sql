USE test;
CREATE TABLE luceneTest (
	id INT NOT NULL AUTO_INCREMENT,
	HotelId   INT,
	ReviewId  INT,
	Score	  DOUBLE,
	Para       VARCHAR(500),
	time       DATETIME,
        PRIMARY KEY (id)
);
