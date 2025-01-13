/*
    Licensed to the Apache Software Foundation (ASF) under one or more
    contributor license agreements.  See the NOTICE file distributed with this
    work for additional information regarding copyright ownership.  The ASF
    licenses this file to you under the Apache License, Version 2.0
    (the "License"); you may not use this file except in compliance with the
    License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
    License for the specific language governing permissions and limitations
    under the License.
*/
package com.maehem.chibacityblues;

import com.maehem.abyss.Engine;
import com.maehem.abyss.engine.GameState;
import com.maehem.abyss.engine.bbs.PublicTerminalSystem;
import com.maehem.abyss.engine.bbs.widgets.BBSHeader;
import com.maehem.chibacityblues.content.matrix.DefaultSitesList;
import com.maehem.chibacityblues.content.sites.SiteHeader;
import com.maehem.chibacityblues.content.things.misc.PawnTicketThing;
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
        gameState.setGameName("ChibaCityBlues", "Chiba City Blues");
        gameState.setGameVersion( "0.0.0");
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

        engine.getPlayer().setAccountId("056306118");
        //engine.getPlayer().setBankMoney(2000);
        //engine.getPlayer().setMoney(6);
        // Overide the default menequin skin for the player.
        engine.getPlayer().setSkin(
                getClass().getResourceAsStream(
                        "/content/player/pose-sheet-4.png"), 04, 12
        );

        PublicTerminalSystem pubTerm = engine.getGameState().getPublicTerminal();
        pubTerm.setHeader(
                new BBSHeader(PublicTerminalSystem.FONT, SiteHeader.PAX)
        );

        String helpBundlePath = "content.messages.bbs.help";
        ResourceBundle helpBundle = ResourceBundle.getBundle(helpBundlePath);
        engine.getGameState().initHelp(helpBundle);

        pubTerm.getBanking().updateContent(engine.getGameState()); // Lastest player info updated.

        engine.getPlayer().getInventory().add(new PawnTicketThing());
     }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
