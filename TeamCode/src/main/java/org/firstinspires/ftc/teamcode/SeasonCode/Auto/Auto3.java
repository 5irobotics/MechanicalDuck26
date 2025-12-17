package org.firstinspires.ftc.teamcode.SeasonCode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.SeasonCode.Auto.subsystems.Drive1;

@Autonomous(name = "LongShotRed", group = "Auto")
public class Auto3 extends LinearOpMode {

    public DcMotor FLeft;
    public DcMotor FRight;
    public DcMotor BLeft;
    public DcMotor BRight;
    public DcMotorEx Intake;
    public DcMotorEx Shooter;
    public CRServo Intake_Helper;
    private ElapsedTime runtime = new ElapsedTime();
    static final double COUNTS_PER_MOTOR_REV = 537.6;
    static final double DRIVE_GEAR_REDUCTION = 1.0;
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    // subsystems

    private final Drive1 driveSubsystem = new Drive1();
    @Override
    public void runOpMode() {
        FLeft  = hardwareMap.get(DcMotor.class, "FLeft");
        FRight  = hardwareMap.get(DcMotor.class, "FRight");
        BLeft  = hardwareMap.get(DcMotor.class, "BLeft");
        BRight  = hardwareMap.get(DcMotor.class, "BRight");
        Intake  = hardwareMap.get(DcMotorEx.class, "Intake");
        Shooter  = hardwareMap.get(DcMotorEx.class, "Shooter");
        Intake_Helper  = hardwareMap.get(CRServo.class, "Intake_Helper");


        FLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FRight.setDirection(DcMotorSimple.Direction.FORWARD);
        BLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BRight.setDirection(DcMotorSimple.Direction.FORWARD);
        //Arm = hardwareMap.get(DcMotor.class,"spin");

        FLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {


            encoderDriving(1, 10, 10);

            sleep(2000);

            encoderTurn(0.5, 35);

            sleep(1000);

            Intake.setVelocity(0);

            Shooter.setVelocity(2075);

            sleep(4000);

            Intake_Helper.setPower(1);

            sleep(5000);

            Intake.setVelocity(-1000);

            sleep(1000);

            Intake.setVelocity(0);

            Shooter.setVelocity(2075);

            sleep(2000);

            Intake_Helper.setPower(1);

            sleep(5000);

            Intake_Helper.setPower(0);

            Shooter.setVelocity(0);

            sleep(1000);

            encoderDriving(1, 20, 20);

            sleep(1000);

            Intake.setVelocity(10);
            Intake.setVelocity(0);
        }

        // Stop motion
        FLeft.setPower(0);
        FRight.setPower(0);
        BLeft.setPower(0);
        BRight.setPower(0);

        // Reset to encoder mode
        FLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderTurn(double speed, double degrees) {
        double TURN_DIAMETER_INCHES = 18.0;
        double TURN_CIRCUMFERENCE = Math.PI * TURN_DIAMETER_INCHES;
        double TURN_FRACTION = Math.abs(degrees) / 360.0;
        double TURN_DISTANCE_INCHES = TURN_CIRCUMFERENCE * TURN_FRACTION;

        double leftInches = degrees > 0 ? TURN_DISTANCE_INCHES : -TURN_DISTANCE_INCHES;
        double rightInches = degrees > 0 ? -TURN_DISTANCE_INCHES : TURN_DISTANCE_INCHES;

        encoderDriving(speed, leftInches, rightInches);
    }

    public void encoderDriving(double speed, double leftInches, double rightInches) {
        int moveCountsLeft = (int) (leftInches * COUNTS_PER_INCH);
        int moveCountsRight = (int) (rightInches * COUNTS_PER_INCH);

        FLeft.setTargetPosition(FLeft.getCurrentPosition() + moveCountsLeft);
        BLeft.setTargetPosition(BLeft.getCurrentPosition() + moveCountsLeft);
        FRight.setTargetPosition(FRight.getCurrentPosition() + moveCountsRight);
        BRight.setTargetPosition(BRight.getCurrentPosition() + moveCountsRight);

        FLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FLeft.setPower(speed);
        FRight.setPower(speed);
        BLeft.setPower(speed);
        BRight.setPower(speed);

        while (opModeIsActive() &&
                FLeft.isBusy() && FRight.isBusy() &&
                BLeft.isBusy() && BRight.isBusy()) {

            telemetry.addData("Path", "Driving");
            telemetry.update();
        }

        FLeft.setPower(0);
        FRight.setPower(0);
        BLeft.setPower(0);
        BRight.setPower(0);

        FLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}