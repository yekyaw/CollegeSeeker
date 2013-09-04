BEGIN TRANSACTION;

DROP INDEX IF EXISTS state_idx;
CREATE INDEX state_idx
ON colleges
(
    state
);

CREATE TABLE major_names
(
    _id INTEGER PRIMARY KEY,
    major_name TEXT
);

CREATE TABLE majors
(
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    college_id INTEGER,
    major INTEGER,
    FOREIGN KEY (major) REFERENCES major_names(_id),
    FOREIGN KEY (college_id) REFERENCES colleges(_id)
);
CREATE INDEX college_idx
ON majors
(
    college_id
);
CREATE INDEX major_idx
ON majors
(
    major
);

COMMIT TRANSACTION;