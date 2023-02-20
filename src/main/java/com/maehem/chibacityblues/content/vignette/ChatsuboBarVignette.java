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
package com.maehem.chibacityblues.content.vignette;

import static com.maehem.abyss.Engine.LOGGER;
import com.maehem.abyss.engine.GameState;
import com.maehem.abyss.engine.Player;
import com.maehem.abyss.engine.PoseSheet;
import com.maehem.abyss.engine.TerminalTrigger;
import com.maehem.abyss.engine.Vignette;
import com.maehem.abyss.engine.VignetteTrigger;
import com.maehem.abyss.engine.babble.DialogResponse2;
import com.maehem.abyss.engine.babble.DialogResponseAction;
import com.maehem.abyss.engine.babble.DialogSheet2;
import java.util.Properties;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author Mark J Koch [flatlinejack at maehem dot com]
 */
public class ChatsuboBarVignette extends Vignette {

    public  static final String PROP_NAME = "chatsubo-bar";    
    private static final String CONTENT_BASE = "/content/vignette/"+ PROP_NAME +"/";
    private static final String SKYLINE_IMAGE_FILENAME   = CONTENT_BASE + "cyberpunk-cityscape.png";
    private static final String BAR_BACKGROUND_IMAGE_FILENAME   = CONTENT_BASE + "bar-background.png";
    private static final String LOGO_IMAGE_FILENAME   = CONTENT_BASE + "chatsubo-logo.png";
    private static final String COUNTERS_IMAGE_FILENAME   = CONTENT_BASE + "counter-overlay.png";
    private static final String BARTENDER_POSE_SHEET_FILENAME = CONTENT_BASE + "ratz-pose-sheet.png";

    public  static final Point2D PLAYER_START = new Point2D(0.5, 0.86);
    private static final double[] WALK_BOUNDARY = new double[] {
                0.08, 0.75,   0.92, 0.75,
                0.92, 0.95,   0.89, 0.95,   
                0.89, 1.0,    0.68, 1.0,
                0.68, 0.95,   0.08, 0.95
    };

    private static final VignetteTrigger exitPort = new VignetteTrigger(
        0.68, 0.97,   // exit location
        0.21, 0.03,   // exit size
        VignetteTrigger.SHOW_TRIGGER,
        0.43, 0.50,   // player position at destination
        PoseSheet.Direction.TOWARD, "StreetVignette2"); // Exit to here
    
    private static final TerminalTrigger terminal = new TerminalTrigger(
        0.43, 0.70,   // trigger location
        0.07, 0.07   // trigger size
    );
    
    private com.maehem.abyss.engine.Character barOwnerCharacter;
    private int shopOwnerAnimationCount = 0;

