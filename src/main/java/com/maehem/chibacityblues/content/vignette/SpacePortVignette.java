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
import com.maehem.abyss.engine.babble.DialogResponse2;
import com.maehem.abyss.engine.babble.DialogResponseAction;
import com.maehem.abyss.engine.babble.DialogSheet2;
import java.util.Properties;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mark J Koch [@maehem on GitHub]
 */
public class SpacePortVignette extends Vignette {

    private static final int ROOM_NUMBER = 19;
    //public  static final String PROP_NAME = "body-shop";
    private static final String CONTENT_BASE = "/content/vignette/space-port/";
    //private static final String SKYLINE_IMAGE_FILENAME   = CONTENT_BASE + "cyberpunk-cityscape.png";
    //private static final String BAR_BACKGROUND_IMAGE_FILENAME   = CONTENT_BASE + "bar-background.png";
    //private static final String LOGO_IMAGE_FILENAME   = CONTENT_BASE + "chatsubo-logo.png";
    private static final String COUNTERS_IMAGE_FILENAME = CONTENT_BASE + "ticket-window-mask.png";
    private static final String NPC_POSE_SHEET_FILENAME = CONTENT_BASE + "npc-pose-sheet.png";
    private static final String NPC_CAMEO_FILENAME = CONTENT_BASE + "npc-cameo.png";

    public static final Point2D PLAYER_START = new Point2D(0.5, 0.86);
    private static final double[] WALK_BOUNDARY = new double[] {
                0.08, 0.75,   0.99, 0.75,
                0.99, 0.95,   0.08, 0.95,
                0.08, 0.75
    };

    private com.maehem.abyss.engine.Character npcCharacter;

    private static final VignetteTrigger exitPort = new VignetteTrigger(
            0.97, 0.75,   // exit location
        0.03, 0.25,   // exit size
        VignetteTrigger.SHOW_TRIGGER,
        0.2, 0.80,   // player position at destination
        PoseSheet.Direction.RIGHT, StreetSpacePortVignette.class); // Exit to here

    public SpacePortVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(ROOM_NUMBER, gs, CONTENT_BASE,prevPort, player,WALK_BOUNDARY);
    }

    @Override
    protected void init() {
        setHorizon(0.23);

        // Do in this order.  TODO: Leverage Z-order of JavaFX?
        initNPC();
        // Background is autoloaded by superclass.
        initBackground(); // then layer in any fixtures on top of them

        //getBgGroup().setOpacity(0.7);

        addPort(exitPort);
    }

    private void initBackground() {
        // Display Window Frame Mask (in front of ticket NPC )
        final ImageView windowMask = new ImageView();
        windowMask.setImage(new Image(getClass().getResourceAsStream(COUNTERS_IMAGE_FILENAME)));
        windowMask.setLayoutX(956);
        windowMask.setLayoutY(221);
        // Add these in visual order.  Back to front.
        getBgGroup().getChildren().add(windowMask);
    }

    private void initNPC() {
        npcCharacter = new com.maehem.abyss.engine.Character(bundle.getString("character.npc.name"));
        npcCharacter.setScale(0.7);
        npcCharacter.setLayoutX(1040);
        npcCharacter.setLayoutY(250);

        // TODO:   Check that file exists.  The current exception message is cryptic.
        LOGGER.config("Add skin for npc. " + NPC_POSE_SHEET_FILENAME);
        npcCharacter.setSkin(getClass().getResourceAsStream(NPC_POSE_SHEET_FILENAME), 1, 4);
        // Dialog
        // Load dialog tree from file.
//        barOwnerCharacter.getDialog().init(getWidth(), getHeight());

        initNpcDialog();

        getCharacterList().add(npcCharacter);
        getBgGroup().getChildren().add(npcCharacter);
    }

    private void initNpcDialog() {
        npcCharacter.setAllowTalk(true);
        npcCharacter.adjustHearingBoundary(0, 300);
        LOGGER.config("Apply Cameo for NPC. " + NPC_CAMEO_FILENAME);
        npcCharacter.setCameo(getClass().getResourceAsStream(NPC_CAMEO_FILENAME));
        DialogSheet2 ds1 = new DialogSheet2(getDialogPane());

        // Ratz has nothing more to say.
        DialogResponseAction endDialog = () -> {
            npcCharacter.setAllowTalk(false);
            getDialogPane().setActionDone(true);
            npcCharacter.setTalking(false);
        };

        // Ratz asks to get paid and player replies with snarky comeback.
        ds1.setDialogText(bundle.getString("dialog.ds1.npc"));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.1"), endDialog));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.2"), endDialog));

        getDialogPane().addDialogSheet(ds1);
    }

    @Override
    public void loop() {
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

//    @Override
//    public String getPropName() {
//        return PROP_NAME;
//    }

    @Override
    public Point2D getDefaultPlayerPosition() {
        return PLAYER_START;
    }

}
