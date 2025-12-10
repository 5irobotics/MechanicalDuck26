package org.firstinspires.ftc.teamcode.SeasonCode.TeleOp.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


@Autonomous
public class        LimeLight1 extends OpMode {

    private Limelight3A limelight3A;

    @Override
    public void init(){
        limelight3A = hardwareMap.get(Limelight3A.class,"limelight");




        limelight3A.pipelineSwitch(4 ); //3 is April tag 20, 4 is 21, 5 is 22, 6 is 23, 7 is 24
    }

    @Override
    public void start(){
        limelight3A.start();
    }

    @Override
    public void loop() {
        LLResult llResult = limelight3A.getLatestResult();
        if(llResult != null && llResult.isValid()){
            telemetry.addData("Target X offset", llResult.getTx());
            telemetry.addData("Target Y offset", llResult.getTy());
            telemetry.addData("Target Area offset", llResult.getTa());
        }
    }

}



