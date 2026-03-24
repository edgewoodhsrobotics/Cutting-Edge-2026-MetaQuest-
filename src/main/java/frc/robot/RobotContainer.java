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
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.BackIntake;
import frc.robot.commands.FrontIntake;
import frc.robot.commands.HubAlign;
import frc.robot.subsystems.BackIntakeSubsystem;
import frc.robot.commands.Drive;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.FrontIntakeSubsystem;
import frc.robot.subsystems.BackIntakeWheelSubsystem;
import frc.robot.commands.BackIntakeWheel;
import frc.robot.commands.Climber;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.commands.BackIntakeWheelRPM;
import frc.robot.subsystems.HubAlignSubsystem;
import frc.robot.subsystems.QuestNavSubsystem;


public class RobotContainer {
  private final DrivetrainSubsystem myDriveTrainSubsystem;
  private final CommandXboxController controllerDrive;
  private final CommandXboxController controllerShoot;
  private final FrontIntakeSubsystem myFrontIntakeSubsystem;
  private final BackIntakeSubsystem myBackIntakeSubsystem;
  private final BackIntakeWheelSubsystem myBackIntakeWheelSubsystem;
  private final ClimberSubsystem myClimberSubsystem;
  private final HubAlignSubsystem myHubAlignSubsystem;
  private final QuestNavSubsystem myQuestNavSubsystem;

  private final SendableChooser<Command> chooser = new SendableChooser<>();

  
  public RobotContainer() throws IOException{

    myDriveTrainSubsystem = new DrivetrainSubsystem();
    myFrontIntakeSubsystem = new FrontIntakeSubsystem();
    myBackIntakeSubsystem = new BackIntakeSubsystem();
    myBackIntakeWheelSubsystem = new BackIntakeWheelSubsystem();
    myClimberSubsystem = new ClimberSubsystem();
    myHubAlignSubsystem = new HubAlignSubsystem(myDriveTrainSubsystem);
    myQuestNavSubsystem = new QuestNavSubsystem(myDriveTrainSubsystem);


    controllerDrive = new CommandXboxController(0);
    controllerShoot = new CommandXboxController(1);


    myDriveTrainSubsystem.setDefaultCommand(new Drive(myDriveTrainSubsystem, () -> -controllerDrive.getLeftY()*4, () -> -controllerDrive.getLeftX()*4, () -> -controllerDrive.getRightX()*2*Math.PI));


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
    controllerShoot.rightTrigger().whileTrue(new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, -1), new FrontIntake(myFrontIntakeSubsystem, 0.5)));
    controllerShoot.rightBumper().whileTrue(new BackIntakeWheelRPM(myBackIntakeWheelSubsystem, -4000));

    // //Climber Up
    // controllerShoot.leftTrigger().whileTrue(new Climber(myClimberSubsystem, 0.5));

    // //Climber Down
    // controllerShoot.leftBumper().whileTrue(new Climber(myClimberSubsystem, -0.5));

    //Continuous intake
    controllerShoot.a().toggleOnTrue(new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, 0.8), new BackIntakeWheel(myBackIntakeWheelSubsystem, 0), new FrontIntake(myFrontIntakeSubsystem, 0.8)));

   //Continuous Shooter
    // controllerShoot.b().toggleOnTrue(new ParallelCommandGroup(
    //     new BackIntakeWheelRPM(myBackIntakeWheelSubsystem, -4000),
    //     new WaitCommand(1).andThen(
    //             new ParallelCommandGroup(new FrontIntake(myFrontIntakeSubsystem, 0.5), 
    //             new BackIntake(myBackIntakeSubsystem, -1))
    //     )));
    //controllerShoot.b().toggleOnTrue(new InstantCommand(()-> SmartDashboard.putBoolean("Shooter?", !SmartDashboard.getBoolean("Shooter?", false))));

    controllerShoot.b().whileTrue(new BackIntakeWheelRPM(myBackIntakeWheelSubsystem, -5000));

    //Reset odometry
    controllerDrive.x().onTrue(new InstantCommand(myDriveTrainSubsystem::resetOdometry));

    //Unstick
     controllerShoot.y().whileTrue(new ParallelCommandGroup(new BackIntake(myBackIntakeSubsystem, 1), new FrontIntake(myFrontIntakeSubsystem, -1), new BackIntakeWheelRPM(myBackIntakeWheelSubsystem, 4000)));
    
     //Align to hub
      controllerDrive.leftBumper().onTrue(new HubAlign(myDriveTrainSubsystem, myHubAlignSubsystem));

  }

  public Command getAutonomousCommand() {
  
    return chooser.getSelected();
  }

  public DrivetrainSubsystem getDriveTrain() {
    return myDriveTrainSubsystem;
  }

  public QuestNavSubsystem getQuestNavSubsystem(){
    return myQuestNavSubsystem;
  }


}
