# iGrader
> Dongyi He, dongyihe@bu.edu  
> Alexandr Kim, aik2347@bu.edu  
> Xiao Lu, lux@bu.edu  
> Vidya Akavoor, vidyaap@bu.edu

## Tech Stack
iGrader is a JavaFX GUI program and connecting to local MySQL database with JDBC.

These external libraries are used:
- Hibernate
- JDBC
- JavaFX

## How to run
### Database configuration
1. Copy `src/hibernate.cfg.xml.template` to `src/hibernate.cfg.xml`
2. Edit `src/hibernate.cfg.xml`: put YOUR local username and password in it
```xml
        <property name="connection.username">YOUR USERNAME</property>
        <property name="connection.password">YOUR PASSWORD</property>
```

### Database initialization
1. Run code below to create a new database `igrader` for this project
```mysql
CREATE DATABASE igrader;
USE igrader;
```
2. Run this code to create tables in database:
```mysql
CREATE TABLE `subject` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `label` VARCHAR(255) NOT NULL,
  `maxpoint` DOUBLE NOT NULL,
  `maxbonus` DOUBLE NOT NULL,
  `weight` DOUBLE NOT NULL,
  `parent` INTEGER,
  `comment` VARCHAR(255) NOT NULL
);

CREATE INDEX `idx_subject__parent` ON `subject` (`parent`);

ALTER TABLE `subject` ADD CONSTRAINT `fk_subject__parent` FOREIGN KEY (`parent`) REFERENCES `subject` (`id`) ON DELETE SET NULL;

CREATE TABLE `course` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `scheme` INTEGER NOT NULL
);

CREATE INDEX `idx_course__scheme` ON `course` (`scheme`);

ALTER TABLE `course` ADD CONSTRAINT `fk_course__scheme` FOREIGN KEY (`scheme`) REFERENCES `subject` (`id`);

CREATE TABLE `section` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `label` VARCHAR(255) NOT NULL,
  `course` INTEGER NOT NULL,
  `curve` DOUBLE NOT NULL
);

CREATE INDEX `idx_section__course` ON `section` (`course`);

ALTER TABLE `section` ADD CONSTRAINT `fk_section__course` FOREIGN KEY (`course`) REFERENCES `course` (`id`) ON DELETE CASCADE;

CREATE TABLE `student` (
  `id` VARCHAR(255) PRIMARY KEY,
  `section` INTEGER NOT NULL,
  `isgrad` BOOLEAN NOT NULL,
  `firstname` VARCHAR(255) NOT NULL,
  `midname` VARCHAR(255) NOT NULL,
  `lastname` VARCHAR(255) NOT NULL,
  `frozen` BOOLEAN NOT NULL,
  `comment` VARCHAR(255) NOT NULL
);

CREATE INDEX `idx_student__section` ON `student` (`section`);

ALTER TABLE `student` ADD CONSTRAINT `fk_student__section` FOREIGN KEY (`section`) REFERENCES `section` (`id`) ON DELETE CASCADE;

CREATE TABLE `grade` (
  `student` VARCHAR(255) NOT NULL,
  `subject` INTEGER NOT NULL,
  `point` DOUBLE,
  `bonus` DOUBLE NOT NULL,
  `comment` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`student`, `subject`)
);

CREATE INDEX `idx_grade__subject` ON `grade` (`subject`);

ALTER TABLE `grade` ADD CONSTRAINT `fk_grade__student` FOREIGN KEY (`student`) REFERENCES `student` (`id`) ON DELETE CASCADE;

ALTER TABLE `grade` ADD CONSTRAINT `fk_grade__subject` FOREIGN KEY (`subject`) REFERENCES `subject` (`id`) ON DELETE CASCADE
```

### Run it
The entry of the project is `src/View/Main:main`

