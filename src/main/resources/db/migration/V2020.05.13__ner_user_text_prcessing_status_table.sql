CREATE TABLE IF NOT EXISTS ner_user_text_processing_result
(
    id             SERIAL,
    user_id        INT       NOT NULL,
    text_id        INT       NOT NULL,
    annotated_text TEXT      NOT NULL,
    created        timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated        timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT nutps_user_id_text_id_uc UNIQUE (user_id, text_id),
    CONSTRAINT nutps_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT nutps_text_id_fk FOREIGN KEY (text_id) REFERENCES ner_jobs_texts (id) ON DELETE CASCADE
);