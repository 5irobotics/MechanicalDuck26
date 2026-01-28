package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOp_Final", group="Z")
public class TeleOp1 extends OpMode {

    private DcMotor FLeft, BLeft, FRight, BRight;
    private DcMotor IntakeMotor, Belt;
    private DcMotorEx Shooter;
    private Servo Recycler;
    private CRServo Kicker;

    private ElapsedTime actionTimer = new ElapsedTime();
    private enum State { IDLE, SHOOT_RAMP, SHOOT_FEED, RECYCLE, RESET }
    private State currentState = State.IDLE;

    // --- TUNING CONSTANTS ---
    final double SHOOT_VELOCITY = 2250;
    final double RAMP_TIME = 0.8;
    final double FEED_TIME = 1.0;
    final double RECYCLE_TIME = 1.5;

    final double RECYCLER_OPEN = 1.0;
    final double RECYCLER_CLOSED = 0.0;
    final double KICKER_SPEED = 1.0;

    @Override
    public void init() {
        FLeft = hardwareMap.get(DcMotor.class, "FLeft");
        BLeft = hardwareMap.get(DcMotor.class, "BLeft");
        FRight = hardwareMap.get(DcMotor.class, "FRight");
        BRight = hardwareMap.get(DcMotor.class, "BRight");

        IntakeMotor = hardwareMap.get(DcMotor.class, "Intake");
        Belt = hardwareMap.get(DcMotor.class, "Belt");
        Recycler = hardwareMap.get(Servo.class, "Recycler");
        Kicker = hardwareMap.get(CRServo.class, "Kicker");
        Shooter = hardwareMap.get(DcMotorEx.class, "Shooter");

        FLeft.setDirection(DcMotor.Direction.REVERSE);
        BLeft.setDirection(DcMotor.Direction.REVERSE);
        Shooter.setDirection(DcMotor.Direction.REVERSE);

        Kicker.setPower(0);
        Recycler.setPosition(RECYCLER_CLOSED);
    }

    @Override
    public void loop() {
        // --- DRIVE LOGIC ---
        double speedMultiplier = gamepad1.left_bumper ? 0.3 : 1.0;
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        FLeft.setPower((drive + strafe + turn) * speedMultiplier);
        BLeft.setPower((drive - strafe + turn) * speedMultiplier);
        FRight.setPower((drive - strafe - turn) * speedMultiplier);
        BRight.setPower((drive + strafe - turn) * speedMultiplier);

        // --- STATE MACHINE (Automated Actions) ---
        switch (currentState) {
            case IDLE:
                if (gamepad2.x) { // NORMAL SHOOT
                    actionTimer.reset();
                    currentState = State.SHOOT_RAMP;
                } else if (gamepad2.b) { // RECYCLE
                    actionTimer.reset();
                    currentState = State.RECYCLE;
                }
                break;

            case SHOOT_RAMP:
                Shooter.setVelocity(SHOOT_VELOCITY);
                if (actionTimer.seconds() >= RAMP_TIME) {
                    actionTimer.reset();
                    currentState = State.SHOOT_FEED;
                }
                break;

            case SHOOT_FEED:
                Shooter.setVelocity(SHOOT_VELOCITY);
                Belt.setPower(1.0);
                Kicker.setPower(KICKER_SPEED);
                if (actionTimer.seconds() >= FEED_TIME) {
                    currentState = State.RESET;
                }
                break;

            case RECYCLE:
                Belt.setPower(-1.0); // Reverse belt to move element back
                Recycler.setPosition(RECYCLER_OPEN); // Open the door
                if (actionTimer.seconds() >= RECYCLE_TIME) {
                    currentState = State.RESET;
                }
                break;

            case RESET:
                Shooter.setVelocity(0);
                Belt.setPower(0);
                Kicker.setPower(0);
                Recycler.setPosition(RECYCLER_CLOSED);
                currentState = State.IDLE;
                break;
        }

        // --- MANUAL CONTROLS (Only active if not in an automated state) ---
        if (currentState == State.IDLE) {
            IntakeMotor.setPower(gamepad2.left_stick_y);

            // Allow manual belt clearing if needed (Right Stick)
            if (Math.abs(gamepad2.right_stick_y) > 0.1) {
                Belt.setPower(-gamepad2.right_stick_y);
            } else {
                Belt.setPower(0);
            }
        }

        // Emergency Kill (Gamepad 2 Left Bumper)
        if (gamepad2.left_bumper) currentState = State.RESET;

        telemetry.addData("Robot State", currentState);
        telemetry.update();
    }
}