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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.unknowndomain.alea.dice.D20;
import net.unknowndomain.alea.messages.ReturnMsg;

/**
 *
 * @author journeyman
 */
public class DarkEye5SkillRoll extends DarkEye5Roll
{
    
    private final Integer skillLevel;
    private final List<Integer> skillAttributes;
    
    public DarkEye5SkillRoll(Integer skillLevel, Integer skillAttribute1, Integer skillAttribute2, Integer skillAttribute3, Modifiers ... mod)
    {
        this(skillLevel, skillAttribute1, skillAttribute2, skillAttribute3, Arrays.asList(mod));
    }
    
    public DarkEye5SkillRoll(Integer skillLevel, Integer skillAttribute1, Integer skillAttribute2, Integer skillAttribute3, Collection<Modifiers> mod)
    {
        super(mod);
        this.skillLevel = skillLevel;
        this.skillAttributes = new ArrayList<>(3);
        this.skillAttributes.add(skillAttribute1);
        this.skillAttributes.add(skillAttribute2);
        this.skillAttributes.add(skillAttribute3);
    }
    
    @Override
    public ReturnMsg getResult()
    {
        DarkEye5Results results = buildResults(D20.INSTANCE.roll(), D20.INSTANCE.roll(), D20.INSTANCE.roll());
        return formatResults(results);
    }
    
    private DarkEye5Results buildResults(Integer ... res)
    {
        DarkEye5Results results = new DarkEye5Results(res);
        int oneCount = 0, twentyCount = 0;
        int sl = skillLevel;
        boolean succ = true;
        int diff = 0;
        for(int i = 0; i < results.getResults().size(); i++)
        {
            int check = results.getResults().get(i);
            int attr = skillAttributes.get(i);
            switch (check)
            {
                case 1:
                    oneCount++;
                    break;
                case 20:
                    twentyCount++;
                    succ = false;
                    diff += ((check - attr) > 0) ? (check - attr) : 0;
                    break;
                default:
                    int tmp = check - attr;
                    diff += (tmp > 0) ? tmp : 0;
                    succ = succ && tmp <=0;
                    break;
            }
        }
        results.setCritical((oneCount >= 2) || (twentyCount >= 2));
        results.setSpectacular((oneCount == 3) || (twentyCount == 3));
        if ( results.isCritical() || succ )
        {
            results.setSuccess(twentyCount < 2);
        }
        else
        {
            results.setSuccess(diff <= sl);
            if (results.isSuccess())
            {
                sl -= diff;
            }
        }
        int ql = 0;
        if (results.isSuccess())
        {
            ql = (new BigDecimal(sl)).divide(new BigDecimal(3), 0, RoundingMode.CEILING).intValue();
            if (ql == 0)
            {
                ql = 1;
            }
        }
        results.setQualityLevel(ql);
        return results;
    }
}
