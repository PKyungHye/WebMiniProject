INSERT INTO user01 VALUES ('tester1', 'pw1', '�׽���');
INSERT INTO post01 VALUES (post01_postno_seq.NEXTVAL, 'tester1', '�뷡 ��õ �Խ����Դϴ�.', '�뷡 ��õ�ϼ���.');


select * from user01;
select * from post01;
COMMIT;