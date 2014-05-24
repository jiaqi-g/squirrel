USE test;
CREATE TABLE sent_cnt(
	id INT NOT NULL AUTO_INCREMENT,
	review_id INT,
	sent_id   INT,
	sent	  varchar(2000),
        PRIMARY KEY (id)
);


