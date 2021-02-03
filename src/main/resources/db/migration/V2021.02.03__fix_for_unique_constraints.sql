ALTER TABLE ner_jobs DROP CONSTRAINT nj_name_uc;
ALTER TABLE ner_jobs ADD CONSTRAINT nk_name_owner_id_uc UNIQUE (name, owner_id);