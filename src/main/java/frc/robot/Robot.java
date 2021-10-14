package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


public class Robot extends TimedRobot {
  Joystick stick = new Joystick(0);
  
  int leftFollowerId = 5;
  int leftLeaderId = 1;
  int rightLeaderId = 2;
  int rightFollowerId = 6;

  int liftMotorId = 3;
  int shooterLeftId = 7;
  int shooterRightId = 8;
  int intakeMotorId = 9;
  


  WPI_VictorSPX leftFollower = new   WPI_VictorSPX(leftFollowerId);
  WPI_VictorSPX leftLeader = new   WPI_VictorSPX(leftLeaderId);
  WPI_VictorSPX rightLeader = new WPI_VictorSPX(rightLeaderId);
  WPI_VictorSPX rightFollower = new WPI_VictorSPX(rightFollowerId);

  WPI_VictorSPX liftMotor = new WPI_VictorSPX(liftMotorId);
  WPI_VictorSPX shooterLeft = new WPI_VictorSPX(shooterLeftId);
  WPI_VictorSPX shooterRight = new WPI_VictorSPX(shooterRightId);
  WPI_VictorSPX intakeMotor = new WPI_VictorSPX(intakeMotorId);


  DifferentialDrive robot = new DifferentialDrive(leftLeader, rightLeader);
  JoystickButton button1 = new JoystickButton(stick, 1);
  JoystickButton button2 = new JoystickButton(stick, 2);
  JoystickButton button3 = new JoystickButton(stick, 3);
  JoystickButton button11 = new JoystickButton(stick, 11);


  Encoder enc_left = new Encoder(0, 1);
  Encoder enc_right = new Encoder(2, 3);
  AnalogGyro gyro = new AnalogGyro(0);

  Solenoid solenoidRight = new Solenoid(5);
  Solenoid solenoidLeft = new Solenoid(6);
  Solenoid solenoidFront = new Solenoid(7);


  @Override
    public void robotInit() {
      
    rightFollower.follow(rightLeader);
    leftFollower.follow(leftLeader);
    leftLeader.setInverted(true);

    enc_left.setDistancePerPulse(1./800);
    enc_right.setDistancePerPulse(1./800);
  }

  @Override
  public void testInit() {
    double rate = enc_left.getRate();
    System.out.println(rate);
    
    double angle = gyro.getAngle();
    System.out.println(angle);

    double error = angle - stick.getY();


    if(enc_left.getDistance() < 5) {
      robot.tankDrive(.5 + error, .5 - error);
    } else {
      robot.tankDrive(0, 0);
    }

    if (button11.get()) {
      return;
    }
  }
  @Override
  public void teleopPeriodic() {
 
    double hiz = stick.getThrottle();

    robot.arcadeDrive(hiz* stick.getY(), hiz*stick.getX());
   
    if (button2.get()) {
      intakeMotor.set(0.4);
    }
    else {
      intakeMotor.set(0);
    }

}
}