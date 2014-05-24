USE test;
CREATE TABLE wordSim(
	id INT NOT NULL AUTO_INCREMENT,
	word_x	 varchar(200),
	word_y	 varchar(200),
	sim	 double,
        PRIMARY KEY (id)
);


