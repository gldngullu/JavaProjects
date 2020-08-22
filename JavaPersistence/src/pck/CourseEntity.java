package pck;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "course", schema = "advancedjava", catalog = "")
public class CourseEntity {
    private String courseId;
    private String subjectId;
    private Integer courseNum;
    private String title;
    private Integer numCredit;
    private Collection<EnrollmentEntity> enrollmentsByCourseId;

    @Id
    @Column(name = "courseID", nullable = false, length = 5)
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "subjectID", nullable = false, length = 4)
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @Basic
    @Column(name = "courseNum", nullable = true)
    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "numCredit", nullable = true)
    public Integer getNumCredit() {
        return numCredit;
    }

    public void setNumCredit(Integer numCredit) {
        this.numCredit = numCredit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(subjectId, that.subjectId) &&
                Objects.equals(courseNum, that.courseNum) &&
                Objects.equals(title, that.title) &&
                Objects.equals(numCredit, that.numCredit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, subjectId, courseNum, title, numCredit);
    }

    @OneToMany(mappedBy = "courseByCourseId")
    public Collection<EnrollmentEntity> getEnrollmentsByCourseId() {
        return enrollmentsByCourseId;
    }

    public void setEnrollmentsByCourseId(Collection<EnrollmentEntity> enrollmentsByCourseId) {
        this.enrollmentsByCourseId = enrollmentsByCourseId;
    }
}
