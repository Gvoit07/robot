
package com.taurus.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.taurus.Utilities;
import com.taurus.robot.OI;
import com.taurus.robot.Robot;

public class DriveArcadeWithXbox extends Command {
    public DriveArcadeWithXbox() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.rockerDriveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Utilities.PrintCommand("Drive", this);
        Robot.rockerDriveSubsystem.arcadeDrive(OI.getThrottle(), OI.getTurn(), OI.getTractionControl());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;  // Always run this command because it will be default command of the subsystem.
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