    public ChatsuboBarVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(gs, CONTENT_BASE,prevPort, player,WALK_BOUNDARY);        
    }

    @Override
    protected void init() {        
        setHorizon(0.3);

        // Do in this order.  TODO: Leverage Z-order of JavaFX?
        // Background is autoloaded by superclass.
        initShopOwner();   // then layer in shop owner     
        initBackground(); // then layer in any fixtures on top of them
        
        //getBgGroup().setOpacity(0.7);
        
        addPort(exitPort);
        addTerminal( terminal );
    }

    private void initShopOwner() {
        barOwnerCharacter = new com.maehem.abyss.engine.Character(bundle.getString("character.ratz.name"));
        barOwnerCharacter.setScale(1.3);
        barOwnerCharacter.setLayoutX(300);
        barOwnerCharacter.setLayoutY(460);

        // TODO:   Check that file exists.  The current exception message is cryptic.
        barOwnerCharacter.setSkin(getClass().getResourceAsStream(BARTENDER_POSE_SHEET_FILENAME), 1, 4);
        LOGGER.config("Add skin for pawn shop owner. " + BARTENDER_POSE_SHEET_FILENAME);
        // Dialog
        // Load dialog tree from file.
//        barOwnerCharacter.getDialog().init(getWidth(), getHeight());

        initShopOwnerDialog();

        getCharacterList().add(barOwnerCharacter);
        getBgGroup().getChildren().add(
                barOwnerCharacter
        );
    }

    private void initBackground() {
        // Skyline behind scene, through window.
        final ImageView skylineView = new ImageView();
        skylineView.setImage(new Image(getClass().getResourceAsStream(SKYLINE_IMAGE_FILENAME)));
        skylineView.setFitHeight(360);
        skylineView.setPreserveRatio(true);
        skylineView.setLayoutX(600);
        skylineView.setLayoutY(140);
        // Add these in visual order.  Back to front.
        getSkylineGroup().getChildren().add( skylineView );

        // Bar bottles behind scene, behind Ratz.
        final ImageView barBackground = new ImageView();
        barBackground.setImage(new Image(getClass().getResourceAsStream(BAR_BACKGROUND_IMAGE_FILENAME)));
        barBackground.setFitHeight(240);
        barBackground.setPreserveRatio(true);
        barBackground.setLayoutX(100);
        barBackground.setLayoutY(110);
        // Add these in visual order.  Back to front.
        getSkylineGroup().getChildren().add( barBackground );
        
        
        // Chatsubo logo over background, top of frame.
        final ImageView logoView = new ImageView();
        logoView.setImage(new Image(getClass().getResourceAsStream(LOGO_IMAGE_FILENAME)));
        logoView.setFitHeight(120);
        logoView.setPreserveRatio(true);
        logoView.setLayoutX(200);
        logoView.setLayoutY(-30);
        logoView.setEffect(new DropShadow(40, new Color(0.6,0.6,1.0,0.7)));
        // Add these in visual order.  Back to front.
        getBgGroup().getChildren().add( logoView );

        // Display Cases (in front of shop owner )
        final ImageView counterView = new ImageView();
        counterView.setImage(new Image(PawnShopVignette.class.getResourceAsStream(COUNTERS_IMAGE_FILENAME)));
        counterView.setLayoutX(138);
        counterView.setLayoutY(339);

        // Add these in visual order.  Back to front.
        getBgGroup().getChildren().add( counterView );

//        // Display Cases (in front of shop owner )
//        final ImageView counterView = new ImageView();
//        counterView.setImage(new Image(PawnShopVignette.class.getResourceAsStream(COUNTERS_IMAGE_FILENAME)));
//        counterView.setLayoutX(getWidth() - counterView.getImage().getWidth());
//        counterView.setLayoutY(getHeight() - counterView.getImage().getHeight());
//
//        // Add these in visual order.  Back to front.
//        getBgGroup().getChildren().add( counterView );
    }

    // TODO:  Ways to automate this.   JSON file?
    private void initShopOwnerDialog() {

        // Eddie kicks the player out of the shop but gives him his item.
//        DialogResponseAction exitAction = () -> {
//            barOwnerCharacter.getDialog().setExit(leftDoor);
//            barOwnerCharacter.getDialog().setActionDone(true);
//            
//            // Add cyberspace deck to inventory.
//            barOwnerCharacter.give(new KomodoDeckThing(), getPlayer());
//            
//            // TODO:
//            // GameState set StreetVignette PawnShop door locked.
//        };
        
        DialogResponseAction leaveAlone = () -> {
            // If paid, exit player and lock door.
            barOwnerCharacter.setTalking(false);
            barOwnerCharacter.getDialogPane().setActionDone(false);
        };


//        // Now just pay up. Nothing else to talk about.
//        DialogSheet ds2 = new DialogSheet(barOwnerCharacter.getDialog());
//        ds2.setDialogText(bundle.getString("dialog.ratz.ds2"));
//        ds2.addResponse(new DialogResponse(bundle.getString("dialog.p.ds2.1"), leaveAlone));
//
//        // Ratz asks to get paid and player replies with snarky comeback.
//        DialogSheet ds1 = new DialogSheet(barOwnerCharacter.getDialog());
//        ds1.setDialogText(bundle.getString("dialog.ratz.ds1"));
//        ds1.addResponse(new DialogResponse(bundle.getString("dialog.p.ds1.1"), ds2));
//        ds1.addResponse(new DialogResponse(bundle.getString("dialog.p.ds1.2"), ds2));
//        ds1.addResponse(new DialogResponse(bundle.getString("dialog.p.ds1.3"), ds2));
//        ds1.addResponse(new DialogResponse(bundle.getString("dialog.p.ds1.4"), ds2));
//
//        barOwnerCharacter.getDialog().addDialogSheet(ds1);
//        barOwnerCharacter.getDialog().addDialogSheet(ds2);

        // Now just pay up. Nothing else to talk about.
        DialogSheet2 ds2 = new DialogSheet2(barOwnerCharacter.getDialogPane());
        ds2.setDialogText(bundle.getString("dialog.ratz.ds2"));
        ds2.addResponse(new DialogResponse2(bundle.getString("dialog.p.ds2.1"), leaveAlone));

        // Ratz asks to get paid and player replies with snarky comeback.
        DialogSheet2 ds1 = new DialogSheet2(barOwnerCharacter.getDialogPane());
        ds1.setDialogText(bundle.getString("dialog.ratz.ds1"));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.p.ds1.1"), ds2));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.p.ds1.2"), ds2));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.p.ds1.3"), ds2));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.p.ds1.4"), ds2));

        barOwnerCharacter.getDialogPane().addDialogSheet(ds1);
        barOwnerCharacter.getDialogPane().addDialogSheet(ds2);
    }

    @Override
    public void loop() {
        shopOwnerAnimationCount++;
        if (shopOwnerAnimationCount > 40) {
            shopOwnerAnimationCount = 0;
            barOwnerCharacter.nextPose();
        }
    }

    @Override
    public Properties saveProperties() {
        Properties p = new Properties();
        
        // example
        // p.setProperty(PROPERTY_CONDITION, condition.toString());
        
        return p;
    }

    @Override
    public void loadProperties(Properties p) {
        // example
        //setCondition(Integer.valueOf(p.getProperty(PROPERTY_CONDITION, String.valueOf(CONDITION_DEFAULT))));
    }

    @Override
    public String getPropName() {
        return PROP_NAME;
    }

}
