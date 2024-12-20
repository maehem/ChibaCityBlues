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
import com.maehem.abyss.engine.Character;
import com.maehem.abyss.engine.GameState;
import com.maehem.abyss.engine.Player;
import com.maehem.abyss.engine.PoseSheet;
import com.maehem.abyss.engine.Vignette;
import com.maehem.abyss.engine.VignetteTrigger;
import com.maehem.abyss.engine.babble.DialogResponse2;
import com.maehem.abyss.engine.babble.DialogResponseAction;
import com.maehem.abyss.engine.babble.DialogSheet2;
import com.maehem.chibacityblues.content.things.deck.KomodoDeckThing;
import java.util.Properties;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mark J Koch [@maehem on GitHub]
 */
public class PawnShopVignette extends Vignette {

    private static final int ROOM_NUMBER = 25;
    //private static final String PROP_NAME = "pawnshop";
    private static final String CONTENT_BASE = "/content/vignette/pawn-shop/";
    public static final Point2D PLAYER_START = new Point2D(0.20, 0.77);
    private static final String COUNTERS_IMAGE_FILENAME = CONTENT_BASE + "counters.png";
    //private static final String DOOR_PATCH_IMAGE_FILENAME = CONTENT_BASE + "patch-left.png";
    private static final String NPC_POSE_SHEET_FILENAME = CONTENT_BASE + "npc-pose-sheet.png";
    private static final String NPC_CAMEO_FILENAME = CONTENT_BASE + "npc-cameo.png";
    private static final double[] WALK_BOUNDARY = {
        0.11, 0.73, 0.98, 0.73,
        0.98, 0.99, 0.04, 0.99,
        0.02, 0.78, 0.02, 0.72,
        0.02, 0.66
    };
    private static final VignetteTrigger leftDoor = new VignetteTrigger(
            0.02, 0.70, // port XY location
            0.04, 0.06, // port size
            0.72, 0.75, // place player at this XY when they leave the pawn shop.
            PoseSheet.Direction.LEFT, // Face this direction at destination
            "StreetPawnShopVignette" // Class name of destination vignette
    );

//    private static final Patch leftDoorPatch = new Patch(
//            0, 0, 425,
//            PawnShopVignette.class.getResourceAsStream(DOOR_PATCH_IMAGE_FILENAME)
//    );
    private Character npcCharacter;
    private int npcAnimationCount = 0;

    // Array slots must match dialog.x fields in the bundle.
    // They are resolved at the init() phase.
    private static final ArrayList<BabbleNode> DIALOG_CHAIN_NEW = new ArrayList<>() {
        {
            add(new NarrationBabbleNode());                         // 0: Long Room Description
            add(new NarrationBabbleNode());                         // 1: Short Room Description
            add(new DialogBabbleNode(3, 4, 5));         // 2: NPC Talks to Player --> 3,4,5
            add(new OptionBabbleNode(7));                       // 3: Player Response. "Why such a rush?"
            add(new OptionBabbleNode(6));                       // 4: Player Response. "Okay. Give me deck..."
            add(new OptionBabbleNode(8));                       // 5: Player Response. "I don't have cash..."
            add(new DialogBabbleNode(ITEM_BUY.num));            // 6: NPC Opens Vend window. "Give ticket and money..."
            add(new DialogBabbleNode(3, 4, 5));         // 7: NPC Talks to Player --> 3,4,5. "Your deck scare away customer..."
            add(new DialogBabbleNode(9, 10));               // 8: NPC Talks to Player --> 3,4,5. "What? I no want deck..."
            add(new OptionBabbleNode(ITEM_GET.num, DESC.num, 13)); // 9: Player Response. "Thanks for my deck..."
            add(new OptionBabbleNode(ITEM_GET.num, DESC.num, 13)); // 10: Player Response. "Okay pal!..."
            add(new OptionBabbleNode(ITEM_GET.num, DESC.num, 13)); // 11: Player Response. "Thanks. I knew you'd..."
            add(new NarrationBabbleNode(DIALOG_NO_MORE.num, EXIT_L.num)); // 12: NPC: "Shin slams door..."
            add(new NarrationBabbleNode(DESC.num, 12)); // 13: NPC: "Shin gives you your deck."
            add(new DialogBabbleNode(ITEM_GET.num, DESC.num, 13)); // 14: NPC gives deck. "You no have ticket?..."
        }
    };

