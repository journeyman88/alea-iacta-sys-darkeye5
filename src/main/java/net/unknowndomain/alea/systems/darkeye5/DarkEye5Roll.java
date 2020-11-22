/*
 * Copyright 2020 Marco Bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems.darkeye5;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.messages.ReturnMsg;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public abstract class DarkEye5Roll implements GenericRoll
{
    
    public enum Modifiers
    {
        VERBOSE
    }
    
    private final Set<Modifiers> mods;
    
    public DarkEye5Roll(Modifiers ... mod)
    {
        this(Arrays.asList(mod));
    }
    
    public DarkEye5Roll(Collection<Modifiers> mod)
    {
        this.mods = new HashSet<>();
        if (mod != null)
        {
            this.mods.addAll(mod);
        }
    }
    
    protected ReturnMsg formatResults(DarkEye5Results results)
    {
        MsgBuilder mb = new MsgBuilder();
        mb.append("Outcome: ");
        if (results.isSpectacular())
        {
            mb.append("Spectacular ");
        }
        else if (results.isCritical())
        {
            mb.append("Critical ");
        }
        if (results.isSuccess())
        {
            mb.append("Success");
        }
        else
        {
            mb.append("Failure");
        }
        if (results.getQualityLevel() != null)
        {
            mb.append(" [ QL : ").append(results.getQualityLevel()).append(" ]");
        }
        mb.appendNewLine();
        if (mods.contains(Modifiers.VERBOSE))
        {
            mb.append("Results: ").append(" [ ");
            for (Integer t : results.getResults())
            {
                mb.append(t).append(" ");
            }
            mb.append("]").appendNewLine();
        }
        return mb.build();
    }
}