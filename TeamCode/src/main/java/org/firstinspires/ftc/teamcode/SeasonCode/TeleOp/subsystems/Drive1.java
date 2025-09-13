package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems;



import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp
public class Drive1 extends OpMode {

    @Override
    public void init() {
//a
    }

    @Override
    public void loop() {

    }

    public void Move(double y, double x,
                     DcMotor motorfl, DcMotor motorbl, DcMotor motorfr, DcMotor motorbr) {
        motorfl.setPower(y + x);
        motorbl.setPower(y - x);
        motorfr.setPower(y - x);
        motorbr.setPower(y + x);
    }

    public void DPAD(boolean up, boolean right, boolean down, boolean left,
                     DcMotor motorfl, DcMotor motorbl, DcMotor motorfr, DcMotor motorbr) {

        if (up){
            motorfl.setPower(-1);
            motorbl.setPower(-1);
            motorfr.setPower(-1);
            motorbr.setPower(-1);
        }
        if (right){
            motorfl.setPower(1);
            motorbl.setPower(-1);
            motorfr.setPower(-1);
            motorbr.setPower(1);
        }
        if (down){
            motorfl.setPower(1);
            motorbl.setPower(1);
            motorfr.setPower(1);
            motorbr.setPower(1);
        }
        if (left){
            motorfl.setPower(-1);
            motorbl.setPower(1);
            motorfr.setPower(1);
            motorbr.setPower(-1);
        }

    }












    public void rotation(boolean right, boolean left,
                         DcMotor motorfl, DcMotor motorbl, DcMotor motorfr, DcMotor motorbr){
        if (right){
            motorfl.setPower(-1);
            motorbl.setPower(-1);
            motorfr.setPower(1);
            motorbr.setPower(1);

        }
        if (left){
            motorfl.setPower(1);
            motorbl.setPower(1);
            motorfr.setPower(-1);
            motorbr.setPower(-1);

        }
    }
}
