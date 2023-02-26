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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mark J Koch [flatlinejack at maehem dot com]
 */
public class StreetMicrosoftsVignette extends Vignette {

    private static final String CONTENT_BASE = "/content/vignette/street-microsofts/";
    private static final String SKYLINE_IMAGE_FILENAME   = CONTENT_BASE + "skyline.png";
    //private static final String ROBOT_POSE_SHEET_FILENAME = CONTENT_BASE + "police-robot.png";
    //private static final String DOOR_PATCH_IMAGE_FILENAME = CONTENT_BASE + "door-right-wing.png";
    public static final Point2D PLAYER_START = new Point2D(0.50, 0.86);

    private static final double[] WALK_BOUNDARY = {
             0.02, 0.97,    0.15, 0.97,
             0.20, 0.99,    0.85, 0.99,
             0.90, 0.97,    0.99, 0.97,
             0.99, 0.65,    0.85, 0.57,
             0.69, 0.59,    0.42, 0.59,
             0.45, 0.56,    0.32, 0.56,
             0.29, 0.59,    0.02, 0.59,
             0.02, 0.97
    };

    private static final VignetteTrigger leftDoor = new VignetteTrigger(
            0.00, 0.59, // Location
            0.02, 0.40, // Size
            VignetteTrigger.SHOW_TRIGGER,
            StreetBodyShopVignette.PLAYER_START.getX(), 
            StreetBodyShopVignette.PLAYER_START.getY(), 
            PoseSheet.Direction.TOWARD, // Player position and orientation at destination
            "StreetBodyShopVignette" // Destination
    );
    private static final VignetteTrigger rightDoor = new VignetteTrigger(
            0.87, 0.57, // Location
            0.06, 0.03, // Size
            PawnShopVignette.PLAYER_START.getX(), 
            PawnShopVignette.PLAYER_START.getY(),
            PoseSheet.Direction.TOWARD, // Player position and orientation at destination
            "PawnShopVignette" // Destination
    );
    private static final VignetteTrigger topDoor = new VignetteTrigger(
            0.36, 0.56,
            0.14, 0.014,
            ChatsuboBarVignette.PLAYER_START.getX(), 
            ChatsuboBarVignette.PLAYER_START.getY(), 
            PoseSheet.Direction.TOWARD,
            "BodyShopVignette"
    );
    private static final VignetteTrigger bottomDoor = new VignetteTrigger(
            0.20, 0.97,
            0.70, 0.03,
            VignetteTrigger.SHOW_TRIGGER,
            DonutShopVignette.PLAYER_START.getX(), 
            DonutShopVignette.PLAYER_START.getY(), 
            PoseSheet.Direction.TOWARD,
            "DonutShopVignette"
    );
    
    public StreetMicrosoftsVignette(GameState gs, VignetteTrigger prevPort, Player player) {
        super(gs, CONTENT_BASE,prevPort, player, WALK_BOUNDARY);
    }

    @Override
    public void loop() {
    }

    @Override
    protected void init() {
        setHorizon(0.30);
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
        final ImageView skylineView = new ImageView();
        skylineView.setImage(new Image(getClass().getResourceAsStream(SKYLINE_IMAGE_FILENAME)));
        skylineView.setFitWidth(1280);
        skylineView.setPreserveRatio(true);
        skylineView.setLayoutX(-500);
        skylineView.setLayoutY(0);
        // Add these in visual order.  Back to front.
        getSkylineGroup().getChildren().add( skylineView );
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
