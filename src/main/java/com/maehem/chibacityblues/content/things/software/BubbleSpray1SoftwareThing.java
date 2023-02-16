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
package com.maehem.chibacityblues.content.things.software;

import com.maehem.abyss.engine.SoftwareThing;

/**
 *
 * @author mark
 */
public class BubbleSpray1SoftwareThing extends SoftwareThing {
    public static final String NAME = "BubbleSpray 1.2.8 (cleans as it cleans)";
    private static final int ATTACK_DAMAGE = 122;

    public BubbleSpray1SoftwareThing() {
        super(NAME);
        
        setRepairSkill(2);
    }

    @Override
    public int getAttackDamage() {
        return ATTACK_DAMAGE;
    }
    
    @Override
    public String getIconPath() {
        return "/content/things/software/bubble-spray_1.png";
    }

    @Override
    public String getDescription() {
        return "BubbleSpray cleans those dirty sites to a completely unsecure " +
                "luster.  Waltz right in without soiling your new spats.  May " +
                "contain chemicals known in the state of Alaska to cause uncontrolable " +
                "urges to dance in the streets.";
    }
        
}
