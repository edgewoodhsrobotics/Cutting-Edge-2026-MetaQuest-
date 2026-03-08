package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ClimberSubsystem extends SubsystemBase{
    private SparkMax climberRightMotor;
    private SparkMax climberLeftMotor;

    public ClimberSubsystem(){
        SparkMaxConfig config2 = new SparkMaxConfig();
        SparkMaxConfig config = new SparkMaxConfig();

        config.inverted(true);
        //config.softLimit.reverseSoftLimit(0).reverseSoftLimitEnabled(true);

        //config2.softLimit.reverseSoftLimit(0).reverseSoftLimitEnabled(true);
        
        climberRightMotor = new SparkMax(18, MotorType.kBrushless);
        climberLeftMotor = new SparkMax(17, MotorType.kBrushless);

        climberRightMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        climberLeftMotor.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void Climber(double speed){
        climberRightMotor.set(speed);
        climberLeftMotor.set(speed);
    }
}
