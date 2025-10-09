package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems.Drive1;
import org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems.MiddlePart1;

@TeleOp
public class TeleOp1 extends OpMode {
    /*

    declare variables

     */

    public DcMotor FLeft;
    public DcMotor FRight;
    public DcMotor BLeft;
    public DcMotor BRight;
    public DcMotor Intake;
    public DcMotor Shooter;
    public CRServo Intake_Helper;

    // subsystems

    private final Drive1 driveSubsystem = new Drive1();
    private final MiddlePart1 MiddlePart1 = new MiddlePart1();


    public void init() {
        FLeft  = hardwareMap.get(DcMotor.class, "FLeft");
        FRight  = hardwareMap.get(DcMotor.class, "FRight");
        BLeft  = hardwareMap.get(DcMotor.class, "BLeft");
        BRight  = hardwareMap.get(DcMotor.class, "BRight");
        Intake  = hardwareMap.get(DcMotor.class, "Intake");
        Shooter  = hardwareMap.get(DcMotor.class, "Shooter");
        Intake_Helper  = hardwareMap.get(CRServo.class, "Intake_Helper");


        FLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FRight.setDirection(DcMotorSimple.Direction.FORWARD);
        BLeft.setDirection(DcMotorSimple.Direction.FORWARD);
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
    }

    public void loop(){
        driveSubsystem.Move();
        driveSubsystem.DPAD(gamepad1.dpad_up,
                gamepad1.dpad_right,
                gamepad1.dpad_down,
                gamepad1.dpad_left,
                FLeft, BLeft, FRight, BRight);
        driveSubsystem.rotation(gamepad1.right_bumper, gamepad1.left_bumper,
                FLeft, BLeft, FRight, BRight);
       MiddlePart1.Intake(gamepad2.a,
        Intake, Shooter);
        MiddlePart1.Shooter(gamepad2.y,
                Shooter);

        //armsubsystem.JoystickMove(gamepad2.right_stick_y, Arm, 1);
    }
}
