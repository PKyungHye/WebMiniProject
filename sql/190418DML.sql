INSERT INTO user01 VALUES ('tester1', 'pw1', '테스터');
INSERT INTO post01 VALUES (post01_postno_seq.NEXTVAL, sysdate, 'tester1', '노래 추천 게시판입니다.', '노래 추천하세요.');

COMMIT;