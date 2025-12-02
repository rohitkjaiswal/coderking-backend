-- Basic schema for CoderKing
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  role VARCHAR(50),
  points INT DEFAULT 0
);
CREATE TABLE IF NOT EXISTS contests (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(500),
  description TEXT,
  status VARCHAR(50),
  starts_at DATETIME,
  ends_at DATETIME,
  rewards VARCHAR(500)
);

CREATE TABLE participants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rank INT,
    score INT,
    contest_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_contest FOREIGN KEY (contest_id) REFERENCES contests(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
