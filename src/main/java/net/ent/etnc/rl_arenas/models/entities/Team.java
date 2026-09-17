package net.ent.etnc.rl_arenas.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import net.ent.etnc.rl_arenas.models.commons.AbstractPersistableWithIdSetter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "TEAMS",
        uniqueConstraints = @UniqueConstraint(name = "uk_TEAM_nom", columnNames = {"nom"}))
@EqualsAndHashCode(callSuper = false, of = {"nom"})
@ToString(callSuper = true, of = {"nom", "tag", "region", "active"})
public class Team extends AbstractPersistableWithIdSetter<Long> {
    @Getter
    @Setter
    @NotNull(message = "nom ne doit pas être null")
    @NotEmpty(message = "nom ne doit pas être vide")
    @NotBlank(message = "nom doit contenir des caractères lisibles")
    @Length(min = 3, max = 50, message = "nom doit avoir entre 3 et 50 caractères")
    @Column(name = "nom", length = 50, nullable = false)
    private String nom;

    @Getter
    @Setter
    @NotNull(message = "tag ne doit pas être null")
    @NotEmpty(message = "tag ne doit pas être vide")
    @NotBlank(message = "tag doit contenir des caractères lisibles")
    @Pattern(regexp = "^[A-Z]{2,5}$")
    @Column(name = "tag", length = 5, nullable = false)
    private String tag;

    @Getter
    @Setter
    @NotNull(message = "region ne doit pas être null")
    @NotEmpty(message = "region ne doit pas être vide")
    @NotBlank(message = "region doit contenir des caractères lisibles")
    @Length(min = 1, max = 5, message = "region doit avoir entre 1 et 5 caractères")
    @Column(name = "region", length = 5, nullable = false)
    private String region;

    @Getter
    @Setter
    @Column(name = "active", nullable = false)
    private boolean active = true;
}