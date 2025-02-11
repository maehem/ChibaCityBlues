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
import com.maehem.abyss.engine.BodyPart;
import com.maehem.abyss.engine.BodyPartThing;
import com.maehem.abyss.engine.GameState;
import com.maehem.abyss.engine.Player;
import com.maehem.abyss.engine.PoseSheet;
import com.maehem.abyss.engine.Vignette;
import com.maehem.abyss.engine.VignetteTrigger;
import com.maehem.abyss.engine.VignetteTrigger.Location;
import com.maehem.abyss.engine.babble.BabbleNode;
import com.maehem.abyss.engine.babble.DialogBabbleNode;
import static com.maehem.abyss.engine.babble.DialogCommand.EXIT_B;
import static com.maehem.abyss.engine.babble.DialogCommand.ITEM_BUY;
import com.maehem.abyss.engine.babble.DialogPane;
import com.maehem.abyss.engine.babble.NarrationBabbleNode;
import com.maehem.abyss.engine.babble.OptionBabbleNode;
import com.maehem.abyss.engine.babble.VendWidget;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;

/**
 *
 * @author Mark J Koch [@maehem on GitHub]
 */
public class BodyShopVignette extends Vignette {

    public static final int ROOM_NUMBER = 4;
    private static final String CONTENT_BASE = "/content/vignette/body-shop/";
    private static final String NPC_POSE_SHEET_FILENAME = CONTENT_BASE + "npc-pose-sheet.png";
    private static final String NPC_CAMEO_FILENAME = CONTENT_BASE + "npc-cameo.png";

    public static final Point2D PLAYER_START = new Point2D(0.5, 0.86);
    private static final double[] WALK_BOUNDARY = new double[]{
        0.08, 0.75, 0.92, 0.75,
        0.92, 0.95, 0.89, 0.95,
        0.89, 1.0, 0.68, 1.0,
        0.68, 0.95, 0.08, 0.95
    };

    private static final VignetteTrigger exitPort = new VignetteTrigger(
            0.39, 0.97, // exit location
            0.22, 0.03, // exit size
            VignetteTrigger.SHOW_TRIGGER,
            0.4, 0.80, // player position at destination
            Location.BOTTOM,
            PoseSheet.Direction.TOWARD, StreetBodyShopVignette.class); // Exit to here

    private static final ArrayList<BabbleNode> DIALOG_CHAIN = new ArrayList<>() {
        {
            // TODO: Figure out when to use #12.
            add(new NarrationBabbleNode());                         // 0: Long Room Description
            add(new NarrationBabbleNode());                         // 1: Short Room Description
            add(new DialogBabbleNode(4, 5, 6, 7));  // 2 "Can I be of service?"
            add(new DialogBabbleNode(4, 5, 6, 7));  // 3  "Hello again..."
            add(new OptionBabbleNode(8)); // 4  "Yes..."
            add(new OptionBabbleNode(9)); // 5  "Just browsing..."
            add(new OptionBabbleNode(10)); // 6  "I feel a certain..."
            add(new OptionBabbleNode(11)); // 7  "Oh, look at the time..."
            add(new DialogBabbleNode(ITEM_BUY.num)); // 8    "Wonderful..."
            add(new DialogBabbleNode(11)); // 9    "Let me know..."
            add(new DialogBabbleNode(ITEM_BUY.num)); // 10    "Let's see..."
            add(new DialogBabbleNode(EXIT_B.num)); // 11    "Thanks for stopping by.."
            add(new DialogBabbleNode(EXIT_B.num)); // 12    "Come back when.."
            add(new DialogBabbleNode(14)); // 13    "I kickstarted your.."
            add(new DialogBabbleNode(4, 6, 7)); // 14    "I'd be happy to..."
            add(new DialogBabbleNode(EXIT_B.num)); // 15    "Enjoy your cheap..."
            add(new DialogBabbleNode(EXIT_B.num)); // 16    "I'll sell you your..."
        }
    };

    private com.maehem.abyss.engine.Character npcCharacter;
    private int npcAnimationCount = 0;

