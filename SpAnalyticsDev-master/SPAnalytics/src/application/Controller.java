package application;


import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.css.Style;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.misc.GC;

public class Controller {

	@FXML BorderPane bp;

	//login scene variables
	@FXML
	private JFXButton				loginButton;
	@FXML
	private ChoiceBox<String>		rosterList;
	@FXML
	private ComboBox<String>		users;
	@FXML
	private PasswordField			adminPass;
//	@FXML
//	public Button 					exitButton; // soon to be implemented exit button

	//Player/Goalie card variables
	@FXML ComboBox GamePicker;
	@FXML ListView ClipList;
	@FXML ListView GameList;
	ArrayList<Clip> goalieClips;
	Clip currentClip;
	ArrayList<Object> gamesList;
	ArrayList<String> playersList;
	private static String currentUser;

	//key scene variables
	@FXML
	private JFXButton			keySubmit;
	@FXML
	private TextField			databaseKey;

	//Scoring Chances vars
	@FXML private Canvas AwaySCCanvas;
	@FXML private Canvas HomeSCCanvas;
	private GraphicsContext homeGC1;
	private GraphicsContext awayGC1;
	private ArrayList<DrawnObject> homeSCItems = new ArrayList<DrawnObject>();
	private ArrayList<DrawnObject> awaySCItems = new ArrayList<DrawnObject>();
	@FXML private Label homeChartFail;
	@FXML private Label homeChartSuccess;
	@FXML private Label awayChartFail;
	@FXML private Label awayChartSuccess;

	//netChart variables
	@FXML private Canvas AwayNetChartCanvas;
	@FXML private Canvas HomeNetChartCanvas;
	private Canvas netChartCanvas;
	private GraphicsContext awayGC;
	private GraphicsContext homeGC;
	private int ovalWidth;
	@FXML private Label homeNetChartFail;
	@FXML private Label homeNetChartSuccess;
	@FXML private Label awayNetChartFail;
	@FXML private Label awayNetChartSuccess;
	
	//possession chart variables
	@FXML private Canvas PossessionDiagramCanvas;
	@FXML private ColorPicker possCP;
	private GraphicsContext possGC;
	
	//Shot Chart variables 
	@FXML private ComboBox<String> teamForCombo;
	@FXML private ComboBox<String> teamAgainstCombo;
	@FXML private ComboBox<String> playerCombo;
	@FXML private ComboBox<String> statusCombo;
	@FXML private ComboBox<String> shotCombo;
	@FXML private ComboBox<String> shotTypeCombo;
	@FXML private ComboBox<String> rbCombo;
	@FXML private ComboBox<String> goalieCombo;
	@FXML private ComboBox<String> extraInfoCombo;
	
	@FXML private ComboBox<String> playTypeCombo;
	@FXML private ComboBox<String> playerStatusCombo;
	@FXML private ComboBox<String> releaseTypeCombo;
	@FXML private ComboBox<String> pChanceCombo;
	@FXML private ComboBox<String> sChanceCombo;
	@FXML private ComboBox<String> createdByCombo;
	@FXML private ComboBox<String> resultCombo;
	@FXML private ComboBox<String> scoringChanceCombo;
	@FXML private TextField urlTextField;

	private ArrayList<DrawnObject> homeNetChartItems = new ArrayList<DrawnObject>();
	private int homeNetChartIndex = 0;
	private ArrayList<DrawnObject> awayNetChartItems = new ArrayList<DrawnObject>();
	private int awayNetChartIndex = 0;

	//Video tab scene variables
	@FXML 
	private TextField NCHC_URL;

	//Timer variables
	@FXML private Label Time;
	private long timerStart;
	private long timerPause = 0;
	private long currentTime = 0;
	static boolean timerOn = false;	
	private static int periodIndex = 0;
	private final static String[] PERIODS = {"1st", "2nd", "3rd", "OT"};
	private ArrayList<Clip> clips = new ArrayList<Clip>();

	//TimeStamp variables
	@FXML private ComboBox<String> TimeStamps;
	@FXML private TextArea TimeStampNotes;

	//RinkDiagram variables
	@FXML private Canvas RinkCanvas;
	@FXML private ColorPicker RinkCP;
	@FXML private Slider RinkSlider;
	@FXML private ToggleGroup RinkGroup;
	@FXML private TextArea RinkDiagramText;
	@FXML private ListView<String> PlayerList;
	private GraphicsContext rinkGC;
	private ArrayList<DrawnObject> drawList = new ArrayList<DrawnObject>();
	private DrawnObject line;
	@FXML private Label 				diagramSuccess;
	@FXML private Label 				titleAndLinkSuccess;
	@FXML private Label 				clipsSuccess;
	@FXML private Label					selectGameFirst;

	//instance variables
	private Scene					scene;
	private Stage					primaryStage;
	private FXMLLoader				fxmlLoader;
	private Parent					parent;

	private boolean	isLogin		= true;

	private final String	LOGIN_SCENE				= "/View/SPAnalytics-Login.fxml";
	private final String	PLAYER_HOME				= "/View/PlayerHome.fxml";
	private final String	GOALIE_HOME				= "/View/GoalieHome.fxml";
	private final String	TEAM_PROFILE			= "/View/TeamProfile.fxml";
	private final String	PLAYER_CARD				= "/View/SPAnalytics-playerCard.FXML";
	private final String	GOALIE_CARD				= "/View/SPAnalytics-goalieCard.fxml";
	private final String	GOALIE_CARD_PERCENT		= "/View/SPAnalytics-goalieCardPercent.fxml";
	private final String	ADMIN_SCORINGCHANCES	= "/View/Admin_ScoringChances.fxml";
	private final String	ADMIN_RINKDIAGRAM		= "/View/Admin_RinkDiagram.fxml";
	private final String	ADMIN_POSSESSIONDIAGRAM	= "/View/Admin_PossessionDiagram.fxml";
	private final String	ADMIN_NETCHART			= "/View/Admin_NetChart.fxml";
	private final String	ADMIN_HOME				= "/View/AdminHome.fxml";
	private final String	ADMIN_ADD				= "/View/ADMIN_ADD.fxml";
	private final String	KEY						= "/View/Admin_Key.fxml";
	private final String	CSS						= "/View/application.css";
	private final String    SHOT_CHART              = "/View/ShotChart.fxml";


	//add player to database variables
	@FXML
	private JFXTextField		fullName;
	@FXML
	private JFXTextField		birthDate;
	@FXML
	private JFXTextField		height;
	@FXML
	private JFXTextField		weight;
	@FXML
	private JFXTextField		homeTown;
	@FXML
	private JFXTextField		addJerseyNumber;
	@FXML
	private JFXButton			addPlayer;
	@FXML 
	private Label 				playerSuccess;
	@FXML 
	private Label 				playerRemoved;
	@FXML 
	private Label 				gameSuccess;
	@FXML private CheckBox		GoalieCheckBox;


