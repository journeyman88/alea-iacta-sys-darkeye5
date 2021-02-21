/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.unknowndomain.alea.systems.darkeye5;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.unknowndomain.alea.systems.RpgSystemOptions;
import net.unknowndomain.alea.systems.annotations.RpgSystemData;
import net.unknowndomain.alea.systems.annotations.RpgSystemOption;


/**
 *
 * @author journeyman
 */
@RpgSystemData(bundleName = "net.unknowndomain.alea.systems.darkeye5.RpgSystemBundle")
public class DarkEye5Options extends RpgSystemOptions
{
    @RpgSystemOption(name = "attribute", shortcode = "a", description = "darkeye5.options.attribute", argName = "attributeValue")
    private Integer attribute;
    @RpgSystemOption(name = "skill-points", shortcode = "sp", description = "darkeye5.options.skill-points", argName = "skillPoints")
    private Integer skillPoints;
    @RpgSystemOption(name = "skill-attribute1", shortcode = "s1", description = "darkeye5.options.skill-attribute1", argName = "firstAttribute")
    private Integer skillAttribute1;
    @RpgSystemOption(name = "skill-attribute2", shortcode = "s2", description = "darkeye5.options.skill-attribute2", argName = "secondAttribute")
    private Integer skillAttribute2;
    @RpgSystemOption(name = "skill-attribute3", shortcode = "s3", description = "darkeye5.options.skill-attribute3", argName = "thirdAttribute")
    private Integer skillAttribute3;
    
    @Override
    public boolean isValid()
    {
        return !(isHelp() || 
                (attribute != null && skillPoints != null) || 
                (skillPoints != null ^ skillAttribute1 != null) ||
                (skillPoints != null ^ skillAttribute2 != null) ||
                (skillPoints != null ^ skillAttribute3 != null)
                );
    }
    
    public boolean isAttributeMode()
    {
        return attribute != null;
    }
    
    public boolean isSkillMode()
    {
        return skillPoints != null;
    }

    public Integer getAttribute()
    {
        return attribute;
    }

    public Integer getSkillPoints()
    {
        return skillPoints;
    }

    public Integer getSkillAttribute1()
    {
        return skillAttribute1;
    }

    public Integer getSkillAttribute2()
    {
        return skillAttribute2;
    }

    public Integer getSkillAttribute3()
    {
        return skillAttribute3;
    }

    public Collection<DarkEye5Modifiers> getModifiers()
    {
        Set<DarkEye5Modifiers> mods = new HashSet<>();
        if (isVerbose())
        {
            mods.add(DarkEye5Modifiers.VERBOSE);
        }
        return mods;
    }
    
}