package org.wfrobotics.robot.commands;

import org.wfrobotics.reuse.commands.vision.VisionModeSet;
import org.wfrobotics.robot.config.VisionMode;

public class VisionModeDefault extends VisionModeSet
{
    public VisionMode getMode()
    {
        return VisionMode.robotDefault();
    }
}
