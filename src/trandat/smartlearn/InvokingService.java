package trandat.smartlearn;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

public class InvokingService extends Service {

private Handler customHandler = new Handler();
	
	public static boolean isCallMainActivity = false;
	public static boolean isLocked = true;
	
	private long startTime = 0L;
	long timeInMilliseconds = 0L;
	public static long timeLimitted = 8 * 1000L;
	Intent i;
	
	// 
	public void setTimeLimitted(long time) {
		timeInMilliseconds = time;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
    
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		//Toast.makeText(this, "Service is created", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "Service is started", Toast.LENGTH_LONG).show();

		// Start time counting
		startTime = SystemClock.uptimeMillis();
		customHandler.postDelayed(updateTimerThread, 0);
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//Toast.makeText(this, "Service is stopped " + timeInMilliseconds + " miliseconds", Toast.LENGTH_LONG).show();
	}
	
	private Runnable updateTimerThread = new Runnable() {
		public void run() {
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			customHandler.postDelayed(this, 0);
			
			// If time counting >= time limitted, do something and stop service
			if ((timeInMilliseconds >= timeLimitted) && (isCallMainActivity) && (isLocked)) {
				// TODO something
				
				if(i == null)
				{
					//android.os.Process.killProcess(android.os.Process.myPid());
                    //System.exit(1);
					i = new Intent(getApplicationContext(),MainActivity.class);
				}
				//Intent i = new Intent(getApplicationContext(),MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				//android.os.Process.killProcess(android.os.Process.);
	            startActivity(i);
	            isCallMainActivity = false;
				
				// Stop time counting
				customHandler.removeCallbacks(updateTimerThread);
				// Stop service
				onDestroy();
			}
		}
		
	};
	
	// Call main application
	public void callApplication() {
	
	}
	
}
