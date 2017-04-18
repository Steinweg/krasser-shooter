/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startupScreen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;

import baseClasses.Player;
import baseClasses.Spielstand;

/**
 *
 * @author eikes
 */
public class StartupScreen extends Application{
    
    private static ArrayList<Player> players = new ArrayList<>();
    private static int playerCount = 0;
    private static int numPlayers = 1;
    private Spielstand spielstand;
    private String musicFile;
    private Media sound;
    private MediaPlayer mediaPlayer;
    
    @Override public void start(final Stage stage){
        stage.setTitle("coolest shooter you will ever see");
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        
        Label player = new Label("player, enter name:");
        gridPane.add(player, 0,0);
        
        final TextField playerEnterName = new TextField();
        gridPane.add(playerEnterName,0,4);
        
        /**
        * uncomment if musicfile is availeble
        */
        //musicFile = "Assets/Sounds/ScreenSound.mp3";
        //sound = new Media(new File(musicFile).toURI().toString());
        //mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.play();
        
        Button button = new Button("ok");
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
                Player myself = new Player(playerEnterName.getText());
                players.add(myself);
                playerCount++;
                /**
                 * enter functionality to wait for other 
                 * players in multiplayer (not done yet)
                 */
                if(playerCount == numPlayers){
                    spielstand.getInstance().initialize(players,myself);
                    stage.close();
                }
            }
        } );
        gridPane.add(button,0,8);

        Scene scene = new Scene(gridPane,300,275);
        /**
        * uncomment with right path if css is availeble
        */
        //scene.getStylesheets().add("assets/Style/StyleSheet.css");
        stage.setScene(scene);
        stage.show();
    }
}
