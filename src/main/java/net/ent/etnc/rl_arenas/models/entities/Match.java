package net.ent.etnc.rl_arenas.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import net.ent.etnc.rl_arenas.models.commons.AbstractPersistableWithIdSetter;
import net.ent.etnc.rl_arenas.models.enums.StatutMatch;
import java.time.LocalDateTime;

@Entity
@Table(name = "MATCH", uniqueConstraints = @UniqueConstraint(
        name = "uk_match_teams_date",
                columnNames = {
                        "team_domicile_id",
                        "team_exterieur_id",
                        "date"
                }
        )
)
@EqualsAndHashCode(callSuper = false, of = {"domicile","exterieur", "date"})
@ToString(callSuper = true, of = {"domicile","exterieur", "date", "statutMatch"})
public class Match extends AbstractPersistableWithIdSetter<Long> {
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_domicile_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_match_team_domicile"))
    private Team domicile;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_exterieur_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_match_team_exterieur"))
    private Team exterieur;
    
    @Getter 
    @Setter
    @NotNull(message = "date ne doit pas être null")
    @Future(message = "date ne doit pas être dans le passé ou dans le présent")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    
    @Getter
    @Setter
    @NotNull(message = "statutMatch ne doit pas être null")
    @Enumerated(EnumType.STRING)
    @Column(name = "statut_match",length = 15, nullable = false)
    private StatutMatch statutMatch = StatutMatch.PROGRAMME;

    @Getter
    @Setter
    @PositiveOrZero(message = "scoreDomicile doit être positif ou nul")
    @Column(name = "score_domicile")
    private Integer scoreDomicile;

    @Getter
    @Setter
    @PositiveOrZero(message = "scoreExterieur doit être positif ou nul")
    @Column(name = "score_exterieur")
    private Integer scoreExterieur;
}