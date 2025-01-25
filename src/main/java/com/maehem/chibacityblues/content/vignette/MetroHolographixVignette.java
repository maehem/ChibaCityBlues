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
import static com.maehem.abyss.engine.babble.DialogCommand.ITEM_BUY;
import static com.maehem.abyss.engine.babble.DialogCommand.SKILL_BUY;
import static com.maehem.abyss.engine.babble.DialogCommand.SOFTWARE_BUY;
import static com.maehem.abyss.engine.babble.DialogCommand.WORD1;
import com.maehem.abyss.engine.babble.DialogPane;
import com.maehem.abyss.engine.babble.EmptyBabbleNode;
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
public class MetroHolographixVignette extends Vignette {

    private static final int ROOM_NUMBER = 32;
    //public  static final String PROP_NAME = "body-shop";
    private static final String CONTENT_BASE = "/content/vignette/metro-holographix/";
    //private static final String SKYLINE_IMAGE_FILENAME   = CONTENT_BASE + "cyberpunk-cityscape.png";
    //private static final String BAR_BACKGROUND_IMAGE_FILENAME   = CONTENT_BASE + "bar-background.png";
    //private static final String LOGO_IMAGE_FILENAME   = CONTENT_BASE + "chatsubo-logo.png";
    //private static final String COUNTERS_IMAGE_FILENAME   = CONTENT_BASE + "counter-overlay.png";
    private static final String NPC_POSE_SHEET_FILENAME = CONTENT_BASE + "npc-pose-sheet.png";
    private static final String NPC_CAMEO_FILENAME = CONTENT_BASE + "npc-cameo.png";
    private static final String FOREGROUND_FILENAME = CONTENT_BASE + "foreground.png";

    private static final double PLAYER_SCALE = DEFAULT_SCALE * 1.7;
    public  static final Point2D PLAYER_START = new Point2D(0.5, 0.86);
    private static final double[] WALK_BOUNDARY = new double[] {
        0.33, 0.88, 0.50, 0.86, 0.90, 0.93,
        0.90, 0.99, 0.04, 0.99, 0.04, 0.92
    };

    private static final VignetteTrigger exitPort = new VignetteTrigger(
            0.50, 0.97, // exit location
            0.33, 0.03, // exit size
        VignetteTrigger.SHOW_TRIGGER,
        0.4, 0.80,   // player position at destination
        PoseSheet.Direction.TOWARD, StreetMetroHoloVignette.class); // Exit to here

