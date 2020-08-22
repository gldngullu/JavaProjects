package entities;
import java.io.Serializable;
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
@Table(name = "course")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c")
        , @NamedQuery(name = "Course.findByCode", query = "SELECT c FROM Course c WHERE c.code = :code")
        , @NamedQuery(name = "Course.findByCourseName", query = "SELECT c FROM Course c WHERE c.slotName = :courseName")
        , @NamedQuery(name = "Course.findByCredit", query = "SELECT c FROM Course c WHERE c.credit = :credit")})

public class Course implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "slotName")
    private String slotName;

    @Basic(optional = false)
    @Column(name = "credit")
    private int credit;

    @Basic(optional = false)
    @Column(name = "slotCode")
    private String slotCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<Enrollment> enrollmentList;

    public Course() {
    }

    public Course(String code) {
        this.code = code;
    }

    public Course(String code, String courseName, int credit) {
        this.code = code;
        this.slotName = courseName;
        this.credit = credit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getSlotCode() {
        return slotCode;
    }

    public void setSlotCode(String slotCode) {
        this.slotCode = slotCode;
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
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return code+" ";
    }

}
