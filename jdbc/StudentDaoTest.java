import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentDaoTest {

  @Mock
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private StudentDao studentDao;

  @BeforeEach
  void setup() {
    this.studentDao = new StudentDao(this.namedParameterJdbcTemplate);
  }

  @Test
  void insert() {
    Student student = new Student(
        "10",
        "Chuck Norris",
        "A",
        98,
        "M295"
    );
    this.studentDao.insert(student);
    ArgumentCaptor<MapSqlParameterSource> argumentCaptor =
        ArgumentCaptor.forClass(MapSqlParameterSource.class);
    verify(this.namedParameterJdbcTemplate).update(
        eq("""
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
              """),
        argumentCaptor.capture()
    );
    MapSqlParameterSource mapSqlParameterSource = argumentCaptor.getValue();
    assertThat(mapSqlParameterSource.getValue("studentID")).isEqualTo("10");
    assertThat(mapSqlParameterSource.getValue("studentName")).isEqualTo("Chuck Norris");
    assertThat(mapSqlParameterSource.getValue("grade")).isEqualTo("A");
    assertThat(mapSqlParameterSource.getValue("age")).isEqualTo(98);
    assertThat(mapSqlParameterSource.getValue("module")).isEqualTo("M295");
  }
} 
