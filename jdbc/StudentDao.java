import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class StudentDao {

  private final static String SELECT_BY_ID = """
      SELECT * FROM Student WHERE studentID = :studentID
      """;

  private final static String INSERT_STUDENT = """
      INSERT INTO Student(
        studentID,
        studentName,
        grade,
        age,
        module
      ) VALUES (
        :studentID,
        :studentName,
        :grade,
        :age,
        :module
      )
      """;

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public StudentDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  public Optional<Student> findById(String studentId) {
    List<Student> studentList = this.namedParameterJdbcTemplate.query(
        SELECT_BY_ID,
        new MapSqlParameterSource()
            .addValue("studentID", studentId),
        (ResultSet rs, int rowNum) ->
            new Student(
                rs.getString("studentID"),
                rs.getString("studentName"),
                rs.getString("grade"),
                rs.getInt("age"),
                rs.getString("module")
            )
    );
    if (studentList.size() > 1) throw new IllegalStateException("There must not be more than one entry" +
        "in the DB because studentID column is a primary key.");
    else if (studentList.isEmpty()) return Optional.empty();
    else return Optional.of(studentList.get(0));
  }

  public int insert(Student student) {
    return this.namedParameterJdbcTemplate.update(INSERT_STUDENT, new MapSqlParameterSource()
        .addValue("studentID", student.studentId())
        .addValue("studentName", student.studentName())
        .addValue("grade", student.grade())
        .addValue("age", student.age())
        .addValue("module", student.module())
    );
  }
}

