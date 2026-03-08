package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BackIntakeWheelSubsystem extends SubsystemBase{
    private SparkMax backIntakeMotorWheel;
    private SparkFlex backIntakeMotorWheelFollower;

    public BackIntakeWheelSubsystem(){
        SparkMaxConfig config = new SparkMaxConfig();
        config.closedLoop.feedForward.kV(1/576.8);
        config.idleMode(IdleMode.kCoast);
        backIntakeMotorWheel = new SparkMax(14, MotorType.kBrushless);
        backIntakeMotorWheel.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        SparkFlexConfig config2 = new SparkFlexConfig();
        config2.follow(14, true);
        config2.idleMode(IdleMode.kCoast);
        backIntakeMotorWheelFollower = new SparkFlex(26, MotorType.kBrushless);
        backIntakeMotorWheelFollower.configure(config2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


    }

    public void BackIntakeWheel(double speed){
        backIntakeMotorWheel.set(speed);
    }

    public void BackIntakeWheelRPM(double speed){
          backIntakeMotorWheel.getClosedLoopController().setSetpoint(speed, ControlType.kVelocity);      
    }

    public double getEncoder(){
        return backIntakeMotorWheel.getEncoder().getVelocity();
    }
}

