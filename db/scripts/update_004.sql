ALTER TABLE candidate ADD COLUMN created timestamp;
UPDATE candidate SET created = current_timestamp;
ALTER TABLE candidate ALTER COLUMN created SET NOT NULL;
ALTER TABLE post ADD COLUMN created timestamp;
UPDATE post SET created = current_timestamp;
ALTER TABLE post ALTER COLUMN created SET NOT NULL;