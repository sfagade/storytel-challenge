
/**
 * Author:  samsonfagade
 * Created: 16 Nov 2020
 */

INSERT INTO application_roles (app_role_id, role_name, created, modified) VALUES (1, 'ADMIN', '2020-11-17 10:30', '2020-11-17 10:30');
INSERT INTO application_roles (app_role_id, role_name, created, modified) VALUES (2, 'USER', '2020-11-17 10:30', '2020-11-17 10:30');
INSERT INTO login_information (login_id, email_address, password, app_role_id, created, modified)
VALUES (1, 'admin@yahoo.com','$2a$10$mVpxdyJx59P9snH16X/esOp6uMk5v3yHkH0XYaqO2QRdc1ZisRgHm', 1, '2020-11-17 10:30', '2020-11-17 10:30');
INSERT INTO login_information (login_id, email_address, password, app_role_id, created, modified)
VALUES (2, 'user@yahoo.com','$2a$10$mVpxdyJx59P9snH16X/esOp6uMk5v3yHkH0XYaqO2QRdc1ZisRgHm', 2, '2020-11-17 10:30', '2020-11-17 10:30');

insert into messages (message_id, created, modified, message_content, login_id, message_subject, view_count)
values (1, '2020-11-19 17:23:01.088329', '2020-11-19 17:23:01.088329', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don''t look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn''t anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.', 2, 'Where can I get some?', 5);
insert into messages (message_id, created, modified, message_content, login_id, message_subject, view_count)
values (2, '2020-11-19 17:23:01.088329', '2020-11-19 17:23:01.088329', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.', 2, 'What is Lorem Ipsum?', 25);
insert into messages (message_id, created, modified, message_content, login_id, message_subject, view_count)
values (3, '2020-11-19 17:23:01.088329', '2020-11-19 17:23:01.088329', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 1, 'The standard Lorem Ipsum passage, used since the 1500s', 22);