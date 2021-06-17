package galerie.entity;

import java.time.LocalDate;
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
public class Trans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NonNull
    @ManyToOne
    public Personne client;

    @NonNull
    @OneToOne
    public Tableau oeuvre;

    @NonNull
    @ManyToOne
    public Exposition lieuDeVente;

    @NonNull
    public Float prixVente;

    public LocalDate venduLe = LocalDate.now();

    public float getPrixVente() {
        return prixVente;
    }
}
