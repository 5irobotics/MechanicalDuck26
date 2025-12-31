package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems.Drive1nonpp;
import org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems.MiddlePart1;
//import org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems.ShooterTest;


@TeleOp(name="TELEOP", group="TeleOp")
public class TeleOp1 extends OpMode {
    /*

    declare variables

     */
    double F = -0.1228;
    double P = -6.0670;

    public DcMotor FLeft;
    public DcMotor FRight;
    public DcMotor BLeft;
    public DcMotor BRight;
    public DcMotor Intake;
    public DcMotorEx Shooter;
    private CRServo Intake_Helper;
    // subsystems

    private final Drive1nonpp driveSubsystem = new Drive1nonpp();
    private final MiddlePart1 middlePart1 = new MiddlePart1();


    public void init() {
        FLeft  = hardwareMap.get(DcMotor.class, "FLeft");
        FRight  = hardwareMap.get(DcMotor.class, "FRight");
        BLeft  = hardwareMap.get(DcMotor.class, "BLeft");
        BRight  = hardwareMap.get(DcMotor.class, "BRight");
        Intake = hardwareMap.get(DcMotor.class, "Intake");
        Shooter = hardwareMap.get(DcMotorEx.class, "Shooter");
        Intake_Helper = hardwareMap.get(CRServo.class, "Intake_Helper" );


        FLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        FRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BRight.setDirection(DcMotorSimple.Direction.REVERSE);
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);
        Shooter.setDirection(DcMotorSimple.Direction.FORWARD);
        Intake_Helper.setDirection(CRServo.Direction.REVERSE);


        FLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        FLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        Shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
    }

    public void loop(){
        driveSubsystem.Move(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x,FLeft, BLeft, FRight, BRight);
        driveSubsystem.slowForward(gamepad1.dpad_down, FLeft, BLeft, FRight, BRight);
        middlePart1.Intake(gamepad2.right_stick_y, gamepad2.left_stick_y,
        Intake, Intake_Helper);
        middlePart1.Shooter(gamepad2.y, gamepad2.b,
        Shooter);
    }
}
