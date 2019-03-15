BEGIN;

CREATE TABLE IF NOT EXISTS ner_jobs (
  id SERIAL,
  name VARCHAR(255) NOT NULL,
  owner_id INT NOT NULL,
  lang VARCHAR(10) NOT NULL DEFAULT 'EN',
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT nj_name_uc UNIQUE (name),
  CONSTRAINT nj_owner_id_fk FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ner_jobs_texts (
  id SERIAL,
  md5_hash VARCHAR(32) NOT NULL,
  job_id INT NOT NULL,
  text TEXT NOT NULL,
  tokens TEXT,
  PRIMARY KEY (id),
  CONSTRAINT njt_md5_hash_job_id_uc UNIQUE (md5_hash, job_id),
  CONSTRAINT njt_job_id_fk FOREIGN KEY (job_id) REFERENCES ner_jobs (id) ON DELETE CASCADE
);


COMMIT;