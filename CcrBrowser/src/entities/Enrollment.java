package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "enrollment")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Enrollment.findAll", query = "SELECT e FROM Enrollment e")
        , @NamedQuery(name = "Enrollment.findByCourseCode", query = "SELECT e FROM Enrollment e WHERE e.enrollmentPK.courseCode = :courseCode")
        , @NamedQuery(name = "Enrollment.findByStudentstudentNo", query = "SELECT e FROM Enrollment e WHERE e.enrollmentPK.studentstudentNo = :studentstudentNo")
        , @NamedQuery(name = "Enrollment.findByYear", query = "SELECT e FROM Enrollment e WHERE e.enrollmentPK.year = :year")
        , @NamedQuery(name = "Enrollment.findBySemester", query = "SELECT e FROM Enrollment e WHERE e.enrollmentPK.semester = :semester")
        , @NamedQuery(name = "Enrollment.findByGrade", query = "SELECT e FROM Enrollment e WHERE e.grade = :grade")})
public class Enrollment implements Serializable {


    @EmbeddedId
    protected EnrollmentPK enrollmentPK;
    @Column(name = "grade")
    private String grade;
    @Column(name = "semesterOfStudent")
    private int semesterOfStudent;
    @JoinColumn(name = "course_code", referencedColumnName = "code", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Course course;
    @JoinColumn(name = "student_studentNo", referencedColumnName = "studentNo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;

    public Enrollment() {
    }

    public Enrollment(EnrollmentPK enrollmentPK) {
        this.enrollmentPK = enrollmentPK;
    }

    @XmlTransient
    private int year;
    @XmlTransient
    private String semester;

    public Enrollment(String courseCode, String studentstudentNo, int year, String semester) {
        this.enrollmentPK = new EnrollmentPK(courseCode, studentstudentNo, year, semester);
        this.year = year;
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    public EnrollmentPK getEnrollmentPK() {
        return enrollmentPK;
    }

    public void setEnrollmentPK(EnrollmentPK enrollmentPK) {
        this.enrollmentPK = enrollmentPK;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getSemesterOfStudent() {
        return semesterOfStudent;
    }

    public void setSemesterOfStudent(int semesterOfStudent) {
        this.semesterOfStudent = semesterOfStudent;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enrollmentPK != null ? enrollmentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enrollment)) {
            return false;
        }
        Enrollment other = (Enrollment) object;
        if ((this.enrollmentPK == null && other.enrollmentPK != null) || (this.enrollmentPK != null && !this.enrollmentPK.equals(other.enrollmentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Enrollment[ enrollmentPK=" + enrollmentPK + " ]";
    }

}
