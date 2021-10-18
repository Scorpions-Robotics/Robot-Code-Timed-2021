package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
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
    leftLeader.setInverted(true);

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
    if (button2.get()) {
      intakeMotor.set(0.4);
    }
    else {
      intakeMotor.set(0);
    }

   
    if (stick.getRawButton(6)) { // top atmak için açılan yer
      double_elevator.set(DoubleSolenoid.Value.kForward);
    } else {
      double_elevator.set(DoubleSolenoid.Value.kReverse);
    }
    
    if (stick.getRawButton(4)) {
      double_intake.set(DoubleSolenoid.Value.kForward);
    } else {
      double_intake.set(DoubleSolenoid.Value.kReverse);
    }

    if (stick.getRawButton(3)) { //intake
      double_shooter.set(DoubleSolenoid.Value.kReverse);
    }

    if (stick.getRawButton(5)) {
      liftMotor.set(0.5);
    } else {
      liftMotor.set(0.5);
    }

    if (stick.getRawButton(11)) {
      shooterLeft.set(0.5);
      shooterRight.set(0.5);
    }


    double hiz = stick.getThrottle();

    if (stick.getRawButton(12)) {
      robot.arcadeDrive(hiz * stick.getY(), 1);
    } else {
      robot.arcadeDrive(hiz * stick.getY(), hiz * stick.getX());
    }
    

    }

}
