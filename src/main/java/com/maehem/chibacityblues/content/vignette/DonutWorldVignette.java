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

/**
 *
 * @author Mark J Koch [@maehem on GitHub]
 */
public class DonutWorldVignette extends Vignette {

    //public  static final String PROP_NAME = "donut-world";    
    private static final String CONTENT_BASE = "/content/vignette/donut-world/";
    private static final String NPC_POSE_SHEET_FILENAME = CONTENT_BASE + "npc-pose-sheet.png";
    public  static final Point2D PLAYER_START = new Point2D(0.5, 0.86);
    private static final double[] WALK_BOUNDARY = new double[] {
                0.05, 0.70,    0.56, 0.70,
                0.67, 0.98,    0.05, 0.98,
                0.05, 0.70
    };

    private static final VignetteTrigger exitPort = new VignetteTrigger(
        0.38, 0.68,   // exit location
        0.21, 0.03,   // exit size
        0.50, 0.80,   // player position at destination
        PoseSheet.Direction.TOWARD, "StreetBodyShopVignette");
    
//    private static final MatrixTrigger matrixJack = new MatrixTrigger(
//        0.58, 0.50,   // trigger location
//        0.05, 0.05,   // trigger size
//        0x00205 // matrix address
//    );
//    
//    private static final TerminalTrigger terminal = new TerminalTrigger(
//        0.28, 0.50,   // trigger location
//        0.05, 0.05   // trigger size
//    );
    
    private com.maehem.abyss.engine.Character npcCharacter;
    
    public DonutWorldVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(gs, CONTENT_BASE,prevPort, player,WALK_BOUNDARY);        
    }

    @Override
    protected void init() {        
        setHorizon(0.2);
        addPort(exitPort);
//        addJack( matrixJack );
//        addTerminal( terminal );

        initNpc();
    }

    private void initNpc() {
        npcCharacter = new com.maehem.abyss.engine.Character(bundle.getString("character.npc.name"));
        npcCharacter.setScale(1.6);
        npcCharacter.setLayoutX(1000);
        npcCharacter.setLayoutY(600);

        // TODO:   Check that file exists.  The current exception message is cryptic.
        npcCharacter.setSkin(getClass().getResourceAsStream(NPC_POSE_SHEET_FILENAME), 1, 4);
        LOGGER.config("Add skin for pawn shop owner. " + NPC_POSE_SHEET_FILENAME);
        // Dialog
        // Load dialog tree from file.
//        barOwnerCharacter.getDialog().init(getWidth(), getHeight());

        initNpcDialog();

        getCharacterList().add(npcCharacter);
        getBgGroup().getChildren().add(
                npcCharacter
        );
    }

    // TODO:  Ways to automate this.   JSON file?
    private void initNpcDialog() {
        npcCharacter.setAllowTalk(true);
        DialogSheet2 ds1 = new DialogSheet2(npcCharacter.getDialogPane());
        DialogSheet2 ds2 = new DialogSheet2(npcCharacter.getDialogPane());
        
        // Ratz has nothing more to say.
        DialogResponseAction endDialog = () -> {
            npcCharacter.setAllowTalk(false);
            npcCharacter.getDialogPane().setActionDone(true);
            npcCharacter.setTalking(false);
        };
                
                
        // Now just pay up. Nothing else to talk about.
        ds2.setDialogText(bundle.getString("dialog.ds2.npc"));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds2.p.1"), endDialog));
                        
        // Ratz asks to get paid and player replies with snarky comeback.
        ds1.setDialogText(bundle.getString("dialog.ds1.npc"));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.1"), ds2));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.2"), ds2));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.3"), ds2));
        ds1.addResponse(new DialogResponse2(bundle.getString("dialog.ds1.p.4"), ds2));

        npcCharacter.getDialogPane().addDialogSheet(ds1);
        npcCharacter.getDialogPane().addDialogSheet(ds2);
    }

    @Override
    public void loop() {}

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
