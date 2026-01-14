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

import com.maehem.abyss.engine.GameState;
import com.maehem.abyss.engine.Player;
import com.maehem.abyss.engine.PoseSheet;
import com.maehem.abyss.engine.TerminalTrigger;
import com.maehem.abyss.engine.Vignette;
import com.maehem.abyss.engine.VignetteTrigger;
import com.maehem.abyss.engine.babble.BabbleNode;
import com.maehem.abyss.engine.babble.DialogBabbleNode;
import com.maehem.abyss.engine.babble.DialogCommand;
import static com.maehem.abyss.engine.babble.DialogCommand.EXIT_R;
import static com.maehem.abyss.engine.babble.DialogCommand.ITEM_BUY;
import static com.maehem.abyss.engine.babble.DialogCommand.WORD1;
import static com.maehem.abyss.engine.babble.DialogCommand.WORD2;
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
public class GentlemanLoserVignette extends Vignette {

    private static final int ROOM_NUMBER = 8;

    private static final String CONTENT_BASE = "/content/vignette/gentleman-loser/";
    private static final String NPC_CAMEO_FILENAME = CONTENT_BASE + "npc-cameo.png";

    //private static final String SKYLINE_IMAGE_FILENAME   = CONTENT_BASE + "cyberpunk-cityscape.png";
    //private static final String BAR_BACKGROUND_IMAGE_FILENAME   = CONTENT_BASE + "bar-background.png";
    //private static final String LOGO_IMAGE_FILENAME   = CONTENT_BASE + "chatsubo-logo.png";
    //private static final String COUNTERS_IMAGE_FILENAME   = CONTENT_BASE + "counter-overlay.png";
    //private static final String NPC_POSE_SHEET_FILENAME = CONTENT_BASE + "npc-pose-sheet.png";

    public  static final Point2D PLAYER_START = new Point2D(0.7, 0.86);
    private static final double[] WALK_BOUNDARY = new double[] {
                0.08, 0.75,   0.99, 0.75,
                0.99, 0.95,   0.08, 0.95,
                0.08, 0.75
    };

    private static final VignetteTrigger exitPort = new VignetteTrigger(
        0.97, 0.75,   // exit location
        0.03, 0.25,   // exit size
        VignetteTrigger.SHOW_TRIGGER,
        0.2, 0.80,   // player position at destination
        PoseSheet.Direction.RIGHT, StreetGentlemanLoserVignette.class); // Exit to here

    private static final TerminalTrigger terminal = new TerminalTrigger(
        0.80, 0.71,   // trigger location
        0.07, 0.07, // trigger size
        0.00, 0.55 // trigger icon location
    );

