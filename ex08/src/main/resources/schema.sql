CREATE TABLE IF NOT EXISTS item (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 10) PRIMARY KEY,
    text VARCHAR(255) NOT NULL,
    done boolean
);

-- do not add data if it is already added.

MERGE INTO item
   USING (VALUES 1) data (id) ON (item.id = data.id)
   WHEN NOT MATCHED THEN INSERT VALUES (1, 'Write an e-mail', false);

MERGE INTO item
   USING (VALUES 2) data (id) ON (item.id = data.id)
   WHEN NOT MATCHED THEN INSERT VALUES (2, 'Call Alice', false);

MERGE INTO item
   USING (VALUES 3) data (id) ON (item.id = data.id)
   WHEN NOT MATCHED THEN INSERT VALUES (3, 'Call Bob', false);
