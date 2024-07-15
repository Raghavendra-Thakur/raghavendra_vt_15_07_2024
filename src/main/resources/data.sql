INSERT INTO student (name) VALUES ('vtStudentOne');
INSERT INTO student (name) VALUES ('vtStudentTwo');

INSERT INTO PERMISSIONS (name) VALUES ('AllowRead');
INSERT INTO PERMISSIONS (name) VALUES ('AllowWrite');
INSERT INTO PERMISSIONS (name) VALUES ('AllowUpdate');


INSERT INTO USER_ROLES (name) VALUES ('SuAdmin');
INSERT INTO USER_ROLES (name) VALUES ('Viewer');



INSERT INTO PER_ROLE_MAP (pid, rid, status) VALUES (1, 1, TRUE); -- Read for Admin
INSERT INTO PER_ROLE_MAP (pid, rid, status) VALUES (2, 1, TRUE); -- write for Admin
INSERT INTO PER_ROLE_MAP (pid, rid, status) VALUES (3, 1, TRUE); -- view for Admin
INSERT INTO PER_ROLE_MAP (pid, rid, status) VALUES (1, 2, TRUE); -- read for Viewer

INSERT INTO USERS (name, email, pass) VALUES ('VTADMIN', 'john@example.com', 'password123');
INSERT INTO USERS (name, email, pass) VALUES ('VTVIEWER', 'jane@example.com', 'secret456');



INSERT INTO USER_ROLES_MAP (uid, rid) VALUES (1, 1); -- John Doe as Admin
INSERT INTO USER_ROLES_MAP (uid, rid) VALUES (2, 2); -- Jane Smith as Editor