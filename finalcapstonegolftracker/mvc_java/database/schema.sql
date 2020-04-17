-- *************************************************************************************************
-- This script creates all of the database objects (tables, sequences, etc) for the database
-- *************************************************************************************************

BEGIN;

-- CREATE statements go here
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS golfer_team;
DROP TABLE IF EXISTS golfer_teetime;
DROP TABLE IF EXISTS league;
DROP TABLE IF EXISTS league_golfer;
DROP TABLE IF EXISTS scores;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS tee_time;

CREATE TABLE app_user
(
 id       serial NOT NULL,
 user_name varchar(50) NOT NULL,
 password varchar(100) NOT NULL,
 salt     varchar(255) NOT NULL,
 role     varchar(50) NOT NULL,
 CONSTRAINT PK_golfer PRIMARY KEY (id)
);


CREATE TABLE courses
(
 courseId serial NOT NULL,
 name     varchar(50) NOT NULL,
 par      integer NOT NULL,
 slope    integer NOT NULL,
 rating   float NOT NULL,
 address  varchar(100) NOT NULL,
 city     varchar(50) NOT NULL,
 state    varchar(50) NOT NULL,
 zip      integer NOT NULL,
 latitude float NULL,
 longitude float NULL,
 CONSTRAINT PK_courses PRIMARY KEY (courseId)
);


CREATE TABLE golfer_team
(
 id     integer NOT NULL,
 teamId integer NOT NULL

);


CREATE TABLE golfer_teeTime
(
 teeTimeId integer NOT NULL,
 id        integer NOT NULL
);


CREATE TABLE league
(
 leagueId    serial NOT NULL,
 leagueName  varchar(50) NOT NULL,
 leagueOwner integer NOT NULL,
 CONSTRAINT PK_league PRIMARY KEY (leagueid)
);


CREATE TABLE league_golfer
(
 id       integer NOT NULL,
 leagueId integer NOT NULL
);


CREATE TABLE scores
(
 scoreId   serial NOT NULL,
 score     integer NOT NULL,
 id        integer NOT NULL,
 teeTimeId integer NOT NULL,
 courseId  integer NOT NULL,
 CONSTRAINT PK_scores PRIMARY KEY (scoreId)
);


CREATE TABLE teams
(
 teamId    serial NOT NULL,
 teamName  varchar(50) NOT NULL,
 leagueId  integer NOT NULL,
 points integer,
 CONSTRAINT PK_teams PRIMARY KEY (teamId)
);


CREATE TABLE tee_time
(
 teeTimeId  serial NOT NULL,
 time       timestamp NOT NULL,
 leagueId   integer NULL,
 numGolfers integer NOT NULL,
 courseId   integer NOT NULL,
 CONSTRAINT PK_tee_time PRIMARY KEY (teeTimeId)

);

ALTER TABLE golfer_team ADD CONSTRAINT fk_id FOREIGN KEY (id) REFERENCES app_user (id);
ALTER TABLE golfer_team ADD CONSTRAINT fk_teamId FOREIGN KEY (teamId) REFERENCES teams (teamId);
ALTER TABLE golfer_teeTime ADD CONSTRAINT fk_teeTimeId FOREIGN KEY (teeTimeId) REFERENCES tee_time (teeTimeId);
ALTER TABLE golfer_teeTime ADD CONSTRAINT fk_id FOREIGN KEY (id) REFERENCES app_user (id);
ALTER TABLE league ADD CONSTRAINT fk_leagueOwner FOREIGN KEY (leagueOwner) REFERENCES app_user (id);
ALTER TABLE league_golfer ADD CONSTRAINT fk_id FOREIGN KEY (id) REFERENCES app_user (id);
ALTER TABLE league_golfer ADD CONSTRAINT fk_leagueId FOREIGN KEY (leagueId) REFERENCES league (leagueId);
ALTER TABLE scores ADD CONSTRAINT fk_courseId FOREIGN KEY (courseId) REFERENCES courses (courseId);
ALTER TABLE scores ADD CONSTRAINT fk_id FOREIGN KEY (id) REFERENCES app_user (id);
ALTER TABLE scores ADD CONSTRAINT fk_teeTimeId FOREIGN KEY (teeTimeId) REFERENCES tee_time (teeTimeId);
ALTER TABLE teams ADD CONSTRAINT fk_leagueId FOREIGN KEY (leagueId) REFERENCES league (leagueId);
ALTER TABLE tee_time ADD CONSTRAINT fk_courseId FOREIGN KEY (courseId) REFERENCES courses (courseId);
ALTER TABLE tee_time ADD CONSTRAINT fk_leagueId FOREIGN KEY (leagueId) REFERENCES league (leagueId);

COMMIT;