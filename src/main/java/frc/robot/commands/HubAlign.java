package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.HubAlignSubsystem;
import swervelib.simulation.ironmaple.simulation.drivesims.configs.DriveTrainSimulationConfig;

public class HubAlign extends Command{
    private DrivetrainSubsystem drivetrainSubsystem;
    private HubAlignSubsystem hubAlignSubsystem;

    public HubAlign(DrivetrainSubsystem drivetrainSubsystem, HubAlignSubsystem hubAlignSubsystem){
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.hubAlignSubsystem = hubAlignSubsystem;
        addRequirements(hubAlignSubsystem, drivetrainSubsystem);
    }

    @Override
    public void initialize(){
        
    }

    @Override
    public void execute(){
        hubAlignSubsystem.HubAlign();
    }

    @Override
    public boolean isFinished() {
    Rotation2d current = drivetrainSubsystem.getQuestPose().getRotation();
    Rotation2d target = hubAlignSubsystem.hubCoordinates
                        .minus(drivetrainSubsystem.getQuestPose().getTranslation())
                        .getAngle();

    double error = target.minus(current).getRadians(); // signed error
    // Normalize to [-pi, pi]
    error = Math.atan2(Math.sin(error), Math.cos(error));

    return Math.abs(error) < 0.05; // radians (~3 degrees)

    }

    @Override
    public void end(boolean isInterrupted){
        hubAlignSubsystem.HubAlignStop();
    }
}
