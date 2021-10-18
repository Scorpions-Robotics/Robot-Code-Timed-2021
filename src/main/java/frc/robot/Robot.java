package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;

//import com.analog.adis16470.frc.ADIS16470_IMU;
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
  
  PWMTalonSRX talon = new PWMTalonSRX(0);
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
  JoystickButton kElevatorS = new JoystickButton(stick, 3);
  JoystickButton kIntakeS = new JoystickButton(stick, 4);
  JoystickButton kShooterS = new JoystickButton(stick, 6);
  JoystickButton kPIDActivate = new JoystickButton(stick, 11);

  //Encoder enc_left = new Encoder(0, 1);
  //Encoder enc_right = new Encoder(2, 3);
  //ADIS16470_IMU gyro = new ADIS16470_IMU();

  DoubleSolenoid double_shooter =
      new DoubleSolenoid(2, 3);

  DoubleSolenoid double_elevator =
      new DoubleSolenoid(6, 7);

  DoubleSolenoid double_intake =
      new DoubleSolenoid(4, 5);


  @Override
    public void robotInit() {
      
    rightFollower.follow(rightLeader);
    leftFollower.follow(leftLeader);


    //enc_left.setDistancePerPulse(1./800);
    //enc_right.setDistancePerPulse(1./800);

    //gyro.calibrate();

  }

  // solneoidler butonlara atanır
  // gyro' nun ayarı yapılır
  // 90 derecelik değişim için butonlara değer ver
  @Override
  public void teleopPeriodic() {

    // Intake motor 
    if (stick.getRawButton(2)) {
      intakeMotor.set(-1);
      talon.set(0.8);
    }
    else {
      intakeMotor.set(0);
      talon.set(0);
    }

   
    if (stick.getRawButton(6)) { // Shooter'ın kapaklarını açan pnömatik
      double_elevator.set(DoubleSolenoid.Value.kForward);
    } else {
      double_elevator.set(DoubleSolenoid.Value.kOff);
    }
    
    if (stick.getRawButton(4)) {
      double_intake.set(DoubleSolenoid.Value.kForward);
    } else {
      double_intake.set(DoubleSolenoid.Value.kReverse);
    }

    if (stick.getRawButton(5)) { //intake
      double_shooter.set(DoubleSolenoid.Value.kReverse);
    } else if (stick.getRawButton(7)) { 
      double_shooter.set(DoubleSolenoid.Value.kForward);
    }
    if (stick.getRawButton(1)) {
      shooterLeft.set(-0.8);
      shooterRight.set(0.8);
    }
    else{
      shooterLeft.set(0);
      shooterRight.set(0);
    }

    if(stick.getRawButton(1)) {
      liftMotor.set(0.5);
    } else {
      liftMotor.set(0);
    }


    double hiz = stick.getThrottle();


  
    robot.arcadeDrive(-1*hiz * stick.getY(), -1* hiz * stick.getX());
    
    

    }

}
