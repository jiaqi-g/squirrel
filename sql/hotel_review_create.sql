USE test;
CREATE TABLE hotelReview(
	id INT NOT NULL AUTO_INCREMENT,
	hotelId	INT NOT NULL,
	reviewId INT NOT NULL,
	Title	 varchar(1000),
	Review	 varchar(10000),
        PRIMARY KEY (id)
);
