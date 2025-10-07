package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;



import org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems.Drive1;

@TeleOp
public class TeleOp1 extends OpMode {
    /*

    declare variables

     */

    public DcMotor FLeft;
    public DcMotor FRight;
    public DcMotor BLeft;
    public DcMotor BRight;
    public DcMotor Arm;

    // subsystems

    private final Drive1 driveSubsystem = new Drive1();


    public void init() {
        FLeft  = hardwareMap.get(DcMotor.class, "FLeft");
        FRight  = hardwareMap.get(DcMotor.class, "FRight");
        BLeft  = hardwareMap.get(DcMotor.class, "BLeft");
        BRight  = hardwareMap.get(DcMotor.class, "BRight");

        FLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        FRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BRight.setDirection(DcMotorSimple.Direction.REVERSE);
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
        //armsubsystem.JoystickMove(gamepad2.right_stick_y, Arm, 1);
    }
}