    protected static final ArrayList<BabbleNode> DIALOG_CHAIN = new ArrayList<>() {
        {
            add(new NarrationBabbleNode());                         // 0: Long Room Description
            add(new NarrationBabbleNode());                         // 1: Short Room Description
            add(new DialogBabbleNode(3, 4, 5, 6)); // [2] :: Hey, kid!  You need chips or software?  I just got some new stuff from those bridge-and-tunnel kids in Jersey.
            add(new OptionBabbleNode(10)); //[3] :: Sure and begora...
            add(new OptionBabbleNode(20)); //[4] :: Yeah, Finn, Im looking for some hot softwarez.
            add(new OptionBabbleNode(8)); //[5] :: Im just browsing right now.
            add(new OptionBabbleNode(9)); //[6] :: I need a scan, Finn. Then, maybe Ill buy something.
            add(new EmptyBabbleNode()); // [7] <empty>
            add(new DialogBabbleNode(11, 12)); // [8] :: Hope youre not allergic to dust. Can I answer any questions?
            add(new DialogBabbleNode(11, 12)); // [9] :: Scanned when you came in. No implants,no biologicals. Youre clean.
            add(new OptionBabbleNode(DIALOG_CLOSE.num)); // [10] :: Geez, my mistake, Im fresh out of inventory today, officer!  Sorry.
            add(new OptionBabbleNode(WORD1.num, 15)); // [11] :: Tell me about @---------------
            add(new OptionBabbleNode(13)); // [12] :: Leave me alone!  I said Im just browsing!
            add(new DialogBabbleNode(14, 15)); // [13] :: Well, excuse me! Sounds like someones been having a rough day!
            add(new OptionBabbleNode(16)); // [14] :: Hey, Finn, did anyone ever tell you your head looks like it was designed in a wind tunnel?
            add(new OptionBabbleNode(WORD1.num)); // [15] :: Okay, what do you know about @---------------
            add(new DialogBabbleNode(DIALOG_CLOSE.num)); // [16] :: You got about as much class as those yahoos from Jersey. Get out of here!
            add(new DialogBabbleNode(SKILL_BUY.num)); // [17] :: Ive got Icebreaking and Debug skill chips for $1,000 each.
            add(new DialogBabbleNode(ITEM_BUY.num)); // [18] :: So, youre on a holy mission, eh? I got what you need.
            add(new DialogBabbleNode(15)); // [19] :: Youll have to hit Sense/Net for one of those.
            add(new DialogBabbleNode(SOFTWARE_BUY.num)); // [20] :: You want software, you got software.
            add(new DialogBabbleNode(15)); // [21] :: Its an AI with Swiss citizenship. Built for Tessier-Ashpool.
            add(new DialogBabbleNode(15)); // [22] :: Its an old novel by William Gibson.
            add(new DialogBabbleNode(15)); // [23] :: Its a first generation, high-orbit family on Freeside, run like a corporation.  Rich and powerful.
            add(new DialogBabbleNode(15)); // [24] :: Artificial Intelligence.  A smart computer.  They design all the ice.
            add(new DialogBabbleNode(15)); // [25] :: Military. Not too successful. Didnt they teach you history in school?
            add(new DialogBabbleNode(15)); // [26] :: Dont know about that.
        }
    };

    private com.maehem.abyss.engine.Character npcCharacter;
    private int npcAnimationCount = 0;

    public MetroHolographixVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(ROOM_NUMBER, gs, CONTENT_BASE,prevPort, player,WALK_BOUNDARY);
    }

    @Override
    protected void init() {
        setHorizon(0.50);
        setPlayerScale(PLAYER_SCALE);
        addPort(exitPort);

        initDialogText(DIALOG_CHAIN);
        initNPC();   // then layer in shop owner

        setDialogPane(new DialogPane(this, npcCharacter));
        initNpcDialog();

        initBackground(); // then layer in any fixtures on top of them

        getDialogPane().setCurrentDialog(dialogWarmUp());
        getDialogPane().setVisible(false);

    }

    private void initNPC() {
        npcCharacter = new com.maehem.abyss.engine.Character(bundle.getString("character.npc.name"));
        npcCharacter.setScale(1.5);
        npcCharacter.setLayoutX(1000);
        npcCharacter.setLayoutY(450);

        // TODO:   Check that file exists.  The current exception message is cryptic.
        npcCharacter.setSkin(getClass().getResourceAsStream(NPC_POSE_SHEET_FILENAME), 1, 1);
        LOGGER.config("Add skin for npc. " + NPC_POSE_SHEET_FILENAME);
        npcCharacter.setCameo(getClass().getResourceAsStream(NPC_CAMEO_FILENAME));

        getCharacterList().add(npcCharacter);
        getBgGroup().getChildren().add(npcCharacter);

        npcCharacter.getHearingBoundary().setTranslateY(130);
    }

    private void initBackground() {
        Image im = new Image(getClass().getResourceAsStream(FOREGROUND_FILENAME));
        ImageView foregroundElement = new ImageView(im);
        foregroundElement.setPreserveRatio(true);
        foregroundElement.setLayoutY(442);
        getFgGroup().getChildren().add(foregroundElement);
    }

    // TODO:  Ways to automate this.   JSON file?
    private void initNpcDialog() {
        npcCharacter.setAllowTalk(true);
        getDialogPane().setDialogChain(DIALOG_CHAIN);
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
        return 2;
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
