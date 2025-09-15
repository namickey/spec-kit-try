CREATE TABLE users (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255),
  contact VARCHAR(255)
);

CREATE TABLE messages (
  id VARCHAR(36) PRIMARY KEY,
  sender_id VARCHAR(36) NOT NULL,
  body TEXT,
  attachment_ref VARCHAR(255),
  created_at TIMESTAMP
);

CREATE TABLE message_recipients (
  message_id VARCHAR(36) NOT NULL,
  recipient_id VARCHAR(36) NOT NULL,
  PRIMARY KEY (message_id, recipient_id)
);

CREATE TABLE invitations (
  token VARCHAR(36) PRIMARY KEY,
  contact VARCHAR(255) NOT NULL,
  expires_at TIMESTAMP
);
