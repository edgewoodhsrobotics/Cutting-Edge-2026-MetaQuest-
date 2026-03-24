package frc.robot.commands;

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

    public void Initialize(){
        
    }

    public void execute(){
        hubAlignSubsystem.HubAlign();
    }
}
