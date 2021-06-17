package galerie.entity;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity // Une entité JPA
public class Exposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NonNull
    public String intitule;

    public LocalDate debut = LocalDate.now();

    public Integer duree = 10;

    @ManyToOne
    @NonNull
    Galerie organisateur;

    @ManyToMany
    @ToString.Exclude
    List<Tableau> oeuvres = new LinkedList<>();

    @OneToMany(mappedBy = "lieuDeVente")
    @ToString.Exclude
    public List<Trans> ventes = new LinkedList<>();

    public float CA() {
        float result = 0.0f;
        for (Trans vente : ventes)
            result += vente.getPrixVente();
        return result;
        // Ca peut s'écrire en une seule ligne avec l'API Stream API.
        // cf. https://www.baeldung.com/java-stream-sum
        // return ventes.stream().map(vente -> vente.getPrixVente()).reduce(0f,
        // Float::sum);
    }

    public Object getDebut() {
        return this.debut;
    }

    public String getIntitule(){
        return this.intitule;
    }

}
