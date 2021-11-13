package frc.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import com.analog.adis16470.frc.ADIS16470_IMU;
//import com.analog.adis16470.frc.ADIS16470_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Robot extends TimedRobot {
  Joystick stick = new Joystick(0);
  XboxController controller = new XboxController(1);
 
 
 // UsbCamera usb = new UsbCamera("USB Camera 0", 1);
  
  DigitalInput switch_value = new DigitalInput(9);

  double MAX_DISTANCE = 5.0; 
  double MIN_DISTANCE = 5.0;
  
  int leftFollowerId = 5;
  int leftLeaderId = 1;
  int rightLeaderId = 2;
  int rightFollowerId = 6;

  int liftMotorId = 3;
  int shooterLeftId = 7;
  int shooterRightId = 8;
  int intakeMotorId = 9;

  frc.robot.Gyro gyro = new frc.robot.Gyro();

  NetworkTableEntry xEntry;
  NetworkTableEntry yEntry;
  NetworkTableEntry hEntry;
  NetworkTableEntry wEntry;
  NetworkTableEntry dEntry;
  NetworkTableEntry bEntry;
  NetworkTableEntry rEntry;

  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("vision");
  
  PWMTalonSRX switchMotor = new PWMTalonSRX(2);
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

  DoubleSolenoid double_shooter =
      new DoubleSolenoid(2, 3);

  DoubleSolenoid double_elevator =
      new DoubleSolenoid(6, 7);

  DoubleSolenoid double_intake =
      new DoubleSolenoid(4, 5);


  
  @Override
  public void robotInit() {
  //  CameraServer.getInstance().startAutomaticCapture();
    UsbCamera usb =  CameraServer.getInstance().startAutomaticCapture(0);
    usb.setResolution(480, 360);
    CameraServer.getInstance().getVideo(usb);

    
    rightFollower.follow(rightLeader);
    leftFollower.follow(leftLeader);

    inst.startClient("roborio-7672-frc.local");

  /*  rightLeader.setSafetyEnabled(true);
    leftLeader.setSafetyEnabled(true);
    rightFollower.setSafetyEnabled(true);
    leftFollower.setSafetyEnabled(true);
    */
  }

  
  @Override
  public void autonomousInit() {
    shooterLeft.set(-0.70);
    shooterRight.set(0.70);
    for(int n = 0; n<2; n++){
      Timer.delay(1.50);
      talon.set(0.9);
      Timer.delay(1.50);
      talon.set(0);
      n++;
    }
    shooterLeft.set(0);
    shooterRight.set(0);
    talon.set(0);
    while(isAutonomous()){
      robot.arcadeDrive(-0.40, 0.20);
    }
  }

  //
  @Override
  public void autonomousPeriodic() {
/*
    shooterLeft.set(-0.83);
    shooterRight.set(0.83);
    double currentTime = Timer.getFPGATimestamp();
    while(currentTime < 1.5) {
      talon.set(0.9);
      currentTime += Timer.getFPGATimestamp();
    } 
    currentTime = 0.0;
    while(currentTime < 1.5) {
      talon.set(0.0);
      currentTime += Timer.getFPGATimestamp();
    } 
    shooterLeft.set(0);
    shooterRight.set(0);
    talon.set(0);
    
    currentTime = 0.0;
    while(currentTime < 1.5){
      rightLeader.set(0.45);
      leftLeader.set(-0.45);
      currentTime += Timer.getFPGATimestamp();
    }
    
    rightLeader.set(0.0);
    leftLeader.set(0.0);
*/
  }

  @Override
  public void teleopInit() {
    gyro.resetGyro();
    gyro.calibrate();
  }

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("Aci", gyro.getGyroAngle());
  // BUNU CONTROLLER'A AL
  if(stick.getRawButton(7)){ //askıyı yukarı al
    if(switch_value.get()){
      switchMotor.set(1);
    }
    else{
      switchMotor.set(0);
    }
  }
  else if(stick.getRawButton(8)){
    switchMotor.set(-1);
  }
  else{
    switchMotor.set(0);
  }

  if (stick.getRawButton(9)){ 
    liftMotor.set(0.5);
  } else if (stick.getRawButton(10)) {
    liftMotor.set(-0.5);
  } else {
    liftMotor.set(0);
  }

    // Intake motor 
    if (stick.getRawButton(2)) {
      intakeMotor.set(-1);
      talon.set(1);
    } else if(stick.getRawButton(3)) {
      intakeMotor.set(1);
      talon.set(-1);
    } else {
      intakeMotor.set(0);
      talon.set(0);
    }

    if (stick.getRawButton(5)){ // Shooter'ın kapaklarını açan pnömatik
      double_elevator.set(DoubleSolenoid.Value.kForward);
    } else {
      double_elevator.set(DoubleSolenoid.Value.kOff);
    }
    
    if (stick.getRawButton(6)) { 
      double_intake.set(DoubleSolenoid.Value.kForward);
    } else {
      double_intake.set(DoubleSolenoid.Value.kReverse);
    }

    if (stick.getRawButton(11)) { //intake
      double_shooter.set(DoubleSolenoid.Value.kReverse);
    } else if (stick.getRawButton(12)) {  //intake kapatma tuşu
      double_shooter.set(DoubleSolenoid.Value.kForward);
    }

    if (stick.getRawButton(1)) {
      shooterLeft.set(-0.83);
      shooterRight.set(0.83);
    }
    else{
      shooterLeft.set(0);
      shooterRight.set(0);
     
    }

    double hiz = stick.getThrottle();

    robot.arcadeDrive(hiz * stick.getY(), hiz * stick.getX());

    }
 /*
  public void centerRobot() {
    try {
      //Video.get_video();
    } catch (Exception e) {
      
      e.printStackTrace();
    }
    // Pulling data from network table
    xEntry = table.getEntry("X");
    yEntry = table.getEntry("Y");
    hEntry = table.getEntry("H");
    wEntry = table.getEntry("W");
    dEntry = table.getEntry("D");
    while (true) {
        if(dEntry.getDouble(0.0) < MIN_DISTANCE){
          rightLeader.set(-0.5);
          leftLeader.set(0.5);
        } else if (dEntry.getDouble(0.0) > MAX_DISTANCE) {
          rightLeader.set(0.5);
          leftLeader.set(-0.5);
        } else{
          rightLeader.set(0);
          leftLeader.set(0);
        }
        if (!stick.getRawButton(11)) {
          teleopPeriodic();
          break;
        }
    }
  } */


  @Override
  public void testPeriodic() {
    // xEntry = table.getEntry("X");
    // yEntry = table.getEntry("Y");
    // hEntry = table.getEntry("H");
    // wEntry = table.getEntry("W");
    // dEntry = table.getEntry("D");
    // bEntry = table.getEntry("B");
    // rEntry = table.getEntry("R");

    

    // SmartDashboard.putNumber("value", controller.getTriggerAxis(GenericHID.Hand.kRight));


    // SmartDashboard.putNumber("B", bEntry.getDouble(0.0));


    // if(bEntry.getDouble(0.0)==0){
    //   SmartDashboard.putString("X_String", "None");
    //   SmartDashboard.putString("Y_String", "None");
    //   SmartDashboard.putString("H_String", "None");
    //   SmartDashboard.putString("W_String", "None");
    //   SmartDashboard.putString("D_String", "None");
    //   SmartDashboard.putString("R_String", "None");
    // }
    // else{
    //   SmartDashboard.putNumber("X", Integer.valueOf(xEntry.getString("")));
    //   SmartDashboard.putNumber("Y", Integer.valueOf(yEntry.getString("")));
    //   SmartDashboard.putNumber("H", Integer.valueOf(hEntry.getString("")));
    //   SmartDashboard.putNumber("W", Integer.valueOf(wEntry.getString("")));
    //   SmartDashboard.putNumber("D", Integer.valueOf(dEntry.getString("")));
    //   SmartDashboard.putNumber("R", Integer.valueOf(rEntry.getString("")));
    // }
    
  }
  }
