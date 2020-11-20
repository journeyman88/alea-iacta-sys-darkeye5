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

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import net.unknowndomain.alea.command.HelpWrapper;
import net.unknowndomain.alea.systems.RpgSystemCommand;
import net.unknowndomain.alea.systems.RpgSystemDescriptor;
import net.unknowndomain.alea.roll.GenericRoll;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.javacord.api.entity.message.MessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author journeyman
 */
public class DarkEye5Command extends RpgSystemCommand
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DarkEye5Command.class);
    private static final RpgSystemDescriptor DESC = new RpgSystemDescriptor("The Dark Eye 5th Edition", "da5", "the-dark-eye-5th");
    
    private static final String ATTR_PARAM = "attribute";
    private static final String SKILL_PARAM = "skill-points";
    private static final String SKILL_ATTR1_PARAM = "skill-attribute1";
    private static final String SKILL_ATTR2_PARAM = "skill-attribute2";
    private static final String SKILL_ATTR3_PARAM = "skill-attribute3";
    
    private static final Options CMD_OPTIONS;
    
    static {
        CMD_OPTIONS = new Options();
        OptionGroup rollMode = new OptionGroup();
        CMD_OPTIONS.addOption(
                Option.builder("a")
                        .longOpt(ATTR_PARAM)
                        .desc("Attribute check")
                        .hasArg()
                        .argName("attributeValue")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("sp")
                        .longOpt(SKILL_PARAM)
                        .desc("Skill points for skill check")
                        .hasArg()
                        .argName("skillPoints")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("s1")
                        .longOpt(SKILL_ATTR1_PARAM)
                        .desc("First attribute for a skill check")
                        .hasArg()
                        .argName("firstAttribute")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("s2")
                        .longOpt(SKILL_ATTR2_PARAM)
                        .desc("Second attribute for a skill check")
                        .hasArg()
                        .argName("secondAttribute")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("s3")
                        .longOpt(SKILL_ATTR3_PARAM)
                        .desc("Third attribute for a skill check")
                        .hasArg()
                        .argName("thirdAttribute")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("h")
                        .longOpt( CMD_HELP )
                        .desc( "Print help")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("v")
                        .longOpt(CMD_VERBOSE)
                        .desc("Enable verbose output")
                        .build()
        );
        
    }
    
    public DarkEye5Command()
    {
        
    }
    
    @Override
    public RpgSystemDescriptor getCommandDesc()
    {
        return DESC;
    }
    
    @Override
    public MessageBuilder execCommand(String cmdLine)
    {
        MessageBuilder retVal = new MessageBuilder();
        Matcher prefixMatcher = PREFIX.matcher(cmdLine);
        if (prefixMatcher.matches())
        {
            String params = prefixMatcher.group(CMD_PARAMS);
            if (params == null || params.isEmpty())
            {
                return HelpWrapper.printHelp(prefixMatcher.group(CMD_NAME), CMD_OPTIONS, true);
            }
            LOGGER.debug(cmdLine);
            try
            {
                CommandLineParser parser = new DefaultParser();
                CommandLine cmd = parser.parse(CMD_OPTIONS, params.split(" "));
                
                if (cmd.hasOption(CMD_HELP) || 
                        (cmd.hasOption(ATTR_PARAM) && cmd.hasOption(SKILL_PARAM)) ||
                        (cmd.hasOption(SKILL_PARAM) ^ cmd.hasOption(SKILL_ATTR1_PARAM)) ||
                        (cmd.hasOption(SKILL_PARAM) ^ cmd.hasOption(SKILL_ATTR2_PARAM)) ||
                        (cmd.hasOption(SKILL_PARAM) ^ cmd.hasOption(SKILL_ATTR3_PARAM))
                        )
                {
                    return HelpWrapper.printHelp(prefixMatcher.group(CMD_NAME), CMD_OPTIONS, true);
                }
                
                
                Set<DarkEye5AttrRoll.Modifiers> mods = new HashSet<>();
                
                String p;
                String t;
                if (cmd.hasOption(CMD_VERBOSE))
                {
                    mods.add(DarkEye5Roll.Modifiers.VERBOSE);
                }
                GenericRoll roll;
                if (cmd.hasOption(ATTR_PARAM))
                {
                    String a = cmd.getOptionValue(ATTR_PARAM);
                    roll = new DarkEye5AttrRoll(Integer.parseInt(a), mods);
                }
                else
                {
                    String sL = cmd.getOptionValue(SKILL_PARAM);
                    String s1 = cmd.getOptionValue(SKILL_ATTR1_PARAM);
                    String s2 = cmd.getOptionValue(SKILL_ATTR2_PARAM);
                    String s3 = cmd.getOptionValue(SKILL_ATTR3_PARAM);
                    roll = new DarkEye5SkillRoll(Integer.parseInt(sL), Integer.parseInt(s1), Integer.parseInt(s2), Integer.parseInt(s3), mods);
                }
                retVal = roll.getResult();
            } 
            catch (ParseException | NumberFormatException ex)
            {
                retVal = HelpWrapper.printHelp(prefixMatcher.group(CMD_NAME), CMD_OPTIONS, true);
            }
        }
        return retVal;
    }
    
}
