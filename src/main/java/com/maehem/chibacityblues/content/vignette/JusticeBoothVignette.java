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
import static com.maehem.abyss.engine.babble.DialogCommand.DEATH;
import static com.maehem.abyss.engine.babble.DialogCommand.EXIT_ST_CHAT;
import static com.maehem.abyss.engine.babble.DialogCommand.FINE_BANK_20K;
import static com.maehem.abyss.engine.babble.DialogCommand.FINE_BANK_500;
import static com.maehem.abyss.engine.babble.DialogCommand.NPC2;
import com.maehem.abyss.engine.babble.DialogPane;
import com.maehem.abyss.engine.babble.NarrationBabbleNode;
import com.maehem.abyss.engine.babble.OptionBabbleNode;
import java.util.ArrayList;
import java.util.Properties;
import javafx.geometry.Point2D;

/**
 *
 * @author Mark J Koch [@maehem on GitHub]
 */
public class JusticeBoothVignette extends Vignette {

    private static final int ROOM_NUMBER = 3;
    private static final String CONTENT_BASE = "/content/vignette/justice-booth/";
    private static final String NPC_JUDGE_CAMEO_FILENAME = CONTENT_BASE + "npc-judge-cameo.png";
    private static final String NPC_LAWYER_CAMEO_FILENAME = CONTENT_BASE + "npc-lawyer-cameo.png";
    private static final String NPC_JUDGE_POSE_SHEET_FILENAME = CONTENT_BASE + "npc-pose-sheet.png";
    private static final String NPC_LAWYER_POSE_SHEET_FILENAME = CONTENT_BASE + "npc-pose-sheet.png";

    public static final Point2D PLAYER_START = new Point2D(0.5, 0.88);
    private static final double[] WALK_BOUNDARY = new double[]{
        0.30, 0.75, 0.70, 0.75,
        0.70, 0.90, 0.30, 0.90
    };

    private static final VignetteTrigger exitPort = new VignetteTrigger(
            0.00, 0.75, // exit location
            0.04, 0.25, // exit size
            0.4, 0.90, // player position at destination
            PoseSheet.Direction.TOWARD, StreetChatsuboVignette.class); // Exit to here
    private static final VignetteTrigger deathPort = new VignetteTrigger(
            -1000, -1000, // exit location
            0.1, 0.1, // exit size
            0.50, 0.90, // player position at destination
            VignetteTrigger.Location.DEATH,
            PoseSheet.Direction.TOWARD, BodyShopVignette.class);

    private com.maehem.abyss.engine.Character npcJudge;
    private com.maehem.abyss.engine.Character npcLawyer;

    private int npcAnimationCount = 0;

    private static final ArrayList<BabbleNode> DIALOG_CHAIN = new ArrayList<>() {
        {
            add(new NarrationBabbleNode());                         // 0: Long Room Description
            add(new NarrationBabbleNode());                         // 1: Short Room Description
            add(new DialogBabbleNode(3)); // 2   "You have been charged"
            add(new DialogBabbleNode(NPC2.num, 4)); // 3    "I'd like to offer my services"
            add(new DialogBabbleNode(5, 6, 7, 8)); // 4  Judge: He's right
            add(new OptionBabbleNode(18)); // 5  Fine, I'll pay lawyer fee.
            add(new OptionBabbleNode(9)); // 6  I can't afford lawyer.
            add(new OptionBabbleNode(9)); // 7  I don't need lawyer.
            add(new OptionBabbleNode(10)); // 8  This is an outrage.
            add(new DialogBabbleNode(NPC2.num, 10)); // 9  OK. defend yourself.
            add(new DialogBabbleNode(11, 12, 13, 14)); // 10 How do you plead.
            add(new OptionBabbleNode(15)); // 11 This is an outrage. I haven't...
            add(new OptionBabbleNode(15)); // 12 I'm innocent.
            add(new OptionBabbleNode(15)); // 13 I'm guilty.
            add(new OptionBabbleNode(15)); // 14 I'm insane.
            add(new DialogBabbleNode(16)); // 15 The court notes your plea.
            add(new DialogBabbleNode(NPC2.num, 17)); // 16 Ha! Guilty.
            add(new DialogBabbleNode(FINE_BANK_500.num, EXIT_ST_CHAT.num)); // 17 Guilty. You must remain.
            add(new DialogBabbleNode(19)); // 18 Wise choice..
            add(new DialogBabbleNode(20)); // 19 How does the defendant plead.
            add(new DialogBabbleNode(NPC2.num, 21, 22, 23, 24)); // 20 (lawyer)Guilty your honor.
            add(new OptionBabbleNode(26)); // 21 What? This is an outrage.
            add(new OptionBabbleNode(26)); // 22 I throw myself on the mercy of the court.
            add(new OptionBabbleNode(26)); // 23 I'm insane!.
            add(new OptionBabbleNode(25)); // 24 You call yourself a lawyer?
            add(new DialogBabbleNode(NPC2.num, 26)); // 25 Trust me I know what I'm doing.
            add(new DialogBabbleNode(27, 28, 29)); // 26 The court finds the defendant guilty. 500 fine.
            add(new OptionBabbleNode(30)); // 27 This is a blantant miscarriage of justice.
            add(new OptionBabbleNode(30)); // 28 I'll get you for this.
            add(new OptionBabbleNode(30)); // 29 I'm sorry. I've learned the error of my ways..
            add(new DialogBabbleNode(FINE_BANK_500.num, EXIT_ST_CHAT.num)); // 30 You are free to go.
            add(new DialogBabbleNode(32)); // 31 You are becoming a familiar face.
            add(new DialogBabbleNode(NPC2.num, 33, 34, 35, 36, 37, 38, 39)); // 32 Death penalty. Give him the death penalty.
            add(new OptionBabbleNode(44)); // 33 Oh put a sock in it.
            add(new OptionBabbleNode(40)); // 34 It's not my fault! I'm the product of...
            add(new OptionBabbleNode(40)); // 35 I can't help it.
            add(new OptionBabbleNode(41)); // 36 Come on. Let's get this over with.
            add(new OptionBabbleNode(42)); // 37 Does it matter.
            add(new OptionBabbleNode(43)); // 38 I'm innocent.
            add(new OptionBabbleNode(44)); // 39 I'm guilty.
            add(new DialogBabbleNode(44)); // 40 That's what they all say.
            add(new DialogBabbleNode(NPC2.num, 45)); // 41 It's your life.
            add(new DialogBabbleNode(43)); // 42 Stop groveling.
            add(new DialogBabbleNode(NPC2.num, 45)); // 43 Innocent? Ha!
            add(new DialogBabbleNode(NPC2.num, 45)); // 44 This is obviously a hardened criminal...
            add(new DialogBabbleNode(46)); // 45 In view of your criminal history...
            add(new DialogBabbleNode(DEATH.num)); // 46 I hereby sentence you to death...
            add(new DialogBabbleNode(FINE_BANK_20K.num, EXIT_ST_CHAT.num)); // 43 I hereby pronouce you guilty as charged. 20000 fine.
        }
    };

