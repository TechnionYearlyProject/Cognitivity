
/*
testManager table holds the information about the managers of the test.
Fields:
@id - the main key for the table
@email - the name of the test manager.
*/
CREATE TABLE testManager(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(256) NOT NULL UNIQUE
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
  name text,
  ipAddress text,
  browser text
);

/*
project table holds the information about a specific test.
Fields:
@id - the main key for the table
@name - the name of the test.
@numberOfSubjects - the number of testees who answered this test.
@state - the state of the project. Can hold one of 3 values: new, active, finished.
@lastModified - the date in which the project was last modified.
@lastAnswered - the date in which the test was last answered.
@numberOfFiledCopies - the number of times the test was answered.
@numberOfQuestions - the number of questions in the test.
@managerId - a reference to the manager who works on this project
*/
CREATE TABLE project (id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name text,
  numberOfSubjects INTEGER NOT NULL ,
  state INT NOT NULL ,
  lastModified DATE NOT NULL ,
  lastAnswered DATE ,
  numberOfFiledCopies INT NOT NULL ,
  numberOfQuestions INT NOT NULL ,
  managerId INT NOT NULL,
  FOREIGN KEY (managerId) REFERENCES testManager(id) ON DELETE CASCADE
);

/**
testBlock table holds the information about each test block.
Fields:
@id - the main key for the table.
@numberOfQuestions - the number of questions in the block.
@randomize - a boolean that holds true if the questions in the block should be randomized, and false otherwise.
@tag - a tag attached to the question

 */
CREATE TABLE testBlock(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  numberOfQuestions INTEGER NOT NULL ,
  randomize BOOLEAN,
  tag text,
  projectId INT NOT NULL,
  FOREIGN KEY (projectId) REFERENCES project(id) ON DELETE CASCADE
);

/*
testQuestion table holds the questions to the test.
Fields:
@id - the main key for the table
@question - the question itself.
@questionType - an ENUM that holds one of 4 values, indicating the type of the question. The type of the question can be one of the following:
rating question, free text question, multiple choice question, drill down question.
@answer - this field holds the correct answer for the question of the question is a multiple choice question or a drill down question. Otherwise it holds NULL.
@project
@block - a reference to the block in which the question is found
*/
CREATE TABLE testQuestion(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  question text NOT NULL ,
  tag text ,
  questionType INT NOT NULL,
  answer text,
  questionPosition INT,
  testManagerId INT NOT NULL,
  projectId INT NOT NULL,
  testBlockId INT NOT NULL,
  FOREIGN KEY (testManagerId) REFERENCES testManager(id) ON DELETE CASCADE,
  FOREIGN KEY (projectId) REFERENCES project(id) ON DELETE CASCADE,
  FOREIGN KEY (testBlockId) REFERENCES testBlock(id) ON DELETE CASCADE
);


/*
testAnswer table holds the information about a specific answer to a question.
Fields:
@id - the main key for the table
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
@testeeId - a reference to the testee who gave this answer.
@questionId - a reference to the question that was answered.
*/
CREATE TABLE testAnswer(id INTEGER PRIMARY KEY AUTO_INCREMENT,
  numberOfClick INTEGER NOT NULL ,
  finalAnswer INTEGER,
  questionPlacement INTEGER NOT NULL ,
  answerPlacement INTEGER NOT NULL ,
  verbalAnswer text,
  questionWithPicture BOOLEAN NOT NULL ,
  timeToAnswer INTEGER NOT NULL ,
  timeMeasured BOOLEAN,
  timeShowed BOOLEAN,
  testeeExit BOOLEAN,
  testeeId INTEGER NOT NULL,
  questionId INTEGER NOT NULL,
  projectId INTEGER NOT NULL ,
  FOREIGN KEY (testeeId) REFERENCES testSubject(id) ON DELETE CASCADE,
  FOREIGN KEY (questionId) REFERENCES testQuestion(id) ON DELETE CASCADE,
  FOREIGN KEY (projectId) REFERENCES project(id) ON DELETE CASCADE
);


