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
public class BackOffice3SoftwareThing extends SoftwareThing {
    public static final String NAME = "Back Office 3.1.1 (business up front)";
    private static final int ATTACK_DAMAGE = 156;

    public BackOffice3SoftwareThing() {
        super(NAME);
        
        setRepairSkill(2);
    }

    @Override
    public int getAttackDamage() {
        return ATTACK_DAMAGE;
    }
    
    @Override
    public String getIconPath() {
        return "/content/things/software/back-office_3.png";
    }

    @Override
    public String getDescription() {
        return "Back Office 3.3.1 brings your favorite productity app to the " +
                "year 2047.  Business up front, party in the rear.";
    }
    
    
}
