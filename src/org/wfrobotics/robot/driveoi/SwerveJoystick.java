package org.wfrobotics.robot.driveoi;

import org.wfrobotics.Utilities;
import org.wfrobotics.Vector;
import org.wfrobotics.commands.Conveyor;
import org.wfrobotics.commands.Rev;
import org.wfrobotics.commands.Shoot;
import org.wfrobotics.commands.drive.DriveConfig;
import org.wfrobotics.controller.Panel;
import org.wfrobotics.controller.Xbox;
import org.wfrobotics.controller.Panel.BUTTON;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class SwerveJoystick {
    public class SwerveJoyStick implements SwerveOI
    {

        private static final double DEADBAND = 0.2;
        
        private final Joystick driver1;
        private final Xbox driver2;
        private final Panel panel;
        
        Button buttonDriveShift;
        Button buttonDriveVisionShoot;
        Button buttonDriveSmartShoot;
        Button buttonDriveDumbShoot;
        Button buttonDriveFieldRelative;
        Button buttonDriveSetGyro;
        
        public SwerveJoyStick(Joystick driver1, Xbox driver2, Panel panel)
        {
            this.driver1 = driver1;
            this.driver2 = driver2;
            this.panel = panel;

            buttonDriveSmartShoot = new JoystickButton(driver1, 6);
            buttonDriveDumbShoot = new JoystickButton(driver1, 7);
            buttonDriveVisionShoot= new JoystickButton(driver1, 11);
            buttonDriveFieldRelative= new JoystickButton(driver1, 8);
            buttonDriveSetGyro = new JoystickButton(driver1, 9);

            buttonDriveShift= new JoystickButton(driver1, 1);

            //buttonDriveVisionShoot.toggleWhenPressed(new VisionShoot());
            buttonDriveDumbShoot.whileHeld(new Rev(Rev.MODE.SHOOT));
            buttonDriveSmartShoot.whileHeld(new Shoot(Conveyor.MODE.CONTINUOUS));
            buttonDriveShift.whenPressed(new DriveConfig(DriveConfig.MODE.HIGH_GEAR));
            buttonDriveFieldRelative.whenPressed(new DriveConfig(DriveConfig.MODE.FIELD_RELATIVE));
            buttonDriveSetGyro.whenPressed(new DriveConfig(DriveConfig.MODE.GYRO_ZERO));
        }

        public double getHaloDrive_Rotation()
        {
            // TODO Auto-generated method stub

            double value = 0;
        
            value = driver2.getAxis(Xbox.AXIS.LEFT_X);
        
            if (Math.abs(value) < DEADBAND)
            {
                value = 0;
            }
            return value;
        }

        public Vector getHaloDrive_Velocity()
        {
            Vector value= new Vector(driver1.getX(), driver1.getY());
            
        
            if (value.getMag() < DEADBAND)
            {
                value.setMag(0);
            }
        
            return value;
            // TODO Auto-generated method stub
        }

        public double getAngleDrive_Heading()
        {
            double Angle;
            if (driver1.getDirectionDegrees() > 0.65)
            {
                Angle = driver1.getDirectionDegrees();
            return Utilities.wrapToRange(Angle + 90, -180, 180);
            }
            else
            {
            return 0;
            }
            // TODO Auto-generated method stub

        }

        @Override
        public double getAngleDrive_Rotation()
        {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public Vector getAngleDrive_Velocity()
        {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public double getCrawlSpeed()
        {
            return driver1.getZ();
        
        }

        @Override
        public int getDpad()
        {
            // TODO Auto-generated method stub
            return 0;
        }

        public double[] getPanelKnobs()
        {
            return new double[] {
                            panel.getTopDial(Hand.kLeft) * 180.0,
                            panel.getTopDial(Hand.kRight) * 180.0,
                            panel.getBottomDial(Hand.kLeft) * 180.0,
                            panel.getBottomDial(Hand.kRight) * 180.0, 
                    };
        }

        public boolean getPanelSave()
        {
            return panel.getButton(BUTTON.SWITCH_L) && panel.getButton(BUTTON.SWITCH_R);
        }

        public double getFusionDrive_Rotation()
        {
            return driver2.getX(Hand.kRight);
        }        
    }
}

