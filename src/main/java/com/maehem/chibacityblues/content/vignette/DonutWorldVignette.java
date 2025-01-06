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
import com.maehem.abyss.engine.babble.BabbleNode;
import com.maehem.abyss.engine.babble.DialogBabbleNode;
import static com.maehem.abyss.engine.babble.DialogCommand.DIALOG_CLOSE;
import static com.maehem.abyss.engine.babble.DialogCommand.DIALOG_END;
import static com.maehem.abyss.engine.babble.DialogCommand.EXIT_T;
import static com.maehem.abyss.engine.babble.DialogCommand.TO_JAIL;
import com.maehem.abyss.engine.babble.DialogPane;
import com.maehem.abyss.engine.babble.NarrationBabbleNode;
import com.maehem.abyss.engine.babble.OptionBabbleNode;
import java.util.ArrayList;
import java.util.Properties;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mark J Koch [@maehem on GitHub]
 */
public class DonutWorldVignette extends Vignette {

    private static final int ROOM_NUMBER = 6;
    //public  static final String PROP_NAME = "donut-world";
    private static final String CONTENT_BASE = "/content/vignette/donut-world/";
    private static final String NPC_POSE_SHEET_FILENAME = CONTENT_BASE + "npc-donut-cop.png";
    private static final String NPC_CAMEO_FILENAME = CONTENT_BASE + "npc-cameo.png";
    private static final String TABLE_PATCH_IMAGE_FILENAME = CONTENT_BASE + "patch-cop-table.png";
    public static final Point2D PLAYER_START = new Point2D(0.5, 0.86);
    private static final double[] WALK_BOUNDARY = new double[]{
        0.05, 0.70, 0.56, 0.70,
        0.67, 0.98, 0.05, 0.98,
        0.05, 0.70
    };

    private static final VignetteTrigger exitPort = new VignetteTrigger(
            0.38, 0.68, // exit location
            0.21, 0.03, // exit size
            0.50, 0.90, // player position at destination
            VignetteTrigger.Location.TOP,
            PoseSheet.Direction.AWAY, StreetBodyShopVignette.class);
    private static final VignetteTrigger jailPort = new VignetteTrigger(
            -1000, -1000, // exit location
            0.1, 0.1, // exit size
            0.50, 0.90, // player position at destination
            VignetteTrigger.Location.JAIL,
            PoseSheet.Direction.TOWARD, JusticeBoothVignette.class);

    private com.maehem.abyss.engine.Character npcCharacter;

    private static final ArrayList<BabbleNode> DIALOG_CHAIN = new ArrayList<>() {
        {
            // TODO:  These need work further down.
            add(new NarrationBabbleNode());                         // 0: Long Room Description
            add(new NarrationBabbleNode());                         // 1: Short Room Description
            add(new DialogBabbleNode(5, 6, 7, 8, 9)); // 2   "Hey we don't allow...." CopTalk add, 3,4
            add(new OptionBabbleNode(15));                          // 3    "St. Patty's Day...." CopTalk 2.0
            add(new OptionBabbleNode(14)); // 4  " Sure and beorrah ..." CopTalk 1.
            add(new OptionBabbleNode(11)); // 5  "I came in for a donut.."
            add(new OptionBabbleNode(12)); // 6 "I'm looking for pirated..."
            add(new OptionBabbleNode(12)); // 7 "Drop dead flatfoot."
            add(new OptionBabbleNode(12)); // 8 "You'll never take me alive."
            add(new OptionBabbleNode(13)); // 9 "Forgive me sir...."
            add(new OptionBabbleNode(12)); // 10 "Am I ever going to get a break...."
            add(new DialogBabbleNode(7, 8, 9, 10)); // 11 "This is a donut shop..."
            add(new DialogBabbleNode(TO_JAIL.num)); // 12 "That's it you're under arrest...."
            add(new DialogBabbleNode(EXIT_T.num)); // 13 "Just don't let me catch...."
            add(new DialogBabbleNode(24, 25, 26)); // 14 "Finnegan old pal...."
            add(new DialogBabbleNode(18, 19, 20, 21, 22)); // 15 "Mulligan! I can barely...."
            add(new DialogBabbleNode(17, 18, 19, 20, 21, 22)); // 16 "Back again?...."
            add(new OptionBabbleNode(23)); // 17 "St. Patty's Day. I'm looking...."
            add(new OptionBabbleNode(23)); // 18 "Sure and begorrah...."
            add(new OptionBabbleNode(30)); // 19 "Well now....."
            add(new OptionBabbleNode(23)); // 20 "Can't a man get a donut...."
            add(new OptionBabbleNode(23)); // 21 "Yeah I'm looking for trouble...."
            add(new OptionBabbleNode(EXIT_T.num)); // 22 "Sorry my mistake...."
            add(new DialogBabbleNode(TO_JAIL.num)); // 23 "I've warned you about this before...."
            add(new OptionBabbleNode(29)); // 24 "Begorrah! I forgot the...."
            add(new OptionBabbleNode(30)); // 25 "What's the password for...."
            add(new OptionBabbleNode(31)); // 26 "O'Riley, I heard they changed...."
            add(new OptionBabbleNode(32)); // 27 "Have you heard any news...."
            add(new OptionBabbleNode(33)); // 28 "Fergus gave me the second level...."
            add(new DialogBabbleNode(DIALOG_CLOSE.num)); // 29 "Don't worry. It's KEISATSU...."
            add(new DialogBabbleNode(DIALOG_CLOSE.num)); // 30 "Wild Irish Rose...."
            add(new DialogBabbleNode(DIALOG_CLOSE.num)); // 31 "The coded Fuji password is...."
            add(new DialogBabbleNode(DIALOG_CLOSE.num)); // 32 "Just got through questioning...."
            add(new DialogBabbleNode(DIALOG_CLOSE.num)); // 33 "You seem to be forgetting alot...."
            add(new OptionBabbleNode(DIALOG_CLOSE.num)); // 34 "Mulligan! Did you hear about...."// What links to this? TODO: Test
            //add(new DialogBabbleNode(4, 5, 6, 8, 9)); // 35 -- CopTalk Skill 1 active
            //add(new DialogBabbleNode(3, 5, 6, 8, 9)); // 35 -- CopTalk Skill 2 active
        }
    };