    public JusticeBoothVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(ROOM_NUMBER, gs, CONTENT_BASE, prevPort, player, WALK_BOUNDARY);
    }

    @Override
    protected void init() {
        setHorizon(0.5);
        addPort(exitPort);
        addPort(deathPort);

        initDialogText(DIALOG_CHAIN);
        initNPC();   // then layer in shop owner

        setDialogPane(new DialogPane(this, npcJudge, npcLawyer));
        initNpcDialog();

        initBackground(); // then layer in any fixtures on top of them

        getDialogPane().setCurrentDialog(dialogWarmUp());
        getDialogPane().setVisible(false);

        // TODO: Any click opens dialog.
    }

    private void initNPC() {
        npcJudge = new com.maehem.abyss.engine.Character(bundle.getString("character.npc1.name"));
//        npcJudge.setScale(1.3);
//        npcJudge.setLayoutX(100);
//        npcJudge.setLayoutY(460);

        npcLawyer = new com.maehem.abyss.engine.Character(bundle.getString("character.npc2.name"));

        LOGGER.config("Apply Cameo for Judge. " + NPC_JUDGE_CAMEO_FILENAME);
        npcJudge.setCameo(getClass().getResourceAsStream(NPC_JUDGE_CAMEO_FILENAME));
        LOGGER.config("Apply Cameo for Lawyer. " + NPC_LAWYER_CAMEO_FILENAME);
        npcLawyer.setCameo(getClass().getResourceAsStream(NPC_LAWYER_CAMEO_FILENAME));

        // TODO:   Check that file exists.  The current exception message is cryptic.
        npcJudge.setSkin(getClass().getResourceAsStream(NPC_JUDGE_POSE_SHEET_FILENAME), 1, 4);
        LOGGER.config("Add skin for judge npc. " + NPC_JUDGE_POSE_SHEET_FILENAME);
        npcLawyer.setSkin(getClass().getResourceAsStream(NPC_LAWYER_POSE_SHEET_FILENAME), 1, 4);
        LOGGER.config("Add skin for lawyer npc. " + NPC_LAWYER_POSE_SHEET_FILENAME);

        //initNpcDialog();
        getCharacterList().add(npcJudge);
        getCharacterList().add(npcLawyer);

        // TODO: Animated versions of these characters.
        //getBgGroup().getChildren().add(npcJudge);
        //getBgGroup().getChildren().add(npcLawyer);
    }

    private void initBackground() {
        // No additional props to add here.
    }

    @Override
    public int dialogWarmUp() {
        // TODO: Check if player has been here more than
        // n times and return 31 n <gs.convictionCount>
        return 2;
    }

    // TODO:  Ways to automate this.   JSON file?
    private void initNpcDialog() {
        npcJudge.setAllowTalk(true);
        getDialogPane().setDialogChain(DIALOG_CHAIN);

//        DialogSheet2 ds1 = new DialogSheet2(getDialogPane());
//
//        // Ratz has nothing more to say.
//        DialogResponseAction endDialog = () -> {
//            npcJudge.setAllowTalk(false);
//            getDialogPane().setActionDone(true);
//            npcJudge.setTalking(false);
//        };
//
//        // Ratz asks to get paid and player replies with snarky comeback.
//        ds1.setDialogText(bundle.getString("dialog.ds1.npc1"));
//        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.1"), endDialog));
//
//        getDialogPane().addDialogSheet(ds1);
    }

    @Override
    public void loop() {
//        npcAnimationCount++;
//        if (npcAnimationCount > 40) {
//            npcAnimationCount = 0;
//            npcJudge.nextPose();
//        }
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
