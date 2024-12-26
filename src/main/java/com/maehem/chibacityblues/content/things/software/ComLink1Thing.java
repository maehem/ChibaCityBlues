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
public class ComLink1Thing extends SoftwareThing {
    public static final String NAME = "ComLink 1.0";

    public ComLink1Thing() {
        super(NAME);
    }

    @Override
    public int getAttackDamage() {
        return -1; // Not for battle.
    }

    @Override
    public double getRecoveryTime() {
        return 0.0;
    }

    @Override
    public String getIconPath() {
        return "/content/things/software/comlink-1.png";
    }

    @Override
    public String getDescription() {
        return "ComLink version 1.0.  Get onto the Matrix now! 50 hours free!"
                + "Just kidding.  Get ready to pay.";
    }


}
