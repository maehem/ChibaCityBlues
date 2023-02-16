/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maehem.chibacityblues;

import com.maehem.abyss.Engine;
import com.maehem.abyss.engine.GameState;
import com.maehem.chibacityblues.content.matrix.DefaultSitesList;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * External content of the game needs a base class for proper class loading.
 * So this dummy class acts as an anchor for content loading.
 * 
 * @author mark
 */
public class ChibaCityBlues extends Application {

    private final Engine engine;
    
    public ChibaCityBlues() {
        engine = new Engine();
        GameState gameState = engine.getGameState();
        gameState.setContentLoader(new ChibaCityResourceLoader());
        
        gameState.setContentPack(this);
        gameState.setSites(new DefaultSitesList(gameState));
        String newsBundlePath = "content.messages.bbs.news";
        ResourceBundle newsBundle = ResourceBundle.getBundle(newsBundlePath);
        gameState.initNews(newsBundle);
        
        String msgBundlePath = "content.messages.bbs.bulletin";
        ResourceBundle msgBundle = ResourceBundle.getBundle(msgBundlePath);
        gameState.initMessages(msgBundle);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        engine.start(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
