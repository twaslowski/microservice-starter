ALTER TABLE short_link
  ADD COLUMN token VARCHAR(255);

CREATE UNIQUE INDEX IF NOT EXISTS uq_short_link_token ON short_link (token);