    public BodyShopVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(ROOM_NUMBER, gs, CONTENT_BASE, prevPort, player, WALK_BOUNDARY);
    }

    @Override
    protected void init() {
        setHorizon(0.3);
        addPort(exitPort);

        initDialogText(DIALOG_CHAIN);
        initNpc();

        setDialogPane(new DialogPane(this, npcCharacter));
        initNpcDialog();

        initBackground();

        getDialogPane().setCurrentDialog(dialogWarmUp());
        getDialogPane().setVisible(false);

        if (getPlayer().getConstitution() <= 0) {
            getPlayer().setConstitution(10);
            // Open the dialog after a delay.
            final Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(0.5),
                    e -> getDialogPane().setVisible(true)
            ));
            timeline.play();
        }
    }

    private void initNpc() {
        npcCharacter = new com.maehem.abyss.engine.Character(bundle.getString("character.npc.name"));
        npcCharacter.setScale(2.0);
        npcCharacter.setLayoutX(900);
        npcCharacter.setLayoutY(580);

        LOGGER.config("Apply Cameo for NPC. " + NPC_CAMEO_FILENAME);
        npcCharacter.setCameo(getClass().getResourceAsStream(NPC_CAMEO_FILENAME));

        // TODO:   Check that file exists.  The current exception message is cryptic.
        LOGGER.config("Add skin for npc. " + NPC_POSE_SHEET_FILENAME);
        npcCharacter.setSkin(getClass().getResourceAsStream(NPC_POSE_SHEET_FILENAME), 1, 4);

        getCharacterList().add(npcCharacter);
        getBgGroup().getChildren().add(npcCharacter);
    }

    private void initBackground() {
        // Nothing to do here.
    }

    private void initNpcDialog() {
        npcCharacter.setAllowTalk(true);
        getDialogPane().setDialogChain(DIALOG_CHAIN);
    }

//    @Override
//    public ArrayList<Thing> getVendItems() {
//        ArrayList<Thing> list = new ArrayList<>();
//
//        for (BodyPart bp : BodyPart.values()) {
//            BodyPartThing thing = new BodyPartThing(bp);
//            thing.setValue(bp.sellPrice);
    ////            if ( getPlayer().soldOrgan(bp) ) {
////                bp.setBuyable(true);
////                // TODO: Factor discount when Bargaining chip installed.
////                thing.setValue(bp.buyPrice);
////            }
//            list.add(thing);
//        }
//
//        return list;
//    }

    /**
     * Configure the Vending Widget. Add body parts here.
     *
     * @param widget
     * @return -1 for normal vending.
     */
    @Override
    public int onVendItemsStart(VendWidget widget) {
        widget.setMode(VendWidget.VendMode.ORGANS);

        for (BodyPart bp : BodyPart.values()) {
            BodyPartThing thing = new BodyPartThing(bp);
            thing.setValue(bp.sellPrice);
            thing.setVendQuantity(-1); // Negative means we have it to sell.
//            if ( getPlayer().soldOrgan(bp) ) {
//                bp.setBuyable(true);
//                // TODO: Factor discount when Bargaining chip installed.
//                thing.setValue(bp.buyPrice);
//                thing.setVendQuantity(1); // We have previously sold it can can buy it.
//            }
            widget.getItems().add(thing);
        }
        return -1;
    }

    @Override
    public boolean onVendItemsFinished() {
        LOGGER.log(Level.CONFIG, "Body Shop: onVendItemsFinished() called.");

        return true;
    }

    @Override
    public void loop() {
        npcAnimationCount++;
        if (npcAnimationCount > 40) {
            npcAnimationCount = 0;
            npcCharacter.nextPose();
        }
    }

    @Override
    public int dialogWarmUp() {
        // TODO:  Zero constitution == 12
        // else: 2
        if (getPlayer().getConstitution() <= 0) { // Dead
            return 13;
        }
        return 2;
    }

    @Override
    public Properties saveProperties() {
        Properties p = new Properties();

        // example
        // p.setProperty(PROPERTY_FOO, condition.toString());
        return p;
    }

    @Override
    public void loadProperties(Properties p) {
        // example
        //setCondition(Integer.valueOf(p.getProperty(PROPERTY_FOO, String.valueOf(FOO_DEFAULT))));
    }

    @Override
    public Point2D getDefaultPlayerPosition() {
        return PLAYER_START;
    }

}
