package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;




@TeleOp
public class MiddlePart1 extends OpMode {

    @Override
    public void init() {
//a
    }

    @Override
    public void loop() {

    }








    public void Intake(boolean intake_button,
                       DcMotor intake1, DcMotor intake_helper ){
        if (intake_button){
        intake1.setPower(1);
        intake_helper.setPower(1);

        }

    }
    public void Shooter(boolean shooter_button
            , DcMotor shooter ){
        if (shooter_button){
            shooter.setPower(1);
        }

    }
}


