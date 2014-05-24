USE test;
CREATE TABLE review_sent(
	id INT NOT NULL AUTO_INCREMENT,	
	reviewId INT NOT NULL,
	sentId INT NOT NULL,
	noun	 varchar(200),
	adj	 varchar(200),
        PRIMARY KEY (id)
);


