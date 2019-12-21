package org.wfrobotics.robot.commands;

import org.wfrobotics.reuse.subsystems.drive.TankSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetBrake extends Command
{
    //    private final LiftSubsystem lift = LiftSubsystem.getInstance();
    private boolean enable;

    public SetBrake(boolean value)
    {
        enable = value;
        //requires(sp);
    }

    protected void initialize()
    {
        TankSubsystem.getInstance().setBrake(enable);
        SmartDashboard.putBoolean("Drive Brake", enable);
    }


    protected boolean isFinished()
    {
        return true;
    }
}
