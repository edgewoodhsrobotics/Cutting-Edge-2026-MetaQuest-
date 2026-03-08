package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BackIntakeSubsystem extends SubsystemBase{
    private SparkMax backIntakeMotorPush;
    private SparkFlex backIntakeMotorWheel;


    public BackIntakeSubsystem(){
        backIntakeMotorPush = new SparkMax(49, MotorType.kBrushless);
    }

    public void BackIntake(double speed){
        backIntakeMotorPush.set(speed);

    }
}