    public DonutWorldVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(ROOM_NUMBER, gs, CONTENT_BASE, prevPort, player, WALK_BOUNDARY);
    }

    @Override
    protected void init() {
        setHorizon(0.2);
        addPort(exitPort);
        addPort(jailPort);


        initDialogText(DIALOG_CHAIN);
        initNpc();

        setDialogPane(new DialogPane(this, npcCharacter));
        initNpcDialog();

        initBackground();

        getDialogPane().setCurrentDialog(dialogWarmUp());
        getDialogPane().setVisible(false);
    }

    private void initNpc() {
        npcCharacter = new com.maehem.abyss.engine.Character(bundle.getString("character.npc.name"));
        npcCharacter.setScale(3.0);
        npcCharacter.setLayoutX(1100);
        npcCharacter.setLayoutY(768);

        LOGGER.config("Apply Cameo for NPC. " + NPC_CAMEO_FILENAME);
        npcCharacter.setCameo(getClass().getResourceAsStream(NPC_CAMEO_FILENAME));

        // TODO:   Check that file exists.  The current exception message is cryptic.
        LOGGER.config("Add skin for pawn shop owner. " + NPC_POSE_SHEET_FILENAME);
        npcCharacter.setSkin(getClass().getResourceAsStream(NPC_POSE_SHEET_FILENAME), 1, 4);

        getCharacterList().add(npcCharacter);
        getBgGroup().getChildren().add(
                npcCharacter
        );
    }

    private void initBackground() {

        // Display Cases (in front of shop owner )
        final ImageView tableView = new ImageView();
        tableView.setImage(new Image(PawnShopVignette.class.getResourceAsStream(TABLE_PATCH_IMAGE_FILENAME)));
        tableView.setLayoutX(912);
        tableView.setLayoutY(457);

        // Add these in visual order.  Back to front.
        getBgGroup().getChildren().add(tableView);
    }

    @Override
    public int dialogWarmUp() {
        if (npcCharacter.canTalk()) {
            return 2;
        } else {
            return DIALOG_END.num;
        }
    }

    private void initNpcDialog() {
        npcCharacter.setAllowTalk(true);
        getDialogPane().setDialogChain(DIALOG_CHAIN);

//        DialogSheet2 ds1 = new DialogSheet2(getDialogPane());
//        DialogSheet2 ds2 = new DialogSheet2(getDialogPane());
//
//        // Ratz has nothing more to say.
//        DialogResponseAction endDialog = () -> {
//            npcCharacter.setAllowTalk(false);
//            getDialogPane().setActionDone(true);
//            npcCharacter.setTalking(false);
//        };
//
//        // Now just pay up. Nothing else to talk about.
//        ds2.setDialogText(bundle.getString("dialog.ds2.npc"));
//        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds2.p.1"), endDialog));
//
//        // Ratz asks to get paid and player replies with snarky comeback.
//        ds1.setDialogText(bundle.getString("dialog.ds1.npc"));
//        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.1"), ds2));
//        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.2"), ds2));
//        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), ds2));
//        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.4"), ds2));
//
//        getDialogPane().addDialogSheet(ds1);
//        getDialogPane().addDialogSheet(ds2);
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