    /*
        protected static final int[][] DIALOG_CHAIN = { // G-Loser
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1
        {ON_FILTER_1.num}, // [2] :: Hey, geek!  Cmere!  I got somethin for ya!
        {8}, // [3] :: Sure and begorrah. Youre under arrest unless you answer some questions.
        {6}, // [4] :: Whatever it is, I hope its not contagious.
        {7}, // [5] :: Later. Ive got biz to attend to right now.
        {9, 10, 11, 12}, // [6] :: Anonymous was here earlier. If youre a friend of his, you know what Ive got for you.
        {DIALOG_CLOSE.num}, // [7] :: Suit yourself, cowboy.
        {EXIT_R.num}, // [8] :: Youre no cop!  Youre using a CopTalk skill chip!  Get out of here!
        {13}, // [9] :: A social disease?
        {14}, // [10] :: Is it smaller than a breadbox?
        {15}, // [11] :: Animal, vegetable, or mineral?
        {WORD1.num}, // [12] :: Ah!  You must be referring to the @---------------
        {DIALOG_CLOSE.num}, // [13] :: Beat it, cyberjerk!
        {DIALOG_CLOSE.num}, // [14] :: Its even smaller than your head, which is pretty small....
        {DIALOG_CLOSE.num}, // [15] :: No, actually I was referrin to somethin else, so get lost, wilson!
        {SKILL_BUY.num}, // [16] :: Yeah. You must be . I got your chip here for ya.
        {WORD1.num}, // [17] :: Okay.  What do you know about @---------------
        {SKILL_BUY.num}, // [18] :: Hey, Babe, I want to buy the chip.
        {22}, // [19] :: Maybe you could answer some questions for me?
        {DIALOG_CLOSE.num}, // [20] :: You already gave me something. I dont want anything else.
        {23}, // [21] :: How about coming back to my place?
        {17}, // [22] :: Sure. Itll be more fun than a poke in the eye with a sharp stick....
        {17, 18, 19, 20}, // [23] :: Forget it. You live at Cheap Hotel. I know all about your kind, wilson....
        {SKILL_BUY.num, 17}, // [24] :: All Ive got is Hardware Repair for $1000.
        {17}, // [25] :: Try Julius Deane for those chips.
        {17}, // [26] :: Julius Deane can upgrade your Cryptology skill chip.
        {17}, // [27] :: Hot stuff. Sense/Net has em all. They even have Dixie Flatline on ROM.
        {17}, // [28] :: Try the Finn at Metro Holografix for that sort of thing.
        {17}, // [29] :: Some kinda computer game, isnt it?
        {17}, // [30] :: Artificial Intelligence.  Smart computers owned by big companies. Just hope you never meet one, wilson.
        {17}, // [31] :: The Loser link code is LOSER. The password is "WILSON", which is a term you should be familiar with.
        {17}, // [32] :: Only a wilson would ask a question like that.
        {17}, // [33] :: I only have the coded password for Hitachi: "SELIM".
        {17}, // [34] :: The coded password for Copenhagen University is "KIKENNA".
        {ITEM_BUY.num}, // [35] :: Emperor Norton left you a Guest Pass for the Matrix Restaurant. He mumbled something about skills and upgrades.
        {17}, // [36] :: Ya got me. I dont know anythin about that.
        {17}, // [37] :: Shiva gives you a guest pass for the Matrix Restaurant.
        {39}, // [38] :: Shiva gives you your Cryptology chip.
        {SKILL_BUY.num, 19}, // [39] :: I also have Hardware Repair for sale for $1000.
        {DIALOG_CLOSE.num} // [40] :: I already gave it to you, cowboy.
    };

     */
    private static final ArrayList<BabbleNode> DIALOG_CHAIN = new ArrayList<>() {
        {
            // TODO: Figure out when to use #12.
            add(new NarrationBabbleNode());                         // 0: Long Room Description
            add(new NarrationBabbleNode());                         // 1: Short Room Description
            add(new DialogBabbleNode(3, 4, 5));  // 2
            add(new OptionBabbleNode(8)); // 3
            add(new OptionBabbleNode(6)); // 4
            add(new OptionBabbleNode(7)); // 5
            add(new DialogBabbleNode(9, 10, 11, 12)); // 6
            add(new DialogBabbleNode(DialogCommand.DIALOG_CLOSE.num)); // 7 Suit yourself
            add(new DialogBabbleNode(EXIT_R.num)); // 8  You're no cop.  <booted>
            add(new OptionBabbleNode(13)); // 9    "social disease"
            add(new OptionBabbleNode(14)); // 10
            add(new OptionBabbleNode(15)); // 11
            add(new OptionBabbleNode(WORD1.num)); // 12
            add(new DialogBabbleNode(EXIT_R.num)); // 13
            add(new DialogBabbleNode(9, 11, 12)); // 14
            add(new DialogBabbleNode(EXIT_R.num)); // 15
            add(new DialogBabbleNode(ITEM_BUY.num)); // 16 "I got your chip..."
            add(new OptionBabbleNode(WORD2.num)); // 17
            add(new OptionBabbleNode(ITEM_BUY.num)); // 18
            add(new DialogBabbleNode(17, 18)); // 19  "Answer questions
            add(new DialogBabbleNode(17, 18)); // 20
        }
    };

    private com.maehem.abyss.engine.Character npcCharacter;

    public GentlemanLoserVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(ROOM_NUMBER, gs, CONTENT_BASE,prevPort, player,WALK_BOUNDARY);
    }

    @Override
    protected void init() {
        setHorizon(0.25);
        addPort(exitPort);
        addTerminal( terminal );

        initNpc();

        setDialogPane(new DialogPane(this, npcCharacter));

        initNpcDialog();

        // Background is autoloaded by superclass.
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
        //npcCharacter.setSkin(getClass().getResourceAsStream(NPC_POSE_SHEET_FILENAME), 1, 4);
        //LOGGER.config("Add skin for Shiva. " + NPC_POSE_SHEET_FILENAME);
        npcCharacter.setCameo(getClass().getResourceAsStream(NPC_CAMEO_FILENAME));

        getCharacterList().add(npcCharacter);
        getBgGroup().getChildren().add(npcCharacter);
        npcCharacter.setHearingBoundary(6.0); // Do after skin set.
        npcCharacter.getHearingBoundary().setTranslateY(100);
    }

    private void initBackground() {
    }

    private void initNpcDialog() {
        npcCharacter.setAllowTalk(true);
        getDialogPane().setDialogChain(DIALOG_CHAIN);
    }

    @Override
    public void loop() {
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
