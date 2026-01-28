package org.firstinspires.ftc.teamcode.quals;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.pedropathing.geometry.Pose2d;
import com.pedropathing.pathgen.*;
import com.pedropathing.drive.PedroDrive;

@Autonomous(name="Pedro_Auto_Quals", group="Quals")
public class Autonomous extends LinearOpMode {

    // ===== SELECTION ENUMS =====
    enum Alliance { RED, BLUE }
    enum StartDistance { NEAR, FAR }
    enum ScoreLevel { ROW_1, ROW_1_2, ROW_1_2_3 }
    enum AutoState { TO_ARTIFACT, COLLECT, TO_SCORE, SCORE, PARK, IDLE }

    private final HardwareConstants robot = new HardwareConstants();
    private PedroDrive drive;

    private Alliance alliance = Alliance.RED;
    private StartDistance distance = StartDistance.NEAR;
    private ScoreLevel scoreLevel = ScoreLevel.ROW_1;
    private AutoState state = AutoState.IDLE;

    private int artifactIndex = 0;
    private int scoreIndex = 0;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        drive = new PedroDrive(hardwareMap);
        drive.setPose(getStartPose());

        // ===== INIT SELECTION MENU =====
        boolean aLast=false, bLast=false, xLast=false;
        while (opModeInInit()) {

            if (gamepad1.x && !xLast)
                alliance = (alliance==Alliance.RED)?Alliance.BLUE:Alliance.RED;

            if (gamepad1.a && !aLast)
                distance = (distance==StartDistance.NEAR)?StartDistance.FAR:StartDistance.NEAR;

            if (gamepad1.b && !bLast)
                switch(scoreLevel){
                    case ROW_1: scoreLevel=ScoreLevel.ROW_1_2; break;
                    case ROW_1_2: scoreLevel=ScoreLevel.ROW_1_2_3; break;
                    case ROW_1_2_3: scoreLevel=ScoreLevel.ROW_1; break;
                }

            aLast=gamepad1.a;
            bLast=gamepad1.b;
            xLast=gamepad1.x;

            telemetry.addLine("=== AUTO CONFIG ===");
            telemetry.addData("Alliance", alliance);
            telemetry.addData("Distance", distance);
            telemetry.addData("ScoreLevel", scoreLevel);
            telemetry.update();
        }

        waitForStart();
        if (!opModeIsActive()) return;
        state = AutoState.TO_ARTIFACT;

        // ===== MAIN STATE LOOP =====
        while(opModeIsActive() && state!=AutoState.IDLE){

            drive.update();

            switch(state){
                case TO_ARTIFACT: goToArtifact(); break;
                case COLLECT: collect(); break;
                case TO_SCORE: goToScore(); break;
                case SCORE: score(); break;
                case PARK: park(); break;
            }
        }
    }

    // ===== PEDRO PATH HELPERS =====
    private Pose2d mirrorIfBlue(Pose2d pose){
        if(alliance==Alliance.BLUE)
            return new Pose2d(-pose.getX(),pose.getY(),Math.PI-pose.getHeading());
        return pose;
    }

    private Pose2d getStartPose(){
        Pose2d near = new Pose2d(12,-60,Math.toRadians(90));
        Pose2d far = new Pose2d(-36,-60,Math.toRadians(90));
        return mirrorIfBlue(distance==StartDistance.NEAR?near:far);
    }

    private Pose2d[] ARTIFACTS = { new Pose2d(-24,-12,0), new Pose2d(0,-12,0), new Pose2d(24,-12,0)};
    private Pose2d ROW_1 = new Pose2d(0,36,Math.toRadians(180));
    private Pose2d ROW_2 = new Pose2d(0,40,Math.toRadians(180));
    private Pose2d ROW_3 = new Pose2d(0,44,Math.toRadians(180));
    private Pose2d PARK = new Pose2d(48,60,Math.toRadians(180));

    private int getScoreCount(){
        switch(scoreLevel){
            case ROW_1: return 1;
            case ROW_1_2: return 2;
            case ROW_1_2_3: return 3;
        }
        return 1;
    }

    private Pose2d getScorePose(){
        Pose2d pose;
        switch(scoreIndex){
            case 0: pose=ROW_1; break;
            case 1: pose=ROW_2; break;
            case 2: pose=ROW_3; break;
            default: pose=ROW_1;
        }
        return mirrorIfBlue(pose);
    }

    // ===== STATES =====
    private void goToArtifact(){
        PathChain path = new PathBuilder()
                .addPath(new BezierLine(drive.getPose(),mirrorIfBlue(ARTIFACTS[artifactIndex])))
                .build();
        drive.followPath(path);
        state=AutoState.COLLECT;
    }

    private void collect(){
        if(!drive.isBusy()){
            // TODO: intake logic
            state=AutoState.TO_SCORE;
        }
    }

    private void goToScore(){
        PathChain path = new PathBuilder()
                .addPath(new BezierLine(drive.getPose(),getScorePose()))
                .build();
        drive.followPath(path);
        state=AutoState.SCORE;
    }

    private void score(){
        if(!drive.isBusy()){
            // TODO: scoring logic
            scoreIndex++;
            artifactIndex++;
            if(scoreIndex<getScoreCount()) state=AutoState.TO_ARTIFACT;
            else state=AutoState.PARK;
        }
    }

    private void park(){
        PathChain path = new PathBuilder()
                .addPath(new BezierLine(drive.getPose(),mirrorIfBlue(PARK)))
                .build();
        drive.followPath(path);
        state=AutoState.IDLE;
    }
}
