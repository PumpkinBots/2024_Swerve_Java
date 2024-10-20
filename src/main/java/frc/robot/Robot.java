// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  public static final String kAutoShootAndLeave = "kAutoShootAndLeave";
  public static final String kAutoShoot = "kAutoShoot";
  public static final String kAutoLeave = "kAutoLeave";
  public static final String kAutoStay = "kAutoStay";
  public String m_autoSelected;
  public final SendableChooser<String> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    CameraServer.startAutomaticCapture().setResolution(480, 320);

    m_chooser.setDefaultOption("Stay", kAutoStay);
    m_chooser.addOption("Shoot", kAutoShoot);
    m_chooser.addOption("Leave", kAutoLeave);
    m_chooser.addOption("Shoot and Leave", kAutoShootAndLeave);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  @Override
  public void robotPeriodic() {
    m_autoSelected = m_chooser.getSelected();
    m_robotContainer.maybeUpdateControllerBindings();
    CommandScheduler.getInstance().run(); 
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    switch (m_autoSelected) {
      case kAutoShootAndLeave:
        m_autonomousCommand = m_robotContainer.getAutonomousShootAndLeaveCommand();
      
        break;
      case kAutoShoot:
        m_autonomousCommand = m_robotContainer.getAutonomousShootCommand();
      
        break;
      case kAutoLeave:
        m_autonomousCommand = m_robotContainer.getAutonomousLeaveCommand();

        break;
      default:
        m_autonomousCommand = null;

        break;
    }

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}
}
