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

import frc.robot.Gyroscope;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;



/* Namlu solenoid 2,3
  Çift solenoidler 6,7
  Intake solenoid 4,5
  
*/
public class Robot extends TimedRobot {
  private static final double kAngleSetpoint = 0.0;
  private static final double kP = 0.005; // propotional turning constant

  // gyro calibration constant, may need to be adjusted
  // gyro value of 360 is set to correspond to one full revolution
  private static final double kVoltsPerDegreePerSecond = 0.0128;

  private static final int kSolenoidButton = 1;
  private static final int kDoubleSolenoidForward = 2;
  private static final int kDoubleSolenoidReverse = 3;

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
  // Gyroscope gyro = new Gyroscope();

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
    // gyro.calibrate();

    /* Solenoids 
    double_elevator.set(true);
    */
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

   
    if (stick.getRawButton(3)) {
      double_elevator.set(DoubleSolenoid.Value.kForward);
    } else {
      double_elevator.set(DoubleSolenoid.Value.kReverse);
    }
    
    if (stick.getRawButton(4)) {
      double_intake.set(DoubleSolenoid.Value.kForward);
    } else {
      double_intake.set(DoubleSolenoid.Value.kReverse);
    }

    if (stick.getRawButton(6)) {
      double_shooter.set(DoubleSolenoid.Value.kForward);
    } else {
      double_shooter.set(DoubleSolenoid.Value.kReverse);
    }

    // Invert the direction of the turn if we are going backwards
    //turningValue = Math.copySign(turningValue, stick.getY());
    double hiz = stick.getThrottle();
    if (stick.getRawButton(11)){
        // PID kontrolü yapılır
    }
    else{
      robot.arcadeDrive(hiz* stick.getY(), hiz* stick.getX());
    }

  

    }

}
