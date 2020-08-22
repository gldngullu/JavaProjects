package entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author emine
 */
@Entity
@Table(name = "student")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
        , @NamedQuery(name = "Student.findByStudentNo", query = "SELECT s FROM Student s WHERE s.studentNo = :studentNo")
        , @NamedQuery(name = "Student.findByGraduationDate", query = "SELECT s FROM Student s WHERE s.graduation_date = :graduationDate")})
public class Student implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "studentNo")
    private String studentNo;
    @Basic(optional = false)
    @Column(name = "isMinor")
    private boolean isMinor;
    @Basic(optional = false)
    @Column(name = "graduation_date")
    private Date graduation_date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<Enrollment> enrollmentList;

    public Student() {
    }

    public Student(String studentNo) {
        this.studentNo = studentNo;
    }

    public Student(String studentNo, Boolean isMinor, Date graduation_date) {
        this.studentNo = studentNo;
        this.isMinor = isMinor;
        this.graduation_date = graduation_date;
    }

    public Student(String studentNo, Date graduation_date) {
        this.studentNo = studentNo;
        this.graduation_date = graduation_date;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public Date getGraduation_date() {
        return graduation_date;
    }

    public boolean isMinor() {
        return isMinor;
    }

    public void setGraduation_date(Date graduation_date) {
        this.graduation_date = graduation_date;
    }

    public void setMinor(boolean minor) {
        isMinor = minor;
    }

    @XmlTransient
    public List<Enrollment> getEnrollmentList() {
        return enrollmentList;
    }

    public void setEnrollmentList(List<Enrollment> enrollmentList) {
        this.enrollmentList = enrollmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentNo != null ? studentNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentNo == null && other.studentNo != null) || (this.studentNo != null && !this.studentNo.equals(other.studentNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return studentNo + " ";
    }

}
