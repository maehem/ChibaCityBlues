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
import com.maehem.abyss.engine.Vignette;
import com.maehem.abyss.engine.VignetteTrigger;
import java.util.Properties;
import javafx.geometry.Point2D;

/**
 *
 * @author Mark J Koch [@maehem on GitHub]
 */
public class StreetCheapHotelVignette extends Vignette {

    private static final String CONTENT_BASE = "/content/vignette/street-cheap-hotel/";
    //private static final String SKYLINE_IMAGE_FILENAME   = CONTENT_BASE + "skyline.png";
    //private static final String ROBOT_POSE_SHEET_FILENAME = CONTENT_BASE + "police-robot.png";
    //private static final String DOOR_PATCH_IMAGE_FILENAME = CONTENT_BASE + "door-right-wing.png";
    public static final Point2D PLAYER_START = new Point2D(0.50, 0.90);

    private static final double[] WALK_BOUNDARY = {
             0.02, 0.97,    0.99, 0.97,
             0.99, 0.68,    0.09, 0.68,
             0.02, 0.97
    };

    private static final VignetteTrigger leftDoor = new VignetteTrigger(
            0.07, 0.68, // Location
            0.06, 0.10, // Size
            StreetBodyShopVignette.PLAYER_START.getX(), 
            StreetBodyShopVignette.PLAYER_START.getY(), 
            PoseSheet.Direction.TOWARD, // Player position and orientation at destination
            "CheapHotelVignette" // Destination
    );
    private static final VignetteTrigger rightDoor = new VignetteTrigger(
            0.98, 0.68, // Location
            0.02, 0.20, // Size
            VignetteTrigger.SHOW_TRIGGER,
            StreetShinsSideVignette.PLAYER_START.getX(), 
            StreetShinsSideVignette.PLAYER_START.getY(),
            PoseSheet.Direction.RIGHT, // Player position and orientation at destination
            "StreetShinsSideVignette" // Destination
    );
    private static final VignetteTrigger topDoor = new VignetteTrigger(
            0.22, 0.68,
            0.66, 0.014,
            StreetPawnShopVignette.PLAYER_START.getX(), 
            StreetPawnShopVignette.PLAYER_START.getY(), 
            PoseSheet.Direction.AWAY,
            "StreetPawnShopVignette"
    );
    private static final VignetteTrigger bottomDoor = new VignetteTrigger(
            0.02, 0.97,
            0.96, 0.03,
            VignetteTrigger.SHOW_TRIGGER,
            StreetGentlemanLoserVignette.PLAYER_START.getX(), 
            StreetGentlemanLoserVignette.PLAYER_START.getY(), 
            PoseSheet.Direction.TOWARD,
            "StreetGentlemanLoserVignette"
    );
    
    public StreetCheapHotelVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(gs, CONTENT_BASE,prevPort, player, WALK_BOUNDARY);
    }

    @Override
    public void loop() {
    }

    @Override
    protected void init() {
        setHorizon(0.47);
        // set player position
        //setPlayerPosition(PLAYER_START);

        addPort(leftDoor);
        addPort(rightDoor);
        addPort(topDoor);
        addPort(bottomDoor);

        initBackground();
    }
    
    private void initBackground() {
        // Skyline behind scene, through window.
//        final ImageView skylineView = new ImageView();
//        skylineView.setImage(new Image(getClass().getResourceAsStream(SKYLINE_IMAGE_FILENAME)));
//        skylineView.setFitWidth(1280);
//        skylineView.setPreserveRatio(true);
//        skylineView.setLayoutX(-500);
//        skylineView.setLayoutY(0);
//        // Add these in visual order.  Back to front.
//        getSkylineGroup().getChildren().add( skylineView );
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

    @Override
    public Point2D getDefaultPlayerPosition() {
        return PLAYER_START;
    }
}
