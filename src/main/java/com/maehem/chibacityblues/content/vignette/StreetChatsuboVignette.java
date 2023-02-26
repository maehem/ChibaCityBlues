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
import com.maehem.abyss.engine.Vignette;
import com.maehem.abyss.engine.VignetteTrigger;
import com.maehem.abyss.engine.babble.DialogPane;
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
public class StreetChatsuboVignette extends Vignette {

    //private static final String PROP_NAME = "street2";
    private static final String CONTENT_BASE = "/content/vignette/street-chatsubo/";
    private static final String SKYLINE_IMAGE_FILENAME   = CONTENT_BASE + "skyline.png";
    private static final String ROBOT_POSE_SHEET_FILENAME = CONTENT_BASE + "police-robot.png";
    //private static final String DOOR_PATCH_IMAGE_FILENAME = CONTENT_BASE + "door-right-wing.png";
    public static final Point2D PLAYER_START = new Point2D(0.50, 0.86);

    private static final double[] WALK_BOUNDARY = {
             0.05, 0.9,     0.11, 0.8,
             0.32, 0.7,    0.6, 0.7,
             0.65, 0.65,      0.73, 0.65,
             0.8, 0.70,    0.99, 0.70,
             0.99, 0.98,     0.05, 0.98
    };

//    private static final Patch pawnShopDoorPatch = new Patch(
//            1140, 0, 500, 
//            StreetChatsuboVignette.class.getResourceAsStream(DOOR_PATCH_IMAGE_FILENAME));
    
    private static final VignetteTrigger rightDoor = new VignetteTrigger(
            0.98, 0.66, // Location
            0.02, 0.34, // Size
            VignetteTrigger.SHOW_TRIGGER,
            StreetBodyShopVignette.PLAYER_START.getX(), 
            StreetBodyShopVignette.PLAYER_START.getY(), 
            PoseSheet.Direction.TOWARD, // Player position and orientation at destination
            "StreetBodyShopVignette" // Destination
    );
    private static final VignetteTrigger topDoor = new VignetteTrigger(
            0.65, 0.65,
            0.073, 0.014,
            ChatsuboBarVignette.PLAYER_START.getX(), 
            ChatsuboBarVignette.PLAYER_START.getY(), 
            PoseSheet.Direction.TOWARD,
            "ChatsuboBarVignette"
    );
    
    private com.maehem.abyss.engine.Character policeRobotCharacter;
    private int npcAnimationCount = 0;

    public StreetChatsuboVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(gs, CONTENT_BASE,prevPort, player, WALK_BOUNDARY);
    }

    @Override
    public void loop() {
        npcAnimationCount++;
        if (npcAnimationCount > 40) {
            npcAnimationCount = 0;
            policeRobotCharacter.nextPose();
        }
    }

    @Override
    protected void init() {
        setHorizon(0.31);
        // set player position
        //setPlayerPosition(PLAYER_START);

        addPort(rightDoor); // Doors
        addPort(topDoor);

        initBackground();
        initNpc();        

        // example: Depth of field
        //fgGroup.setEffect(new BoxBlur(10, 10, 3));
    }

    private void initNpc() {
        policeRobotCharacter = new com.maehem.abyss.engine.Character(bundle.getString("character.robot.name"));
        policeRobotCharacter.setScale(2.6);
        policeRobotCharacter.setLayoutX(250);
        policeRobotCharacter.setLayoutY(520);
        policeRobotCharacter.setEffect(new DropShadow(70, 0, 110, new Color(0,0,0,0.5)));

        // TODO:   Check that file exists.  The current exception message is cryptic.
        policeRobotCharacter.setSkin(PawnShopVignette.class.getResourceAsStream(ROBOT_POSE_SHEET_FILENAME), 1, 4);
        LOGGER.finer("Add skin for police robot. " + ROBOT_POSE_SHEET_FILENAME);

        // Dialog
        initRobotPoliceDialog();

        getCharacterList().add(policeRobotCharacter);
        getBgGroup().getChildren().add( policeRobotCharacter );
    }
    
    private void initBackground() {
        // Skyline behind scene, through window.
        final ImageView skylineView = new ImageView();
        skylineView.setImage(new Image(getClass().getResourceAsStream(SKYLINE_IMAGE_FILENAME)));
        skylineView.setFitWidth(1280);
        skylineView.setPreserveRatio(true);
        skylineView.setLayoutX(0);
        skylineView.setLayoutY(0);
        // Add these in visual order.  Back to front.
        getSkylineGroup().getChildren().add( skylineView );


    }

    // TODO:  Ways to automate this.   JSON file?
    private void initRobotPoliceDialog() {
        DialogPane dp = policeRobotCharacter.getDialogPane();
        policeRobotCharacter.setAllowTalk(true);
        // Eddie kicks the player out of the shop but gives him his item.
        DialogResponseAction exitAction = () -> {
            dp.setActionDone(true);
        };

        DialogSheet2 ds1= new DialogSheet2(dp);
        ds1.setDialogText(bundle.getString("dialog.ds1.robot"));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.1"), exitAction)); // Exit action

        dp.addDialogSheet(ds1);
    }

//    @Override
//    public String getPropName() {
//        return PROP_NAME;
//    }

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
    public Point2D getDefaultPlayerPosition() {
        return PLAYER_START;
    }
}
