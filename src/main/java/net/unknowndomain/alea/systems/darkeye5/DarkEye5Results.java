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

/**
 *
 * @author journeyman
 */
public class DarkEye5Results
{
    private final List<Integer> results;
    private boolean success;
    private boolean critical;
    private boolean spectacular;
    private Integer qualityLevel = null;
    
    public DarkEye5Results(Integer ... results)
    {
        this(Arrays.asList(results));
    }
    
    public DarkEye5Results(List<Integer> results)
    {
        List<Integer> tmp = new ArrayList<>(results.size());
        tmp.addAll(results);
        this.results = Collections.unmodifiableList(tmp);
    }

    public List<Integer> getResults()
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
}
