package pck;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "league", schema = "league", catalog = "")
public class LeagueEntity {
    private String id;
    private String name;
    private String sport;
    private String dtype;
    private Collection<TeamEntity> teamsById;

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
    @Column(name = "sport", nullable = true, length = 255)
    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    @Basic
    @Column(name = "dtype", nullable = true, length = 255)
    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeagueEntity that = (LeagueEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(sport, that.sport) &&
                Objects.equals(dtype, that.dtype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sport, dtype);
    }

    @OneToMany(mappedBy = "leagueByLeagueId")
    public Collection<TeamEntity> getTeamsById() {
        return teamsById;
    }

    public void setTeamsById(Collection<TeamEntity> teamsById) {
        this.teamsById = teamsById;
    }
}
