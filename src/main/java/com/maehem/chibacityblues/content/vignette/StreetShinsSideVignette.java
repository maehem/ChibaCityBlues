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
public class StreetShinsSideVignette extends Vignette {

    private static final int ROOM_NUMBER = 26;
    private static final String CONTENT_BASE = "/content/vignette/street-shins-side/";
    //private static final String SKYLINE_IMAGE_FILENAME   = CONTENT_BASE + "skyline.png";
    //private static final String ROBOT_POSE_SHEET_FILENAME = CONTENT_BASE + "police-robot.png";
    //private static final String DOOR_PATCH_IMAGE_FILENAME = CONTENT_BASE + "door-right-wing.png";
    public static final Point2D PLAYER_START = new Point2D(0.50, 0.86);
    public static final Point2D PLAYER_START_L = new Point2D(0.15, 0.86);
    public static final Point2D PLAYER_START_R = new Point2D(0.85, 0.86);

    private static final double[] WALK_BOUNDARY = {
             0.02, 0.97,    0.35, 0.97,
             0.37, 0.99,    0.63, 0.99,
             0.65, 0.97,    0.99, 0.97,
             0.99, 0.72,    0.42, 0.72,
             0.45, 0.70,     0.32, 0.70,
             0.29, 0.72,     0.02, 0.72,
             0.02, 0.97
    };

    private static final VignetteTrigger leftDoor = new VignetteTrigger(
            0.00, 0.70, // Location
            0.02, 0.30, // Size
            VignetteTrigger.SHOW_TRIGGER,
            StreetCheapHotelVignette.PLAYER_START_R.getX(),
            StreetCheapHotelVignette.PLAYER_START_R.getY(),
            PoseSheet.Direction.LEFT, // Player position and orientation at destination
            StreetCheapHotelVignette.class // Destination
    );
    private static final VignetteTrigger rightDoor = new VignetteTrigger(
            0.98, 0.70, // Location
            0.02, 0.30, // Size
            VignetteTrigger.SHOW_TRIGGER,
            StreetMetroHoloVignette.PLAYER_START_L.getX(),
            StreetMetroHoloVignette.PLAYER_START_L.getY(),
            PoseSheet.Direction.RIGHT, // Player position and orientation at destination
            StreetMetroHoloVignette.class // Destination
    );

    public StreetShinsSideVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(ROOM_NUMBER, gs, CONTENT_BASE, prevPort, player, WALK_BOUNDARY);
    }

    @Override
    public void loop() {
    }

    @Override
    protected void init() {
        //getPlayer().setScale(DEFAULT_SCALE*0.90);
        setHorizon(0.36);
        // set player position
        //setPlayerPosition(PLAYER_START);

        addPort(leftDoor);
        addPort(rightDoor);
        //addPort(topDoor);
        //addPort(bottomDoor);

        initBackground();
    }

    private void initBackground() {
        // Skyline behind scene, through window.
//        final ImageView skylineView = new ImageView();
//        skylineView.setImage(new Image(getClass().getResourceAsStream(SKYLINE_IMAGE_FILENAME)));
//        skylineView.setFitWidth(1280);
//        skylineView.setPreserveRatio(true);
//        skylineView.setLayoutX(0);
//        skylineView.setLayoutY(-200);
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
