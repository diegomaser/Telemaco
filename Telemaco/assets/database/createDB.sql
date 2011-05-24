CREATE TABLE Trip (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, start_date TEXT, end_date TEXT);
CREATE TABLE Country (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, plug INTEGER, currency INTEGER, languages INTEGER);
CREATE TABLE City (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, country INTEGER, timezone INTEGER);