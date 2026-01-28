package org.firstinspires.ftc.teamcode.quals;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOp_Final", group="Quals")
public class TeleOp extends OpMode {

    private final HardwareConstants robot = new HardwareConstants();
    private final ElapsedTime actionTimer = new ElapsedTime();

    private enum State { IDLE, SHOOT_RAMP, SHOOT_FEED, RECYCLE, RESET }
    private State currentState = State.IDLE;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {

        // ===== DRIVE =====
        double speedMultiplier = gamepad1.left_bumper
                ? robot.SLOW_MODE_MULTIPLIER
                : 1.0;

        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        robot.FLeft.setPower((drive + strafe + turn) * speedMultiplier);
        robot.BLeft.setPower((drive - strafe + turn) * speedMultiplier);
        robot.FRight.setPower((drive - strafe - turn) * speedMultiplier);
        robot.BRight.setPower((drive + strafe - turn) * speedMultiplier);

        // ===== STATE MACHINE =====
        switch (currentState) {
            case IDLE:
                if (gamepad2.x) {
                    actionTimer.reset();
                    currentState = State.SHOOT_RAMP;
                } else if (gamepad2.b) {
                    actionTimer.reset();
                    currentState = State.RECYCLE;
                }
                break;

            case SHOOT_RAMP:
                robot.Shooter.setVelocity(robot.SHOOT_VELOCITY);
                if (actionTimer.seconds() >= robot.RAMP_TIME) {
                    actionTimer.reset();
                    currentState = State.SHOOT_FEED;
                }
                break;

            case SHOOT_FEED:
                robot.Shooter.setVelocity(robot.SHOOT_VELOCITY);
                robot.Belt.setPower(1.0);
                robot.Kicker.setPower(robot.KICKER_SPEED);
                if (actionTimer.seconds() >= robot.FEED_TIME) {
                    currentState = State.RESET;
                }
                break;

            case RECYCLE:
                robot.Belt.setPower(-1.0);
                robot.Recycler.setPosition(robot.RECYCLER_OPEN);
                if (actionTimer.seconds() >= robot.RECYCLE_TIME) {
                    currentState = State.RESET;
                }
                break;

            case RESET:
                robot.stopAll();
                robot.Recycler.setPosition(robot.RECYCLER_CLOSED);
                currentState = State.IDLE;
                break;
        }

        // ===== MANUAL CONTROLS =====
        if (currentState == State.IDLE) {
            robot.IntakeMotor.setPower(gamepad2.left_stick_y);

            if (Math.abs(gamepad2.right_stick_y) > 0.1) {
                robot.Belt.setPower(-gamepad2.right_stick_y);
            } else {
                robot.Belt.setPower(0);
            }
        }

        // Emergency Kill
        if (gamepad2.left_bumper) currentState = State.RESET;

        telemetry.addData("State", currentState);
        telemetry.update();
    }
}
