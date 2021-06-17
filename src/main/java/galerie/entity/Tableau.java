package galerie.entity;

import java.util.*;
import javax.persistence.*;
import lombok.*;

// Un exemple d'entité
// On utilise Lombok pour auto-générer getter / setter / toString...
// cf. https://examples.javacodegeeks.com/spring-boot-with-lombok/
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity // Une entité JPA
public class Tableau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NonNull
    public String titre;

    public String support;

    public String dimension;

    @OneToOne(mappedBy = "oeuvre")
    @ToString.Exclude
    public Trans vendu;

    @ManyToOne
    // Peut-être null
    Artiste auteur;

    @ToString.Exclude
    @ManyToMany(mappedBy = "oeuvres")
    public List<Exposition> accrochages = new LinkedList<>();

    public String getTitre() {
        return this.titre;
    }

    public String getSupport() {
        return this.support;
    }

}
