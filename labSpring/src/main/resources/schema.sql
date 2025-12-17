CREATE TABLE IF NOT EXISTS substations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(500),
    region VARCHAR(100),
    voltage_level DECIMAL(10,2),
    power DECIMAL(10,2),
    status VARCHAR(50),
    commissioning_year INTEGER
);

CREATE TABLE IF NOT EXISTS maintenance_records (
    id BIGSERIAL PRIMARY KEY,
    work_type VARCHAR(50) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE,
    cost DECIMAL(15,2),
    contractor VARCHAR(255),
    substation_id BIGINT NOT NULL,
    CONSTRAINT fk_substation FOREIGN KEY (substation_id) REFERENCES substations(id)
);

CREATE INDEX idx_maintenance_substation ON maintenance_records(substation_id);
CREATE INDEX idx_maintenance_work_type ON maintenance_records(work_type);
CREATE INDEX idx_maintenance_start_date ON maintenance_records(start_date);