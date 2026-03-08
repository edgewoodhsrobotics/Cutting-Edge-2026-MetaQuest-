package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivetrainSubsystem;

public class Drive extends Command {
    private DrivetrainSubsystem myDriveTrainSubsystem;
    private DoubleSupplier myTranslationXSupplier;
    private DoubleSupplier myTranslationYSupplier;
    private DoubleSupplier myOmegaSupplier;


    public Drive(DrivetrainSubsystem driveTrainSubsystem, DoubleSupplier translationXSupplier, DoubleSupplier translationYSupplier, DoubleSupplier omegaSupplier){
        myDriveTrainSubsystem = driveTrainSubsystem;
        addRequirements(myDriveTrainSubsystem);
        myTranslationXSupplier = translationXSupplier;
        myTranslationYSupplier = translationYSupplier;
        myOmegaSupplier = omegaSupplier;
    }

    public void initialize(){

    }

    public void execute(){
        ChassisSpeeds speeds = new ChassisSpeeds(myTranslationXSupplier.getAsDouble(), myTranslationYSupplier.getAsDouble(), myOmegaSupplier.getAsDouble());
        myDriveTrainSubsystem.Drive(speeds);
    }


}
