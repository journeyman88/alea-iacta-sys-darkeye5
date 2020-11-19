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
import net.unknowndomain.alea.dice.D20;
import org.javacord.api.entity.message.MessageBuilder;

/**
 *
 * @author journeyman
 */
public class DarkEye5AttrRoll extends DarkEye5Roll
{
    
    private final Integer attribute;
    
    public DarkEye5AttrRoll(Integer attribute, Modifiers ... mod)
    {
        this(attribute, Arrays.asList(mod));
    }
    
    public DarkEye5AttrRoll(Integer attribute, Collection<Modifiers> mod)
    {
        super(mod);
        this.attribute = attribute;
    }
    
    @Override
    public MessageBuilder getResult()
    {
        DarkEye5Results results = buildResults(D20.INSTANCE.roll());
        return formatResults(results);
    }
    
    private DarkEye5Results buildResults(Integer res)
    {
        DarkEye5Results results;
        int tmp = D20.INSTANCE.roll();
        switch (res)
        {
            case 1:
                results = new DarkEye5Results(res, tmp);
                results.setSuccess(true);
                if (tmp <= attribute)
                {
                    results.setCritical(true);
                }
                break;
                
            case 20:
                results = new DarkEye5Results(res, tmp);
                results.setSuccess(false);
                if ((tmp > attribute) || (tmp >= 20))
                {
                    results.setCritical(true);
                }
                break;
                    
            default:
                results = new DarkEye5Results(res);
                results.setSuccess(res <= attribute);
                break;
        }
        
        return results;
    }
}
