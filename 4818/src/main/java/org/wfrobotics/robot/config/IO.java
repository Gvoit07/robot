package org.wfrobotics.robot.config;

import org.wfrobotics.reuse.commands.SignalHumanPlayer;
import org.wfrobotics.reuse.config.AutoFactory;
import org.wfrobotics.reuse.config.ButtonFactory;
import org.wfrobotics.reuse.config.ButtonFactory.TRIGGER;
import org.wfrobotics.reuse.config.HerdJoystick;
import org.wfrobotics.reuse.config.EnhancedIO;
import org.wfrobotics.reuse.config.Xbox;
import org.wfrobotics.robot.commands.climb.Hug;
import org.wfrobotics.robot.commands.elevator.ElevatorShift;
import org.wfrobotics.robot.commands.intake.PopHatch;
import org.wfrobotics.robot.commands.system.SystemToHigh;
import org.wfrobotics.robot.commands.system.SystemToLow;
import org.wfrobotics.robot.commands.system.SystemToMiddle;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;

/** Maps controllers to Commands **/
public final class IO implements EnhancedIO
{
    private static IO instance = null;
    private final HerdJoystick driverThrottle;  // TODO Refactor - Make Button from JoyStick instead?
    private final Joystick driverTurn;
    private final Xbox operator;

    /** Create and configure controls for Drive Team */
    private IO()
    {
        driverThrottle = new HerdJoystick(0);
        driverTurn = new Joystick(1);
        operator = new Xbox(2);
    }

    /** Configure each Button to run a Command */
    public void assignButtons()
    {
        // ---------------------- Autonomous ----------------------
        ButtonFactory.makeButton(driverThrottle, HerdJoystick.BUTTON.BASE_TOP_RIGHT, TRIGGER.WHEN_PRESSED, AutoFactory.getInstance().makeCommand(Auto.modes));
        ButtonFactory.makeButton(driverThrottle, HerdJoystick.BUTTON.BASE_MIDDLE_RIGHT, TRIGGER.WHEN_PRESSED, AutoFactory.getInstance().makeCommand(Auto.delays));
        ButtonFactory.makeButton(driverThrottle, HerdJoystick.BUTTON.BASE_BOTTOM_RIGHT, TRIGGER.WHEN_PRESSED, AutoFactory.getInstance().makeCommand(Auto.positions));

        //----------------------- Climber -------------------------
        ButtonFactory.makeButton(operator, Xbox.DPAD.RIGHT, TRIGGER.WHEN_PRESSED, new Hug(true));
        ButtonFactory.makeButton(operator, Xbox.DPAD.LEFT, TRIGGER.WHEN_PRESSED, new Hug(false));

        //----------------------- Driver --------------------------
        //        ButtonFactory.makeButton(driverThrottle, HerdJoystick.BUTTON.THUMB_TOP_RIGHT, TRIGGER.WHILE_HELD, new DriveToTarget());

        //----------------------- Elevator ------------------------
        ButtonFactory.makeButton(operator, Xbox.BUTTON.LB, TRIGGER.WHEN_PRESSED, new ElevatorShift(true));
        ButtonFactory.makeButton(operator, Xbox.BUTTON.RB, TRIGGER.WHEN_PRESSED, new ElevatorShift(false));

        //----------------------- Intake --------------------------
        ButtonFactory.makeButton(operator, Xbox.BUTTON.X, TRIGGER.WHEN_PRESSED, new PopHatch());


        //----------------------- System --------------------------
        ButtonFactory.makeButton(operator, Xbox.BUTTON.Y, TRIGGER.WHEN_PRESSED, new SystemToHigh());
        ButtonFactory.makeButton(operator, Xbox.BUTTON.B, TRIGGER.WHEN_PRESSED, new SystemToMiddle());
        ButtonFactory.makeButton(operator, Xbox.BUTTON.A, TRIGGER.WHEN_PRESSED, new SystemToLow());
        ButtonFactory.makeButton(operator, Xbox.BUTTON.START, TRIGGER.WHILE_HELD, new SignalHumanPlayer());

        //----------------------- Wrist ---------------------------

        //----------------------- Testing -------------------------
        //        ButtonFactory.makeButton(operator, Xbox.BUTTON.X, TRIGGER.WHEN_PRESSED, new TurnToTarget(10));
    }

    // ------------------- Robot-specific --------------------
    public double getCargoStick()
    {
    	return -operator.getX(Hand.kLeft);
    }
    public double getElevatorStick()
    {
        return -operator.getY(Hand.kRight);
    }

    public double getIntakeStick()
    {
        return operator.getX(Hand.kRight);
    }

    public double getLinkDown()
    {
        return operator.getTrigger(Hand.kLeft);
    }

    public double getLinkUp()
    {
        return operator.getTrigger(Hand.kRight);
    }

    public double getWristStick()
    {
        return -operator.getY(Hand.kLeft);
    }

    public boolean isElevatorOverrideRequested()
    {
        return  Math.abs(getElevatorStick()) > .15;
    }

    // ------------------------ Reuse ------------------------

    public static IO getInstance()
    {
        if (instance == null)
        {
            instance = new IO();
        }
        return instance;
    }

    public double getThrottle()
    {
        return -driverThrottle.getY();
    }

    public double getTurn()
    {
        return -driverTurn.getRawAxis(0);
    }

    public boolean getDriveQuickTurn()
    {
        return Math.abs(getThrottle()) < 0.1;
    }

    public boolean isDriveOverrideRequested()
    {
        return Math.abs(getThrottle()) > 0.15 || Math.abs(getTurn()) > 0.15;
    }

    public void setRumble(boolean rumble)
    {
        float state = (rumble) ? 1 : 0;
        operator.setRumble(Hand.kLeft, state);
        operator.setRumble(Hand.kRight, state);
    }
}