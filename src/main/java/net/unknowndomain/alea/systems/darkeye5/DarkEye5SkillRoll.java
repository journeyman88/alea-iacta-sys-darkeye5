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
import java.util.Locale;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.random.dice.bag.D20;
import net.unknowndomain.alea.roll.GenericResult;

/**
 *
 * @author journeyman
 */
public class DarkEye5SkillRoll extends DarkEye5Roll
{
    
    private final Integer skillLevel;
    private final List<Integer> skillAttributes;
    
    public DarkEye5SkillRoll(Locale lang, Integer skillLevel, Integer skillAttribute1, Integer skillAttribute2, Integer skillAttribute3, DarkEye5Modifiers ... mod)
    {
        this(lang, skillLevel, skillAttribute1, skillAttribute2, skillAttribute3, Arrays.asList(mod));
    }
    
    public DarkEye5SkillRoll(Locale lang, Integer skillLevel, Integer skillAttribute1, Integer skillAttribute2, Integer skillAttribute3, Collection<DarkEye5Modifiers> mod)
    {
        super(lang, mod);
        this.skillLevel = skillLevel;
        this.skillAttributes = new ArrayList<>(3);
        this.skillAttributes.add(skillAttribute1);
        this.skillAttributes.add(skillAttribute2);
        this.skillAttributes.add(skillAttribute3);
    }
    
    @Override
    public GenericResult getResult()
    {
        DarkEye5Results results = buildResults(D20.INSTANCE.nextResult().get(), D20.INSTANCE.nextResult().get(), D20.INSTANCE.nextResult().get());
        setResultFlags(results);
        return results;
    }
    
    private DarkEye5Results buildResults(SingleResult<Integer> ... res)
    {
        DarkEye5Results results = new DarkEye5Results(res);
        int oneCount = 0, twentyCount = 0;
        int sl = skillLevel;
        boolean succ = true;
        int diff = 0;
        for(int i = 0; i < results.getResults().size(); i++)
        {
            SingleResult<Integer> check = results.getResults().get(i);
            int attr = skillAttributes.get(i);
            switch (check.getValue())
            {
                case 1:
                    oneCount++;
                    break;
                case 20:
                    twentyCount++;
                    succ = false;
                    diff += ((check.getValue() - attr) > 0) ? (check.getValue() - attr) : 0;
                    break;
                default:
                    int tmp = check.getValue() - attr;
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
