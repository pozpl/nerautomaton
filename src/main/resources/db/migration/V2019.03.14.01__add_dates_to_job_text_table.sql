BEGIN;


ALTER TABLE ner_jobs_texts ADD column created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE ner_jobs_texts ADD column updated timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;


COMMIT;