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
package com.maehem.chibacityblues.content.things.skillchip;

import com.maehem.abyss.engine.SkillChipThing;
import javafx.scene.paint.Color;

/**
 * Improves the ability to ask questions and get better answers sooner.
 * 
 * @author mark
 */
public class NegotiationSkillChip extends SkillChipThing {
    public static final String NAME = "Negotiation";
    public static final Color  COLOR = Color.PAPAYAWHIP;
    public static final String DESCRIPTION = 
            "Increase your chances of getting a good deal on that new " +
            "peice of gear you've been wanting. Go ahead and try it out!";

    public NegotiationSkillChip() {
        super(NAME);
        setColor(COLOR);
        addBuff(Buff.NEGOTIATE, 100);
    }
    
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
    
}
