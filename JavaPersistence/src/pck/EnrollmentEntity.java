package pck;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "enrollment", schema = "advancedjava", catalog = "")
@IdClass(EnrollmentEntityPK.class)
public class EnrollmentEntity {
    private String ssn;
    private String courseId;
    private Date dateReg;
    private String grade;
    private StudentEntity studentBySsn;
    private CourseEntity courseByCourseId;

    @Id
    @Column(name = "ssn", nullable = false, length = 9)
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Id
    @Column(name = "courseID", nullable = false, length = 5)
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "dateReg", nullable = true)
    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    @Basic
    @Column(name = "grade", nullable = true, length = 1)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentEntity that = (EnrollmentEntity) o;
        return Objects.equals(ssn, that.ssn) &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(dateReg, that.dateReg) &&
                Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssn, courseId, dateReg, grade);
    }

    @ManyToOne
    @JoinColumn(name = "ssn", referencedColumnName = "ssn", nullable = false)
    public StudentEntity getStudentBySsn() {
        return studentBySsn;
    }

    public void setStudentBySsn(StudentEntity studentBySsn) {
        this.studentBySsn = studentBySsn;
    }

    @ManyToOne
    @JoinColumn(name = "courseID", referencedColumnName = "courseID", nullable = false)
    public CourseEntity getCourseByCourseId() {
        return courseByCourseId;
    }

    public void setCourseByCourseId(CourseEntity courseByCourseId) {
        this.courseByCourseId = courseByCourseId;
    }
}
