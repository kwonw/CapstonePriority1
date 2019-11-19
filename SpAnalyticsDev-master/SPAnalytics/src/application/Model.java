package application;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldPath;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Model {
	private DocumentReference docRef;
	private DocumentReference docTeamRef;
	private DocumentReference docClipRef;
	private Firestore db;
		
		public boolean makeDatabase(String key) {
			boolean authenticated = false;
			FileInputStream serviceAccount;
			try {
				serviceAccount = new FileInputStream(key);
				
				// Initialize the app with a custom auth variable, limiting the server's access
				GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
				
				FirestoreOptions firestoreOptions = 
						FirestoreOptions.newBuilder().setTimestampsInSnapshotsEnabled(true).build();

				FirebaseOptions options = new FirebaseOptions.Builder()
				    .setCredentials(credentials).setFirestoreOptions(firestoreOptions)
				    .build();
				FirebaseApp.initializeApp(options);
				authenticated = true;
				this.db = FirestoreClient.getFirestore();
				FirebaseApp.initializeApp(options,"data");
				this.docRef = db.collection("information").document("players");
				this.docTeamRef = db.collection("information").document("team");
				this.docClipRef = db.collection("information").document("clip");

//				ArrayList<DrawnObject> drawList = new ArrayList<DrawnObject>();
//				DrawnObject text = new DrawnObject(100-12, 100+12, Color.BLACK, 0, "Test");
//				drawList.add(text);
//				ArrayList<String> players = new ArrayList<String>();
//				players.add("TestName1");
//				players.add("TestName2");
//				Clip c = new Clip("TestTime", "Miami vs. Omaha", players, drawList, "testurl", "testGame2");
//				addClip(c);
				ArrayList<DrawnObject> obj = new ArrayList<DrawnObject>();
				addChartToPosition("defense", "Miami vs. Test2", obj, "netChart");
				//addGame("opponentTest", "9/3/2018");

				//getPlayer(db, "6");
				//getPlayers(db);
				//getGameStats("Miami vs. Omaha");
				//addMember(docRef);
				//addGoalie();
				
				//deletePlayerGoalie(docRef);
				// getPlayerStats("6");
				//createTeam(docRefTeam);
//				ArrayList<Object> map = getGameStats();
//				System.out.println(map);
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return authenticated;
		}

	/**
	 * Adds a new clip if it has a unique title or modifies an existing clip if one
	 * exists
	 * 
	 * @param clip
	 */
	@SuppressWarnings("unchecked")
	public void addClip(Clip clip) {
		DocumentReference ref = this.docClipRef.collection(clip.getGame()).document(clip.getTitle());
		ApiFuture<WriteResult> result = ref.set(clip, SetOptions.merge());

		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Retrieve an arraylist of clips based on a string game
	 * 
	 * @param game
	 * @return
	 */
	public ArrayList<Clip> getClips(String game) {
		ArrayList<Clip> clips = new ArrayList<Clip>();
		Iterable<CollectionReference> collections = this.docClipRef.getCollections();

		for (CollectionReference col : collections) {
			if (col.getId().toString().equals(game)) { // this means this collection we get clips
				ApiFuture<QuerySnapshot> future = col.get();
				try {
					QuerySnapshot query = future.get();
					List<QueryDocumentSnapshot> docs = query.getDocuments();
					for (QueryDocumentSnapshot snap : docs) { // this gets every clip object for that game
						String gameName = (String) snap.get("game"); // get game name
						ArrayList<String> players = (ArrayList<String>) snap.get("players"); // get list of players
						ArrayList<Object> rinkDiagram = (ArrayList<Object>) snap.get("rinkDiagram");
						String time = (String) snap.get("time");
						String title = (String) snap.get("title");
						String url = (String) snap.get("url");
						ArrayList<DrawnObject> drawings = new ArrayList<DrawnObject>();
						for (Object s : rinkDiagram) {
							// start passing diagram
							ArrayList<Point> points = new ArrayList<Point>();
							String text = null;
							double width = 0;
							boolean hasText = false;
							DrawnObject drawnObject = new DrawnObject(); // create a new object
							if (s instanceof HashMap) {
								Map map = (HashMap) s;
								for (Object obj : map.keySet()) {
									Object value = map.get(obj);
									if (value instanceof ArrayList) { // this is an arraylist of points
										ArrayList result = (ArrayList) value;
										for (Object v : result) {
											if (v instanceof HashMap) { // for a single point
												HashMap pointInfo = (HashMap) v;
												double x = 0;
												double y = 0;
												Color color = new Color(0, 0, 0, 0);
												for (Object pointKeys : pointInfo.keySet()) {
													System.out.println(pointKeys + " = " + pointInfo.get(pointKeys));
													if (pointKeys.equals("x")) {
														x = (double) pointInfo.get(pointKeys);
													} else if (pointKeys.equals("y")) {
														y = (double) pointInfo.get(pointKeys);
													} else { // color

														// System.out.println(pointInfo.get(pointKeys).getClass());
														if (pointInfo.get(pointKeys) instanceof HashMap) {
															HashMap colorCreate = (HashMap) pointInfo.get(pointKeys);
															double r = 0, g = 0, b = 0;

															for (Object colorVals : colorCreate.keySet()) {
																if (colorVals.equals("red")) {
																	double val = (double) colorCreate.get(colorVals);
																	r = val;
																} else if (colorVals.equals("green")) {
																	double val = (double) colorCreate.get(colorVals);
																	g = val;
																} else if (colorVals.equals("blue")) {
																	double val = (double) colorCreate.get(colorVals);
																	b = val;
																} // I am ignoring saturation, brightness, opague, hue
																	// System.out.println(colorVals + " = " +
																	// colorCreate.get(colorVals));
															}

															color = Color.color(r, g, b);
														}

													} // end color
												}
												// now set the point value
												Point p = new Point(x, y, color);
												points.add(p);
											} // end point creation

										} // created a number of points

									} else if (!(value instanceof HashMap)) { // not include last point
										System.out.println(obj + " = " + map.get(obj));
										if (obj.equals("width")) {
											width = (double) value;
											System.out.println(width);
										} else if (obj.equals("text")) {
											text = (String) value;
											hasText = true;
											System.out.println(text);
										} else if (obj.equals("hasText")) {
											hasText = (boolean) value;
											System.out.println(hasText);
										}
									} // end search through words

								}

							}
							drawnObject.setHasText(hasText);
							drawnObject.setPoints(points);
							drawnObject.setText(text);
							drawnObject.setWidth(width);
							drawings.add(drawnObject);

						} // stop parsing through rink

						// Make a new clip object
						Clip clip = new Clip();
						clip.setGame(game);
						clip.setRinkDiagram(drawings);
						clip.setTime(time);
						clip.setTitle(title);
						clip.setURL(url);
						clip.setPlayers(players);

						// add this clip to arraylist of clips
						clips.add(clip);

					}
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break; // no need to look at other references
			}

		}
		return clips;
	}

	// example of adding a team
	public void createTeam() {
		// TODO Auto-generated method stub
		Team team = new Team("Miami", 14, 44, 35, 468, 405, "9-3-1-1");
		Map<String, Game> games = new HashMap<String, Game>();
		Game game1 = new Game();
		game1.gameName = "Miami vs. Test2";
		game1.finalScore = "3-1";

		TeamScore offense = new TeamScore("Miami", 4, 3, 6, 7);
		TeamScore defense = new TeamScore("Omaha", 5, 2, 7, 1);
		// create array list of drawn objects
		ArrayList<DrawnObject> NetChart = new ArrayList<DrawnObject>();

		DrawnObject d1 = new DrawnObject(100.0, 60.0, Color.color(.77, .13, .2), 40, "1");
		DrawnObject d2 = new DrawnObject(540.0, 137.0, Color.color(.77, .13, .2), 40, "2");
		DrawnObject d3 = new DrawnObject(115.0, 231.0, Color.GREEN, 40, "3");
		DrawnObject d4 = new DrawnObject(620.0, 364.0, Color.GREEN, 40, "4");

		NetChart.add(d1);
		NetChart.add(d2);
		NetChart.add(d3);
		NetChart.add(d4);
		defense.setNetChart(NetChart);

		// Sample data for a scoring chances chart - very similar, pretty much only
		// width is different
		// (and arbitrary x and y positions obviously)
		ArrayList<DrawnObject> SCChart = new ArrayList<DrawnObject>();

		DrawnObject s1 = new DrawnObject(206.0, 109.0, Color.color(.77, .13, .2), 20, "1");
		DrawnObject s2 = new DrawnObject(302.0, 126.0, Color.color(.77, .13, .2), 20, "2");
		DrawnObject s3 = new DrawnObject(436.0, 258.0, Color.GREEN, 20, "3");
		DrawnObject s4 = new DrawnObject(214.0, 285.0, Color.GREEN, 20, "4");

		SCChart.add(s1);
		SCChart.add(s2);
		SCChart.add(s3);
		SCChart.add(s4);
		offense.setScoringChart(SCChart);

		// set game offense and defense
		game1.offense = offense;
		game1.defense = defense;

		games.put(game1.gameName, game1);
		team.games = games;

		// add to database
		addTeam(team);

	}

	/**
	 * Example method of deleting a goalie
	 * 
	 * @param docRef
	 */
	public void deletePlayerGoalie() {
		Position position = new Position("goalie");
		Goalie goalie = new Goalie(4, 3, 2);
		position.goalie(goalie);
		PlayerStats stats1 = new PlayerStats("16-17", 6, position);
		Map<String, PlayerStats> stats = new HashMap<String, PlayerStats>();
		stats.put(stats1.season, stats1);
		Player player = new Player(31, "Ryan Larkin", "6'1", "201 lbs", "6/10/97", "Clarkson, MI", stats);

		deletePlayer(player);
	}

	/**
	 * Example method of adding a goalie
	 * 
	 * @param docRef
	 */
	public void addGoalie() {
		Position position = new Position("goalie");
		Goalie goalie = new Goalie(4, 3, 2);
		position.goalie(goalie);
		PlayerStats stats1 = new PlayerStats("17-17", 6, position);
		Map<String, PlayerStats> stats = new HashMap<String, PlayerStats>();
		stats.put(stats1.season, stats1);
		Player player = new Player(31, "Ryan Larkin", "6'1", "201 lbs", "6/10/97", "Clarkson, MI", stats);

		addPlayer(player);
	}

	/**
	 * Example method of adding a player
	 * 
	 * @param docRef
	 */
	public void addMember() {
		Position position = new Position("defense");
		Member defense = new Member(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
		position.member(defense);
		PlayerStats stats1 = new PlayerStats("16-17", 8, position);
		Map<String, PlayerStats> stats = new HashMap<String, PlayerStats>();
		stats.put(stats1.season, stats1);
		Player player = new Player(6, "Alec Mahalak", "5'9", "161 lbs", "9/14/98", "Monroe, MI", stats);

		addPlayer(player);
	}

	/**
	 * add player to the database (does not override the database only appends to
	 * it)
	 * 
	 * @param docRef
	 * @param player
	 */
	public void addPlayer(Player player) {

		Map<String, Player> players = new HashMap<>();

		players.put("" + player.jerseyNo, player);

		ApiFuture<WriteResult> result = this.docRef.set(players, SetOptions.merge());
		// ...
		// result.get() blocks on response
		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void addPlayerWithPosition(Player player, String position) {

		Map<String, Player> players = new HashMap<>();

		// ...

		Position createdPosition = new Position();
		createdPosition.namePosition = position;
		System.out.println("I got here");
			if (position.equals("goalie")) {

				Goalie goalie = new Goalie(0,0,0,0,0,0,0,0,0);
				createdPosition.goalie = goalie;

			} else {

				Member mem = new Member(0,0,0,0,0,0,0,0,0,0,0,0,0);
				createdPosition.member = mem;
			} 

			Map<String, PlayerStats> stats = new HashMap<String, PlayerStats>();
			PlayerStats pStats = new PlayerStats(null, 0, createdPosition);
			stats.put("current", pStats);
			player.stats = stats;

		

		players.put("" + player.jerseyNo, player);
		ApiFuture<WriteResult> result = this.docRef.set(players, SetOptions.merge());

		try {

			System.out.println("Update time : " + result.get().getUpdateTime());

		} catch (InterruptedException | ExecutionException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	/**
	 * adds an array of players to the database (does not override the database only
	 * appends to it)
	 * 
	 * @param docRef
	 * @param player
	 */
	public void addPlayers(Map<String, Player> players) {
		ApiFuture<WriteResult> result = this.docRef.set(players, SetOptions.merge());

		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Removes all players from the document (essentially deleting the document
	 * players)
	 * 
	 * @param docRef
	 */
	public void removeAllPlayers() {

		ApiFuture<WriteResult> result = this.docRef.delete();
		// ...
		// result.get() blocks on response
		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deletePlayerByJerseyNo(long jerseyNo) {
		Map<String, Object> players = new HashMap<>();

		players.put("" + jerseyNo, FieldValue.delete());

		ApiFuture<WriteResult> result = this.docRef.set(players, SetOptions.merge());
		// ...
		// result.get() blocks on response
		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Remove existing player from database
	 */
	public void deletePlayer(Player player) {

		Map<String, Object> players = new HashMap<>();

		players.put("" + player.jerseyNo, FieldValue.delete());

		ApiFuture<WriteResult> result = this.docRef.set(players, SetOptions.merge());
		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Add a team to the database
	 * 
	 * @param docRef
	 * @param team
	 */
	public void addTeam(Team team) {
		Map<String, Team> teams = new HashMap<>();

		teams.put(team.teamName, team);

		ApiFuture<WriteResult> result = this.docTeamRef.set(teams, SetOptions.merge());
		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Add a game with string opponent and string date
	 */
	public void addGame(String opponent, String date) {
		Game game = new Game();
		game.gameName = opponent + " " + date;
		TeamScore offense = new TeamScore();
		offense.teamName = "Miami";
		TeamScore defense = new TeamScore();
		defense.teamName = opponent;
		game.offense = offense;
		game.defense = defense;
		Team team = new Team();
		team.teamName = "Miami";
		Map<String, Game> games = new HashMap<String, Game>();
		//games.put("Miami vs. " + opponent, game);
		games.put(game.gameName, game);
		team.games = games;
		addTeam(team);
		
	}
	
	
	/**
	 * get a specific player based on their jersey no
	 * 
	 * @param db
	 * @param jerseyNo
	 * @return
	 */
	public HashMap getPlayer(String jerseyNo) {
		// asynchronously retrieve all documents
		// asynchronously retrieve multiple documents
		DocumentReference docRef = this.db.collection("information").document("players");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document;

		try {
			document = future.get();
			HashMap map = (HashMap) document.get(jerseyNo);

			if (map.size() > 0) {
				return map;
			}
			// future.get() blocks on response

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		// get the hashmap that contains the player stats

	}

	/**
	 * Get the player stats on the player based on jerseyNo. It creates a String
	 * which is the jerseyNo and the rest of the stats is a hashmap of all the
	 * seasons for that player
	 * 
	 * @param jerseyNo
	 * @return String season and the rest of the stats
	 */
	public HashMap<String, HashMap> getPlayerStats(String jerseyNo) {
		// asynchronously retrieve all documents
		// asynchronously retrieve multiple documents
		DocumentReference docRef = this.db.collection("information").document("players");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document;
		HashMap<String, HashMap> allStats = new HashMap<String, HashMap>();

		try {
			document = future.get();
			
			Object snap =  document.get(jerseyNo + ".stats");
			if (snap != null) {
			HashMap map = (HashMap) snap;
			
			if (map.size() > 0) {
				for (Object s : map.keySet()) { // for each game
					// System.out.println(s);
					Object detailsOnGame = map.get(s);

					if (detailsOnGame instanceof HashMap) {

						HashMap details = (HashMap) detailsOnGame;
						// System.out.println("Details on "+s+" = " +details);
						String season = (String) details.get("season");

						Long GP = (Long) details.get("GP");
						HashMap position = (HashMap) details.get("position");

						if (position instanceof HashMap) {
							HashMap positionG = (HashMap) position;
							if (positionG.get("goalie") != null) {
								// the player is a goalie

								Object posInfo = positionG.get("goalie");

								if (posInfo instanceof HashMap) {
									HashMap member = (HashMap) posInfo;
									// System.out.println("Details on goalie " + member);
									// Create a hashmap for one season
									HashMap<String, Object> info = new HashMap<String, Object>();

									info.put("season", season);
									info.put("GP", GP);
									info.put("W", member.get("W"));
									info.put("L", member.get("L"));
									info.put("T", member.get("T"));
									info.put("GA", member.get("GA"));
									info.put("GAA", member.get("GAA"));
									info.put("SA", member.get("SA"));
									info.put("SV", member.get("SV"));
									info.put("SVpercent", member.get("SVpercent"));
									info.put("SO", member.get("SO"));
									allStats.put(season, info);
									// System.out.println("info: "+info);
								}

							} else {
								// the player cis a member
								Object posInfo = positionG.get("member");
								if (posInfo instanceof HashMap) {
									HashMap member = (HashMap) posInfo;

									// Create a hashmap for one season
									HashMap<String, Object> info = new HashMap<String, Object>();
									info.put("season", season);
									info.put("GP", GP);
									info.put("PPA", member.get("PPA"));
									info.put("A", member.get("A"));
									info.put("PPG", member.get("PPG"));
									info.put("G", member.get("G"));
									info.put("SOG", member.get("SOG"));
									info.put("percent", member.get("percent"));
									info.put("PTS", member.get("PTS"));
									info.put("PROD", member.get("PROD"));
									info.put("SHG", member.get("SHG"));
									info.put("GWG", member.get("GWG"));
									info.put("winsOrLosses", member.get("winsOrLosses"));
									info.put("GTG", member.get("GTG"));
									info.put("TOIG", member.get("TOIG"));
									allStats.put(season, info);
								}

							}

						}
					}
				}
			} 
			}
				else {
				
				return null;
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		return allStats;

	}

	/**
	 * get hashmap of all player stats (string being the jerseyNo and object being
	 * the rest of the player stats
	 * 
	 * @return a map of all the players that exist
	 */
	public Map<String, Object> getPlayers() {

		DocumentReference docRef = this.db.collection("information").document("players");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document;
		try {
			document = future.get();

			if (document.exists()) {

				System.out.println("Document data: " + document.getData());
				return document.getData();
			} else {
				System.out.println("No such document!");
				return null;
			}

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * get hashmap of all team stats (string being the jerseyNo and object being the
	 * rest of the player stats
	 * 
	 * @return all the statistics about the team Miami
	 */
	public Map<String, Object> getTeam() {

		DocumentReference docRef = this.db.collection("information").document("team");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document;
		try {
			document = future.get();

			if (document.exists()) {

				System.out.println("Document data: " + document.getData());
				return document.getData();
			} else {
				System.out.println("No such document!");
				return null;
			}

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * get information about a specific game
	 * 
	 * @param game
	 * @return details about that game
	 */
	public HashMap getGameStats(String game) {
		// asynchronously retrieve all documents
		// asynchronously retrieve multiple documents
		DocumentReference docRef = this.db.collection("information").document("team");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document;

		try {
			document = future.get();

			HashMap map = (HashMap) document.get("Miami.games");
			// Returns about a specific game
			// System.out.println(map.get(game).toString());
			if (map.size() > 0) {
				return map;
			}
			// future.get() blocks on response

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * An ArrayList of Objects for all the games (The Objects are Strings)
	 */
	public ArrayList<Object> getGameStats() {
		ArrayList<Object> list;
		// asynchronously retrieve all documents
		// asynchronously retrieve multiple documents
		DocumentReference docRef = this.db.collection("information").document("team");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document;

		try {
			document = future.get();

			HashMap map = (HashMap) document.get("Miami.games");
			if (map.size() > 0) {
				list = new ArrayList<Object>(Arrays.asList(map.keySet().toArray()));
				return list;
			}
			// future.get() blocks on response

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	

	
	/**
	 * Update game info (charts)
	 */
	public void addGame(Game g) {
		Game game = g;
		Team team = new Team();
		team.teamName = "Miami";
		Map<String, Game> games = new HashMap<String, Game>();
		games.put(game.gameName, game);
		team.games = games;
		addTeam(team);
	}
	
	/**
	 * add chart info based on game, chart, offense/defense
	 */
	public void addChart(String position, String game, String chart, ArrayList<DrawnObject> diagram) {
		System.out.println("Game: " + game);
		HashMap games = getGameStats(game);
		System.out.println(games.keySet().toString());
		HashMap currentGame = (HashMap) games.get(game);
		System.out.println(currentGame.keySet().toString());
		
		//Recreate the Game object
		Game gameData = new Game();
		if(currentGame.get("gameName") != null) {
			String gameName = (String) currentGame.get("gameName");
			System.out.println("gameName: " + gameName);
			gameData.gameName = gameName;
		}
		if(currentGame.get("finalScore") != null) {
			String finalScore = (String) currentGame.get("finalScore");
			gameData.finalScore = finalScore;
		}
		if(currentGame.get("url") != null) {
			String url = (String) currentGame.get("url");
			gameData.url = url;
		}
		
		//offense for Game object
		HashMap offense = (HashMap) currentGame.get("offense");
		System.out.println(offense.keySet().toString());
		String teamName = (String) offense.get("teamName");
		int shotsFor = ((Long) offense.get("shotsFor")).intValue();
		int goalsFor = ((Long) offense.get("goalsFor")).intValue();
		int shotsAgainst = ((Long) offense.get("shotsAgainst")).intValue();
		int goalsAgainst = ((Long) offense.get("shotsFor")).intValue();
		TeamScore oScore = new TeamScore(teamName, goalsFor, goalsAgainst, shotsFor, shotsAgainst);
		if(offense.get("netChart") != null) {
			//recreate netChart object
			oScore.setNetChart(getChart("offense", game, "netChart"));
		}
		if(offense.get("scoringChart") != null) {
			//recreate scoringChart
			oScore.setScoringChart(getChart("offense", game, "scoringChart"));
		}
		
		//replace with desired chart
		if(position.equals("offense")) {
			if(chart.equals("netChart")) {
				oScore.setNetChart(diagram);
			}
			else if(chart.equals("scoringChart")) {
				oScore.setScoringChart(diagram);
			}
		}
		//set offense
		gameData.offense = oScore;
		
		//defense for Game object
		HashMap defense = (HashMap) currentGame.get("defense");
		System.out.println(defense.keySet().toString());
		teamName = (String) defense.get("teamName");
		shotsFor = ((Long) defense.get("shotsFor")).intValue();
		goalsFor = ((Long) defense.get("goalsFor")).intValue();
		shotsAgainst = ((Long) defense.get("shotsAgainst")).intValue();
		goalsAgainst = ((Long) defense.get("shotsFor")).intValue();
		TeamScore dScore = new TeamScore(teamName, goalsFor, goalsAgainst, shotsFor, shotsAgainst);
		if(defense.get("netChart") != null) {
			//recreate netChart object
			dScore.setNetChart(getChart("defense", game, "netChart"));
		}
		if(defense.get("scoringChart") != null) {
			//recreate scoringChart
			dScore.setScoringChart(getChart("defense", game, "scoringChart"));
		}
		
		//replace with desired chart
		if(position.equals("defense")) {
			if(chart.equals("netChart")) {
				dScore.setNetChart(diagram);
			}
			else if(chart.equals("scoringChart")) {
				dScore.setScoringChart(diagram);
			}
		}
		
		//set defense
		gameData.defense = dScore;
		
		addGame(gameData);
	}
	

	/**
	 * Gives the position of the player (goalie, or member) by inputting a jersey
	 * no.
	 */
	public String getPosition(String jerseyNo) {
		String posName = null;
		DocumentReference docRef = db.collection("information").document("players");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document;

		try {
			document = future.get();
			
			Object snap =  document.get(jerseyNo + ".stats");  //will throw a null pointer exception if this is null
			if (snap != null) {
			HashMap map = (HashMap) snap;
			// System.out.println(map);
			if (map.size() > 0) {

				for (Object s : map.keySet()) { // for each game
					Object detailsOnGame = map.get(s);
					if (detailsOnGame instanceof HashMap && posName == null) {
						HashMap details = (HashMap) detailsOnGame;
						HashMap position = (HashMap) details.get("position");
						posName = (String) position.get("namePosition");
					}
				}
			}
			} else {  //return something or else an exception will be thrown later
				return "";
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return posName;

	} // end getPosition

	/**
	 * Get the jerseyNo of a player by inputting their name
	 * 
	 * @param playerName
	 * @return jerseyNo
	 */
	public String getJerseyNo(String playerName) {
		String jerseyNo = null;
		DocumentReference docRef = db.collection("information").document("players");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document;

		try {
			document = future.get();
			Map<String, Object> players = getPlayers();

			if (players.size() > 0) {

				for (Object s : players.keySet()) { // for each game
					Object detailsOfPlayer = players.get(s);
					if (detailsOfPlayer instanceof HashMap && jerseyNo == null) {
						HashMap details = (HashMap) detailsOfPlayer;

						if (details.get("name").equals(playerName)) {
							jerseyNo = details.get("jerseyNo").toString();
						}
					}
				}
			}

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jerseyNo;
	}
	
	public ArrayList<String> playerNames() {

		ArrayList<String> names = new ArrayList<String>();

		Map<String, Object> players = getPlayers();

		for (String obj: players.keySet()) {

			Object value = players.get(obj);

			if (value instanceof HashMap) {

				HashMap val = (HashMap) value;

				if (val.containsKey("name")) {

					Object name = val.get("name");

					names.add((String) name);

				}

				

			}

	

		}		

		return names;

	}
	
	public void addChartToPosition(String position, String game, ArrayList<DrawnObject> chart, String typeOfChart) {
		
		
		ApiFuture<DocumentSnapshot> future = this.docTeamRef.get();
		DocumentSnapshot document;
		
		try {
			document = future.get();
			HashMap map = (HashMap) document.get("Miami.games");

			// System.out.println(map);
			if (map.size() > 0) {

				for (Object s : map.keySet()) { // for each game
					Object detailsOnGame = map.get(s);
					System.out.println(s + "=>" + detailsOnGame);
				}
			}

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
	}
	}

	// position = defense or offense, and string game
	// chart is either "scoringChart" or "netChart"
	public ArrayList<DrawnObject> getChart(String position, String game, String chart) {
		ArrayList<DrawnObject> drawings = new ArrayList<DrawnObject>();
		ApiFuture<DocumentSnapshot> future = this.docTeamRef.get();
		DocumentSnapshot document;
		try {
			document = future.get();
			HashMap map = (HashMap) document.get("Miami.games");
			if (map.size() > 0) {
				for (Object s : map.keySet()) { // for each game
					// System.out.println(s);
					if (s.equals(game)) {// get the key that equals the game
						Object detailsOnGame = map.get(s);
						if (detailsOnGame instanceof HashMap) {
							HashMap details = (HashMap) detailsOnGame;
							for (Object d : details.keySet()) {
								if (d.equals(position)) {
									Object teamStats = details.get(d); // gives offense/defense details
									if (teamStats instanceof HashMap) {
										HashMap teamShots = (HashMap) teamStats;
										for (Object t : teamShots.keySet()) {
											if (t.equals(chart)) {
												Object chartDetails = teamShots.get(t);
												if (chartDetails instanceof ArrayList) {
													ArrayList<Object> chartDetail = (ArrayList) chartDetails;
													for (Object n : chartDetail) {
														// System.out.println(n);
														String text = null;
														double width = 0;
														boolean hasText = false;
														ArrayList<Point> points = new ArrayList<Point>();
														DrawnObject drawnObject = new DrawnObject(); // create a new
																										// object
														if (n instanceof HashMap) {
															Map mapPoints = (HashMap) n;

															for (Object obj : mapPoints.keySet()) {
																Object value = mapPoints.get(obj);
											
																if (value instanceof HashMap) { // this is an arraylist
																								// of points
																	HashMap result = (HashMap) value;
																	double x = 0;
																	double y = 0;
																	Color color = null;
																	for (Object v : result.keySet()) {
																		
																		Object resultValue = result.get(v);
																		if (resultValue instanceof HashMap) { // this is
																												// color
																			HashMap colorCreate = (HashMap) resultValue;
																			double r = 0, g = 0, b = 0;

																			for (Object colorVals : colorCreate
																					.keySet()) {

																				if (colorVals.equals("red")) {
																					double val = (double) colorCreate
																							.get(colorVals);
																					r = val;
																				} else if (colorVals.equals("green")) {
																					double val = (double) colorCreate
																							.get(colorVals);
																					g = val;
																				} else if (colorVals.equals("blue")) {
																					double val = (double) colorCreate
																							.get(colorVals);
																					b = val;
																				} // I am ignoring saturation,
																					// brightness, opague, hue

																			}
																			// create color
																			color = Color.color(r, g, b);

																		}

																		else if (v.equals("x")) {
																			x = (double) result.get(v);
																		} else if (v.equals("y")) {
																			y = (double) result.get(v);
																		}
																		// end color

																		// now set the point value
																		Point p = new Point(x, y, color);

																		points.add(p);
																	} // end point creation

																} else if (!(value instanceof HashMap)) { // not include
																											// last
																											// point

																	if (obj.equals("width")) {
																		width = (double) value;

																	} else if (obj.equals("text")) {
																		text = (String) value;
																		hasText = true;

																	} else if (obj.equals("hasText")) {
																		hasText = (boolean) value;

																	}
																} // end search through words

															}

														}
														drawnObject.setHasText(hasText);
														drawnObject.setPoints(points);
														drawnObject.setText(text);
														drawnObject.setWidth(width);

														drawings.add(drawnObject);
													}
												}

											} // end found scoring chart
										}

									} // end instanceof Hashmap
								} // end find position

							} // end second for
						} // end instanceof Hashmap

						break; // no need to look at other games
					} // end game found

				} // end first for
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return drawings;
	}

}
