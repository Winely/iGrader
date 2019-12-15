# iGrader
> Dongyi He, dongyihe@bu.edu  
> Alexandr Kim, aik2347@bu.edu  
> Xiao Lu, lux@bu.edu  
> Vidya Akavoor, vidyaap@bu.edu

## Overview
From this doc you will see:

## Scope


## Functionality


## Goals


## Model Design
### Overview
We have a really clean and clear class design.
![](img/er-diagram.png)

### How we implement grading scheme
- Each grading item/column is a `Subject`. For example, "Homework" and "Exam" can be two different `Subject`.
- Each `Subject` can be composed of multiple children `Subject`s, as "Homework" can be broke down to "HW 1" and "HW2", then be graded independently.

All `Subject` and its children of a course eventually become a typical **tree** structure:

![](img/tree.png)

In this way, each `Course` just need to hold a root `Subject` for its the scheme.

#### Flexibility & Cleanliness
- Label of `Subject` can be set to anything! No longer restricted by any hardcoded category settings!
- We allow **infinite** depth of the tree! Users can break down as detailed as they want!
- Only **one** class is used for the scheme. All methods, specifically the grade-computation-related logic, can be implemented once and called recursively, making it easy to debug and prevent duplicated codes

### Where are the Grades?
- Each `Grade` holds its `Subject`, `Student` and the `Score`.
- A class `Score` is made to embed actual points and bonus
- Each subject will hold a map from `Student` to `Grade`; Score output can also come from the weighted average of its children

### Course, Section and Student
#### Course
`Course` holds a list of `Section`s, and a root `Subject` for its scheme reference

#### Section
`Section` holds a list of `Student`s, with a label specifying its name. There is a `curve` field marking the curve value for this section.

#### Student
Each `Student` has an ID, a name and a graduation type. A boolean property `frozen` will be set true if the student withdraw the class.

### Commentable
`Subject`, `Student` and `Grade` implemented `Commentable` interface to provide extra comment.

### Dive into Database... 
The project used [Hibernate](https://hibernate.org/) to MySQL database connection and object-relational mapping. Other than intuitive entity annotation within `Model` package, we implemented a `DAO` class and `BaseEntity` class to encapsulate all DB-related "dirty job".

#### Data Access Object (DAO)
The class integrated all direct database access, including session operations and some basic query functionality. Any database access in the project will calling the methods provided in the class.

#### BasicEntity
The abstract class provide `save`, `update` and `refresh` methods to directly update a record in the database. With most of the classes in `Model` implemented this, it provides convenience when our teammate want some simple database CRUD.