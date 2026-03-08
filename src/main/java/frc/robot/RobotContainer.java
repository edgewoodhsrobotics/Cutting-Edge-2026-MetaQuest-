// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.BackIntake;
import frc.robot.commands.FrontIntake;
import frc.robot.subsystems.BackIntakeSubsystem;
import frc.robot.commands.Drive;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.FrontIntakeSubsystem;
import frc.robot.subsystems.BackIntakeWheelSubsystem;
import frc.robot.commands.BackIntakeWheel;
import frc.robot.commands.Climber;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.commands.BackIntakeWheelRPM;


public class RobotContainer {
  private final DrivetrainSubsystem myDriveTrainSubsystem;
  private final CommandXboxController controller;
  private final FrontIntakeSubsystem myFrontIntakeSubsystem;
  private final BackIntakeSubsystem myBackIntakeSubsystem;
  private final BackIntakeWheelSubsystem myBackIntakeWheelSubsystem;
  private final ClimberSubsystem myClimberSubsystem;
  private final SendableChooser<Command> chooser = new SendableChooser<>();

  public RobotContainer() throws IOException{

    myDriveTrainSubsystem = new DrivetrainSubsystem();
    myFrontIntakeSubsystem = new FrontIntakeSubsystem();
    myBackIntakeSubsystem = new BackIntakeSubsystem();
    myBackIntakeWheelSubsystem = new BackIntakeWheelSubsystem();
    myClimberSubsystem = new ClimberSubsystem();
    controller = new CommandXboxController(0);
    myDriveTrainSubsystem.setDefaultCommand(new Drive(myDriveTrainSubsystem, () -> -controller.getLeftY()*4, () -> -controller.getLeftX()*4, () -> -controller.getRightX()*2*Math.PI));


    NamedCommands.registerCommand("ClimberUp", new Climber(myClimberSubsystem, 1).withTimeout(1));
    NamedCommands.registerCommand("ClimberDown", new Climber(myClimberSubsystem, -1));
    NamedCommands.registerCommand("Shooter", new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, -1), new FrontIntake(myFrontIntakeSubsystem, 0.5), new BackIntakeWheelRPM(myBackIntakeWheelSubsystem, -4000)).withTimeout(5));
    NamedCommands.registerCommand("Flywheel", new BackIntakeWheelRPM( myBackIntakeWheelSubsystem, -4000));
    NamedCommands.registerCommand("Kicker", new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, -1), new FrontIntake(myFrontIntakeSubsystem, 0.5)));
    NamedCommands.registerCommand("FlywheelSlow", new BackIntakeWheelRPM( myBackIntakeWheelSubsystem, -3000));
    NamedCommands.registerCommand("Intake", new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, 0.8), new FrontIntake(myFrontIntakeSubsystem, 0.8)));
  
    chooser.setDefaultOption("Nothing", null);
    chooser.addOption("One", new PathPlannerAuto("ONE"));
    chooser.addOption("Two", new PathPlannerAuto("TWO"));
    chooser.addOption("Three", new PathPlannerAuto("THREE"));
    chooser.addOption("Test", new PathPlannerAuto("Test"));

    SmartDashboard.putData("Auto Modes", chooser);

    configureBindings();
  }

  private void configureBindings() {
  //Shooter 
  controller.rightTrigger().whileTrue(new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, -1), new FrontIntake(myFrontIntakeSubsystem, 0.5)));
controller.rightBumper().whileTrue(new BackIntakeWheelRPM(myBackIntakeWheelSubsystem, -4000));

  //Fixer
    //controller.x().whileTrue(new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, 
   // 1), new BackIntakeWheel(myBackIntakeWheelSubsystem, 0), new FrontIntake(myFrontIntakeSubsystem, 0)));

    controller.leftBumper().whileTrue(new FrontIntake(myFrontIntakeSubsystem, -1));
  //Climber up
  controller.y().whileTrue(new Climber(myClimberSubsystem, 1));

  //Climber down 
  controller.x().whileTrue(new Climber(myClimberSubsystem, -1));

  //Intake
 controller.leftTrigger().whileTrue(new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, 0.8), new BackIntakeWheel(myBackIntakeWheelSubsystem, 0), new FrontIntake(myFrontIntakeSubsystem, 0.8)));

  //Continuous intake
   controller.a().toggleOnTrue(new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, 0.8), new BackIntakeWheel(myBackIntakeWheelSubsystem, 0), new FrontIntake(myFrontIntakeSubsystem, 0.8)));

   //Continuous Shooter
    controller.b().toggleOnTrue(new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, -1), new FrontIntake(myFrontIntakeSubsystem, 0.5), new BackIntakeWheelRPM(myBackIntakeWheelSubsystem, -4000)));

  }

  public Command getAutonomousCommand() {
  
    return chooser.getSelected();
  }



}
