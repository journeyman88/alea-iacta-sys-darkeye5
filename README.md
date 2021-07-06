# alea-iacta-sys-darkeye5
A RPG system module for Alea Iacta Est implementing The Dark Eye - 5th Edition

## Description
This command implements the dice rule system of The Dark Eye and can work in two modes: Attribute mode and Skill mode.

### Attribute Mode
This mode will roll 1d20 and check it against the attribute, it will also auto-roll confirmation in case of possible critical success or critical failure.

### Skill Mode
This mode will attempt a skill check, rolling 1d20 for each attribute, automatically checking for Success, spending skill points to transform Failures into successes, checking for Critical Success ( 1, 1, n ), Spectacular Success ( 1, 1, 1 ) or the respective failures; then communicate the result and the achieved QL for the check.

### Roll modifiers
Passing these parameters, the associated modifier will be enabled:

* `-v` : Will enable a more verbose mode that will show a detailed version of every result obtained in the roll.

## Help print
```
The Dark Eye 5th Edition [ the-dark-eye-5th | da5 ]

Usage: da5 -a <attributeValue>
   or: da5 -sp <skillPoints> -s1 <firstAttribute> -s2 <secondAttribute> -s3
<thirdAttribute>

Description:
This command implements the dice rule system of The Dark Eye
and can work in two modes: Attribute mode and Skill mode.

Options:
  -a, --attribute=attributeValue
                  Attribute check
      -sp, --skill-points=skillPoints
                  Skill points for skill check
      -s1, --skill-attribute1=firstAttribute
                  First attribute for a skill check
      -s2, --skill-attribute2=secondAttribute
                  Second attribute for a skill check
      -s3, --skill-attribute3=thirdAttribute
                  Third attribute for a skill check
  -h, --help      Print the command help
  -v, --verbose   Enable verbose output
```
