package galerie.dto;

/**
 * Utilisé pour représenter le résultat des requêtes statistiques
 * 
 * @see comptoirs.dao.ExpositionRepository Cette interface sera auto-implémentée
 *      par Spring
 */
public interface CaExposition {
	String getIntitule();

	Float getCA();
}
