package trandat.smartlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * @author pirates
 *	Main activity
 */
public class MainActivity extends Activity {

	static private String PassWord = "admin";
	String temp_pass = "";
	Button btn_class1,btn_class2,btn_class3,btn_close,btn_add,btn_setting; //Button in this activity
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //set screen orientation on portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        //get resource to draw image button
        Resources resources = getResources();
        
        //initialize for button in this activity
        btn_class1 = (Button)findViewById(R.id.btn_class1);
        btn_class1.setBackground(resources.getDrawable(R.drawable.icon_btn_class1_config));
        
        btn_class2 = (Button)findViewById(R.id.btn_class2);
        btn_class2.setBackground(resources.getDrawable(R.drawable.icon_btn_class2_config));
        
        btn_class3 = (Button)findViewById(R.id.btn_class3);
        btn_class3.setBackground(resources.getDrawable(R.drawable.icon_btn_class3_config));
        
        btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setBackground(resources.getDrawable(R.drawable.btn_close));
        btn_add = (Button)findViewById(R.id.btn_addQuestion);
        btn_add.setBackground(resources.getDrawable(R.drawable.btn_add));
        btn_setting = (Button)findViewById(R.id.btn_setting);
        btn_setting.setBackground(resources.getDrawable(R.drawable.btn_setting));
        
        //Set event click on button: when any button is clicked, system will call the answer activity 
        //and set UserChoice to decide which grade questions will be loaded
        btn_class1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AnswerActivity.UserChoice = 1;
				CallFormAnswer();
			}
		});
        
        btn_class2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AnswerActivity.UserChoice = 2;
				CallFormAnswer();
			}
		});
        
        btn_class3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AnswerActivity.UserChoice = 3;
				CallFormAnswer();
			}
		});
        
        //set onclick event for button close, button setting, button add
        //button close: user must input correct password to terminate app
        btn_close.setOnClickListener(new OnClickListener() 
        {			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
				alertDialog.setTitle("Nhập mật khẩu");
				alertDialog.setMessage("Nhập mật khẩu");
	
				final EditText input = new EditText(MainActivity.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				     LinearLayout.LayoutParams.MATCH_PARENT,
				     LinearLayout.LayoutParams.MATCH_PARENT);
				input.setLayoutParams(lp);
				 alertDialog.setView(input);
				 //alertDialog.setIcon(R.drawable.key);
				
				 alertDialog.setPositiveButton("Xong",
				     new DialogInterface.OnClickListener() 
				 {
				         public void onClick(DialogInterface dialog, int which) 
				         {
				             temp_pass = input.getText().toString();
				             if (temp_pass != null) 
					         {
					             if (temp_pass.equals(PassWord)) 
					             {
					                 MainActivity.super.onBackPressed();
					             } 
					             else 
					             {
					                 Toast.makeText(getApplicationContext(),
					                     "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
					                 }
					             }
				         	}
				 });
				
				 alertDialog.setNegativeButton("Hủy",
				     new DialogInterface.OnClickListener() 
				 {
				         public void onClick(DialogInterface dialog, int which) 
				         {
				             dialog.cancel();
				         }
				 });
				
				 alertDialog.show();
			}
        });
    }
	
	@Override
	public void onBackPressed()
	{
		
	}
	
	public static void Exit()
	{
		
	}
	
	
	/**
	 * call answer activity
	 */
	public void CallFormAnswer()
    {
    	Intent answer_activity = new Intent(this,AnswerActivity.class);
		startActivity(answer_activity);
		super.onBackPressed();
    }
}
