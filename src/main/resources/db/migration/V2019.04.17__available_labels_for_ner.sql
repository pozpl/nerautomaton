CREATE TABLE IF NOT EXISTS ner_job_available_entities (
  id       SERIAL,
  name     VARCHAR(256) NOT NULL,
  description     TEXT,
  job_id   INT    NOT NULL,
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT njae_name_job_id_uc UNIQUE (name, job_id),
  CONSTRAINT njae_job_id_fk FOREIGN KEY (job_id) REFERENCES ner_jobs (id) ON DELETE CASCADE
);