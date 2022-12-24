package assignment5;
/* CRITTERS GUI <MyClass.java>
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Prithvi Senthilkumar
 * ps33536
 * 17840
 * Hamza Shaikh
 * hms2659
 * 17835
 * Slip days used: <0>
 * Fall 2021
 */
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import assignment5.Critter;
import assignment5.InvalidCritterException;
import assignment5.Params;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

	private int spawnCount = 1;
	private int stepCount = 1;
	private boolean isAnimated = false;
	private AnimationTimer timer;
	private String critterName;
	private long seedCount = 0;
	private static String myPackage;
	private int BUTTON_WIDTH = 200;
	private int BUTTON_HEIGHT = 30;
	private long lastTime = 0;
	
	static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
	
    public static void main(String[] args) {
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane map = new GridPane();
		GridPane controller = new GridPane();
		
		//Text coordinate = new Text("");
    	//map.add(coordinate, 0, 0);
		
		Text spawnText = new Text("Select Critter:");
		controller.add(spawnText, Params.WORLD_WIDTH, 0);
		
		//TextField spawn = new TextField();
    	//controller.add(spawn, Params.WORLD_WIDTH, 1);
    	
    	ComboBox spawnMenu = new ComboBox();
    	File f = new File("./src/assignment5/");
    	String[] classes = f.list();
    	
    	for (String s : classes) {
    		String subs = s.substring(0, s.indexOf("."));
    		if (!(s.substring(s.indexOf(".")).equals(".java"))) {
    			break;
    		}
    		Class<?> c = Class.forName(myPackage + "." + subs);
    		//Critter blankCritter = null;
    		Class critClass = Class.forName(myPackage + "." + "Critter");
    		if (!subs.equals("Critter") && critClass.isAssignableFrom(c)) {
    			spawnMenu.getItems().add(subs);
    		}
    	}
    	spawnMenu.getSelectionModel().selectFirst();
    	controller.add(spawnMenu, Params.WORLD_WIDTH, 1);
    	
    	
		Text spawnIntro = new Text("Number of Critters to Spawn: " + spawnCount);
		controller.add(spawnIntro, Params.WORLD_WIDTH, 3);
    	
    	Slider spawnSlider = new Slider(1, 1000, 1);
    	controller.add(spawnSlider, Params.WORLD_WIDTH, 4);
    	
    	Button spawnBtn = new Button();
    	controller.add(spawnBtn, Params.WORLD_WIDTH, 5);
    	
    	Text errorText = new Text("");
    	controller.add(errorText, Params.WORLD_WIDTH + 1, 5);
    	
    	Text spacer1 = new Text("");
    	controller.add(spacer1, Params.WORLD_WIDTH, 6);
    	
    	Button statsBtn = new Button();
    	controller.add(statsBtn, Params.WORLD_WIDTH, 7);
    	
    	Text errorText2 = new Text("");
    	controller.add(errorText2, Params.WORLD_WIDTH + 1, 7);
    	
    	Text spacer2 = new Text("");
    	controller.add(spacer2, Params.WORLD_WIDTH, 8);
    	
    	Text stepIntro = new Text("Number of Time Steps: " + stepCount);
    	controller.add(stepIntro, Params.WORLD_WIDTH, 9);
		
		Slider stepSlider = new Slider(1, 1000, 1);
		controller.add(stepSlider, Params.WORLD_WIDTH, 10);
		
		Button timeStep = new Button();
		controller.add(timeStep, Params.WORLD_WIDTH, 11);
		
		Text spacer3 = new Text("");
    	controller.add(spacer3, Params.WORLD_WIDTH, 12);
    	
    	Button animateBtn = new Button();
    	controller.add(animateBtn, Params.WORLD_WIDTH, 13);
    	
    	Text spacer4 = new Text("");
    	controller.add(spacer4, Params.WORLD_WIDTH, 14);
    	
    	Text seedText = new Text("Change Seed: " + seedCount);
    	controller.add(seedText, Params.WORLD_WIDTH, 15);
		
		Slider seed = new Slider(-500, 500, 0);
		controller.add(seed, Params.WORLD_WIDTH, 16);
    	
    	Button seedBtn = new Button();
    	controller.add(seedBtn, Params.WORLD_WIDTH, 17);
    	
    	Text spacer5 = new Text("");
    	controller.add(spacer5, Params.WORLD_WIDTH, 18);
    	
    	Button clearBtn = new Button();
    	controller.add(clearBtn, Params.WORLD_WIDTH, 19);
    	
    	Text spacer6 = new Text("");
    	controller.add(spacer6, Params.WORLD_WIDTH, 20);
    	
    	Button quitBtn = new Button();
    	controller.add(quitBtn, Params.WORLD_WIDTH, 21);
		
    	timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (now - lastTime <= 500_000_000) {//set refresh rate
					return;
				}
				lastTime = now;
				for (int i = 0; i < stepCount; i++) {
            		Critter.worldTimeStep();
            	}
            	Critter.displayWorld(map);
            	
            	critterName = spawnMenu.getValue().toString();
	           	try {
	           		String className = myPackage + "." + critterName;
	           		Class<?> c = Class.forName(className);
	           		Method m = c.getMethod("runStats", List.class);
	           		String statsText = (String) m.invoke(null, Critter.getInstances(critterName));
					//errorText2.setText(Critter.runStats(Critter.getInstances(critterName)));	
	           		errorText2.setText(statsText);
				} catch (InvalidCritterException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					errorText2.setText("Invalid Critter!");
				}
			}
    	};
    	
    	//spawn.setPrefWidth(BUTTON_WIDTH);
    	spawnSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (isAnimated) return;
				spawnCount = (int) spawnSlider.getValue();
				spawnIntro.setText("Number of Critters to Spawn: " + spawnCount);
			}
			
		});
    	
    	spawnBtn.setText("Spawn Critter!");
    	spawnBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        spawnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (isAnimated) return;
            	critterName = spawnMenu.getValue().toString();
            	for (int i = 0; i < spawnCount; i++) {
            		try {
						Critter.createCritter(critterName);
						errorText.setText("");
					} catch (InvalidCritterException e) {
						errorText.setText("Invalid Critter!");
					}
            	}
            	Critter.displayWorld(map);
            }
        });
    	
        statsBtn.setText("Check Stats!");
        statsBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        statsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (isAnimated) return;
	            critterName = spawnMenu.getValue().toString();
	           	try {
	           		String className = myPackage + "." + critterName;
	           		Class<?> c = Class.forName(className);
	           		Method m = c.getMethod("runStats", List.class);
	           		String statsText = (String) m.invoke(null, Critter.getInstances(critterName));
					//errorText2.setText(Critter.runStats(Critter.getInstances(critterName)));	
	           		errorText2.setText(statsText);
				} catch (InvalidCritterException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					errorText2.setText("Invalid Critter!");
				}
	           	
            }
        });
        
		stepSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (isAnimated) return;
				stepCount = (int) stepSlider.getValue();
				stepIntro.setText("Number of Time Steps: " + stepCount);
			}
			
		});
    	
        timeStep.setText("Do Time Step!");
        timeStep.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        timeStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (isAnimated) return;
            	for (int i = 0; i < stepCount; i++) {
            		Critter.worldTimeStep();
            	}
            	Critter.displayWorld(map);
            }
        });
        
        animateBtn.setText("Start Animation!");
        animateBtn.setStyle("-fx-base: green;");
        animateBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        animateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (isAnimated == false) {
            		animateBtn.setText("Stop Animation");
            		animateBtn.setStyle("-fx-base: red;");
            		spawnMenu.setDisable(true);
            		seed.setDisable(true);
            		stepSlider.setDisable(true);
            		spawnSlider.setDisable(true);
            		timer.start();
            		isAnimated = true;
            	}
            	else {
            		animateBtn.setText("Start Animation!");
            		animateBtn.setStyle("-fx-base: green;");
            		spawnMenu.setDisable(false);
            		seed.setDisable(false);
            		stepSlider.setDisable(false);
            		spawnSlider.setDisable(false);
            		timer.stop();
            		isAnimated = false;
            	}
            }
        });
    	
        seed.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (isAnimated) return;
				seedCount = (long) seed.getValue();
				seedText.setText("Change Seed: " + seedCount);
			}
			
		});
        
        seedBtn.setText("Change Seed!");
        seedBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        seedBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (isAnimated) return;
            	Critter.setSeed(seedCount);
            }
        });
        
        clearBtn.setText("KILL THEM ALL!");
        clearBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (isAnimated) return;
            	Critter.clearWorld();
            	Critter.displayWorld(map);
            }
        });
        
        quitBtn.setText("Quit!");
        quitBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	System.exit(0);
            }
        });
        
        Text title = new Text("Map");
        //title.setFont();
        Text ctrlTitle = new Text("Controller");
        //ctrlTitle.setFont();
        
        
        Critter.displayWorld(map);
        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.HONEYDEW, null, null)));
        grid.add(title, 0, 0);
        grid.add(ctrlTitle, 1, 0);
        grid.add(map, 0, 1);
        grid.add(controller, 1, 1);
        
        //primaryStage.setScene(new Scene(grid, 250, 520));
        
        //primaryStage.setScene(new Scene(grid));

        primaryStage.setScene(new Scene(grid, 1400, 700));
        /*
        if (Params.WORLD_HEIGHT > 520) {
        	primaryStage.setScene(new Scene(grid, Params.WORLD_WIDTH*100, Params.WORLD_HEIGHT*82));
        }
        else {
        	primaryStage.setScene(new Scene(grid, Params.WORLD_WIDTH*100, 520));
        }
        */
        primaryStage.show();
        
        
	
	
	/*
	 	
    		case "stats":
    			if (inputArray.length > 2) {
    				System.out.println("error processing: " + input);
    				break;
    			}
    			if (inputArray[1].equals("Critter")) {
    				System.out.println("error processing: " + input);
    				break;
    			}
    			String className = myPackage + "." + inputArray[1];
    			try {
    				Class<?> c = Class.forName(className);
					try {
						Method m = c.getMethod("runStats", List.class);
						m.invoke(null, Critter.getInstances(inputArray[1]));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InvalidCritterException | NoSuchMethodException | SecurityException e) {
						try {
							Critter.runStats(Critter.getInstances(inputArray[1]));
						} catch (InvalidCritterException e1) {
							System.out.println("error processing: " + input);
						}
					}
    			} catch (ClassNotFoundException | ClassCastException e) {
    				System.out.println("error processing: " + input);
	    		}
    			break;
    */
    }
}