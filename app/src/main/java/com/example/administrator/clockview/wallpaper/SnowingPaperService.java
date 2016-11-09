package com.example.administrator.clockview.wallpaper;

import android.app.WallpaperManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/**
 * Purpose     :壁纸管理服务
 * Description :
 * Author      : FLY
 * Date        : 2016.10.08 13:56
 */

public class SnowingPaperService extends WallpaperService {
    SnowingPaperService() {
    }

    @Override
    public WallpaperService.Engine onCreateEngine() {
        return new SnowingEngine();
    }


    public class SnowingEngine extends WallpaperService.Engine implements SensorEventListener {


        @Override
        public void onSensorChanged(SensorEvent event) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
        }
    }

}
