package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BackIntakeWheelSubsystem;

public class BackIntakeWheelRPM extends Command{
    private BackIntakeWheelSubsystem myBackIntakeWheelSubsystem;
    private double myRPM;

    public BackIntakeWheelRPM(BackIntakeWheelSubsystem backIntakeWheelSubsystem, double RPM){
        myBackIntakeWheelSubsystem = backIntakeWheelSubsystem;
        myRPM = RPM;
        addRequirements(myBackIntakeWheelSubsystem);
    }

    public void Initialize(){

    }

    public void execute(){
        myBackIntakeWheelSubsystem.BackIntakeWheelRPM(myRPM);
    }

    public void end(boolean isInterrupted){
        myBackIntakeWheelSubsystem.BackIntakeWheel(0);
    }
}



