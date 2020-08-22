package pck;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "player", schema = "league", catalog = "")
public class PlayerEntity {
    private String id;
    private String name;
    private String posiiton;
    private Double salary;
    private Collection<TeamPlayerEntity> teamPlayersById;

    @Id
    @Column(name = "id", nullable = false, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "posiiton", nullable = true, length = 255)
    public String getPosiiton() {
        return posiiton;
    }

    public void setPosiiton(String posiiton) {
        this.posiiton = posiiton;
    }

    @Basic
    @Column(name = "salary", nullable = true, precision = 0)
    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerEntity that = (PlayerEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(posiiton, that.posiiton) &&
                Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, posiiton, salary);
    }

    @OneToMany(mappedBy = "playerByPlayerId")
    public Collection<TeamPlayerEntity> getTeamPlayersById() {
        return teamPlayersById;
    }

    public void setTeamPlayersById(Collection<TeamPlayerEntity> teamPlayersById) {
        this.teamPlayersById = teamPlayersById;
    }
}
