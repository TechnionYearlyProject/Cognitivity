package services;
/*The type T has the input types.
  The type _T has the input types plus id from SQL table */
class DatabaseService extends ServiceBlatt {

  //TestManagerService
  public void addTestManager(TestManager tm);

  public _TestManager[] getTestManagers();

  public _TestManager getTestManager(String id);

  //SubjectService
  public void addSubject(Subject s);

  public _Subject[] getSubjects();

  public _Subject getSubject(String id);

  //TestService
  public void addTestForTM(Test t, String testManagerID);

  public _Test[] getTestsForTM(String testManagerID);

  public _Test getTestForTM(String testID, String testManagerID);

  //QuestionService
  public void addQuestion(Question q);

  public _Question[] getQuestions();

  public _Question getQuestion(String id);

  //TestAnswerService
  public void addTestAnswers(String testID,String subjectID, TestAnswer[] testAnswers);

  public _TestAnswer[] getTestAnswers(String testID, String subjectID);

  public _TestAnswer getTestAnswer(String testID, String subjectID, String answerID);

  //Add theme styling properties?






}
