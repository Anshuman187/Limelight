import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import com.ctre.phoenix.motorcontrol.can.TalonSRX; // Updated import

public class Robot extends TimedRobot {
    private DifferentialDrive drive;
    private TalonSRX leftMotor1, leftMotor2, leftMotor3; // Changed to TalonSRX
    private TalonSRX rightMotor1, rightMotor2, rightMotor3; // Changed to TalonSRX
    private SpeedControllerGroup leftMotors, rightMotors;

    private NetworkTable table;
    private NetworkTableEntry tx;
    private double targetOffsetAngle_Horizontal;

    @Override
    public void robotInit() {
        leftMotor1 = new TalonSRX(2); // Updated to TalonSRX
        leftMotor2 = new TalonSRX(4); // Updated to TalonSRX
        leftMotor3 = new TalonSRX(6); // Updated to TalonSRX
        rightMotor1 = new TalonSRX(1); // Updated to TalonSRX
        rightMotor2 = new TalonSRX(3); // Updated to TalonSRX
        rightMotor3 = new TalonSRX(5); // Updated to TalonSRX

        leftMotors = new SpeedControllerGroup(leftMotor1, leftMotor2, leftMotor3);
        rightMotors = new SpeedControllerGroup(rightMotor1, rightMotor2, rightMotor3);

        drive = new DifferentialDrive(leftMotors, rightMotors);

        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
    }

    @Override
    public void teleopPeriodic() {
        targetOffsetAngle_Horizontal = tx.getDouble(0.0);

        double steeringAdjust = 0.0;
        if (targetOffsetAngle_Horizontal > 1.0) {
            steeringAdjust = 0.03; // these values need tuning
        } else if (targetOffsetAngle_Horizontal < 1.0) {
            steeringAdjust = -0.03;
        }

        drive.arcadeDrive(0.0, steeringAdjust); // move robot
    }
}
