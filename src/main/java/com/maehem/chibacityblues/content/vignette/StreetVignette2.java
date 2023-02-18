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
import com.maehem.abyss.engine.Patch;
import com.maehem.abyss.engine.Player;
import com.maehem.abyss.engine.PoseSheet;
import com.maehem.abyss.engine.Vignette;
import com.maehem.abyss.engine.VignetteTrigger;
import java.util.Properties;
import javafx.geometry.Point2D;

/**
 *
 * @author Mark J Koch [flatlinejack at maehem dot com]
 */
public class StreetVignette2 extends Vignette {

    private static final String PROP_NAME = "street2";
    private static final String CONTENT_BASE = "/content/vignette/street2/";
    private static final String DOOR_PATCH_IMAGE_FILENAME = CONTENT_BASE + "door-right-wing.png";
    public static final Point2D PLAYER_START = new Point2D(0.50, 0.66);

    private static final double[] WALK_BOUNDARY = {
             0.05, 0.5,     0.41, 0.5,
             0.42, 0.44,    0.48, 0.44,
             0.5, 0.5,      0.68, 0.5,
             0.75, 0.63,    0.94, 0.63,
             0.94, 0.67,    0.86, 0.67,
             0.94, 0.9,     0.05, 0.9
    };

    private static final Patch pawnShopDoorPatch = new Patch(
            1140, 0, 500, 
            StreetVignette2.class.getResourceAsStream(DOOR_PATCH_IMAGE_FILENAME));
    
    private static final VignetteTrigger rightDoor = new VignetteTrigger(
            0.89, 0.60, // Location
            0.016, 0.083, // Size
            PawnShopVignette.PLAYER_START.getX(), 
            PawnShopVignette.PLAYER_START.getY(), 
            PoseSheet.Direction.TOWARD, // Player position and orientation at destination
            "PawnShopVignette" // Destination
    );
    private static final VignetteTrigger topDoor = new VignetteTrigger(
            0.42, 0.44,
            0.063, 0.014,
            ChatsuboBarVignette.PLAYER_START.getX(), 
            ChatsuboBarVignette.PLAYER_START.getY(), 
            PoseSheet.Direction.TOWARD,
            "ChatsuboBarVignette"
    );

    public StreetVignette2(GameState gs, VignetteTrigger prevPort, Player player) {
        super(gs, CONTENT_BASE,prevPort, player, WALK_BOUNDARY);
    }

    @Override
    public void loop() {
        // example: Test the gauges.
        //getPlayer().setMoney(getPlayer().getMoney()-1);
        //getPlayer().setHealth(getPlayer().getHealth()-1);
        //getPlayer().setConstitution(getPlayer().getConstitution()-1);                
    }

    @Override
    protected void init() {
        // set player position
        //setPlayerPosition(PLAYER_START);

        addPort(rightDoor); // Doors
        addPort(topDoor);

        addPatch(pawnShopDoorPatch); // Patch over pawn shop door.
        
        // example: Depth of field
        //fgGroup.setEffect(new BoxBlur(10, 10, 3));
    }

    @Override
    public String getPropName() {
        return PROP_NAME;
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
}
