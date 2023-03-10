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
import java.util.Properties;
import javafx.geometry.Point2D;

/**
 *
 * @author Mark J Koch [@maehem on GitHub]
 */
public class CheapHotelVignette extends Vignette {

    //public  static final String PROP_NAME = "body-shop";  
    private static final String CONTENT_BASE = "/content/vignette/cheap-hotel/";
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
        0.3, 0.80,   // player position at destination
        PoseSheet.Direction.RIGHT, "StreetCheapHotelVignette"); // Exit to here
    
    private static final TerminalTrigger terminal = new TerminalTrigger(
        0.46, 0.71,   // trigger location
        0.07, 0.07   // trigger size
    );
    
    public CheapHotelVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(gs, CONTENT_BASE,prevPort, player,WALK_BOUNDARY);
    }

    @Override
    protected void init() {        
        setHorizon(0.25);

        // Do in this order.  TODO: Leverage Z-order of JavaFX?
        // Background is autoloaded by superclass.
        initBackground(); // then layer in any fixtures on top of them
        
        //getBgGroup().setOpacity(0.7);
        
        addPort(exitPort);
        addTerminal( terminal );
    }

    private void initBackground() {
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
