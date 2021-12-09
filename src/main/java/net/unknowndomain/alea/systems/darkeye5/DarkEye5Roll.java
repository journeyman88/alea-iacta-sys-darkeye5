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
import java.util.Locale;
import java.util.Set;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public abstract class DarkEye5Roll implements GenericRoll
{
    private final Locale lang;
    protected final Set<DarkEye5Modifiers> mods;
    
    public DarkEye5Roll(Locale lang, DarkEye5Modifiers ... mod)
    {
        this(lang, Arrays.asList(mod));
    }
    
    public DarkEye5Roll(Locale lang, Collection<DarkEye5Modifiers> mod)
    {
        this.mods = new HashSet<>();
        if (mod != null)
        {
            this.mods.addAll(mod);
        }
        this.lang = lang;
    }
    
    protected void setResultFlags(DarkEye5Results results)
    {
        results.setVerbose(mods.contains(DarkEye5Modifiers.VERBOSE));
        results.setLang(lang);
    }
}
