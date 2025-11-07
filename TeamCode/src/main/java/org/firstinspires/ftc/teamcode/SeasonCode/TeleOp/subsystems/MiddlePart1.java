package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="MIDDLEPART", group="TeleOp")
public class MiddlePart1 extends OpMode {

    @Override
    public void init() {
//a
    }

    @Override
    public void loop() {

    }

    public void Intake(double y, double helper_button,
                       DcMotor intake1, CRServo intake_helper ){
        intake1.setPower(-y);
        intake_helper.setPower(-helper_button);

        }

    public void Shooter(boolean shooterspeed1, boolean shooterspeed2
            , DcMotor shooter ){
        if (shooterspeed1){
            shooter.setPower(0.81);
        } else if (shooterspeed2) {
            shooter.setPower(0.70);
        } else{
            shooter.setPower(0);
        }

    }

    public void Lift_Off(boolean lift_off, boolean reset,
                         Servo lift_L, Servo lift_R){
        if(lift_off){
            lift_L.setPosition(0.2);
            lift_R.setPosition(1);
        } else if (reset) {
            lift_L.setPosition(0);
            lift_R.setPosition(0);
        }

    }



}



