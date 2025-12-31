package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems.MiddlePart1;

@TeleOp
public class VelocityTest extends OpMode {
    /*

    declare variables

     */




    double[] stepSizes1 = {1000.0, 100.0, 10.0, 1.0, 0.1, 0.001, 0.0001};

    int stepIndex1 = 0;

        public DcMotor Intake;
        public DcMotorEx Shooter;
        private CRServo Intake_Helper;


        private final MiddlePart1 middlePart1 = new MiddlePart1();


        public void init() {
            Intake = hardwareMap.get(DcMotor.class, "Intake");
            Shooter = hardwareMap.get(DcMotorEx.class, "Shooter");
            Intake_Helper = hardwareMap.get(CRServo.class, "Intake_Helper" );



            Intake.setDirection(DcMotorSimple.Direction.FORWARD);
            Shooter.setDirection(DcMotorSimple.Direction.FORWARD);
            Intake_Helper.setDirection(CRServo.Direction.REVERSE);

            Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            Intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        public void loop(){
            middlePart1.Intake(gamepad2.right_stick_y, gamepad2.left_stick_y,
                    Intake, Intake_Helper);

            double curVelocity = Shooter.getVelocity();

            if (gamepad2.bWasPressed()){
                stepIndex1 = (stepIndex1 + 1) % stepSizes1.length;
            }
            if (gamepad2.dpadDownWasPressed()){
                Shooter.setVelocity( curVelocity -= stepSizes1[stepIndex1]);
            }
            if (gamepad2.dpadUpWasPressed()){
                Shooter.setVelocity( curVelocity += stepSizes1[stepIndex1]);
            }

            telemetry.addData("Current Velocity", "%.2f", curVelocity);
            telemetry.addLine("----------------------");
            telemetry.addData("Step Size", "%.4f (B Button)", stepSizes1[stepIndex1]);


        }
    }


