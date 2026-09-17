package net.ent.etnc.rl_arenas.models.enums;

import lombok.Getter;

import java.util.Set;

public enum StatutMatch {
    ANNULE("Annule", Set.of()),
    TERMINE("Terminé", Set.of()),
    EN_COURS("En cours", Set.of(TERMINE)),
    PROGRAMME("Programme", Set.of(EN_COURS, ANNULE));

    @Getter
    private final String libelle;
    @Getter
    private final Set<StatutMatch> statutMatches;



    StatutMatch(String libelle, Set<StatutMatch> statutMatches) {
        this.libelle = libelle;
        this.statutMatches = statutMatches;
    }

    public boolean peutPasserA(StatutMatch nouveauStatut) {
        return statutMatches.contains(nouveauStatut);
    }

}
