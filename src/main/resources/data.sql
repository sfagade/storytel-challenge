
/**
 * Author:  samsonfagade
 * Created: 16 Nov 2020
 */

INSERT INTO application_roles (app_role_id, role_name, created, modified) VALUES (1, 'ADMIN', '2020-11-17 10:30', '2020-11-17 10:30');
INSERT INTO application_roles (app_role_id, role_name, created, modified) VALUES (2, 'USER', '2020-11-17 10:30', '2020-11-17 10:30');
INSERT INTO login_information (login_id, email_address, password, app_role_id, created, modified)
VALUES (1, 'admin@yahoo.com',"my_password", 1, '2020-11-17 10:30', '2020-11-17 10:30');
INSERT INTO login_information (login_id, email_address, password, app_role_id, created, modified)
VALUES (1, 'user@yahoo.com',"my_password", 2, '2020-11-17 10:30', '2020-11-17 10:30');