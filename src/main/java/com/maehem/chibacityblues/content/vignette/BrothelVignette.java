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

import com.maehem.abyss.engine.Player;
import com.maehem.abyss.engine.PoseSheet;
import com.maehem.abyss.engine.Vignette;
import com.maehem.abyss.engine.Character;
import com.maehem.abyss.engine.babble.DialogResponseAction;
import com.maehem.abyss.engine.GameState;
import com.maehem.abyss.engine.VignetteTrigger;
import com.maehem.abyss.engine.babble.DialogResponse2;
import com.maehem.abyss.engine.babble.DialogSheet2;
import java.util.Properties;
import javafx.geometry.Point2D;

/**
 *
 * @author Mark J Koch [flatlinejack at maehem dot com]
 */
public class BrothelVignette extends Vignette {

    //private static final String PROP_NAME = "pawnshop";
    private static final String CONTENT_BASE = "/content/vignette/brothel/";
    public static final Point2D PLAYER_START = new Point2D(0.20, 0.77);
    //private static final String COUNTERS_IMAGE_FILENAME   = CONTENT_BASE + "counters.png";
    //private static final String DOOR_PATCH_IMAGE_FILENAME = CONTENT_BASE + "patch-left.png";
    private static final String NPC_POSE_SHEET_FILENAME = CONTENT_BASE + "npc-pose-sheet.png";
    private static final String NPC_CAMEO_FILENAME = CONTENT_BASE + "npc-cameo.png";
    private static final double[] WALK_BOUNDARY = {
            0.11, 0.73,   0.98, 0.73,
            0.98, 0.99,   0.04, 0.99,
            0.02, 0.78,   0.02, 0.72,
            0.02, 0.66
    };
    private static final VignetteTrigger leftDoor = new VignetteTrigger(
            0.02, 0.70,  // port XY location
            0.04, 0.06,  // port size
            0.72, 0.65,  // place player at this XY when they leave the pawn shop.        
            PoseSheet.Direction.LEFT, // Face this direction at destination
            "StreetMicrosoftsVignette"  // Class name of destination vignette
    );
    
//    private static final Patch leftDoorPatch = new Patch(
//            0, 0, 425, 
//            PawnShopVignette.class.getResourceAsStream(DOOR_PATCH_IMAGE_FILENAME)
//    );

    private Character npcCharacter;
    private int npcAnimationCount = 0;
    
    /**
     * 
     * @param gs
     * @param prevPort where the player came from
     * @param player the @Player
     */
    public BrothelVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(gs, CONTENT_BASE, prevPort, player, WALK_BOUNDARY);
        // Don't put things here.  Override @init() which is called during creation.
    }

    @Override
    protected void init() {
        setHorizon(0.37);

        initNpc();        
        initBackground();
                
        addPort(leftDoor);
        
        //addPatch(leftDoorPatch);
        
        // example Depth of field
        //fgGroup.setEffect(new BoxBlur(10, 10, 3));
    }

    private void initNpc() {
        npcCharacter = new Character(bundle.getString("character.npc.name"));
        npcCharacter.setScale(1.3);
        npcCharacter.setLayoutX(730);
        npcCharacter.setLayoutY(550);

        // TODO:   Check that file exists.  The current exception message is cryptic.
        npcCharacter.setSkin(BrothelVignette.class.getResourceAsStream(NPC_POSE_SHEET_FILENAME), 1, 4);
        npcCharacter.setCameo(BrothelVignette.class.getResourceAsStream(NPC_CAMEO_FILENAME));
        LOGGER.config("Add skin for npc. " + NPC_POSE_SHEET_FILENAME);

        initNpcDialog();

        getCharacterList().add(npcCharacter);
        getBgGroup().getChildren().add(npcCharacter);
    }

    private void initBackground() {

//        // Display Cases (in front of shop owner )
//        final ImageView counterView = new ImageView();
//        counterView.setImage(new Image(BrothelVignette.class.getResourceAsStream(COUNTERS_IMAGE_FILENAME)));
//        counterView.setLayoutX(0);
//        counterView.setLayoutY(0);
//        //counterView.setBlendMode(BlendMode.MULTIPLY);
//
//        // Add these in visual order.  Back to front.
//        getBgGroup().getChildren().add( counterView );
    }

    @Override
    public void loop() {
        // animate shop owner.
        npcAnimationCount++;
        if (npcAnimationCount > 40) {
            npcAnimationCount = 0;
            npcCharacter.nextPose();
        }

    }

    // TODO:  Ways to automate this.   JSON file?
    private void initNpcDialog() {
        npcCharacter.setAllowTalk(true);
        DialogSheet2 ds1 = new DialogSheet2(npcCharacter.getDialogPane());
        DialogSheet2 ds2 = new DialogSheet2(npcCharacter.getDialogPane());
        DialogSheet2 ds3 = new DialogSheet2(npcCharacter.getDialogPane());
        DialogSheet2 ds4 = new DialogSheet2(npcCharacter.getDialogPane());
        DialogSheet2 ds5 = new DialogSheet2(npcCharacter.getDialogPane());
        DialogSheet2 ds6 = new DialogSheet2(npcCharacter.getDialogPane());

        DialogResponseAction npcNoTalkAction = () -> {
            npcCharacter.setAllowTalk(false);
        };

        DialogResponseAction takeFeeAction = () -> {
            // Take fee from player
            
            
            ds4.doResponseAction();
        };

        DialogResponseAction lawbotAction = () -> {
            // Show Lawbot and initate talking to lawbot.
        };

        ds6.setDialogText(bundle.getString("dialog.ds6.npc"));
        ds6.addResponse(new DialogResponse2(bundle.getString("dialog.ds6.p.1"), lawbotAction)); // Exit action

        ds5.setDialogText(bundle.getString("dialog.ds5.npc"));
        ds5.addResponse(new DialogResponse2(bundle.getString("dialog.ds5.p.1"), ds6)); // Exit action

        ds4.setDialogText(bundle.getString("dialog.ds4.npc"));
        ds4.addResponse(new DialogResponse2(bundle.getString("dialog.ds4.p.1"), ds5)); // Exit action

        ds3.setDialogText(bundle.getString("dialog.ds3.npc"));
        ds3.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), takeFeeAction));

        ds2.setDialogText(bundle.getString("dialog.ds2.npc"));
        ds2.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.2"), npcNoTalkAction));

        ds1.setDialogText(bundle.getString("dialog.ds1.npc"));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.1"), lawbotAction));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.2"), ds2));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), ds3));

        npcCharacter.getDialogPane().addDialogSheet(ds1);
        npcCharacter.getDialogPane().addDialogSheet(ds2);
        npcCharacter.getDialogPane().addDialogSheet(ds3);
        npcCharacter.getDialogPane().addDialogSheet(ds4);
    }

//    @Override
//    public String getPropName() {
//        return getClass().getSimpleName();
//    }
//
    @Override
    public Properties saveProperties() {
        Properties p = new Properties();
//        p.setProperty(PROPERTY_CONDITION, condition.toString());
        
        return p;
    }

    @Override
    public void loadProperties(Properties p) {
        // Empty for now
        //setCondition(Integer.valueOf(p.getProperty(PROPERTY_CONDITION, String.valueOf(CONDITION_DEFAULT))));
    }

    @Override
    public Point2D getDefaultPlayerPosition() {
        return PLAYER_START;
    }
}
