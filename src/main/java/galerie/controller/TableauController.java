package galerie.controller;

import galerie.dao.ArtisteRepository;
import galerie.dao.TableauRepository;
import galerie.entity.Tableau;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@Controller
@RequestMapping(path = "/tableau")
public class TableauController {

    private final TableauRepository tableauDAO;

    private final ArtisteRepository artisteDAO;

    public TableauController(TableauRepository tableauDAO, ArtisteRepository artisteDAO) {
        this.tableauDAO = tableauDAO;
        this.artisteDAO = artisteDAO;
    }

    /**
     * Affiche toutes les tableaux dans la base
     * 
     * @param model pour transmettre les informations à la vue
     * @return le nom de la vue à afficher ('afficheTableaux.html')
     */
    @GetMapping(path = "show")
    public String afficheLesTableaux(Model model) {
        model.addAttribute("tableaux", tableauDAO.findAll());
        return "afficheTableaux";
    }

    /**
     * Montre le formulaire permettant d'ajouter un tableau
     * 
     * @param tableau initialisé par Spring, valeurs par défaut à afficher dans le
     *                formulaire
     * @param model   pour transmettre des informations à la vie
     * @return le nom de la vue à afficher ('formulaireTableau.html')
     */
    @GetMapping(path = "add")
    public String FormulaireAjout(@ModelAttribute("tableau") Tableau tableau, Model model) {
        // Tableau tableau = new Tableau();
        // model.addAttribute("tableau", tableau) ;
        // On transmet la liste des artistes pour pouvoir choisir l'auteur du tableau
        model.addAttribute("artistes", artisteDAO.findAll());
        return "formulaireTableau";
    }

    /**
     * Appelé par 'formulaireCategorie.html', méthode POST
     * 
     * @param tableau      Un tableau initialisé avec les valeurs saisies dans le
     *                     formulaire
     * @param redirectInfo pour transmettre des paramètres lors de la redirection
     * @return une redirection vers l'affichage de la liste des galeries
     */
    @PostMapping(path = "save")
    public String ajouteTableauMontreListe(Tableau tableau, RedirectAttributes redirectInfo) {
        String message;
        // tableau.setAuteur(auteur);
        // cf. https://www.baeldung.com/spring-data-crud-repository-save
        tableauDAO.save(tableau);
        message = "Le tableau '" + tableau.getTitre() + "' a été correctement enregistré";
        // RedirectAttributes permet de transmettre des informations lors d'une
        // redirection,
        // Ici on transmet un message de succès ou d'erreur
        // Ce message est accessible et affiché dans la vue 'afficheGalerie.html'
        redirectInfo.addFlashAttribute("message", message);
        return "redirect:show"; // POST-Redirect-GET : on se redirige vers l'affichage de la liste
    }

    /**
     * Appelé par le lien 'Supprimer' dans 'afficheTableaux.html'
     * 
     * @param tableau      à partir de l'id du tableau transmis en paramètre, Spring
     *                     fera une requête SQL SELECT pour chercher le tableau dans
     *                     la base
     * @param redirectInfo pour transmettre des paramètres lors de la redirection
     * @return une redirection vers l'affichage de la liste des tableaux
     */
    @GetMapping(path = "delete")
    public String supprimeTableau(@RequestParam("id") Tableau tableau,
            RedirectAttributes redirectInfo) {
        String message;
        try {
            tableauDAO.delete(tableau); // Ici on peut avoir une erreur (Si il y a des produits dans cette catégorie par
                                        // exemple)
            message = "Le tableau '" + tableau.getTitre() + "' a bien été supprimé";
        } catch (DataIntegrityViolationException e) {
            // Par exemple, si le tableau est dans une exposition
            message = "Erreur : Impossible de supprimer Le tableau '" + tableau.getTitre() + "'";
        }
        // RedirectAttributes permet de transmettre des informations lors d'une
        // redirection,
        // Ici on transmet un message de succès ou d'erreur
        // Ce message est accessible et affiché dans la vue 'afficheGalerie.html'
        redirectInfo.addFlashAttribute("message", message);
        return "redirect:show"; // on se redirige vers l'affichage de la liste
    }
}
