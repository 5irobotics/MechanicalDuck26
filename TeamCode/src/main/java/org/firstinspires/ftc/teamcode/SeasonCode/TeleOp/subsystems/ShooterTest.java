package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class ShooterTest extends OpMode{

    public DcMotorEx flywheelMotor;

    public double highvelocity = 1500;

    public double lowvelocity = 900;

    double curTargetVelocity = highvelocity;

    double F = 0;
    double P = 0;

    double[] stepSizes = {10.0, 1.0, 0.1, 0.001, 0.0001};


    int stepIndex = 1;

    @Override
    public void init() {

        flywheelMotor = hardwareMap.get(DcMotorEx.class, "Shooter");
        flywheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        telemetry.addLine("Init Complete");

    }

    @Override
    public void loop() {
        //get all our gamepad commands
        // set target velocity
        // update telemetry

        if (gamepad2.yWasPressed()) {
            if (curTargetVelocity == highvelocity){
                curTargetVelocity = lowvelocity;
            } else {curTargetVelocity = highvelocity;}
        }
        if (gamepad2.bWasPressed()){
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if (gamepad2.dpadLeftWasPressed()){
            F -= stepSizes[stepIndex];
        }
        if (gamepad2.dpadRightWasPressed()){
            F += stepSizes[stepIndex];

    }
        if (gamepad2.dpadDownWasPressed()){
            P += stepSizes[stepIndex];
}
        if (gamepad2.dpadUpWasPressed()){
            P -= stepSizes[stepIndex];
        }

        //set new PIDF coefficients
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);


        //set velocity
        flywheelMotor.setVelocity(curTargetVelocity);

        double curVelocity = flywheelMotor.getVelocity();
        double error = curTargetVelocity - curVelocity;

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", "%.2f", curVelocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addLine("----------------------");
        telemetry.addData("Tuning P", "%.4f (D-Pad U/D)", P);
        telemetry.addData("Tuning F", "%.4f (D-Pad L/R)", F);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);
    }
}
