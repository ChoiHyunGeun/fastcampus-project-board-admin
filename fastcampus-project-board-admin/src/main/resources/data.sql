-- 테스트 계정
-- TODO: 테스트용이지만 비밀번호가 노출된 데이터 세팅. 개선하는 것이 좋을 지 고민해 보자.
insert into user_account (user_id, user_password, role_types, nickname, email, memo, create_date, create_user, update_date, update_user) values
('uno', '{noop}asdf1234', 'ADMIN', 'hg', 'hg@mail.com', 'I am HG.', now(), 'uno', now(), 'uno'),
('mark', '{noop}asdf1234', 'MANAGER', 'Mark', 'mark@mail.com', 'I am Mark.', now(), 'uno', now(), 'uno'),
('susan', '{noop}asdf1234', 'MANAGER,DEVELOPER', 'Susan', 'Susan@mail.com', 'I am Susan.', now(), 'uno', now(), 'uno'),
('jim', '{noop}asdf1234', 'USER', 'Jim', 'jim@mail.com', 'I am Jim.', now(), 'uno', now(), 'uno')
;