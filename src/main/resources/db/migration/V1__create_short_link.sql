CREATE TABLE IF NOT EXISTS short_link
(
  id           VARCHAR PRIMARY KEY,
  original_url VARCHAR                  NOT NULL,
  url_stub     VARCHAR                  NOT NULL,
  created_at   TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at   TIMESTAMP WITH TIME ZONE NOT NULL
);
