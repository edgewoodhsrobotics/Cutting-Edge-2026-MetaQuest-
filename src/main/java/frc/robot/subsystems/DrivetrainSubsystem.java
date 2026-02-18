package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;
import swervelib.parser.SwerveParser;
import swervelib.SwerveDrive;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;

public class DrivetrainSubsystem extends SubsystemBase {
    private SwerveDrive swerveDrive;
    public DrivetrainSubsystem() throws IOException {
        double maximumSpeed = Units.feetToMeters(4.5);
        File swerveJsonDirectory = new File(Filesystem.getDeployDirectory(),"swerve");
        swerveDrive = new SwerveParser(swerveJsonDirectory).createSwerveDrive(maximumSpeed);

        
    }

    public void Drive(ChassisSpeeds speed){
        swerveDrive.driveFieldOriented(speed);
    }

}