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
package com.maehem.chibacityblues.content.things.ram;

import com.maehem.abyss.engine.RamThing;

/**
 *
 * @author mark
 */
public class ColdFireRamThing extends RamThing {
    public static final String NAME = "Cold Fire UltraBitz 256 RAM Module";

    public ColdFireRamThing() {
        super(NAME);
        setCapacity(256);
        setCondition(1000);
    }

    @Override
    public String getDescription() {
        return "The coolest RAM burns with a blue light. Cold fire will " +
                "light the way to the hardest to crack sites on the Matrix.";
    }

    
}
