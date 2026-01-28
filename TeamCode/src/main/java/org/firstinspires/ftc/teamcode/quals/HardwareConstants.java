package org.firstinspires.ftc.teamcode.quals;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class HardwareConstants {

    // ===== DRIVE MOTORS =====
    public DcMotor FLeft, BLeft, FRight, BRight;

    // ===== MECHANISMS =====
    public DcMotor IntakeMotor, Belt;
    public DcMotorEx Shooter;
    public Servo Recycler;
    public CRServo Kicker;

    // ===== CONSTANTS =====
    public final double SHOOT_VELOCITY = 2250;
    public final double RAMP_TIME = 0.8;
    public final double FEED_TIME = 1.0;
    public final double RECYCLE_TIME = 1.5;
    public final double RECYCLER_OPEN = 1.0;
    public final double RECYCLER_CLOSED = 0.0;
    public final double KICKER_SPEED = 1.0;
    public final double SLOW_MODE_MULTIPLIER = 0.3;

    // ===== INIT METHOD =====
    public void init(HardwareMap hw) {

        // Drive
        FLeft = hw.get(DcMotor.class, "FLeft");
        BLeft = hw.get(DcMotor.class, "BLeft");
        FRight = hw.get(DcMotor.class, "FRight");
        BRight = hw.get(DcMotor.class, "BRight");

        // Mechanisms
        IntakeMotor = hw.get(DcMotor.class, "Intake");
        Belt = hw.get(DcMotor.class, "Belt");
        Shooter = hw.get(DcMotorEx.class, "Shooter");
        Recycler = hw.get(Servo.class, "Recycler");
        Kicker = hw.get(CRServo.class, "Kicker");

        // Directions
        FLeft.setDirection(DcMotor.Direction.REVERSE);
        BLeft.setDirection(DcMotor.Direction.REVERSE);
        Shooter.setDirection(DcMotor.Direction.REVERSE);

        // Safe defaults
        stopAll();
        Recycler.setPosition(RECYCLER_CLOSED);
    }

    // ===== HELPER METHODS =====
    public void stopDrive() {
        FLeft.setPower(0);
        BLeft.setPower(0);
        FRight.setPower(0);
        BRight.setPower(0);
    }

    public void stopAll() {
        stopDrive();
        IntakeMotor.setPower(0);
        Belt.setPower(0);
        Shooter.setVelocity(0);
        Kicker.setPower(0);
    }
}
