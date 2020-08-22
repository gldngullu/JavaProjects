package pck;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EnrollmentEntityPK implements Serializable {
    private String ssn;
    private String courseId;

    @Column(name = "ssn", nullable = false, length = 9)
    @Id
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Column(name = "courseID", nullable = false, length = 5)
    @Id
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentEntityPK that = (EnrollmentEntityPK) o;
        return Objects.equals(ssn, that.ssn) &&
                Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssn, courseId);
    }
}
