package cognitivity.repositories;

import javax.persistence.*;
import java.sql.Date;

import org.springframework.stereotype.Repository;

/**
 *
 * Data Access Object representation of a repository of cognitive tests.
 *
 * This class will use hibernate to pull and push data from the sql data server.
 * This data will be a representation of a cognitive test.
 *
 */

@Repository
@Entity
@Table(name = "project")
public class CognitiveTestRepository extends AbstractRepository {
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id")
    private TestManager manager;

    @Column(name = "numberOfTestees", nullable = false)
    private Integer numberOfTestees;

    @Column(name = "state", nullable = false)
    private Integer state;

    @Column(name = "lastModified", nullable = false)
    private Date lastModified;

    @Temporal(TemporalType.DATE)
    @Column(name = "lastAnswered", nullable = false)
    private String lastAnswered;

    @Column(name = "numberOfFiledCopies", nullable = false)
    private Integer numberOfFiledCopies;

    @Column(name = "numberOfQuestions", nullable = false)
    private Integer numberOfQuestions;

   /**
   * Returns value of name
   * @return
   */
   public String getName() {
      return name;
   }

   /**
   * Sets new value of name
   * @param
   */
   public void setName(String name) {
      this.name = name;
   }

   /**
   * Returns value of manager
   * @return
   */
   public TestManager getManager() {
      return manager;
   }

   /**
   * Sets new value of manager
   * @param
   */
   public void setManager(TestManager manager) {
      this.manager = manager;
   }

   /**
   * Returns value of numberOfTestees
   * @return
   */
   public Integer getNumberOfTestees() {
      return numberOfTestees;
   }

   /**
   * Sets new value of numberOfTestees
   * @param
   */
   public void setNumberOfTestees(Integer numberOfTestees) {
      this.numberOfTestees = numberOfTestees;
   }

   /**
   * Returns value of state
   * @return
   */
   public Integer getState() {
      return state;
   }

   /**
   * Sets new value of state
   * @param
   */
   public void setState(Integer state) {
      this.state = state;
   }

   /**
   * Returns value of lastModified
   * @return
   */
   public Date getLastModified() {
      return lastModified;
   }

   /**
   * Sets new value of lastModified
   * @param
   */
   public void setLastModified(Date lastModified) {
      this.lastModified = lastModified;
   }

   /**
   * Returns value of lastAnswered
   * @return
   */
   public String getLastAnswered() {
      return lastAnswered;
   }

   /**
   * Sets new value of lastAnswered
   * @param
   */
   public void setLastAnswered(String lastAnswered) {
      this.lastAnswered = lastAnswered;
   }

   /**
   * Returns value of numberOfFiledCopies
   * @return
   */
   public Integer getNumberOfFiledCopies() {
      return numberOfFiledCopies;
   }

   /**
   * Sets new value of numberOfFiledCopies
   * @param
   */
    public void setNumberOfFiledCopies(Integer numberOfFiledCopies) {
        this.numberOfFiledCopies = numberOfFiledCopies;
    }

    /**
    * Returns value of numberOfQuestions
    * @return
    */
    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    /**
    * Sets new value of numberOfQuestions
    * @param
    */
    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}
