CREATE TABLE testQuestions(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  question VARCHAR(255) NOT NULL ,
  questionType ENUM(4) NOT NULL,
  answer INT
);

CREATE TABLE testManager(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255)
);


CREATE TABLE testSubject(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  ipAddress INTEGER,
  browser VARCHAR(255)
);

CREATE TABLE project (id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  managerId INTEGER NOT NULL ,
  numberOfTestees INTEGER NOT NULL ,
  state ENUM(3) NOT NULL ,
  lastModified DATE NOT NULL ,
  lastAnswered DATE NOT NULL ,
  numberOfFiledCopies INT NOT NULL ,
  numberOfQuestions INT NOT NULL
  FOREIGN KEY(managerId) REFERENCES(testManager(id)),
);

CREATE TABLE testAnswer(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  testeeId INTEGER NOT NULL ,
  questionId INTEGER NOT NULL ,
  projectId INTEGER NOT NULL ,
  numberOfClick INTEGER NOT NULL ,
  finalAnswer INTEGER,
  questionPlacement ENUM(9) NOT NULL ,
  answerPlacement ENUM(9) NOT NULL ,
  verbalAnswer VARCHAR(255),
  questionWithPicture BOOLEAN NOT NULL ,
  timeToAnswer TIME NOT NULL ,
  timeMeasured BOOLEAN,
  timeShowed BOOLEAN,
  testeeExit BOOLEAN,
  FOREIGN KEY(testeeId) REFERENCES(testSubject(id)),
  FOREIGN KEY(questionId) REFERENCES(testQuestions(id)),
  FOREIGN KEY(projectId) REFERENCES(project(id)),
);
