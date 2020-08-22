package pck;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "student", schema = "advancedjava", catalog = "")
public class StudentEntity {
    private String ssn;
    private String fName;
    private String mi;
    private String lName;
    private Date bDate;
    private String street;
    private String phone;
    private String zipCode;
    private String deptId;
    private Collection<EnrollmentEntity> enrollmentsBySsn;

    @Id
    @Column(name = "ssn", nullable = false, length = 9)
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Basic
    @Column(name = "fName", nullable = true, length = 25)
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    @Basic
    @Column(name = "mi", nullable = true, length = 1)
    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    @Basic
    @Column(name = "lName", nullable = true, length = 25)
    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @Basic
    @Column(name = "bDate", nullable = true)
    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    @Basic
    @Column(name = "street", nullable = true, length = 25)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "zipCode", nullable = true, length = 5)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "deptId", nullable = true, length = 4)
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return Objects.equals(ssn, that.ssn) &&
                Objects.equals(fName, that.fName) &&
                Objects.equals(mi, that.mi) &&
                Objects.equals(lName, that.lName) &&
                Objects.equals(bDate, that.bDate) &&
                Objects.equals(street, that.street) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(deptId, that.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssn, fName, mi, lName, bDate, street, phone, zipCode, deptId);
    }

    @OneToMany(mappedBy = "studentBySsn")
    public Collection<EnrollmentEntity> getEnrollmentsBySsn() {
        return enrollmentsBySsn;
    }

    public void setEnrollmentsBySsn(Collection<EnrollmentEntity> enrollmentsBySsn) {
        this.enrollmentsBySsn = enrollmentsBySsn;
    }
}