    /**
     *
     * @param gs
     * @param prevPort where the player came from
     * @param player the @Player
     */
    public PawnShopVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(ROOM_NUMBER, gs, CONTENT_BASE, prevPort, player, WALK_BOUNDARY);
        // Don't put things here.  Override @init() which is called during creation.
    }

    @Override
    protected void init() {
        setHorizon(0.3);

        initDialogText();
        initNpcDialog();
        initBackground();

        addPort(leftDoor);

        //addPatch(leftDoorPatch);

        // example Depth of field
        //fgGroup.setEffect(new BoxBlur(10, 10, 3));
    }

    private void initDialogText() {
        npcCharacter = new Character(bundle.getString("character.npc.name"));
        npcCharacter.setScale(1.6);
        npcCharacter.setLayoutX(580);
        npcCharacter.setLayoutY(480);

        // TODO:   Check that file exists.  The current exception message is cryptic.
        npcCharacter.setSkin(PawnShopVignette.class.getResourceAsStream(NPC_POSE_SHEET_FILENAME), 1, 4);
        LOGGER.config("Add skin for pawn shop owner. " + NPC_POSE_SHEET_FILENAME);

        getCharacterList().add(npcCharacter);
        getBgGroup().getChildren().add(
                npcCharacter
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
        getBgGroup().getChildren().add(counterView);
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

    @Override
    public int dialogWarmUp() {
        if (npcCharacter.canTalk()) {
            //if (gs.roomCanTalk()) {
            if (getPlayer().getInventory().hasItemType(KomodoDeckThing.class.getSimpleName())) {
                //gs.pawnRecent = GameState.PawnRecent.NONE;
                LOGGER.log(Level.SEVERE, "Player already bought the deck.");

                // Dialog to player with 9,10
                return 8;
            }
            return 2;
        } else {
            return DIALOG_END.num;
        }
    }

    // TODO:  Ways to automate this.   JSON file?
    private void initNpcDialog() {
        npcCharacter.setAllowTalk(true);
        LOGGER.config("Apply Cameo for NPC. " + NPC_CAMEO_FILENAME);
        npcCharacter.setCameo(getClass().getResourceAsStream(NPC_CAMEO_FILENAME));
        // Shin kicks the player out of the shop but gives him his item.
        DialogResponseAction exitAction = () -> {
            npcCharacter.getDialogPane().setExit(leftDoor);
            npcCharacter.getDialogPane().setActionDone(true);

            // Add cyberspace deck to inventory.
            npcCharacter.give(new KomodoDeckThing(), getPlayer());

            // TODO:
            // GameState set StreetVignette PawnShop door locked.
            getGameState().setProperty(getClass().getSimpleName(), Vignette.RoomState.LOCKED.name());
        };

        DialogSheet2 ds4 = new DialogSheet2(getDialogPane());
        ds4.setDialogText(bundle.getString("dialog.ds4.npc"));
        ds4.addResponse(new DialogResponse2(bundle.getString("dialog.ds4.p.1"), exitAction)); // Exit action

        DialogSheet2 ds3 = new DialogSheet2(getDialogPane());
        ds3.setDialogText(bundle.getString("dialog.ds3.npc"));
        ds3.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), ds4));

        DialogSheet2 ds2 = new DialogSheet2(getDialogPane());
        ds2.setDialogText(bundle.getString("dialog.ds2.npc"));
        ds2.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.2"), ds3));
        ds2.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), ds4));

        DialogSheet2 ds1 = new DialogSheet2(getDialogPane());
        ds1.setDialogText(bundle.getString("dialog.ds1.npc"));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.1"), ds2));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.2"), ds3));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), ds4));

        getDialogPane().addDialogSheet(ds1);
        getDialogPane().addDialogSheet(ds2);
        getDialogPane().addDialogSheet(ds3);
        getDialogPane().addDialogSheet(ds4);
    @Override
    public void onItemGet() {
        LOGGER.log(Level.SEVERE, "PawnShop: onItemGet() called. What to do?");

        // Add cyberspace deck to inventory.
        npcCharacter.give(new KomodoDeckThing(), getPlayer());

        // Shin kicks out player and locks door.
    }

    @Override
    public ArrayList<Thing> getVendItems(GameState gs) {

        KomodoDeckThing thing = new KomodoDeckThing();
        thing.setValue(100);
        thing.setCondition(50);

        ArrayList<Thing> list = new ArrayList<>();
        list.add(thing);

        return list;
    }

    @Override
    public boolean onVendItemsFinished(GameState gs) {
        LOGGER.log(Level.CONFIG, "Pawn Shop: onVendItemsFinished() called.");

        if (getPlayer().getInventory().hasItemType(KomodoDeckThing.class.getSimpleName())) {
            // Shin locks door as you leave.
            LOGGER.log(Level.CONFIG, "Add GameGoal ==> ShinClosedGoal");
            gs.getGoals().add(new ShinClosedGoal());

            return true;
        }
        return false;
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
