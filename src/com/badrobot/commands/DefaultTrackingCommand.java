/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badrobot.commands;

import com.badrobot.utils.DetectedPoint;
import com.badrobot.utils.TrackingCriteria;

/**
 * On average one pass takes up around 200ms
 * definitely going to need to run this on either
 * the driver station, or on a Pi
 * @author ajtgarber
 */
public class DefaultTrackingCommand extends BadCommand {
    
    private boolean completed = false;
    
    public DefaultTrackingCommand() {
        requires(imageTrackingSystem);
    }

    public String getConsoleIdentity() 
    {
        return "DefaultTrackingCommand";
    }

    protected void initialize() 
    {
        setInterruptible(false);
    }

    protected void execute() 
    {
        /*
         * For later tests we should look at CPU usage...
         * and definitely check for memory leaks
         * Ryan was here
         */
        long startTime = System.currentTimeMillis();
        double aspectRatio = 8.75/5.875;
        TrackingCriteria criteria = new TrackingCriteria(aspectRatio, .95, 51, .95, 0, 255,
                                        0, 255, 0, 255);
        DetectedPoint[] detectedPoints = imageTrackingSystem.getTargetCoordinates(criteria);
        if(detectedPoints != null) {
            for(int i = 0; i < detectedPoints.length; i++) {
                DetectedPoint point = detectedPoints[i];
                if(point != null)
                    log("I has a point! ("+point.getX()+", "+point.getY()+")");
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime-startTime;
        log("Completed my work in "+duration+" milliseconds :3");
        try {
            Thread.sleep(750);
        } catch(Exception ex) {
        
        }
        completed = true;
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end() 
    {
    
    }

    protected void interrupted() 
    {
        log("Shouldn't have been interrupted... I had imporant work to do :c");
    }
    
}
