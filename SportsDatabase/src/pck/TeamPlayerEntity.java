package pck;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "team_player", schema = "league", catalog = "")
public class TeamPlayerEntity {
    private String playerId;
    private String teamId;
    private PlayerEntity playerByPlayerId;
    private TeamEntity teamByTeamId;

    @Basic
    @Column(name = "player_id", nullable = false, length = 255)
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Basic
    @Column(name = "team_id", nullable = false, length = 255)
    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamPlayerEntity that = (TeamPlayerEntity) o;
        return Objects.equals(playerId, that.playerId) &&
                Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, teamId);
    }

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    public PlayerEntity getPlayerByPlayerId() {
        return playerByPlayerId;
    }

    public void setPlayerByPlayerId(PlayerEntity playerByPlayerId) {
        this.playerByPlayerId = playerByPlayerId;
    }

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    public TeamEntity getTeamByTeamId() {
        return teamByTeamId;
    }

    public void setTeamByTeamId(TeamEntity teamByTeamId) {
        this.teamByTeamId = teamByTeamId;
    }
}
