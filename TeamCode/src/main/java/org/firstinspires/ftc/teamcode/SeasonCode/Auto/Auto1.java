package org.firstinspires.ftc.teamcode.SeasonCode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.SeasonCode.Auto.subsystems.Drive1;

@Autonomous(name = "Both", group = "Auto")
public class Auto1 extends LinearOpMode {

    public DcMotor FLeft;
    public DcMotor FRight;
    public DcMotor BLeft;
    public DcMotor BRight;
    public DcMotorEx Intake;
    public DcMotorEx Shooter;
    public CRServo Intake_Helper;

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

        FLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {


            driveSubsystem.encoderDrive(1, -43, 5.0,
                    FLeft, FRight, BRight, BLeft);

            Intake.setVelocity(1600);

            sleep(1000);

            Intake.setVelocity(0);

            Shooter.setVelocity(1900);

            sleep(2000);

            Intake_Helper.setPower(1);

            sleep(5000);

            Intake_Helper.setPower(0);

            Shooter.setVelocity(0);


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
}