package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author emine
 */
@Embeddable
public class EnrollmentPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "course_code")
    private String courseCode;
    @Basic(optional = false)
    @Column(name = "student_studentNo")
    private String studentstudentNo;
    @Basic(optional = false)
    @Column(name = "year")
    private int year;
    @Basic(optional = false)
    @Column(name = "semester")
    private String semester;

    public EnrollmentPK() {
    }

    public EnrollmentPK(String courseCode, String studentstudentNo, int year, String semester) {
        this.courseCode = courseCode;
        this.studentstudentNo = studentstudentNo;
        this.year = year;
        this.semester = semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStudentstudentNo() {
        return studentstudentNo;
    }

    public void setStudentstudentNo(String studentstudentNo) {
        this.studentstudentNo = studentstudentNo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseCode != null ? courseCode.hashCode() : 0);
        hash += (studentstudentNo != null ? studentstudentNo.hashCode() : 0);
        hash += (int) year;
        hash += (semester != null ? semester.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EnrollmentPK)) {
            return false;
        }
        EnrollmentPK other = (EnrollmentPK) object;
        if ((this.courseCode == null && other.courseCode != null) || (this.courseCode != null && !this.courseCode.equals(other.courseCode))) {
            return false;
        }
        if ((this.studentstudentNo == null && other.studentstudentNo != null) || (this.studentstudentNo != null && !this.studentstudentNo.equals(other.studentstudentNo))) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if ((this.semester == null && other.semester != null) || (this.semester != null && !this.semester.equals(other.semester))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  courseCode + " " + studentstudentNo + ", year=" + year + ", semester=" + semester + ": ";
    }

}
