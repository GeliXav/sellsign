DROP TABLE IF EXISTS users;

CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  role VARCHAR(250) check (role in ('ADMIN', 'READ_ONLY', 'WRITE')),
  enabled BOOLEAN NOT NULL);

CREATE TABLE apikey (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  key UUID NOT NULL
);


INSERT INTO user (username, password, role, enabled)
VALUES ('admin', '$2y$12$5zROnn2pE4lLUrNN3l95l.ztqC6qdHiy4tN8F1O92r2.6UsoeiJ2y', 'ADMIN', true),
       ('read', '$2y$12$DRpqstL4cl1.ymqgHnoMjek2ti8Ken6FCCOc8jZg08PsfE9PrTJvO', 'READ_ONLY', true),
     ('write', '$2y$12$zlO3.pgTXp77pDt9OuRw.OpQ.Q5w3t1Z5kV4RaXXSmuz8YNNsJ1/q', 'WRITE', true)
