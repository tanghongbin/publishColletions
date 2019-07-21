package publish.tang.common.commonutils;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;


public class ShakeListenerModule implements SensorEventListener {

    private  Vibrator vibrator;
    private  SensorManager sensorManager;
    Context context;
    public ShakeListenerModule(Context context) {
        super();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴    
        float[] values = event.values;

        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
  
            /*正常情况下，任意轴数值最大就在9.8~10之间，只有在突然摇动手机  
              的时候，瞬时加速度才会突然增大或减少。   监听任一轴的加速度大于17即可 
            */
            if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                    .abs(values[2]) > 17)) {
                vibrator.vibrate(500);
                if(onSensorChangeListener != null){
                    onSensorChangeListener.sensorChangeListener();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //当传感器精度改变时回调该方法，Do nothing.   
    }
    OnSensorChangeListener onSensorChangeListener;

    public void setOnSensorChangeListener(OnSensorChangeListener onSensorChangeListener) {
        if(register = false) throw new RuntimeException("必须先注册调用register()方法");
        this.onSensorChangeListener = onSensorChangeListener;
    }

    public interface OnSensorChangeListener{
        void sensorChangeListener();
    }

    boolean register;
    public void register(){
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        register = true;
    }
    public void unRegister(){
        sensorManager.unregisterListener(this);
        register = false;
    }

}  