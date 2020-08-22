package pck;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "team", schema = "league", catalog = "")
public class TeamEntity {
    private String id;
    private String city;
    private String name;
    private String leagueId;
    private LeagueEntity leagueByLeagueId;
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
    @Column(name = "city", nullable = true, length = 255)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
    @Column(name = "league_id", nullable = true, length = 255)
    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamEntity that = (TeamEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(city, that.city) &&
                Objects.equals(name, that.name) &&
                Objects.equals(leagueId, that.leagueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, name, leagueId);
    }

    @ManyToOne
    @JoinColumn(name = "league_id", referencedColumnName = "id")
    public LeagueEntity getLeagueByLeagueId() {
        return leagueByLeagueId;
    }

    public void setLeagueByLeagueId(LeagueEntity leagueByLeagueId) {
        this.leagueByLeagueId = leagueByLeagueId;
    }

    @OneToMany(mappedBy = "teamByTeamId")
    public Collection<TeamPlayerEntity> getTeamPlayersById() {
        return teamPlayersById;
    }

    public void setTeamPlayersById(Collection<TeamPlayerEntity> teamPlayersById) {
        this.teamPlayersById = teamPlayersById;
    }
}
