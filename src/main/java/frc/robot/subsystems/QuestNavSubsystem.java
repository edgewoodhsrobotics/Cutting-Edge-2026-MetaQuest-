package frc.robot.subsystems;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import gg.questnav.questnav.PoseFrame;
import gg.questnav.questnav.QuestNav;
import frc.robot.commands.Drive;
import frc.robot.subsystems.DrivetrainSubsystem;

public class QuestNavSubsystem extends SubsystemBase {
    private QuestNav questNav;
    private Transform3d ROBOT_TO_QUEST = new Transform3d( /*TODO: Put your x, y, z, yaw, pitch, and roll offsets here!*/ );
    private DrivetrainSubsystem drivetrainSubsystem;


    private final Matrix<N3, N1> QUESTNAV_STD_DEVS =
    VecBuilder.fill(
        0.02, // Trust down to 2cm in X direction
        0.02, // Trust down to 2cm in Y direction
        0.035 // Trust down to 2 degrees rotational
    );

/* from quest website: 
 The FRC coordinate system follows these standards:
X: Positive -> Forward from robot center
Y: Positive -> Left from robot center
Z: Positive -> Up from robot center
Yaw (Z): Rotation -> Counter-clockwise (right-handed) rotation around the Z axis
Pitch (Y): Rotation -> Counter-clockwise (right-handed) rotation around the Y axis
Roll (X): Rotation -> Counter-clockwise (right-handed) rotation around the X axis
https://docs.wpilib.org/en/stable/docs/software/basic-programming/coordinate-system.html
 */
    
    public QuestNavSubsystem(DrivetrainSubsystem drivetrainSubsystem){
        questNav = new QuestNav();
        this.drivetrainSubsystem = drivetrainSubsystem;

        //Connection callbacks
        questNav.onConnected(() ->
            System.out.println("Quest connected!")
        );
        questNav.onDisconnected(() ->
            DriverStation.reportWarning("Quest disconnected!", false)
        );
        questNav.onTrackingAcquired(() ->
            System.out.println("Quest tracking acquired!")
        );
        questNav.onTrackingLost(() ->
            DriverStation.reportWarning("Quest tracking lost!", false)
        );
        questNav.onLowBattery(20, level ->
            DriverStation.reportWarning("Quest battery low: " + level + "%", false)
        );
        questNav.onCommandSuccess(response ->
            System.out.println("Command succeeded: " + response.getCommandId())
        );
        questNav.onCommandFailure(response ->
            DriverStation.reportError("Command failed: " + response.getErrorMessage(), false)
        );
    }

    @Override
    public void periodic(){
        questNav.commandPeriodic();

        //GET POSE DATA FROM QUEST AND TRANSFORM IT TO ROBOT
        //Periodically get latest pose data from quest
        PoseFrame[] poseFrames = questNav.getAllUnreadPoseFrames();
        
        // Loop over the pose data frames and send them to the pose estimator
        for(PoseFrame poseFrame : poseFrames){
             // Make sure the Quest was tracking the pose for this frame
            if(poseFrame.isTracking()){
                //Get pose of quest
                Pose3d questPose = poseFrame.questPose3d(); //NOTE: Cannot be declared in constructor, temporary values found every loop

                //Get timestamp for when data sent
                double timestamp = poseFrame.dataTimestamp();

                //Transform to get robot pose
                Pose3d robotPose = questPose.transformBy(ROBOT_TO_QUEST.inverse());
                
                //Add measurement to estimator
                drivetrainSubsystem.addVisionMeasurement(robotPose.toPose2d(), timestamp, QUESTNAV_STD_DEVS);
            }
        }

        /* SIMPLE POSE ESTIMATOR
        if (poseFrames.length > 0) {
            //get most recent quest pose
            questPose = poseFrames[poseFrames.length - 1].questPose3d();

            //transform by the mount pose to get actual robot pose
            //inverted because going from quest to robot, not robot to quest
            robotPose = questPose.transformBy(ROBOT_TO_QUEST.inverse());
        }
            */
    }

    //Run this method at start of match to set 'starting position' on field
    public void setRobotPose(Pose3d robotPose){
        Pose3d questPose = robotPose.transformBy(ROBOT_TO_QUEST);
        questNav.setPose(questPose);
    }
}

