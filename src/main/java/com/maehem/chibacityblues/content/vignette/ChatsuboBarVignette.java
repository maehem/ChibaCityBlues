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
import com.maehem.abyss.engine.babble.BabbleNode;
import com.maehem.abyss.engine.babble.DialogBabbleNode;
import static com.maehem.abyss.engine.babble.DialogCommand.EXIT_B;
import static com.maehem.abyss.engine.babble.DialogCommand.ITEM_BUY;
import com.maehem.abyss.engine.babble.DialogPane;
import com.maehem.abyss.engine.babble.NarrationBabbleNode;
import com.maehem.abyss.engine.babble.OptionBabbleNode;
import java.util.ArrayList;
import java.util.Properties;
import javafx.geometry.Point2D;
import javafx.scene.media.Media;

/**
 *
 * @author Mark J Koch [@maehem on GitHub]
 */
public class ChatsuboBarVignette extends Vignette {

    private static final int ROOM_NUMBER = 1;
    private static final String MUSIC = "/content/audio/music/Uncan - The Cold.mp3";

    private static final double PLAYER_SCALE = DEFAULT_SCALE * 1.4;

    private static final String CONTENT_BASE = "/content/vignette/chatsubo-bar/";
    //private static final String SKYLINE_IMAGE_FILENAME   = CONTENT_BASE + "cyberpunk-cityscape.png";
    //private static final String BAR_BACKGROUND_IMAGE_FILENAME   = CONTENT_BASE + "bar-background.png";
    //private static final String LOGO_IMAGE_FILENAME   = CONTENT_BASE + "chatsubo-logo.png";
    //private static final String COUNTERS_IMAGE_FILENAME   = CONTENT_BASE + "counter-overlay.png";
    private static final String BARTENDER_POSE_SHEET_FILENAME = CONTENT_BASE + "ratz-pose-sheet.png";
    private static final String NPC_CAMEO_FILENAME = CONTENT_BASE + "npc-cameo.png";
    private static final int PAY_AMOUNT = 46;

    public  static final Point2D PLAYER_START = new Point2D(0.5, 0.86);
    private static final double[] WALK_BOUNDARY = new double[]{
        0.7, 0.65, 0.92, 0.65,
        0.92, 0.95, 0.89, 0.95,
        0.89, 1.0, 0.68, 1.0,
        0.6, 0.95, 0.35, 0.95
    };

    private static final VignetteTrigger exitPort = new VignetteTrigger(
        0.68, 0.97,   // exit location
        0.21, 0.03,   // exit size
        VignetteTrigger.SHOW_TRIGGER,
        0.7, 0.80,   // player position at destination
        PoseSheet.Direction.TOWARD, StreetChatsuboVignette.class); // Exit to here

    private static final TerminalTrigger terminal = new TerminalTrigger(
            0.65, 0.60, // trigger location
            0.06, 0.06, // trigger size
            0.00, 0.45 // trigger icon location
    );

