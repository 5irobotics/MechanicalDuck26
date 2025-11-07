package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems;



import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp
public class Drive1nonpp extends OpMode {




    @Override
    public void init() {

//a
    }

    @Override
    public void loop() {

    }

    public void Move(double y, double x, double rx, DcMotor motorfl, DcMotor motorbl, DcMotor motorfr, DcMotor motorbr) {

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        //y = y;
        x = -x;
        rx = -rx;

        motorfl.setPower((y + x + rx) / denominator);
        motorbl.setPower((y - x + rx) / denominator);
        motorfr.setPower((y - x - rx) / denominator);
        motorbr.setPower((y + x - rx) / denominator);
    }


    public void slowForward(boolean button, DcMotor motorfl, DcMotor motorbl, DcMotor motorfr, DcMotor motorbr) {
        if (button){
            motorfl.setPower(-0.3);
            motorbl.setPower(-0.3);
            motorfr.setPower(-0.3);
            motorbr.setPower(-0.3);
        }

    }

    public void DPAD(boolean up, boolean right, boolean down, boolean left,
                     DcMotor motorfl, DcMotor motorbl, DcMotor motorfr, DcMotor motorbr) {

        if (up) {
            motorfl.setPower(-1);
            motorbl.setPower(-1);
            motorfr.setPower(-1);
            motorbr.setPower(-1);
        }
        if (right) {
            motorfl.setPower(1);
            motorbl.setPower(-1);
            motorfr.setPower(-1);
            motorbr.setPower(1);
        }
        if (down) {
            motorfl.setPower(1);
            motorbl.setPower(1);
            motorfr.setPower(1);
            motorbr.setPower(1);
        }
        if (left) {
            motorfl.setPower(-1);
            motorbl.setPower(1);
            motorfr.setPower(1);
            motorbr.setPower(-1);
        }

    }


    public void rotation(boolean right, boolean left,
                         DcMotor motorfl, DcMotor motorbl, DcMotor motorfr, DcMotor motorbr) {
        if (right) {
            motorfl.setPower(-1);
            motorbl.setPower(-1);
            motorfr.setPower(1);
            motorbr.setPower(1);

        }
        if (left) {
            motorfl.setPower(1);
            motorbl.setPower(1);
            motorfr.setPower(-1);
            motorbr.setPower(-1);

        }
    }
}
