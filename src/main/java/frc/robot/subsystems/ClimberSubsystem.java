package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ClimberSubsystem extends SubsystemBase{
    private SparkMax climberRightMotor;
    private SparkMax climberLeftMotor;

    public ClimberSubsystem(){
        SparkMaxConfig config = new SparkMaxConfig();
        
        climberRightMotor = new SparkMax(18, MotorType.kBrushless);
        climberLeftMotor = new SparkMax(17, MotorType.kBrushless);
    }

    public void Climber(double speed){
        climberRightMotor.set(speed);
        climberLeftMotor.set(speed);
    }
}
