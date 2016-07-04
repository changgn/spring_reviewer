
/* Drop Triggers */

DROP TRIGGER TRI_board_recommend_num;

/* Drop Tables */

DROP TABLE comments CASCADE CONSTRAINTS;
DROP TABLE notice CASCADE CONSTRAINTS;
DROP TABLE photo CASCADE CONSTRAINTS;
DROP TABLE profilephoto CASCADE CONSTRAINTS;
DROP TABLE recommend CASCADE CONSTRAINTS;
DROP TABLE report CASCADE CONSTRAINTS;
DROP TABLE screp CASCADE CONSTRAINTS;
DROP TABLE board CASCADE CONSTRAINTS;
DROP TABLE members_category CASCADE CONSTRAINTS;
DROP TABLE category CASCADE CONSTRAINTS;
DROP TABLE follow CASCADE CONSTRAINTS;
DROP TABLE members CASCADE CONSTRAINTS;
DROP TABLE secret CASCADE CONSTRAINTS;



/* Drop Sequences */

DROP SEQUENCE SEQ_board_board_num;
DROP SEQUENCE SEQ_comments_comment_num;


/* Create Sequences */

CREATE SEQUENCE SEQ_board_board_num INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_comments_comment_num INCREMENT BY 1 START WITH 1;


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
	pageno number,
	pagesize number,
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
	gender varchar2(6) NOT NULL,
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
	kind varchar2(20) NOT NULL,
	id varchar2(20),
	targetid varchar2(20),
	board_num number,
	notice_date date NOT NULL,
	read number DEFAULT 0
);


CREATE TABLE photo
(
	fileName varchar2(100) NOT NULL,
	board_num number NOT NULL,
	realPath varchar2(200) NOT NULL,
	o_fileName varchar2(100) NOT NULL
);

CREATE TABLE profilephoto
(
	fileName varchar2(100) NOT NULL,
	id varchar2(20) NOT NULL,
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

CREATE TABLE secret
(
   id varchar2(20) NOT NULL,
   board_num number
);


/* Create Foreign Keys */

ALTER TABLE secret
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE
;

ALTER TABLE secret
	ADD FOREIGN KEY (board_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE
;


ALTER TABLE notice
	ADD FOREIGN KEY (board_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE
;


ALTER TABLE notice
	ADD FOREIGN KEY (targetid)
	REFERENCES members (id)
	ON DELETE CASCADE
;


ALTER TABLE notice
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE
;

ALTER TABLE comments
	ADD FOREIGN KEY (board_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE;


ALTER TABLE photo
	ADD FOREIGN KEY (board_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE;

ALTER TABLE profilephoto
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE;
	
ALTER TABLE screp
	ADD FOREIGN KEY (board_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE;


ALTER TABLE board
	ADD FOREIGN KEY (category_id)
	REFERENCES category (category_id)
	ON DELETE CASCADE;


ALTER TABLE members_category
	ADD FOREIGN KEY (category_id)
	REFERENCES category (category_id)
	ON DELETE CASCADE;


ALTER TABLE board
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE;


ALTER TABLE follow
	ADD FOREIGN KEY (to_id)
	REFERENCES members (id)
	ON DELETE CASCADE;


ALTER TABLE follow
	ADD FOREIGN KEY (from_id)
	REFERENCES members (id)
	ON DELETE CASCADE;


ALTER TABLE members_category
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE;


ALTER TABLE screp
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE;


ALTER TABLE recommend
	ADD FOREIGN KEY (id)
	REFERENCES members (id)
	ON DELETE CASCADE;


ALTER TABLE recommend
	ADD FOREIGN KEY (recommend_num)
	REFERENCES board (board_num)
	ON DELETE CASCADE;


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




/* Comments */

COMMENT ON COLUMN board.board_num IS '글번호';
COMMENT ON COLUMN board.id IS '작성자';
COMMENT ON COLUMN board.category_id IS '카테고리 분류 번호';
COMMENT ON COLUMN board.content IS '본문내용';
COMMENT ON COLUMN board.write_date IS '작성시간';
COMMENT ON COLUMN board.recommend_num IS '추천수';
COMMENT ON COLUMN board.report_num IS '신고수';
COMMENT ON COLUMN board.screp IS '스크랩수';
COMMENT ON COLUMN board.comment_num IS '댓글수';
COMMENT ON COLUMN category.category_id IS '카테고리 번호';
COMMENT ON COLUMN category.group1 IS '대분류';
COMMENT ON COLUMN category.group2 IS '중분류';
COMMENT ON COLUMN category.group3 IS '소분류';
COMMENT ON COLUMN comments.comment_num IS '댓글번호';
COMMENT ON COLUMN comments.board_num IS '본문내용 번호';
COMMENT ON COLUMN comments.id IS '아이디';
COMMENT ON COLUMN comments.content IS '글내용';
COMMENT ON COLUMN comments.write_date IS '작성시간';
COMMENT ON COLUMN follow.from_id IS '팔로우';
COMMENT ON COLUMN follow.to_id IS '팔로잉';
COMMENT ON COLUMN members.id IS '아이디';
COMMENT ON COLUMN members.passwd IS '패스워드';
COMMENT ON COLUMN members.name IS '이름';
COMMENT ON COLUMN members.birth IS '생년월일';
COMMENT ON COLUMN members.email IS '이메일';
COMMENT ON COLUMN members.phone_num IS '휴대폰번호';
COMMENT ON COLUMN members.reg_date IS '가입시간';
COMMENT ON COLUMN members.recommend_num IS '추천수';
COMMENT ON COLUMN members_category.id IS '아이디';
COMMENT ON COLUMN members_category.category_id IS '카테고리 번호';
COMMENT ON COLUMN notice.id IS '아이디';
COMMENT ON COLUMN notice.targetid IS '아이디';
COMMENT ON COLUMN notice.board_num IS '글번호';
COMMENT ON COLUMN photo.fileName IS '파일이름';
COMMENT ON COLUMN photo.board_num IS '본문번호';
COMMENT ON COLUMN photo.realPath IS '절대경로';
COMMENT ON COLUMN recommend.id IS '아이디';
COMMENT ON COLUMN recommend.recommend_num IS '글번호';
COMMENT ON COLUMN report.id IS '아이디';
COMMENT ON COLUMN report.report_num IS '글번호';
COMMENT ON COLUMN screp.id IS '스크랩 아이디';
COMMENT ON COLUMN screp.board_num IS '글번호';



