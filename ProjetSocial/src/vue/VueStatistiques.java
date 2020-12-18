package vue;

import java.util.HashMap;
import java.util.List;

import com.sun.media.jfxmedia.logging.Logger;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import modele.PageStatistique;
import modele.StatistiqueUtilisateur;

public class VueStatistiques extends Vue{

	protected Controleur controleur = null;
	public static VueStatistiques instance;
	public static VueStatistiques getInstance() {if(null == instance)instance = new VueStatistiques(); return instance;}
	
	private VueStatistiques () {
		super("vue_statistiques.fxml");
		super.controleur = this.controleur = new Controleur();
		Logger.logMsg(Logger.INFO, "new VueAcceuil()");
	}
	
	public Controleur getControleur() {return this.controleur;}
	
	public void activerControles(){
		super.activerControles();
		
		Button actionAccueil = (Button) lookup("#btn-accueil");
		actionAccueil.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override public void handle(ActionEvent e) 
            {
            	Logger.logMsg(Logger.INFO, "Bouton Accueil Activer");
            	VueAccueil.getInstance().getControleur().actionOuvrirAccueil(VueAccueil.getInstance());
            }
        });
		
		Button actionListeSalon = (Button) lookup("#btn-salons");
		actionListeSalon.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override public void handle(ActionEvent e) 
            {
            	Logger.logMsg(Logger.INFO, "Bouton Accueil Activer");
            	VueSalons.getInstance().getControleur().actionOuvrirListeSalons(VueSalons.getInstance());
            }
        });
		
		Button actionChatPrive = (Button) lookup("#btn-chat");
		actionChatPrive.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override public void handle(ActionEvent e) 
            {
            	Logger.logMsg(Logger.INFO, "Bouton Suivie Activer");
            	VueChatPrive.getInstance().getControleur().actionOuvrirChatPrive(VueChatPrive.getInstance());
            }
        });
		
		Button actionParametre = (Button) lookup("#btn-parametres");
		actionParametre.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override public void handle(ActionEvent e) 
            {
            	Logger.logMsg(Logger.INFO, "Bouton Suivie Activer");
            	VueParametre.getInstance().getControleur().actionOuvrirParametre(VueParametre.getInstance());
            }
        });
		
		/*Button actionStatistiques = (Button) lookup("#btn-statistiques");
		actionStatistiques.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override public void handle(ActionEvent e) 
            {
            	Logger.logMsg(Logger.INFO, "Bouton Suivie Activer");
            	VueStatistiques.getInstance().getControleur().actionOuvrirStatistiques(VueStatistiques.getInstance());
            }
        });*/
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void afficherPageStatistique(PageStatistique page) 
	{
		//Afficher le graphique
		
		Pane pane = (Pane) lookup("#vue-statistique-pane");
		pane.getChildren().clear();
		
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("heures");
		NumberAxis yAxis = new NumberAxis(0, page.getValeurGraphiqueMaximale() + 1, 1);
		yAxis.setLabel("frequence");
		
		LineChart<String, Number> chart = new LineChart<String, Number>(xAxis, yAxis);
		chart.setTitle("Message par heure durant les 12 derni�res heures");
		
		List<StatistiqueUtilisateur> listeUtilisateur = page.getListeStatistiqueUtilisateur();
		for (StatistiqueUtilisateur utilisateur : listeUtilisateur) {	
			XYChart.Series serie = new XYChart.Series();
			serie.setName(utilisateur.getNom());
			
			List<HashMap<String, Integer>> listeStatistiques = utilisateur.getListeStatistique();
			for (HashMap<String, Integer> statistique : listeStatistiques) {
				serie.getData().add(new XYChart.Data<>(statistique.get("heure") + "h00", statistique.get("frequence")));
			}
			chart.getData().add(serie);
		}
		
		chart.setPrefWidth(pane.getPrefWidth());
		chart.setPrefHeight(pane.getPrefHeight());
		pane.getChildren().add(chart);
		
		//Afficher la statistique personelle (Nombre total de messages depuis 12h)
		Label labelNombreMessages = (Label) lookup("#vue-statistique-nombre-messages");
		labelNombreMessages.setText(page.getStatistiquePersonelle() + "");
	}
}
