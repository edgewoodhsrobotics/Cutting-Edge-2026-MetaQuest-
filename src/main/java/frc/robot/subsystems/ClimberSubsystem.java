package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ClimberSubsystem extends SubsystemBase{
    private SparkMax climberMotor;

    public ClimberSubsystem(){
        SparkMaxConfig config = new SparkMaxConfig();

        //config.softLimit.reverseSoftLimit(0).reverseSoftLimitEnabled(true);
        
        climberMotor = new SparkMax(17, MotorType.kBrushless);

        climberMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void Climber(double speed){
        climberMotor.set(speed);
    }
}
