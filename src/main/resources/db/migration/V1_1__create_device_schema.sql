CREATE TABLE IF NOT EXISTS device (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    device_id VARCHAR(7) UNIQUE NOT NULL,
    brand_name brand_type NOT NULL,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);