    /*
        protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1
        {3, 4, 5, 6}, // 2   "I don't care if you eat that spaghetti..."
        {7}, // 3    "Sorry. I can't afford it..."
        {7}, // 4
        {7}, // 5
        {8}, // 6
        {3, 4, 5, 6}, // 7
        //{3, 4, 5, 6}, // 8
        {DIALOG_CLOSE.num}, // 8
        {}, // 9
        {}, // 10
        {12, 13, 14}, // 11
        {15}, // 12
        {16}, // 13
        {19}, // 14
        {12, 13, 14}, // 15
        {12, 13, 14}, // 16
        {}, // 17
        {}, // 18
        {23, 24}, // 19
        {}, // 20
        {}, // 21
        {}, // 22
        {25}, // 23
        {26}, // 24
        {24}, // 25
        {27, 28}, // 26
        {29}, // 27
        {30}, // 28
        {DIALOG_END.num}, // 29 ::  99 == npc no longer responds to player
        {DIALOG_END.num}, // 30
        {31}, //31  87 = player gives npc less than 46 credits.
        {2} // 32 Response to underpayment.
    };

     */
    private static final ArrayList<BabbleNode> DIALOG_CHAIN = new ArrayList<>() {
        {
            // TODO: Figure out when to use #12.
            add(new NarrationBabbleNode());                         // 0: Long Room Description
            add(new NarrationBabbleNode());                         // 1: Short Room Description
            add(new DialogBabbleNode(3, 4, 5, 6));  // 2
            add(new OptionBabbleNode(7)); // 3
            add(new OptionBabbleNode(7)); // 4
            add(new OptionBabbleNode(7)); // 5
            add(new OptionBabbleNode(8)); // 6
            add(new DialogBabbleNode(3, 4, 5, 6)); // 7
            add(new DialogBabbleNode(ITEM_BUY.num)); // 8
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

    private final Media media = new Media(getClass().getResource(MUSIC).toExternalForm());
    private com.maehem.abyss.engine.Character npcCharacter;
    private int shopOwnerAnimationCount = 0;

//    private final MusicTrack musicTrack;

    public ChatsuboBarVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(ROOM_NUMBER, gs, CONTENT_BASE,prevPort, player,WALK_BOUNDARY);

//        musicTrack = new MusicTrack(media);
//        musicTrack.play();
        setMusic(media);
    }

    @Override
    protected void init() {
        setHorizon(0.4);
        setPlayerScale(PLAYER_SCALE);
        addPort(exitPort);
        addTerminal( terminal );

        //initDialogText(DIALOG_CHAIN);
        initNpc();   // then layer in shop owner

        setDialogPane(new DialogPane(this, npcCharacter));
        //getDialogPane().setCameoTranslate(0, 10);
        initNpcDialog();

        initBackground(); // then layer in any fixtures on top of them

        getDialogPane().setCurrentDialog(dialogWarmUp());
        getDialogPane().setVisible(false);
    }

    private void initNpc() {
        npcCharacter = new com.maehem.abyss.engine.Character(bundle.getString("character.ratz.name"));
        npcCharacter.setScale(2.0);
        npcCharacter.setLayoutX(140);
        npcCharacter.setLayoutY(550);

        // TODO:   Check that file exists.  The current exception message is cryptic.
        npcCharacter.setSkin(getClass().getResourceAsStream(BARTENDER_POSE_SHEET_FILENAME), 1, 4);
        LOGGER.config("Add skin for Ratz. " + BARTENDER_POSE_SHEET_FILENAME);
        npcCharacter.setCameo(getClass().getResourceAsStream(NPC_CAMEO_FILENAME));

        getCharacterList().add(npcCharacter);
        getBgGroup().getChildren().add(npcCharacter);
        npcCharacter.setHearingBoundary(6.0); // Do after skin set.
        npcCharacter.getHearingBoundary().setTranslateY(100);
    }

    private void initBackground() {
    }

    // TODO:  Ways to automate this.   JSON file?
    private void initNpcDialog() {
        npcCharacter.setAllowTalk(true);
        getDialogPane().setDialogChain(DIALOG_CHAIN);

//        DialogPane dialogPane = getDialogPane();
//
//        dialogPane.setVars(new String[]{ getGameState().getPlayer().getName() });
//
//        DialogSheet2 ds1 = new DialogSheet2(dialogPane);
//        DialogSheet2 ds2 = new DialogSheet2(dialogPane);
//        DialogSheet2 ds3 = new DialogSheet2(dialogPane);
//        DialogSheet2 ds4 = new DialogSheet2(dialogPane);
//        DialogSheet2 ds5 = new DialogSheet2(dialogPane);
//        DialogSheet2 ds6 = new DialogSheet2(dialogPane);
//        DialogSheet2 ds7 = new DialogSheet2(dialogPane);
//        DialogSheet2 ds8 = new DialogSheet2(dialogPane);
//        DialogSheet2 ds9 = new DialogSheet2(dialogPane);
//
//        // Ratz has nothing more to say.
//        DialogResponseAction endDialog = () -> {
//            npcCharacter.setAllowTalk(false);
//            dialogPane.setActionDone(true);
//            npcCharacter.setTalking(false);
//        };
//
//        ds9.setDialogText(bundle.getString("dialog.ds9.ratz"));
//        ds9.addResponse(new DialogResponse2( bundle.getString("dialog.ds9.p.1"), endDialog));
//
//        ds8.setDialogText(bundle.getString("dialog.ds8.ratz"));
//        ds8.addResponse(new DialogResponse2( bundle.getString("dialog.ds8.p.1"), ds9));
//
//        ds7.setDialogText(bundle.getString("dialog.ds7.ratz"));
//        ds7.addResponse(new DialogResponse2( bundle.getString("dialog.ds7.p.1"), ds8));
//
//        ds6.setDialogText(bundle.getString("dialog.ds6.ratz"));
//        ds6.addResponse(new DialogResponse2( bundle.getString("dialog.ds6.p.1"), ds4));
//
//        ds5.setDialogText(bundle.getString("dialog.ds5.ratz"));
//        ds5.addResponse(new DialogResponse2( bundle.getString("dialog.ds5.p.1"), ds4));
//
//        ds4.setDialogText(bundle.getString("dialog.ds4.ratz"));
//        ds4.addResponse(new DialogResponse2( bundle.getString("dialog.ds4.p.1"), ds5));
//        ds4.addResponse(new DialogResponse2( bundle.getString("dialog.ds4.p.2"), ds6));
//        ds4.addResponse(new DialogResponse2( bundle.getString("dialog.ds4.p.3"), ds7));
//
//
//                    // If paid, exit player and lock door.
//        DialogResponseAction payUpAction = () -> {
//            //barOwnerCharacter.getDialogPane().doCloseDialog();
//
//            // Pop up money paymemt GUI.
//            setGiveMoneyShowing(PAY_AMOUNT,
//                    "Pay " + npcCharacter.getName() + " for your meal.",
//                    (t) -> {  // Success action handler.
//                        getDialogPane().setCurrentDialogSheet(ds4);
//                        // Display ds4
//                        // Set dialog to ds4
//                        // open dialog
//
//                    }
//            );
//            // On cancel, open last dialog again.
//
//            // On sucess, do the follwong.
//
//            // We will only set action done if npc gets paid.
//            //barOwnerCharacter.getDialogPane().setActionDone(true);
//
//
//        };
//
//        // Now just pay up. Nothing else to talk about.
//        ds3.setDialogText(bundle.getString("dialog.ds3.ratz"));
//
//        // Configure the dialog based on if player can pay the bill.
//        DialogResponseAction preDs3 = () -> {
//            ds3.clearReponses();
//            ds3.addResponse(new DialogResponse2( // I'll see what i can do.
//                    bundle.getString("dialog.ds3.p.1"),
//                    () -> {
//                        getDialogPane().doCloseDialog();
//                        getDialogPane().setCurrentDialogSheet(ds1);
//                    }
//            ));
//
//            // I have your money right here.
//            if (getGameState().getPlayer().getMoney() >= PAY_AMOUNT) {
//                ds3.addResponse(new DialogResponse2(
//                bundle.getString("dialog.ds3.p.2"),
//                payUpAction
//                ));
//            }
//            ds3.doResponseAction(); // calling this on a DialogSheet causes it to become the active dialog.
//        };
//
//        // Now just pay up. Nothing else to talk about.
//        ds2.setDialogText(bundle.getString("dialog.ds2.ratz"));
//
//        // Configure the dialog based on if player can pay the bill.
//        DialogResponseAction preDs2 = () -> {
//            ds2.clearReponses();
//            ds2.addResponse(new DialogResponse2( // I'll see what I can do.
//                    bundle.getString("dialog.ds2.p.1"),
//                    () -> {
//                        dialogPane.doCloseDialog();
//                        dialogPane.setCurrentDialogSheet(ds1);
//                    }
//            ));
//
//            // I have your money right here.
//            if (getGameState().getPlayer().getMoney() >= PAY_AMOUNT) {
//                ds2.addResponse(new DialogResponse2(
//                bundle.getString("dialog.ds2.p.2"),
//                payUpAction
//                ));
//            }
//            ds2.doResponseAction(); // calling this on a DialogSheet causes it to become the active dialog.
//        };
//
//        // Ratz asks to get paid and player replies with snarky comeback.
//        ds1.setDialogText(bundle.getString("dialog.ds1.ratz"));
//        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.1"), preDs2));
//        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.2"), preDs2));
//        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), preDs2));
//        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.4"), preDs3));
//
//        dialogPane.addDialogSheet(ds1);
//        dialogPane.addDialogSheet(ds2);
//        dialogPane.addDialogSheet(ds3);
//        dialogPane.addDialogSheet(ds4);
//        dialogPane.addDialogSheet(ds5);
//        dialogPane.addDialogSheet(ds6);
//        dialogPane.addDialogSheet(ds7);
//        dialogPane.addDialogSheet(ds8);
    }

    @Override
    public void loop() {
        shopOwnerAnimationCount++;
        if (shopOwnerAnimationCount > 40) {
            shopOwnerAnimationCount = 0;
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