	//remove player to database variables
	@FXML
	private JFXTextField		removeJerseyNumber;
	@FXML
	private JFXButton			removePlayer;


	//add a game to database variables
	@FXML
	private JFXTextField		opponent;
	@FXML
	private JFXTextField		date;
	@FXML
	private JFXButton			addGame;

	//Database connection
	Model m = new Model();

	/**
	 * This is the method that will allow this Controller class to
	 * load new FXML files. 
	 */
	public void setPrimaryStage(Stage inStage) {
		primaryStage = inStage;
		
		//Read the users keypath if it exists and populate the text field with the path
		try {
			Scanner f = new Scanner(new File("Key.txt"));
			String key = f.nextLine();
			databaseKey.setText(key);
			f.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Method that will close the application when the Exit button is clicked 
	 */
//	@FXML
//	public void handleCloseButtonAction(ActionEvent event) {
//	    Stage stage = (Stage) exitButton .getScene().getWindow();
//	    stage.close();
//	}

	/**
	 * Helper method that will load scene
	 */
	private void loadScene(String newScene) {

		if (newScene.equals(LOGIN_SCENE)) {
			isLogin = true;
		} else {
			isLogin = false;
		}

		try {
			// Switch to player card scene
			fxmlLoader = new FXMLLoader(
					getClass().getResource(newScene));

			// To keep the states of everything in this controller
			fxmlLoader.setController(this);


			// Loading the new FXML file
			parent = fxmlLoader.load();

			playersList = m.playerNames();
			users.getItems().add("ADMIN");
			users.getItems().addAll(playersList);
			System.out.println(users.getItems().size());

			//Load the proper player

			JFXTextArea textArea = (JFXTextArea) parent.lookup("#playerInfo");


			String jerseyNo = null;

			//Handle the scene for goalie/player cards
			if (newScene.equals(GOALIE_CARD) || newScene.equals(PLAYER_CARD)) {

				if (newScene.equals(GOALIE_CARD)) {
					try {
						jerseyNo = m.getJerseyNo(currentUser);
						Object check = m.getPlayerStats(jerseyNo);

						if (check != null) {
							//make the observable list
							HashMap<String, HashMap> map = (HashMap<String, HashMap>) check;
							ObservableList<GoalieModel> data = makeGoalieTable(map);
							//find the table needed to be added to
							TableView<GoalieModel> tbData = (TableView<GoalieModel>) parent.lookup("#tbData");
							//add the items to be updated
							tbData.setItems(data);
							makeGoalieCols(tbData); //create the columns
						}						
					} catch(Exception e) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("No Player Info Found");
						alert.setHeaderText("Some information not loaded");
						alert.setContentText("Please check with the database admins");
						alert.show();						
					}

				} else if (newScene.equals(PLAYER_CARD)) {
					try {
						jerseyNo = m.getJerseyNo(currentUser);
						//Get the player stats
						Object check = m.getPlayerStats(jerseyNo);

						if (check != null) {
							HashMap<String, HashMap> map = (HashMap<String, HashMap>) check;
							//make the observable list
							ObservableList<MemberModel> data = makeMemberTable(map);
							//find the table needed to be added to
							TableView<MemberModel> tbData = (TableView<MemberModel>) parent.lookup("#tbData");
							//add the items to be updated
							tbData.setItems(data);
							makeMemberCols(tbData); //create the columns
						}
					} catch(Exception e) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("No Player Info Found");
						alert.setHeaderText("Some information not loaded");
						alert.setContentText("Please check with the database admins");
						alert.show();
					}

				}

				//Set up all canvases
				rinkGC = RinkCanvas.getGraphicsContext2D();
				homeGC = HomeNetChartCanvas.getGraphicsContext2D();
				homeGC.setLineWidth(7);
				awayGC = AwayNetChartCanvas.getGraphicsContext2D();
				awayGC.setLineWidth(7);
				homeGC1 = HomeSCCanvas.getGraphicsContext2D();
				homeGC1.setLineWidth(7);
				awayGC1 = AwaySCCanvas.getGraphicsContext2D();
				awayGC1.setLineWidth(7);

				//Populate the games list with all games for Miami
				gamesList = m.getGameStats();
				for(Object s : gamesList) {
					GameList.getItems().add(s.toString());
				}

				//Allow the game list to have multiple choices at once, for aggregate charts
				GameList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

				//Load charts and information based on selected game
				GameList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
						//clear out pre-existing data from charts
						homeGC.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
						homeGC1.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
						awayGC.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
						awayGC1.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());

						ClipList.getItems().clear();
						
						//get all games selected by user, populate the associated charts
						Object[] games = GameList.getSelectionModel().getSelectedItems().toArray();
						for(int i = 0; i < games.length; i++) {
							String game = games[i].toString();

							goalieClips = m.getClips(game); 
							System.out.println(game);

							homeNetChartItems = m.getChart("offense", game, "netChart");
							awayNetChartItems = m.getChart("defense", game, "netChart");
							homeSCItems = m.getChart("offense", game, "scoringChart");
							awaySCItems = m.getChart("defense", game, "scoringChart");

							drawOvals(homeNetChartItems, homeGC);
							drawOvals(awayNetChartItems, awayGC);
							drawOvals(homeSCItems, homeGC1);
							drawOvals(awaySCItems, awayGC1);						

							//add the clips matching the user and current game
							for(Clip c : goalieClips) {
								if(c.getPlayers().contains(currentUser)) {
									String clipText = "Clip: " + c.getTime() + "\n" + "Description: " + c.getTitle();
									ClipList.getItems().add(clipText);
								}
							}
						}


					}

				});

			}

			//Populate player bio information
			if (textArea != null && jerseyNo != null) {

				HashMap playerInfo = m.getPlayer(jerseyNo); //based on jersey no
				Object birthDate = playerInfo.get("birthDate");
				Object name = playerInfo.get("name");
				Object height = playerInfo.get("height");
				Object weight = playerInfo.get("weight");
				Object homeTown = playerInfo.get("homeTown");
				Object position = m.getPosition(jerseyNo); //based on jersey no
				textArea.setText("#"+jerseyNo+" "+name+" "+position+" Height: "+height+"\nWeight: "+weight+" Born: "+birthDate+"\n"+homeTown);
			}
			scene = new Scene(parent, 600, 400);
			Pane root = (Pane) parent;
			scene = new Scene(new Group(root), 600, 400);

			//setting scaling
			Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
			double width = resolution.getWidth();
			double height = resolution.getHeight();
			double w = width/bp.getPrefWidth();
			double h = height/bp.getPrefHeight();
			Scale scale = new Scale(w,h,0,0);
			root.getTransforms().add(scale);

			//Setting a Scene KeyListener, allows user to add clip with the S key
			scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent event) {
					switch (event.getCode()) {
					case S: 
						getTime();
						break;
					}
				}
			});

			scene.getStylesheets().add(getClass().getResource(CSS).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("SP Analytics");
			primaryStage.setMaximized(true);
			primaryStage.setFullScreen(true);
			primaryStage.show();

		} catch (Exception err) {
			System.out.println(err);
		}
		
		//Setting up the net/scoring chances charts
		if(newScene.equals(ADMIN_NETCHART) || newScene.equals(ADMIN_SCORINGCHANCES)) {
			try {
				//chart graphics settings
				netChartCanvas = HomeNetChartCanvas;

				homeGC = HomeNetChartCanvas.getGraphicsContext2D();
				homeGC.setStroke(Color.color(.77, .13, .2));
				homeGC.setLineWidth(7);
				homeNetChartItems = new ArrayList<DrawnObject>();
				homeNetChartIndex = 0;

				awayGC = AwayNetChartCanvas.getGraphicsContext2D();
				awayGC.setStroke(Color.color(.77, .13, .2));
				awayGC.setLineWidth(7);
				awayNetChartItems = new ArrayList<DrawnObject>();
				awayNetChartIndex = 0;

				if(newScene.equals(ADMIN_NETCHART)) {
					ovalWidth = 40;
				} else if(newScene.equals(ADMIN_SCORINGCHANCES)) {
					ovalWidth = 20;
				}
				
				//add games to picker dropdown
				GamePicker.getItems().addAll(m.getGameStats());

			} catch(Exception e) {}
		}

		if(newScene.equals(ADMIN_RINKDIAGRAM)) {
			try {
				//setting up rink diagram graphics
				RinkCP.setValue(Color.BLACK);
				rinkGC = RinkCanvas.getGraphicsContext2D();
				rinkGC.setStroke(RinkCP.getValue());
				rinkGC.setFill(RinkCP.getValue());
				rinkGC.setLineWidth(RinkSlider.getValue());
				rinkGC.setFont(new Font("Verdana", 24));
				
				//draws associated diagram and populates information for a selected timestamp
				TimeStamps.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
						int index = TimeStamps.getSelectionModel().getSelectedIndex();
						TimeStampNotes.setText(clips.get(index).getTitle());
						NCHC_URL.setText(clips.get(index).getURL());
						copyDrawList(clips.get(index).getRinkDiagram());
						rinkGC.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
						drawLinesAndNumbers(drawList, rinkGC);
						PlayerList.getSelectionModel().clearSelection();
						for(String player : clips.get(index).getPlayers()) {
							PlayerList.getSelectionModel().select(player);
						}

					}

				});

				//gets the clips for a specific game selected
				GamePicker.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
						String game = GamePicker.getSelectionModel().getSelectedItem().toString();
						clips = m.getClips(game);
						System.out.println(clips.size());
						TimeStamps.getItems().clear();
						for(Clip c : clips) {
							TimeStamps.getItems().add(c.getTime());
						}
					}
				});

				//populate gamepicker
				gamesList = m.getGameStats();
				for(Object s : gamesList) {
					GamePicker.getItems().add(s.toString());
				}

				//allow multiple selection, add players to list
				PlayerList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				PlayerList.getItems().addAll(m.playerNames());
			} catch(Exception e) {}
		}
		
		if(newScene.equals(ADMIN_POSSESSIONDIAGRAM)) {
			try {
				
				//set up graphics??
				possCP.setValue(Color.BLACK);
				possGC = PossessionDiagramCanvas.getGraphicsContext2D();
				possGC.setLineWidth(7);
				possGC.setStroke(possCP.getValue());
				possGC.setFill(possCP.getValue());
				possGC.setFont(new Font("Verdana", 24));
				
			} catch(Exception e) {}

		}
		
		if(newScene.equals(SHOT_CHART)) {
			
			//gets all the info for each combo box
			netChartCanvas = HomeNetChartCanvas;
			
			homeGC = HomeNetChartCanvas.getGraphicsContext2D();
			homeGC.setStroke(Color.color(.77, .13, .2));
			homeGC.setLineWidth(7);
			homeNetChartItems = new ArrayList<DrawnObject>();
			homeNetChartIndex = 0;

//			awayGC = AwayNetChartCanvas.getGraphicsContext2D();
//			awayGC.setStroke(Color.color(.77, .13, .2));
//			awayGC.setLineWidth(7);
//			awayNetChartItems = new ArrayList<DrawnObject>();
//			awayNetChartIndex = 0;
			ovalWidth = 30;
			initializeDropdowns();
		}	
	}// end of loadScene Method 

	public void initializeDropdowns() {
		//gets all the info for each combo box
		GamePicker.getItems().addAll(m.getGameStats());
	    playTypeCombo.getItems().addAll();   	//DEFINED IN DATABASE
	    releaseTypeCombo.getItems().addAll(
	    		"One-timer",
	    		"Catch-Shoot");
		pChanceCombo.getItems().addAll(
				"3","4","6","7","9","10","11","13","18","19","20","22",
				"28","31","33","36","37","39","55","67","71","81","85");
		sChanceCombo.getItems().addAll(
				"3","4","6","7","9","10","11","13","18","19","20","22",
				"28","31","33","36","37","39","55","67","71","81","85");
		createdByCombo.getItems().addAll(
				"3","4","6","7","9","10","11","13","18","19","20","22",
				"28","31","33","36","37","39","55","67","71","81","85");
		resultCombo.getItems().addAll(
				"First Goal",
				"Down >2",
				"Down 2",
				"Down 1",
				"Tied",
				"Up 1",
				"Up 2",
				"Up >2",
				"Empty Net"
				);
		teamForCombo.getItems().addAll(
				"CC",
				"Denver",
				"Miami",
				"UMD",
				"UND",
				"UNO",
				"SCSU",
				"WMU",
				"Other");
		teamAgainstCombo.getItems().addAll(
				"CC",
				"Denver",
				"Miami",
				"UMD",
				"UND",
				"UNO",
				"SCSU",
				"WMU",
				"Other");
		playerCombo.getItems().addAll(
				"3","4","6","7","9","10","11","13","18","19","20","22",
				"28","31","33","36","37","39","55","67","71","81","85");
		playerStatusCombo.getItems().addAll(
				"Stationary",
				"Moving Forward",
				"Moving Across");
		shotCombo.getItems().addAll(
				"1","2","3","4","5","6","7","8","9","10",
				"11","12","13","14","15","16","17","18","19","20",
				"21","22","23","24","25","26","27","28","29","30",
				"31","32","33","34","35","36","37","38","39","40","41","42","43","44",
				"45","46","47","48","49","50","51","52","53","54","55","56",
				"57","58","59","60","61","62","63","64","65","66","67","68","69","70",
				"71","72","73","74","75","76","77","78","79","80","81","82","83","84","85",
				"86","87","88","89","90","91","92","93","94","95","96","97","98","99","100");
		shotTypeCombo.getItems().addAll(
				"Wrist shot" 
				,"Slapshot"
				,"Backhand");
		rbCombo.getItems().addAll(
				"Yes",
				"No");
		goalieCombo.getItems().addAll(
				"Moving",
				"Stationary");
		extraInfoCombo.getItems().addAll(
				"5v5", "4v4","3v3",	"5v4","4v5",
				"5v3","3v5","4v3","3v4","6v5","5v6","6v4","4v6","6v3","3v6");
		statusCombo.getItems().addAll(
				"Goal",
				"No Goal");
		scoringChanceCombo.getItems().addAll(
				"Yes",
				"No");
	}//end of initializeDropdowns()
		
	/**
	 * Helper method to copy rink diagram from Clip object to the drawList
	 */
	private void copyDrawList(List<DrawnObject> list) {
		drawList = new ArrayList<DrawnObject>();
		drawList.addAll(list);
	}

	/**
	 * Helper method that converts milliseconds to a stopwatch time format
	 * example output is 19:03 1st
	 */
	private static String formatTime(final long l)
	{
		final long time;

		if(periodIndex <= 2) {
			time = TimeUnit.MINUTES.toMillis(20) - l;
		} else {
			time = TimeUnit.MINUTES.toMillis(5) - l;
		}
		if (time < 0) {
			return String.format("%02d:%02d %s", 0, 0, PERIODS[periodIndex]);
		}
		final long min = TimeUnit.MILLISECONDS.toMinutes(time);
		final long sec = TimeUnit.MILLISECONDS.toSeconds(time - TimeUnit.MINUTES.toMillis(min));
		return String.format("%02d:%02d %s", min, sec, PERIODS[periodIndex]);
	}

	/**
	 * creates a new clip with the current time on the timer, range of 15 seconds
	 */
	private void getTime() {
		long sTime = currentTime - TimeUnit.SECONDS.toMillis(15);
		String start = formatTime(Math.max(sTime, 0));
		String end = formatTime(currentTime);
		//TimeStamps.appendText(start + " - " + end + "\n");
		Clip c = new Clip(start + " - " + end, "Untitled");
		TimeStamps.getItems().add(c.getTime());
		clips.add(c);
	}

	/**
	 * Opens provided link when clicked
	 */
	@FXML
	public void OpenGameButtonClicked() {

		if(currentClip != null) {
			System.out.println(currentClip.getURL());
			try {
				Desktop.getDesktop().browse(new URL(currentClip.getURL()).toURI());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Method Draws selected Clip's diagram
	 */
	@FXML
	public void ClipListSelectionChanged() {

		if(ClipList.getSelectionModel().getSelectedItem() == null) return;
		String clipName = ClipList.getSelectionModel().getSelectedItem().toString();
		System.out.println(clipName);
		for(int i = 0; i < goalieClips.size(); i++) {
			Clip c = goalieClips.get(i);
			String clipMsg = "Clip: " + c.getTime() + "\n" + "Description: " + c.getTitle();
			if(clipMsg.equals(clipName)) {
				currentClip = c;
				break;
			}
		}

		//System.out.println(c.getRinkDiagram().get(0).getText());
		rinkGC.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
		drawLinesAndNumbers(currentClip.getRinkDiagram(), rinkGC);
	}

	/**
	 * Checks the database connection before login
	 */
	@FXML
	public void submitKey() {
		primaryStage.getScene().setCursor(Cursor.WAIT);
		String keyVal = databaseKey.getText();
		
		//Create a task to connect to database, async call
		Task<Boolean> task = new Task<Boolean>() {
			@Override
			public Boolean call() {
				String key = databaseKey.getText();
				boolean authenticated = m.makeDatabase(key);
				Boolean result = Boolean.valueOf(authenticated);
				return result ;
			}
		};
		
		//get the result of db connection
		//if success -> go to login page
		//if fail -> alert error to user
		task.setOnSucceeded(e -> {
			boolean authenticated = task.getValue().booleanValue();
			primaryStage.getScene().setCursor(Cursor.DEFAULT);
			if(authenticated == true) {
				try {
					//Write the user's choice for future use
					File keyFile = new File("Key.txt");					
					PrintWriter pw = new PrintWriter(keyFile);
					pw.println(keyVal);
					pw.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				loadScene(LOGIN_SCENE);		
			} else {
				String msg = "Error on getting key";
				Alert err = new Alert(AlertType.CONFIRMATION, msg);
				err.show();
			}
		});

		new Thread(task).start();


	}

	/**
	 * This is the method that will switch to the home screen once login is clicked
	 * If player --> player home screen.
	 * If goalie --> goalie home screen
	 * if admin --> admin home screen
	 */
	@FXML
	public void loginButtonClicked() {
		currentUser = users.getValue();
		String number = m.getJerseyNo(currentUser);
		String position = m.getPosition(number);
		if(position.equals("goalie")) {
			loadScene(GOALIE_CARD);
		}else if(currentUser.equals("ADMIN")) {
			adminPass.setVisible(true);
			if(adminPass.getText().equals("test")) {
				loadScene(ADMIN_HOME);
			}		
		}
		else {	
			loadScene(PLAYER_CARD);
		}
	}
	//Admin card button functionalities
	/**
	 * This is the method that will go back to the previous scene.
	 */
	@FXML
	public void goBack() {
		loadScene(ADMIN_HOME);
	}

	/**
	 * This is the method that will go to the admin database.
	 */
	@FXML
	public void databaseClicked() {
		loadScene(ADMIN_ADD);
	}
	
	/**
	 * This is the method that will add a player to the database.
	 */
	@FXML
	public void addPlayer() {
		Player player = new Player(Integer.parseInt(addJerseyNumber.getText()), fullName.getText(), 
				height.getText(), weight.getText(), birthDate.getText(), homeTown.getText());
		String position;
		if(GoalieCheckBox.isSelected()) {
			position = "goalie";
		} else {
			position = "player";
		}
		m.addPlayerWithPosition(player, position);
		//Displays success text for 2 seconds
		playerSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(
				Duration.seconds(2)
				);
		visiblePause.setOnFinished(
				event -> playerSuccess.setVisible(false)
				);
		visiblePause.play();	
	}

	/**
	 * This is the method that will remove player from the database.
	 */
	@FXML
	public void removePlayer() {
		Player Removeplayer = new Player(Integer.parseInt(removeJerseyNumber.getText()));
		m.deletePlayer(Removeplayer);
		playerRemoved.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(
				Duration.seconds(2)
				);
		visiblePause.setOnFinished(
				event -> playerRemoved.setVisible(false)
				);
		visiblePause.play();	
	}


	/**
	 * This is the method that will add a game to the database.
	 */
	@FXML
	public void addGame() {
		String opp = opponent.getText();
		String day = date.getText();
		String name = opp + " " + day;
		//System.out.println(name);
		Game game = new Game(name);
		m.addGame(opp, day);

		gameSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(
				Duration.seconds(2)
				);
		visiblePause.setOnFinished(
				event -> gameSuccess.setVisible(false)
				);
		visiblePause.play();	
	}


	/**
	 * This is the method that will go to the net chart scene.
	 */
	@FXML
	public void NetChartClicked() {
		loadScene(ADMIN_NETCHART);
	}

	/**
	 * This is the method that will go to the scoring chances scene.
	 */
	@FXML
	public void ScoringChancesClicked() {
		loadScene(ADMIN_SCORINGCHANCES);
	}

	/**
	 * This is the method that will go to the possession diagram scene.
	 */
	@FXML
	public void PossessionDiagramClicked() {
		loadScene(ADMIN_POSSESSIONDIAGRAM);
	}
	
	/**
	 * This is the method that will go to the rink diagram scene.
	 */
	@FXML
	public void RinkDiagramClicked() {
		loadScene(ADMIN_RINKDIAGRAM);
	}


	// player card button functionalities 
	/**
	 * This is the method that will go to the player card scene.
	 */
	@FXML
	public void PlayerCardClicked() {
		System.out.print("player card clicked");
		loadScene(PLAYER_CARD);

	}

	/**
	 * This is the method that will go back the home scene.
	 */
	@FXML
	public void PlayerHomeButtonClicked() {
		loadScene(PLAYER_HOME);
	}

	/**
	 * This is the method that will logout and go back the login scene.
	 */
	@FXML
	public void PlayerLogoutButtonClicked() {
		loadScene(LOGIN_SCENE);
	}


	// goalie card button functionalities
	/**
	 * This is the method that will go to the player card scene.
	 */
	@FXML
	public void GoalieCardClicked() {
		loadScene(GOALIE_CARD);
	}

	/**
	 * This is the method that will go to the player card scene.
	 */
	@FXML
	public void GoalieTeamProfileClicked() {
		loadScene(TEAM_PROFILE);
	}


	/**
	 * This is the method that will go back the home scene.
	 */
	@FXML
	public void GoalieHomeButtonClicked() {
		loadScene(GOALIE_HOME);
	}

	/**
	 * This is the method that will logout and go back the login scene.
	 */
	@FXML
	public void GoalieLogoutButtonClicked() {
		loadScene(LOGIN_SCENE);
	}
	/**
	 * This method loads the Shot Chart page 
	 */
	@FXML
	public void shotChartClicked() {
		loadScene(SHOT_CHART);
	}

	/**
	 * This is the method that will change the color of the circle to red
	 */
	@FXML
	public void setColorRed() {
		homeGC.setStroke(Color.color(.77, .13, .2));
		awayGC.setStroke(Color.color(.77, .13, .2));
	}

	/**
	 * This is the method that will change the color of the circle to green
	 */
	@FXML
	public void setColorGreen() {
		homeGC.setStroke(Color.GREEN);
		awayGC.setStroke(Color.GREEN);
	}

	/**
	 * This is the method that will draw circles Net chart when mouse released
	 */
	@FXML
	public void drawCircle(MouseEvent e) {
		//set a home circle
		if(netChartCanvas == HomeNetChartCanvas) {
			
			System.out.println(e.getY());
			//System.out.println("HOME GC WHATEVER: " + homeGC.toString());// This gives me the error
			System.out.println("OVAL WIDTH" +  ovalWidth); //ovalWidth is 0 
			
			//draw the circle
			Point p1 = new Point(e.getX()-(ovalWidth/2), e.getY()-(ovalWidth/2), homeGC.getStroke());
			homeGC.strokeOval(p1.getX(), p1.getY(), ovalWidth, ovalWidth);
			DrawnObject oval = new DrawnObject(p1, p1.getColor(), ovalWidth);
			homeNetChartItems.add(oval);

			//draw the number of the shot (with offset to be in center of circle
			int xOff = 2*(int)(Math.log10(Math.max(1, homeNetChartIndex))+1);
			Point p2 = new Point(e.getX()-(3+xOff), e.getY()+5, homeGC.getFill());
			homeGC.fillText(""+ ++homeNetChartIndex, p2.getX(), p2.getY());
			DrawnObject number = new DrawnObject(p2, p1.getColor(), 0, ""+homeNetChartIndex);
			homeNetChartItems.add(number);
		
		} else if(netChartCanvas == AwayNetChartCanvas) { //away circle
			
			//draw circle
			Point p1 = new Point(e.getX()-(ovalWidth/2), e.getY()-(ovalWidth/2), awayGC.getStroke());
			awayGC.strokeOval(p1.getX(), p1.getY(), ovalWidth, ovalWidth);
			DrawnObject oval = new DrawnObject(p1, p1.getColor(), ovalWidth);
			awayNetChartItems.add(oval);

			//draw number w/ offset
			int xOff = 2*(int)(Math.log10(Math.max(1, awayNetChartIndex))+1);
			Point p2 = new Point(e.getX()-(3+xOff), e.getY()+5, awayGC.getFill());
			awayGC.fillText(""+ ++awayNetChartIndex, p2.getX(), p2.getY());
			DrawnObject number = new DrawnObject(p2, p1.getColor(), 0, ""+awayNetChartIndex);
			awayNetChartItems.add(number);
		}
	}

	/**
	 * Method changes highlighted canvas to home
	 */
	@FXML
	public void NetChartHomeSelected() {
		netChartCanvas = HomeNetChartCanvas;
	}

	/**
	 * Method changes highlighted canvas to away
	 */
	@FXML
	public void NetChartAwaySelected() {
		netChartCanvas = AwayNetChartCanvas;
	}

	/**
	 * Method undos an item on the NetChartScene
	 */
	@FXML
	public void NetChartUndoPressed() {
		//removes the last circle, redraws chart
		if(netChartCanvas == HomeNetChartCanvas) {
			Color original = (Color) homeGC.getStroke();
			homeGC.clearRect(0, 0, netChartCanvas.getWidth(), netChartCanvas.getHeight());
			if(homeNetChartItems.size() < 2) return;

			//have to remove two (one for circle, one for text)
			homeNetChartItems.remove(homeNetChartItems.size()-1);
			homeNetChartItems.remove(homeNetChartItems.size()-1);
			drawOvals(homeNetChartItems, homeGC);
			homeNetChartIndex--;
			homeGC.setStroke(original);
		} else if(netChartCanvas == AwayNetChartCanvas) {
			Color original = (Color) awayGC.getStroke();
			awayGC.clearRect(0, 0, netChartCanvas.getWidth(), netChartCanvas.getHeight());
			if(awayNetChartItems.size() < 2) return;

			//have to remove two (one for circle, one for text)
			awayNetChartItems.remove(awayNetChartItems.size()-1);
			awayNetChartItems.remove(awayNetChartItems.size()-1);
			drawOvals(awayNetChartItems, awayGC);
			awayNetChartIndex--;
			awayGC.setStroke(original);
		}


	}

	/**
	 * Helper drawing method for any canvas using ovals
	 */
	private static void drawOvals(ArrayList<DrawnObject> d, GraphicsContext gc) {
		for(int i = 0; i < d.size(); i++) {
			DrawnObject obj = d.get(i);
			System.out.println("[" + obj.getLastPoint().getX() + ", " + obj.getLastPoint().getY() + "]");
			System.out.println(obj.getWidth());

			//draw either the circle or the text inside
			if(obj.getText() == null) {
				Point p = obj.getLastPoint();
				gc.setStroke(p.getColor());
				gc.strokeOval(p.getX(), p.getY(), obj.getWidth(), obj.getWidth());
			} else {
				System.out.println("text: " + obj.getText());
				Point p = obj.getLastPoint();
				gc.fillText(obj.getText(), p.getX(), p.getY());
			}
		}
	}

	/**
	 * Helper method to draw lines and numbers
	 */
	private static void drawLinesAndNumbers(ArrayList<DrawnObject> d, GraphicsContext gc) {
		for(int i = 0; i < d.size(); i++) {
			DrawnObject obj = d.get(i);
			System.out.println("Points size: " + obj.getPoints().size());
			if(obj.getText() == null) {
				//start the line
				gc.beginPath();
				gc.setLineWidth(obj.getWidth());
				
				//draw all points on line
				for(int xy = 0; xy < obj.size(); xy++) {
					Point p = obj.getPoint(xy);
					gc.setStroke(p.getColor());
					gc.lineTo(p.getX(), p.getY());
					gc.stroke();
				}
			} else {
				//draw the text
				Point p = obj.getPoint(obj.size()-1);
				gc.setFill(p.getColor());
				gc.fillText(obj.getText(), p.getX(), p.getY());
			}
		}
	}

	@FXML
	/**
	 * Saving the home scoring chart
	 */
	public void ScoringChartSaveHomePressed() {
		if(GamePicker.getSelectionModel().getSelectedItem() == null) {
			homeChartFail.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> homeChartFail.setVisible(false));
			visiblePause.play();
			return;
		}
		m.addChart("offense", GamePicker.getSelectionModel().getSelectedItem().toString(), "scoringChart", homeNetChartItems);
		homeChartSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> homeChartSuccess.setVisible(false));
		visiblePause.play();
		return;
	}

	@FXML
	/**
	 * Saving the home scoring chart
	 */
	public void ScoringChartSaveAwayPressed() {
		if(GamePicker.getSelectionModel().getSelectedItem() == null) {
			awayChartFail.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> awayChartFail.setVisible(false));
			visiblePause.play();
			return;
		}
		m.addChart("defense", GamePicker.getSelectionModel().getSelectedItem().toString(), "scoringChart", awayNetChartItems);
		awayChartSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> awayChartSuccess.setVisible(false));
		visiblePause.play();	
	}

	@FXML
	/**
	 * Method saves the home chart
	 */
	public void NetChartSaveHomePressed() {
		if(GamePicker.getSelectionModel().getSelectedItem() == null) {
			homeNetChartFail.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> homeNetChartFail.setVisible(false));
			visiblePause.play();
			return;
		}
		m.addChart("offense", GamePicker.getSelectionModel().getSelectedItem().toString(), "netChart", homeNetChartItems);
		homeNetChartSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> homeNetChartSuccess.setVisible(false));
		visiblePause.play();
	}

	@FXML
	/**
	 * Method saves the away chart
	 */
	public void NetChartSaveAwayPressed() {
		if(GamePicker.getSelectionModel().getSelectedItem() == null) {
			awayNetChartFail.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> awayNetChartFail.setVisible(false));
			visiblePause.play();
			return;
		}
		m.addChart("defense", GamePicker.getSelectionModel().getSelectedItem().toString(), "netChart", awayNetChartItems);
		awayNetChartSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> awayNetChartSuccess.setVisible(false));
		visiblePause.play();
	}

	/**
	 * Method opens NCHC link on default browser
	 */
	@FXML
	public void NCHCOpenButtonClicked() {
		try {
			Desktop.getDesktop().browse(new URL(NCHC_URL.getText()).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method increments the period
	 */
	@FXML
	public void NextPeriodClicked() {
		periodIndex = (periodIndex+1) % PERIODS.length;
		timerStart = System.nanoTime();
		timerPause = 0;
		currentTime = 0;
		Time.setText(formatTime(currentTime));
	}

	/**
	 * Method starts timer
	 */
	@FXML
	public void StartTimerClicked() {
		timerOn = true;
		timerStart = System.nanoTime();

		//run stopwatch on thread to keep app from locking
		Thread t = new Thread() {
			public void run() {
				while(timerOn) {
					try {
						currentTime = (System.nanoTime() - timerStart) / 1000000 + timerPause;
						String formattedTime = formatTime(currentTime);

						Thread.sleep(10);
						//update the application thread to show time
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								Time.setText(formattedTime);
							}
						});

					} catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}	
			}
		};
		t.start();
	}


	/**
	 * Method stops timer
	 */
	@FXML
	public void StopTimerClicked() {
		timerOn = false;
		timerPause += (System.nanoTime() - timerStart) / 1000000;
	}

	/**
	 * Method restarts timer
	 */
	@FXML
	public void ResetTimerClicked() {
		timerStart = System.nanoTime();
		timerPause = 0;
		currentTime = 0;
		periodIndex = 0;
		Time.setText("20:00 1st");
	}

	/**
	 * Method saves all clips to the database
	 */
	@FXML
	public void SaveClipsToDBClicked() {
		if(GamePicker.getSelectionModel().getSelectedItem()	 == null) {
			selectGameFirst.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> selectGameFirst.setVisible(false));
			visiblePause.play();
		} else {
			String game = GamePicker.getSelectionModel().getSelectedItem().toString();
			for(int i = 0; i < clips.size(); i++) {
				clips.get(i).setGame(game);
				m.addClip(clips.get(i));
			}
		}
		clipsSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> clipsSuccess.setVisible(false));
		visiblePause.play();
	}

	/**
	 * Method saves diagram to current clip
	 */
	@FXML
	public void SaveRinkClicked() {
		int index = TimeStamps.getSelectionModel().getSelectedIndex();
		if (index == -1) return;
		clips.get(index).setRinkDiagram(drawList);
		ObservableList<String> players = PlayerList.getSelectionModel().getSelectedItems();
		ArrayList<String> playersAL = new ArrayList<String>();
		for(String player : players) {
			playersAL.add(player);
		}
		clips.get(index).addPlayer(playersAL);
		
		diagramSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> diagramSuccess.setVisible(false));
		visiblePause.play();	
	}

	/**
	 * Method Saves the title of selected clip
	 */
	@FXML
	public void SaveNotesButtonClicked() {
		int index = TimeStamps.getSelectionModel().getSelectedIndex();
		if (index == -1) return;
		clips.get(index).setTitle(TimeStampNotes.getText());
		clips.get(index).setURL(NCHC_URL.getText());
		titleAndLinkSuccess.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
		visiblePause.setOnFinished(event -> titleAndLinkSuccess.setVisible(false));
		visiblePause.play();
	}

	/**
	 * Method begins drawing on canvas when mouse pressed
	 */
	@FXML
	public void CanvasMousePressed(MouseEvent e) {
		RadioButton selected = (RadioButton) RinkGroup.getSelectedToggle();
		if(selected.getText().equals("Line")) {
			line = new DrawnObject(e.getX(), e.getY(), rinkGC.getStroke(), rinkGC.getLineWidth());
			rinkGC.beginPath();
			rinkGC.lineTo(line.getLastPoint().getX(), line.getLastPoint().getY());
			rinkGC.stroke();
			drawList.add(line);
		} else if(selected.getText().equals("Text")) {
			DrawnObject text = new DrawnObject(e.getX()-12, e.getY()+12, rinkGC.getFill(), 0, RinkDiagramText.getText());
			rinkGC.fillText(text.getText(), text.getLastPoint().getX(), text.getLastPoint().getY());
			drawList.add(text);
		}
	}

	/**
	 * Method draws on canvas with mouse movement
	 */
	@FXML
	public void CanvasMouseDragged(MouseEvent e) {
		RadioButton selected = (RadioButton) RinkGroup.getSelectedToggle();
		if(selected.getText().equals("Line")) {
			line.addPoint(e.getX(), e.getY(), rinkGC.getStroke());
			rinkGC.lineTo(line.getLastPoint().getX(), line.getLastPoint().getY());
			rinkGC.stroke();
		}
	}

	/**
	 * Method undos the last drawing action
	 */
	@FXML
	public void UndoRinkClicked() {
		rinkGC.clearRect(0, 0, RinkCanvas.getWidth(), RinkCanvas.getHeight());
		if(drawList.size() < 1) return;

		//delete last object, redraw
		drawList.remove(drawList.size()-1);
		drawLinesAndNumbers(drawList, rinkGC);

		rinkGC.setStroke(RinkCP.getValue());
		rinkGC.setFill(RinkCP.getValue());
		rinkGC.setLineWidth(RinkSlider.getValue());
		//drawList.clear();
	}

	/**
	 * Method changes selected color
	 */
	@FXML
	public void RinkCPColorChange() {
		rinkGC.setStroke(RinkCP.getValue());
		rinkGC.setFill(RinkCP.getValue());
	}

	/**
	 * Method updates line width
	 */
	@FXML
	public void RinkSliderDropped() {
		rinkGC.setLineWidth(RinkSlider.getValue());
	}

	/**
	 * Method to initialize table for a player
	 */
	@FXML
	public ObservableList<MemberModel> makeMemberTable(HashMap<String, HashMap> playerData) {
		ObservableList<MemberModel> data = FXCollections.observableArrayList();;

		Iterator it = playerData.entrySet().iterator();
		while (it.hasNext()) {
			MemberModel model = new MemberModel();
			Map.Entry pair = (Map.Entry)it.next();
			HashMap values = (HashMap) pair.getValue();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			Iterator stats = values.entrySet().iterator();
			//each game would be a new row
			while (stats.hasNext()) {
				Map.Entry details = (Map.Entry)stats.next();
				System.out.println(details.getKey() + " = " + details.getValue());
				//check what value it is and make the correct value
				if (details.getKey() == "PPA") {
					model.setPPA(details.getValue());
				}
				else if (details.getKey() == "A") {
					model.setA(details.getValue());
				}
				else if (details.getKey() == "PPG") {
					model.setPPG(details.getValue());
				}
				else if (details.getKey() == "G") {
					model.setG(details.getValue());
				}
				else if (details.getKey() == "GP") {
					model.setGP(details.getValue());
				}
				else if (details.getKey() == "SOG") {
					model.setSOG(details.getValue());
				}
				else if (details.getKey() == "percent") {
					model.setPercent(details.getValue());
				}
				else if (details.getKey() == "PTS") {
					model.setPTS(details.getValue());
				}
				else if (details.getKey() == "PROD") {
					model.setPROD(details.getValue());
				}
				else if (details.getKey() == "SHG") {
					model.setSHG(details.getValue());
				}
				else if (details.getKey() == "GWG") {
					model.setGWG(details.getValue());
				}
				else if (details.getKey() == "winsOrLosses") {
					model.setPlusMinus(details.getValue());
				}
				else if (details.getKey() == "season") {
					model.setSeason(details.getValue());
				}
				else if (details.getKey() == "GTG") {
					model.setGTG(details.getValue());
				}
				else if (details.getKey() == "TOIG") {
					model.setTOI(details.getValue());
				}
				stats.remove();
			}
			it.remove(); // avoids a ConcurrentModificationException
			data.add(model);

			//here model goes out of scope
		}
		return data;
	}
	/**
	 * Method to initialize table for a player
	 */
	@FXML
	public ObservableList<GoalieModel> makeGoalieTable(HashMap<String, HashMap> playerData) {
		ObservableList<GoalieModel> data = FXCollections.observableArrayList();

		Iterator it = playerData.entrySet().iterator();
		while (it.hasNext()) {
			GoalieModel model = new GoalieModel();
			Map.Entry pair = (Map.Entry)it.next();
			HashMap values = (HashMap) pair.getValue();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			Iterator stats = values.entrySet().iterator();
			//each game would be a new row
			while (stats.hasNext()) {
				Map.Entry details = (Map.Entry)stats.next();
				System.out.println(details.getKey() + " = " + details.getValue());
				//check what value it is and make the correct value
				if (details.getKey() == "GP") {
					TableColumn<GoalieModel,String> nameColumn=new TableColumn<>("GP");
					model.setGP(details.getValue());
				}
				else if (details.getKey() == "season") {
					model.setSeason(details.getValue());
				}
				else if (details.getKey() == "SV") {
					model.setSV(details.getValue());
				}
				else if (details.getKey() == "T") {
					model.setT(details.getValue());
				}
				else if (details.getKey() == "GAA" ) {
					model.setGAA(details.getValue());
				}
				else if (details.getKey() == "W") {
					model.setW(details.getValue());
				}
				else if (details.getKey() == "GA") {
					model.setGA(details.getValue());
				}
				else if (details.getKey() == "SA") {
					model.setSA(details.getValue());
				}
				else if (details.getKey() == "SVpercent") {
					model.setSVpercent(details.getValue());
				}
				else if (details.getKey() == "L") {
					model.setL(details.getValue());
				}
				else if (details.getKey() == "SO") {
					model.setSO(details.getValue());
				}

				stats.remove();
			}
			it.remove(); // avoids a ConcurrentModificationException
			data.add(model);

			//here model goes out of scope
		}
		return data;


	}

	//creates a table with goalie stats
	public void makeGoalieCols(TableView<GoalieModel> tbData) {

		TableColumn<GoalieModel, String> season = new TableColumn<GoalieModel, String>("Season");
		season.setCellValueFactory(new PropertyValueFactory("season"));
		TableColumn<GoalieModel, String> GP = new TableColumn<GoalieModel, String>("GP");
		GP.setCellValueFactory(new PropertyValueFactory("GP"));
		TableColumn<GoalieModel, String> W = new TableColumn<GoalieModel, String>("W");
		W.setCellValueFactory(new PropertyValueFactory("W"));
		TableColumn<GoalieModel, String> L = new TableColumn<GoalieModel, String>("L");
		L.setCellValueFactory(new PropertyValueFactory("L"));
		TableColumn<GoalieModel, String> T = new TableColumn<GoalieModel, String>("T");
		T.setCellValueFactory(new PropertyValueFactory("T"));
		TableColumn<GoalieModel, String> GA = new TableColumn<GoalieModel, String>("GA");
		GA.setCellValueFactory(new PropertyValueFactory("GA"));
		TableColumn<GoalieModel, String> GAA = new TableColumn<GoalieModel, String>("GAA");
		GAA.setCellValueFactory(new PropertyValueFactory("GAA"));
		TableColumn<GoalieModel, String> SA = new TableColumn<GoalieModel, String>("SA");
		SA.setCellValueFactory(new PropertyValueFactory("SA"));
		TableColumn<GoalieModel, String> SV = new TableColumn<GoalieModel, String>("SV");
		SV.setCellValueFactory(new PropertyValueFactory("SV"));
		TableColumn<GoalieModel, String> SVpercent = new TableColumn<GoalieModel, String>("SV%");
		SVpercent.setCellValueFactory(new PropertyValueFactory("SVpercent"));
		TableColumn<GoalieModel, String> SO = new TableColumn<GoalieModel, String>("SO");
		SO.setCellValueFactory(new PropertyValueFactory("SO"));

		tbData.getColumns().setAll(season,GP,W,L,T,GA,GAA,SA,SV,SVpercent,SO);
	}

	//create table with player stats
	public void makeMemberCols(TableView<MemberModel> tbData) {

		TableColumn<MemberModel, String> season = new TableColumn<MemberModel, String>("Season");
		season.setCellValueFactory(new PropertyValueFactory("season"));
		TableColumn<MemberModel, String> GP = new TableColumn<MemberModel, String>("GP");
		GP.setCellValueFactory(new PropertyValueFactory("GP"));
		TableColumn<MemberModel, String> A = new TableColumn<MemberModel, String>("A");
		A.setCellValueFactory(new PropertyValueFactory("A"));
		TableColumn<MemberModel, String> PPG = new TableColumn<MemberModel, String>("PPG");
		PPG.setCellValueFactory(new PropertyValueFactory("PPG"));
		TableColumn<MemberModel, String> G = new TableColumn<MemberModel, String>("G");
		G.setCellValueFactory(new PropertyValueFactory("G"));
		TableColumn<MemberModel, String> SOG = new TableColumn<MemberModel, String>("SOG");
		SOG.setCellValueFactory(new PropertyValueFactory("SOG"));
		TableColumn<MemberModel, String> percent = new TableColumn<MemberModel, String>("\'%");
		percent.setCellValueFactory(new PropertyValueFactory("percent"));
		TableColumn<MemberModel, String> PTS = new TableColumn<MemberModel, String>("PTS");
		PTS.setCellValueFactory(new PropertyValueFactory("PTS"));
		TableColumn<MemberModel, String> PROD = new TableColumn<MemberModel, String>("PROD");
		PROD.setCellValueFactory(new PropertyValueFactory("PROD"));
		TableColumn<MemberModel, String> SHG = new TableColumn<MemberModel, String>("SHG");
		SHG.setCellValueFactory(new PropertyValueFactory("SHG"));
		TableColumn<MemberModel, String> GWG = new TableColumn<MemberModel, String>("GWG");
		GWG.setCellValueFactory(new PropertyValueFactory("GWG"));
		TableColumn<MemberModel, String> winsOrLosses = new TableColumn<MemberModel, String>("+/-");
		winsOrLosses.setCellValueFactory(new PropertyValueFactory("plusMinus"));
		TableColumn<MemberModel, String> GTG = new TableColumn<MemberModel, String>("GTG");
		GTG.setCellValueFactory(new PropertyValueFactory("GTG"));
		TableColumn<MemberModel, String> TOIG = new TableColumn<MemberModel, String>("TOI/G");
		TOIG.setCellValueFactory(new PropertyValueFactory("TOI"));
		tbData.getColumns().setAll(season,GP,A,PPG,G,SOG,percent,PTS,PROD,SHG,GWG, winsOrLosses, GTG, TOIG);
	}

	public  HashMap<String, HashMap<String, Object>> readMemberCols(TableView<MemberModel> tbData) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		HashMap<String, HashMap<String, Object>> allValues = new  HashMap<String, HashMap<String, Object>>();
		ObservableList<TableColumn<MemberModel, ?>> columns = tbData.getColumns();

		for (Object row : tbData.getItems()) {

			for (TableColumn column : columns) {
				System.out.println(column.getText());
				values.put(column.getText(), (Object) column.
						getCellObservableValue(row).
						getValue());

			}
			Object season = values.get("Season");
			allValues.put(season.toString(), values);
		}

		Iterator stats = allValues.entrySet().iterator();
		//each game would be a new row
		while (stats.hasNext()) {
			Map.Entry details = (Map.Entry)stats.next();
			System.out.println(details.getKey() + " = " + details.getValue());

		}
		return allValues;

	}
	//get
	public  HashMap<String, HashMap<String, Object>> readGoalieCols(TableView<GoalieModel> tbData) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		HashMap<String, HashMap<String, Object>> allValues = new  HashMap<String, HashMap<String, Object>>();
		ObservableList<TableColumn<GoalieModel, ?>> columns = tbData.getColumns();

		for (Object row : tbData.getItems()) {

			for (TableColumn column : columns) {
				System.out.println(column.getText());
				values.put(column.getText(), (Object) column.
						getCellObservableValue(row).
						getValue());

			}
			Object season = values.get("Season");
			allValues.put(season.toString(), values);
		}

		Iterator stats = allValues.entrySet().iterator();
		//each game would be a new row
		while (stats.hasNext()) {
			Map.Entry details = (Map.Entry)stats.next();
			System.out.println(details.getKey() + " = " + details.getValue());

		}
		return allValues;
	}
	//delete player
	public void deletePlayer(int jerseyNo, TableView<?> tbData,JFXTextArea textArea) {
		tbData.getItems().clear();
		textArea.clear();
		m.deletePlayerByJerseyNo(jerseyNo);

	}
}


