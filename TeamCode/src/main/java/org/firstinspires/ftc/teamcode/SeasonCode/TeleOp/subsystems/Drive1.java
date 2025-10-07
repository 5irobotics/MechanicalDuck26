package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems;



import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.changes;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.drawCurrent;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.drawCurrentAndHistory;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.stopRobot;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.telemetryM;

@TeleOp
public class Drive1 extends OpMode {

    @Override
    public void init() {
//a
    }

    @Override
    public void loop() {

    }

    public void Move() {
        follower.setTeleOpDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, true);
        follower.update();

        telemetryM.debug("x:" + Math.round(follower.getPose().getX()));
        telemetryM.debug("y:" + Math.round(follower.getPose().getY()));
        telemetryM.debug("heading:" + Math.round(follower.getPose().getHeading()));
        telemetryM.debug("total heading:" + Math.round(follower.getTotalHeading()));
        telemetryM.update(telemetry);

        drawCurrentAndHistory();
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
