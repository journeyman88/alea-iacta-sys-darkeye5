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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.roll.LocalizedResult;

/**
 *
 * @author journeyman
 */
public class DarkEye5Results extends LocalizedResult
{
    
    private static final String BUNDLE_NAME = "net.unknowndomain.alea.systems.darkeye5.RpgSystemBundle";
    
    private final List<SingleResult<Integer>> results;
    private boolean success;
    private boolean critical;
    private boolean spectacular;
    private Integer qualityLevel = null;
    
    public DarkEye5Results(SingleResult<Integer> ... results)
    {
        this(Arrays.asList(results));
    }
    
    public DarkEye5Results(List<SingleResult<Integer>> results)
    {
        List<SingleResult<Integer>> tmp = new ArrayList<>(results.size());
        tmp.addAll(results);
        this.results = Collections.unmodifiableList(tmp);
    }

    public List<SingleResult<Integer>> getResults()
    {
        return results;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public boolean isCritical()
    {
        return critical;
    }

    public void setCritical(boolean critical)
    {
        this.critical = critical;
    }

    public boolean isSpectacular()
    {
        return spectacular;
    }

    public void setSpectacular(boolean spectacular)
    {
        this.spectacular = spectacular;
    }

    public Integer getQualityLevel()
    {
        return qualityLevel;
    }

    public void setQualityLevel(Integer qualityLevel)
    {
        this.qualityLevel = qualityLevel;
    }

    @Override
    protected void formatResults(MsgBuilder messageBuilder, boolean verbose, int indentValue)
    {
        String indent = getIndent(indentValue);
        messageBuilder.append(indent).append(translate("darkeye5.results.outcome"));
        if (isSpectacular() && isSuccess())
        {
            messageBuilder.append(translate("darkeye5.results.spectacularSuccess"));
        }
        else if (isSpectacular())
        {
            messageBuilder.append(translate("darkeye5.results.spectacularFailure"));
        }
        else if (isCritical() && isSuccess())
        {
            messageBuilder.append(translate("darkeye5.results.criticalSuccess"));
        } 
        else if (isCritical())
        {
            messageBuilder.append(translate("darkeye5.results.criticalFailure"));
        }
        else if (isSuccess())
        {
            messageBuilder.append(translate("darkeye5.results.success"));
        }
        else
        {
            messageBuilder.append(translate("darkeye5.results.failure"));
        }
        if (getQualityLevel() != null)
        {
            messageBuilder.append(translate("darkeye5.results.qualityLevel", getQualityLevel()));
        }
        messageBuilder.appendNewLine();
        if (verbose)
        {
            messageBuilder.append(indent).append("Roll ID: ").append(getUuid()).appendNewLine();
            messageBuilder.append(indent).append(translate("darkeye5.results.diceResults")).append(" [ ");
            for (SingleResult<Integer> t : getResults())
            {
                messageBuilder.append("( ").append(t.getLabel()).append(" => ");
                messageBuilder.append(t.getValue()).append(") ");
            }
            messageBuilder.append("]").appendNewLine();
        }
    }

    @Override
    protected String getBundleName()
    {
        return BUNDLE_NAME;
    }
}
