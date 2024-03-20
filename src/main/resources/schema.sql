CREATE TABLE IF NOT EXISTS account
(
    account_number   UUID PRIMARY KEY,
    beneficiary_name VARCHAR(255)   NOT NULL,
    pin_code         VARCHAR(255)   NOT NULL,
    balance          DECIMAL(19, 2) NOT NULL,
    created          TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transaction
(
    id               UUID PRIMARY KEY,
    from_account     UUID,
    to_account       UUID,
    amount           DECIMAL(19, 2) NOT NULL,
    transaction_type VARCHAR(255)   NOT NULL,
    created          TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
