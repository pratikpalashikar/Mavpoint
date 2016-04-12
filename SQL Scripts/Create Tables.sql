DROP DATABASE IF EXISTS mavadvisor_dev;
CREATE DATABASE mavadvisor_dev;
USE mavadvisor_dev;

# Populate user status enum table
CREATE TABLE user_status (
  id INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL UNIQUE
);
INSERT INTO user_status (id, description) VALUES
  (0, "Inactive"),
  (1, "Active");

# Create User Table
CREATE TABLE user (
  id INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(50) NOT NULL,
  username VARCHAR(30),
  password_hash VARCHAR(50) NOT NULL,
  password_salt VARCHAR(50) NOT NULL,
  password_expiration date NOT NULL,
  firstname VARCHAR(30) NOT NULL,
  lastname VARCHAR(30) NOT NULL,
  uta_id VARCHAR(10),
  user_type INT NOT NULL,
  user_status INT NOT NULL,
  PRIMARY KEY (email),
  KEY (id),
  CONSTRAINT fk_user_status
    FOREIGN KEY (user_status)
	REFERENCES user_status (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  UNIQUE INDEX username_UNIQUE (username ASC),
  UNIQUE INDEX email_UNIQUE (email ASC));

# Populate enrollment status enum table
CREATE TABLE enrollment_status_type (
  id INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(50) NOT NULL UNIQUE
);
INSERT INTO enrollment_status_type (description) VALUES 
  ("Prospective"),
  ("Current"),
  ("Alumni");

# Populate student type enum table
CREATE TABLE student_type (
  id INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(50) NOT NULL UNIQUE
);
INSERT INTO student_type (description) VALUES
  ("Undergraduate"),
  ("Graduate"),
  ("Doctorate");

# Create Student Table
CREATE TABLE student (
  id INT PRIMARY KEY,
  enrollment_status INT NOT NULL,
  student_type INT NOT NULL,
  CONSTRAINT fk_student_user
    FOREIGN KEY (id)
    REFERENCES user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_enrollment_status
    FOREIGN KEY (enrollment_status)
	REFERENCES enrollment_status_type (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_student_type
    FOREIGN KEY (student_type)
    REFERENCES student_type (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

# Create Advisor Table
CREATE TABLE advisor (
  id INT PRIMARY KEY,
  email_notify BOOLEAN,
  assigned_students BOOLEAN,
  start_student VARCHAR(1),
  end_student VARCHAR(1),
  CONSTRAINT fk_advisor_user
    FOREIGN KEY (id)
    REFERENCES user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

# Create UserType enum table
CREATE TABLE user_type (
  user_type_id INT NOT NULL AUTO_INCREMENT,
  user_class VARCHAR(100) NOT NULL UNIQUE,
  PRIMARY KEY (user_type_id));
  
# Populate UserType table
INSERT INTO user_type (user_class) VALUES 
	('edu.uta.cse.group9.model.Admin'), 
    ('edu.uta.cse.group9.model.Advisor'),
    ('edu.uta.cse.group9.model.Student');

# Create FK relationship between User and UserType
ALTER TABLE user 
ADD INDEX fk_mav_user_type_idx (user_type ASC);
ALTER TABLE mavadvisor_dev.user 
ADD CONSTRAINT fk_mav_user_type
  FOREIGN KEY (user_type)
  REFERENCES user_type (user_type_id)
  ON DELETE NO ACTION
  ON UPDATE CASCADE;

# CREATE DUMMY USERS FOR ADMIN, ADVISOR and STUDENT
# All passwords are 'abc123'
INSERT INTO user (email, username, password_hash, password_salt, password_expiration, firstname, lastname, uta_id, user_type, user_status) VALUES
	('admin@mavs.uta.edu', 'admin', 'h8pZ8TVpdqY3muqGXTjRCyIiROWNiphv4lKKuxHwXJY=', 'Sdhz6ypNg1cKf0cBVsjNZg==', '2016-01-01', 'System', 'Admin', '1001999999', 1, 1),
    ('dkung@mavs.uta.edu', 'dkung', 'RT7alRXi1mc2TC0/iLyl22i+herJDKunD/zVsv3E8DY=', 'eK0PkdrGz4SFB6UrMybw7Q==', '2016-01-01', 'David', 'Kung', '1001001234', 2, 1),
    ('tprescott@mavs.uta.edu', 'tprescott', 'NlSnpZZR5EKmVXYJMzL+pdGwSsmv5+czi3GXecUMOvA=', 'R+Z/6aNNRWjxD27lBQ2sMg==', '2016-01-01', 'Travis', 'Prescott', '1001123456', 3, 1),
    ('relmasri@mavs.uta.edu', 'relmasri', 'FmDO5Gt+2Ml9JOVkBw5vGSeqmWvSF07ixL7D2hjE4YE=', 'XkggsLkcb38J1/fVEKqUdw==', '2016-01-01', 'Ramirez', 'Elmasri', '1001444444', 2, 1);

# DUMMY STUDENT AND ADVISOR DATA
INSERT INTO student (id, enrollment_status, student_type) values (3, 2, 2);
INSERT INTO advisor (id, email_notify, assigned_students, start_student, end_student) VALUES (2, true, false, null, null);
INSERT INTO advisor (id, email_notify, assigned_students, start_student, end_student) VALUES (4, true, false, null, null);

# Create Session table
CREATE TABLE user_session (
	token VARCHAR(32) PRIMARY KEY,
    user_id INT UNIQUE
);

# Create TimeSlot table
CREATE TABLE timeslot (
  id INT NOT NULL AUTO_INCREMENT,
  advisor_id INT NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_advisor_time_slot_idx (advisor_id ASC),
  CONSTRAINT fk_advisor_time_slot
    FOREIGN KEY (advisor_id)
    REFERENCES user (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

# Create AppointmentStatus enum table
CREATE TABLE appointment_status (
	id INT NOT NULL,
    description VARCHAR(30) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

# Populate AppointmentStatus table
INSERT INTO appointment_status (id, description) VALUES 
	(0, 'Scheduled'), (1, 'Complete'), (2, 'Canceled');
 
# Create Advising Task table
CREATE TABLE advising_task (
  id INT PRIMARY KEY,
  advisor_id INT NOT NULL,
  description VARCHAR(100) NOT NULL,
  duration INT NOT NULL,
  INDEX fk_advisor_id_idx (advisor_id ASC),
  CONSTRAINT fk_advisor_id
    FOREIGN KEY (advisor_id)
    REFERENCES user (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
# Populate AdvisingTasks table
INSERT INTO advising_task (id, advisor_id, description, duration) VALUES 
	(1, 2, 'Academic Suggestions', 30), 
    (2, 2, 'Course Selection', 30), 
    (3, 2, 'Swap Classes', 15),
    (4, 2, 'Drop Classes', 15),
    (5, 4, 'General Advising', 30),
    (6, 4, 'Graduation Review', 30);
    
# Create Appointment table
CREATE TABLE appointment (
  id INT PRIMARY KEY AUTO_INCREMENT,
  advisor_id INT NOT NULL,
  student_id INT NOT NULL,
  start_time DATETIME NOT NULL,
  end_time DATETIME NOT NULL,
  student_notes VARCHAR(255),
  advisor_notes VARCHAR(255),
  status_id INT NOT NULL,
  task_id INT NOT NULL,
  INDEX fk_student_user_id_idx (student_id ASC),
  CONSTRAINT fk_student_user_id
    FOREIGN KEY (student_id)
    REFERENCES user (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  INDEX fk_advisor_user_id_idx (advisor_id ASC),
  CONSTRAINT fk_advisor_user_id
	FOREIGN KEY (advisor_id)
	REFERENCES user (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_appointment_status
	FOREIGN KEY (status_id)
    REFERENCES appointment_status (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_task_id
	FOREIGN KEY (task_id)
    REFERENCES advising_task (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);