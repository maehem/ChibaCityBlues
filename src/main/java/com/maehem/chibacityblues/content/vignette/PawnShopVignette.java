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
import com.maehem.chibacityblues.content.things.deck.KomodoDeckThing;
import com.maehem.abyss.engine.GameState;
import com.maehem.abyss.engine.VignetteTrigger;
import com.maehem.abyss.engine.babble.DialogResponse2;
import com.maehem.abyss.engine.babble.DialogSheet2;
import java.util.Properties;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mark J Koch [@maehem on GitHub]
 */
public class PawnShopVignette extends Vignette {

    //private static final String PROP_NAME = "pawnshop";
    private static final String CONTENT_BASE = "/content/vignette/pawn-shop/";
    public static final Point2D PLAYER_START = new Point2D(0.20, 0.77);
    private static final String COUNTERS_IMAGE_FILENAME   = CONTENT_BASE + "counters.png";
    //private static final String DOOR_PATCH_IMAGE_FILENAME = CONTENT_BASE + "patch-left.png";
    private static final String BROKER_POSE_SHEET_FILENAME = CONTENT_BASE + "pawn-broker-pose-sheet.png";
    private static final double[] WALK_BOUNDARY = {
            0.11, 0.73,   0.98, 0.73,
            0.98, 0.99,   0.04, 0.99,
            0.02, 0.78,   0.02, 0.72,
            0.02, 0.66
    };
    private static final VignetteTrigger leftDoor = new VignetteTrigger(
            0.02, 0.70,  // port XY location
            0.04, 0.06,  // port size
            0.72, 0.75,  // place player at this XY when they leave the pawn shop.        
            PoseSheet.Direction.LEFT, // Face this direction at destination
            "StreetPawnShopVignette"  // Class name of destination vignette
    );
    
//    private static final Patch leftDoorPatch = new Patch(
//            0, 0, 425, 
//            PawnShopVignette.class.getResourceAsStream(DOOR_PATCH_IMAGE_FILENAME)
//    );

    private Character shopOwnerCharacter;
    private int shopOwnerAnimationCount = 0;
    
    /**
     * 
     * @param gs
     * @param prevPort where the player came from
     * @param player the @Player
     */
    public PawnShopVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(gs, CONTENT_BASE, prevPort, player, WALK_BOUNDARY);
        // Don't put things here.  Override @init() which is called during creation.
    }

    @Override
    protected void init() {
        setHorizon(0.27);

        initShopOwner();        
        initBackground();
                
        addPort(leftDoor);
        
        //addPatch(leftDoorPatch);
        
        // example Depth of field
        //fgGroup.setEffect(new BoxBlur(10, 10, 3));
    }

    private void initShopOwner() {
        shopOwnerCharacter = new Character(bundle.getString("character.npc.name"));
        shopOwnerCharacter.setScale(1.4);
        shopOwnerCharacter.setLayoutX(580);
        shopOwnerCharacter.setLayoutY(450);

        // TODO:   Check that file exists.  The current exception message is cryptic.
        shopOwnerCharacter.setSkin(PawnShopVignette.class.getResourceAsStream(BROKER_POSE_SHEET_FILENAME), 1, 4);
        LOGGER.config("Add skin for pawn shop owner. " + BROKER_POSE_SHEET_FILENAME);

        initShopOwnerDialog();

        getCharacterList().add(shopOwnerCharacter);
        getBgGroup().getChildren().add(
                shopOwnerCharacter
        );
    }

    private void initBackground() {

        // Display Cases (in front of shop owner )
        final ImageView counterView = new ImageView();
        counterView.setImage(new Image(PawnShopVignette.class.getResourceAsStream(COUNTERS_IMAGE_FILENAME)));
        counterView.setLayoutX(0);
        counterView.setLayoutY(0);
        //counterView.setBlendMode(BlendMode.MULTIPLY);

        // Add these in visual order.  Back to front.
        getBgGroup().getChildren().add( counterView );
    }

    @Override
    public void loop() {
        // animate shop owner.
        shopOwnerAnimationCount++;
        if (shopOwnerAnimationCount > 40) {
            shopOwnerAnimationCount = 0;
            shopOwnerCharacter.nextPose();
        }

    }

    // TODO:  Ways to automate this.   JSON file?
    private void initShopOwnerDialog() {
        shopOwnerCharacter.setAllowTalk(true);
        // Shin kicks the player out of the shop but gives him his item.
        DialogResponseAction exitAction = () -> {
            shopOwnerCharacter.getDialogPane().setExit(leftDoor);
            shopOwnerCharacter.getDialogPane().setActionDone(true);
            
            // Add cyberspace deck to inventory.
            shopOwnerCharacter.give(new KomodoDeckThing(), getPlayer());
            
            // TODO:
            // GameState set StreetVignette PawnShop door locked.
            getGameState().setProperty(getClass().getSimpleName(), Vignette.RoomState.LOCKED.name() );
        };

        DialogSheet2 ds4 = new DialogSheet2(shopOwnerCharacter.getDialogPane());
        ds4.setDialogText(bundle.getString("dialog.ds4.npc"));
        ds4.addResponse(new DialogResponse2(bundle.getString("dialog.ds4.p.1"), exitAction)); // Exit action

        DialogSheet2 ds3 = new DialogSheet2(shopOwnerCharacter.getDialogPane());
        ds3.setDialogText(bundle.getString("dialog.ds3.npc"));
        ds3.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), ds4));

        DialogSheet2 ds2 = new DialogSheet2(shopOwnerCharacter.getDialogPane());
        ds2.setDialogText(bundle.getString("dialog.ds2.npc"));
        ds2.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.2"), ds3));
        ds2.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), ds4));

        DialogSheet2 ds1 = new DialogSheet2(shopOwnerCharacter.getDialogPane());
        ds1.setDialogText(bundle.getString("dialog.ds1.npc"));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.1"), ds2));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.2"), ds3));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), ds4));

        shopOwnerCharacter.getDialogPane().addDialogSheet(ds1);
        shopOwnerCharacter.getDialogPane().addDialogSheet(ds2);
        shopOwnerCharacter.getDialogPane().addDialogSheet(ds3);
        shopOwnerCharacter.getDialogPane().addDialogSheet(ds4);

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
