CREATE TABLE IF NOT EXISTS patients (
                                        id                   BIGSERIAL PRIMARY KEY,
                                        first_name           VARCHAR(80)  NOT NULL,
    last_name            VARCHAR(80)  NOT NULL,
    document_number      VARCHAR(20)  NOT NULL UNIQUE,
    birth_date           DATE,
    phone                VARCHAR(40),
    email                VARCHAR(120),
    address              VARCHAR(200),
    obra_social           VARCHAR(120),
    obra_social_number    VARCHAR(60),
    photo_url             VARCHAR(300),
    active               BOOLEAN NOT NULL DEFAULT true
    );

CREATE INDEX IF NOT EXISTS idx_patients_last_name
    ON patients (LOWER(last_name));

CREATE INDEX IF NOT EXISTS idx_patients_first_name
    ON patients (LOWER(first_name));

CREATE INDEX IF NOT EXISTS idx_patients_document
    ON patients (document_number);

-----

CREATE TABLE IF NOT EXISTS odontograms (
                                           id          BIGSERIAL PRIMARY KEY,
                                           patient_id  BIGINT NOT NULL UNIQUE
                                           REFERENCES patients(id) ON DELETE CASCADE,

    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now()
    );

CREATE OR REPLACE FUNCTION set_updated_at_odontograms()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = now();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_odontograms_updated_at ON odontograms;

CREATE TRIGGER trg_odontograms_updated_at
    BEFORE UPDATE ON odontograms
    FOR EACH ROW EXECUTE FUNCTION set_updated_at_odontograms();

-----

CREATE TABLE IF NOT EXISTS odontogram_items (
                                                id            BIGSERIAL PRIMARY KEY,
                                                odontogram_id BIGINT NOT NULL
                                                REFERENCES odontograms(id) ON DELETE CASCADE,

    tooth_code    VARCHAR(10) NOT NULL,    -- "11", "36", etc.
    surface       VARCHAR(10) NOT NULL DEFAULT 'GENERAL',
    status        VARCHAR(30) NOT NULL,    -- HEALTHY, CARIES, FILLING, MISSING...
    note          TEXT,

    created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT uq_odontogram_tooth_surface
    UNIQUE (odontogram_id, tooth_code, surface)
    );

CREATE INDEX IF NOT EXISTS idx_odontogram_items_odontogram
    ON odontogram_items (odontogram_id);

CREATE INDEX IF NOT EXISTS idx_odontogram_items_tooth
    ON odontogram_items (odontogram_id, tooth_code);

---
CREATE OR REPLACE FUNCTION set_updated_at_odontogram_items()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = now();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_odontogram_items_updated_at ON odontogram_items;

CREATE TRIGGER trg_odontogram_items_updated_at
    BEFORE UPDATE ON odontogram_items
    FOR EACH ROW EXECUTE FUNCTION set_updated_at_odontogram_items();


----

CREATE TABLE IF NOT EXISTS clinical_history_entries (
                                                        id            BIGSERIAL PRIMARY KEY,
                                                        patient_id    BIGINT NOT NULL
                                                        REFERENCES patients(id) ON DELETE CASCADE,

    occurred_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    type          VARCHAR(30) NOT NULL,   -- NOTE, DIAGNOSIS, TREATMENT, EVOLUTION

    tooth_code    VARCHAR(10),             -- opcional
    surface       VARCHAR(10),             -- GENERAL / O / M / D / B / L

    title         VARCHAR(120),
    note          TEXT NOT NULL,

    created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT now()
    );

CREATE INDEX IF NOT EXISTS idx_history_patient_occurred
    ON clinical_history_entries (patient_id, occurred_at DESC);

CREATE INDEX IF NOT EXISTS idx_history_patient_type
    ON clinical_history_entries (patient_id, type);

CREATE OR REPLACE FUNCTION set_updated_at_history()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = now();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_history_updated_at ON clinical_history_entries;

CREATE TRIGGER trg_history_updated_at
    BEFORE UPDATE ON clinical_history_entries
    FOR EACH ROW EXECUTE FUNCTION set_updated_at_history();

-----

CREATE TABLE IF NOT EXISTS odontogram_events (
                                                 id            BIGSERIAL PRIMARY KEY,
                                                 patient_id    BIGINT NOT NULL
                                                 REFERENCES patients(id) ON DELETE CASCADE,

    occurred_at   TIMESTAMPTZ NOT NULL DEFAULT now(),

    tooth_code    VARCHAR(10) NOT NULL,
    surface       VARCHAR(10) NOT NULL DEFAULT 'GENERAL',
    status        VARCHAR(30) NOT NULL,
    note          TEXT
    );

CREATE INDEX IF NOT EXISTS idx_odontogram_events_patient_time
    ON odontogram_events (patient_id, occurred_at DESC);
