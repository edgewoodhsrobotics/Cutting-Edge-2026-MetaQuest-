package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HubAlignSubsystem extends SubsystemBase{
    private DrivetrainSubsystem drivetrainSubsystem;
    private final double HUB_X = -143.5; //check later if inches or meters on bookmarked link (prob inches - convert later)
    private final double HUB_Y = 0;
    private final Translation2d hubCoordinates;

    public HubAlignSubsystem(DrivetrainSubsystem drivetrainSubsystem){
        //for future reference use this.subsystem instead of mySubsystem for clarity
        //(I can fix this later in the other subsystems)
        this.drivetrainSubsystem = drivetrainSubsystem;
        hubCoordinates = new Translation2d(HUB_X, HUB_Y);
    }

    public void HubAlign(){
        //get current position (x,y,rotation)
        Pose2d currentPosition = drivetrainSubsystem.getQuestPose();
        
        //Desired angle - angle from +X axis to Hub from current position MINUS current angle (look at ella's diagram)
        double angleToTarget = hubCoordinates.minus(currentPosition.getTranslation()).getAngle() //abs angle from current to target using just translation
        .minus(currentPosition.getRotation()).getRadians(); //current angle calculated by quest

        //Turn robot by calculated angle
        ChassisSpeeds rotationSpeed = new ChassisSpeeds(0,0, angleToTarget);
        drivetrainSubsystem.Drive(rotationSpeed);

    }
}
