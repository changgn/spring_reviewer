
/* Drop Triggers */

DROP TRIGGER TRI_board_recommend_num;


/* Drop Tables */

DROP TABLE comments CASCADE CONSTRAINTS;
DROP TABLE notice CASCADE CONSTRAINTS;
DROP TABLE photo CASCADE CONSTRAINTS;
DROP TABLE recommend CASCADE CONSTRAINTS;
DROP TABLE report CASCADE CONSTRAINTS;
DROP TABLE screp CASCADE CONSTRAINTS;
DROP TABLE board CASCADE CONSTRAINTS;
DROP TABLE members_category CASCADE CONSTRAINTS;
DROP TABLE category CASCADE CONSTRAINTS;
DROP TABLE follow CASCADE CONSTRAINTS;
DROP TABLE members CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE SEQ_comments_comment_num;
DROP SEQUENCE SEQ_board_board_num;

/* Create Sequences */

CREATE SEQUENCE SEQ_comments_comment_num INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_board_board_num INCREMENT BY 1 START WITH 1;

/* Create Tables */

CREATE TABLE board
(
	board_num number NOT NULL,
	id varchar2(20) NOT NULL,
	category_id varchar2(20) NOT NULL,
	content varchar2(4000) NOT NULL,
	write_date date NOT NULL,
	recommend_num number DEFAULT 0,
	report_num number DEFAULT 0,
	screp number DEFAULT 0,
	comment_num number DEFAULT 0,
	PRIMARY KEY (board_num)
);


CREATE TABLE category
(
	category_id varchar2(20) NOT NULL,
	group1 varchar2(50),
	group2 varchar2(50),
	group3 varchar2(50),
	PRIMARY KEY (category_id)
);


CREATE TABLE comments
(
	comment_num number NOT NULL,
	board_num number NOT NULL,
	id varchar2(20) NOT NULL,
	content varchar2(4000) NOT NULL,
	write_date date NOT NULL,
	PRIMARY KEY (comment_num)
);


CREATE TABLE follow
(
	from_id varchar2(20) NOT NULL,
	to_id varchar2(20) NOT NULL
);


CREATE TABLE members
(
	id varchar2(20) NOT NULL,
	passwd varchar2(20) NOT NULL,
	name varchar2(20) NOT NULL,
	birth varchar2(9) NOT NULL,
	gender varchar2(5) NOT NULL,
	email varchar2(50) NOT NULL,
	phone_num varchar2(14) NOT NULL,
	reg_date date NOT NULL,
	recommend_num number,
	PRIMARY KEY (id)
);


CREATE TABLE members_category
(
	id varchar2(20) NOT NULL,
	category_id varchar2(20) NOT NULL
);


CREATE TABLE notice
(
	kind number,
	id varchar2(20),
	targetid varchar2(20),
	board_num number
);


CREATE TABLE photo
(
	fileName varchar2(100) NOT NULL,
	board_num number NOT NULL,
	realPath varchar2(200) NOT NULL,
	o_fileName varchar2(100) NOT NULL
);


CREATE TABLE recommend
(
	id varchar2(20) NOT NULL,
	recommend_num number
);


CREATE TABLE report
(
	id varchar2(20) NOT NULL,
	report_num number
);


CREATE TABLE screp
(
	id varchar2(20) NOT NULL,
	board_num number NOT NULL
);



/* Create Foreign Keys */

ALTER TABLE comments
	ADD FOREIGN KEY (board_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE
;


ALTER TABLE notice
	ADD FOREIGN KEY (board_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE
;


ALTER TABLE photo
	ADD FOREIGN KEY (board_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE
;


ALTER TABLE recommend
	ADD FOREIGN KEY (recommend_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE
;


ALTER TABLE report
	ADD FOREIGN KEY (report_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE
;


ALTER TABLE screp
	ADD FOREIGN KEY (board_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE
;


ALTER TABLE board
	ADD FOREIGN KEY (category_id)
	REFERENCES category (category_id)
	ON DELETE CASCADE
;


ALTER TABLE members_category
	ADD FOREIGN KEY (category_id)
	REFERENCES category (category_id)
	ON DELETE CASCADE
;


ALTER TABLE board
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE
;


ALTER TABLE follow
	ADD FOREIGN KEY (from_id)
	REFERENCES members (id)
	ON DELETE CASCADE
;


ALTER TABLE follow
	ADD FOREIGN KEY (to_id)
	REFERENCES members (id)
	ON DELETE CASCADE
;


ALTER TABLE members_category
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE
;


ALTER TABLE notice
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE
;


ALTER TABLE notice
	ADD FOREIGN KEY (targetid)
	REFERENCES members (id)
	ON DELETE CASCADE
;


ALTER TABLE recommend
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE
;


ALTER TABLE report
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE
;


ALTER TABLE screp
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE
;



/* Create Triggers */

CREATE OR REPLACE TRIGGER TRI_board_recommend_num AFTER UPDATE ON board
REFERENCING OLD AS old NEW AS new
FOR EACH ROW
declare
	oldRNum NUMBER;
	newRNum NUMBER;
BEGIN
	oldRNum := :old.recommend_num;
	newRNum := :new.recommend_num;
	
	IF oldRNum = (newRNum +1) THEN
	UPDATE members
	SET recommend_num = recommend_num + 1
	WHERE id = :new.id;
	END IF;
END;

/

