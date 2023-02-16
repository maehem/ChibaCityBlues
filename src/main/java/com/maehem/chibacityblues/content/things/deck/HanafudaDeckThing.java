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
package com.maehem.chibacityblues.content.things.deck;

import static com.maehem.abyss.Engine.LOGGER;
import com.maehem.abyss.engine.DeckThing;
import com.maehem.chibacityblues.ChibaCityBlues;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;

/**
 *
 * @author Mark J Koch [flatlinejack at maehem dot com]
 */
public class HanafudaDeckThing extends DeckThing {
    public static final String DEFAULT_NAME = "Hanafuda Sheika Slate Model 1";
    public static final Color COLOR = Color.CORNFLOWERBLUE;
    public static final int BASE_RAM = 256;
    public static final int BASE_SHIELD = 270;
    public static final int SOFTWARE_CAPACITY = 12;
    public static final int RAM_SLOTS = 8;
    public static final int REPAIR_SKILL_MIN = 10;
    
    public static final String ICON_PATH = "/content/things/deck/NES-thing.png";
    
    public HanafudaDeckThing() {
        super(  DEFAULT_NAME,
                BASE_RAM, BASE_SHIELD, 
                SOFTWARE_CAPACITY, RAM_SLOTS
        );
        setRepairSkill(REPAIR_SKILL_MIN);
        setColor(COLOR);
    }

    @Override
    public String getIconPath() {
        return ICON_PATH;
    }

    @Override
    public String getDescription() {
        return "This limited edition hardware has a few surpises in it. " +
                "More RAM than the average matrix deck and an always on light " +
                "that says 'Turbo'";
    }
    
    @Override
    public Class<?> getContentClass(String itemClass) {
        try {
            return Class.forName(ChibaCityBlues.class.getPackageName() + itemClass);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Class not found: " + itemClass, ex);
        }
        return null;
    }
    
    
}
