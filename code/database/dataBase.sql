
/*
testQuestions table holds the questions to the test.
Fields:
@id - the main key for the table
@question - the question itself.
@questionType - an ENUM that holds one of 4 values, indicating the type of the question. The type of the question can be one of the following:
rating question, free text question, multiple choice question, drill down question.
@answer - this feilds holds the correct answer for the question of the question is a multiple choice question or a drill down question. Otherwise it holds NULL.
*/
CREATE TABLE testQuestions(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  question VARCHAR(255) NOT NULL ,
  questionType ENUM(4) NOT NULL,
  answer INT
);


/*
testManager table holds the information about the managers of the test.
Fields:
@id - the main key for the table
@name - the name of the test manager.
@password - the login password for the manager account.
*/
CREATE TABLE testManager(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  password VARCHAR(255)
);

/*
testSubject table holds the information about the testees in a certain test.
Fields:
@id - the main key for the table
@name - the name of the test subject.
@ipAddress - the ip address of the computer from which the test was answered.
@browser - the type of the browser from which the test was answered.
*/
CREATE TABLE testSubject(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  ipAddress INTEGER,
  browser VARCHAR(255)
);

/*
project table holds the information about a specific test.
Fields:
@id - the main key for the table
@name - the name of the test.
@managerId - the id of the testManager who created this test.
@numberOfSubjects - the number of testees who answered this test.
@state - the state of the project. Can hold one of 3 values: new, active, finished.
@lastModified - the date in which the project was last modified.
@lastAnswered - the date in which the test was last answered.
@numbeOfFiledCopies - the number of times the test was answered.
@numberOfQuestions - the number of questions in the test.
*/
CREATE TABLE project (id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  managerId INTEGER NOT NULL ,
  numberOfSubjects INTEGER NOT NULL ,
  state ENUM(3) NOT NULL ,
  lastModified DATE NOT NULL ,
  lastAnswered DATE NOT NULL ,
  numberOfFiledCopies INT NOT NULL ,
  numberOfQuestions INT NOT NULL
  FOREIGN KEY(managerId) REFERENCES(testManager(id)),
);

/*
testAnswer table holds the information about a specific answer to a question.
Fields:
@id - the main key for the table
@testeeId - the id of the test subject who gave this answer.
@questionId - the id of the question that correspons to the answer.
@numberOfClicks - the number of mouse clicks the test subject did while answering.
@finalAnswer - the answer to the question for a drill down, multiple choice, or rating questions.
@verabalAnswer - the answer for the question, in case it's a free text question.
@answerPlacement - the the place of the answer on the screen. can hold one of 8 possible values.
@questionPlacement - the the place of the question on the screen. can hold one of 8 possible values.
@timeToAnswer - the time it took the testee to answer the question.
@timeMeasured - a boolean that holds true if time was measured for this question, and false otherwise.
@timeShowed - a boolean that holds true if the time taken was shown to the testee, and false otherwise.
@testeeExited - a boolean that holds true if the testee existed the test frame during the test, and false otherwise.
*/
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
