package trandat.smartlearn;

import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author pirates
 *	Name: answer activity
 *	Purpose: connect to database and load correspond question when user click any button on MainActivity
 */
public class AnswerActivity extends Activity {

	static public int UserChoice = 0;
	//Vector<Integer> SetOfQuestions = new Vector<Integer>();
	//Vector<Integer> randomAnswerDisplay = new Vector<Integer>();
	//Integer element;
	Random random = new Random();
	MyDatabase database;
	Vector<Vector<String>> result = new Vector<Vector<String>>();
	
	String UserAnswer = "";
	int NumberOfCorrectAnswer = 0;
	
	Button btn_next,btn_check,btn_answer1,btn_answer2,btn_answer3,btn_answer4;
	TextView txt_content,txt_numOfCRemainQuestion;
	
	//sounds
	MediaPlayer correctVoice, wrongVoice;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_answer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //set screen orientation on landscape
        
        //initialize sounds
        correctVoice = MediaPlayer.create(this, R.raw.correctanswer);
        wrongVoice = MediaPlayer.create(this, R.raw.wronganswer);
        
        
        //initialize animation for correct answer when user pressed check button
        final Animation animation = new AlphaAnimation(1, (float)0.3); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(6); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        
        //initialize for button check and button next
        Resources resources = getResources();
        btn_next = (Button)findViewById(R.id.btn_next);
        btn_next.setBackground(resources.getDrawable(R.drawable.btn_next));
        
        btn_check = (Button)findViewById(R.id.btn_check);
        btn_check.setBackground(resources.getDrawable(R.drawable.btn_check));
        
        //invisible button next
        btn_next.setActivated(false);
        btn_next.setVisibility(View.INVISIBLE);
        
        //set color for button
        btn_answer1 = (Button)findViewById(R.id.btn_answer1);
        btn_answer1.setBackgroundColor(Color.rgb(248, 161, 48));
        
        btn_answer2 = (Button)findViewById(R.id.btn_answer2);
        btn_answer2.setBackgroundColor(Color.rgb(248, 161, 48));
        
        btn_answer3 = (Button)findViewById(R.id.btn_answer3);
        btn_answer3.setBackgroundColor(Color.rgb(248, 161, 48));
        
        btn_answer4 = (Button)findViewById(R.id.btn_answer4);
        btn_answer4.setBackgroundColor(Color.rgb(248, 161, 48));
        
        //random sequence of question to display for user
        /*element = 0;
        for(int i = 0; i< 30; i++)
        {
    		element = random.nextInt(30);
    		if(SetOfQuestions.contains(element) ||
    			element == 0)
    		{
    			//SetOfQuestions.remove(element);
    			i--;
    		}
    		else
    			SetOfQuestions.add(element);
        } */
        
        //initialize for text view content
        txt_content = (TextView)findViewById(R.id.txt_content);
        txt_numOfCRemainQuestion = (TextView)findViewById(R.id.txt_NumOfRemainQuestion);
        
        
        HandleDatabase();
        Display();
        
        txt_numOfCRemainQuestion.setText(30-result.size() + 1 +"/30");
        
        /*txt_content.setText(result.firstElement().firstElement());
        btn_answer1.setText(result.firstElement().elementAt(1));
        btn_answer2.setText(result.firstElement().elementAt(2));
        btn_answer3.setText(result.firstElement().elementAt(3));
        btn_answer4.setText(result.firstElement().elementAt(4));*/
        
        
        //set event click on answer button, check button and next button:
        //change color when click on button answer
        //visible next button when click on check button
        //move to next question when click on next button
        btn_answer1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_answer1.setBackgroundColor(Color.CYAN);
		        btn_answer2.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer3.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer4.setBackgroundColor(Color.rgb(248, 161, 48));
		        
		        UserAnswer = btn_answer1.getText().toString();
			}
		});
        
        
        btn_answer2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_answer1.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer2.setBackgroundColor(Color.CYAN);
		        btn_answer3.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer4.setBackgroundColor(Color.rgb(248, 161, 48));
		        
		        UserAnswer = btn_answer2.getText().toString();
			}
		});


		btn_answer3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_answer1.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer2.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer3.setBackgroundColor(Color.CYAN);
		        btn_answer4.setBackgroundColor(Color.rgb(248, 161, 48));
		        
		        UserAnswer = btn_answer3.getText().toString();
			}
		});
		
		
		btn_answer4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_answer1.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer2.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer3.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer4.setBackgroundColor(Color.CYAN);
		        
		        UserAnswer = btn_answer4.getText().toString();
			}
		});

		
		btn_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//visible button next
				btn_check.setActivated(false);
				btn_check.setVisibility(View.INVISIBLE);
				btn_next.setActivated(true);
				btn_next.setVisibility(View.VISIBLE);
				
				if(btn_answer1.getText().toString().equals(result.firstElement().elementAt(1)))
					btn_answer1.setAnimation(animation);
				if(btn_answer2.getText().toString().equals(result.firstElement().elementAt(1)))
					btn_answer2.setAnimation(animation);
				if(btn_answer3.getText().toString().equals(result.firstElement().elementAt(1)))
					btn_answer3.setAnimation(animation);
				if(btn_answer4.getText().toString().equals(result.firstElement().elementAt(1)))
					btn_answer4.setAnimation(animation);
				
				if(UserAnswer.equals(result.firstElement().elementAt(1)))
				{
					correctVoice.start();
					NumberOfCorrectAnswer ++;
				}
				else
					wrongVoice.start();
				
				//update priority of this question (the question will be showed to user)
				database.OpenConnecttion();
	            database.updatePriority(Integer.parseInt(result.firstElement().elementAt(5)), 
	            		Integer.parseInt(result.firstElement().elementAt(6)) + 1);
	            database.close();
				
			}
		});
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//correctVoice.stop();
				//wrongVoice.stop();
				
				//clear animation
				btn_answer1.clearAnimation();
				btn_answer2.clearAnimation();
				btn_answer3.clearAnimation();
				btn_answer4.clearAnimation();
				
				//make all button back to the original color
				btn_answer1.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer2.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer3.setBackgroundColor(Color.rgb(248, 161, 48));
		        btn_answer4.setBackgroundColor(Color.rgb(248, 161, 48));
				
		        //hide next button and show check button
				btn_check.setActivated(true);
				btn_check.setVisibility(View.VISIBLE);
				btn_next.setActivated(false);
				btn_next.setVisibility(View.INVISIBLE);
				
				if(30-result.size() + 1 == 30)
				{
					//terminate this app when user answer correct number question equal with the given number
					if(NumberOfCorrectAnswer >= 5)
					{
						InvokingService.isCallMainActivity = false;
						android.os.Process.killProcess(android.os.Process.myPid());
	                    System.exit(1);
	                    MainActivity.Exit();
					}
					else
					{
						HandleDatabase();
						NumberOfCorrectAnswer = 0;
					}
				}
				else
					result.remove(0);
				//update number of remain question
				txt_numOfCRemainQuestion.setText(30-result.size() + 1 +"/30");
				
				if(!result.isEmpty())
					Display();
			}
		});
		
	}
	
	/**
	 * Purpose: Display question for user
	 */
	public void Display()
	{
		//random sequence to display the answer in button answer
		/*element = 0;
        for(int i = 0; i< 4; i++)
        {
    		element = random.nextInt(4);
    		if(randomAnswerDisplay.contains(element) ||
    			element == 0)
    		{
    			//SetOfQuestions.remove(element);
    			i--;
    		}
    		else
    			randomAnswerDisplay.add(element);
        }*/ 
		
		int[] randomAnswerDisplay = new int[4];
		randomAnswerDisplay[0] = random.nextInt(3)+1;
		switch(randomAnswerDisplay[0])
		{
		case 1:
			randomAnswerDisplay[1] = 2;
			randomAnswerDisplay[2] = 3;
			randomAnswerDisplay[3] = 4;
			break;
		case 2:
			randomAnswerDisplay[1] = 1;
			randomAnswerDisplay[2] = 3;
			randomAnswerDisplay[3] = 4;
			break;
		case 3:
			randomAnswerDisplay[1] = 1;
			randomAnswerDisplay[2] = 2;
			randomAnswerDisplay[3] = 4;
			break;
		case 4:
			randomAnswerDisplay[1] = 1;
			randomAnswerDisplay[2] = 2;
			randomAnswerDisplay[3] = 3;
			break;
		}
        
        //display question and answer in to correspond place
        //while(!SetOfQuestions.isEmpty())
        {
        	/*txt_content.setText(result.elementAt(SetOfQuestions.firstElement()).firstElement());
        	btn_answer1.setText(result.elementAt(SetOfQuestions.firstElement()).elementAt(randomAnswerDisplay.elementAt(0)));
            btn_answer2.setText(result.elementAt(SetOfQuestions.firstElement()).elementAt(randomAnswerDisplay.elementAt(1)));
            btn_answer3.setText(result.elementAt(SetOfQuestions.firstElement()).elementAt(randomAnswerDisplay.elementAt(2)));
            btn_answer4.setText(result.elementAt(SetOfQuestions.firstElement()).elementAt(randomAnswerDisplay.elementAt(3)));*/
        	
        	txt_content.setText(result.firstElement().firstElement());
        	btn_answer1.setText(result.firstElement().elementAt(randomAnswerDisplay[0]));
            btn_answer2.setText(result.firstElement().elementAt(randomAnswerDisplay[1]));
            btn_answer3.setText(result.firstElement().elementAt(randomAnswerDisplay[2]));
            btn_answer4.setText(result.firstElement().elementAt(randomAnswerDisplay[3]));
            
        }
	}
	
	/**
	 * Handle database
	 */
	public void HandleDatabase()
	{
        database = new MyDatabase(this);
        database.OpenConnecttion();
        //database.LoadDataFromFile();
        result = database.GetDataBaseOnGradeAndID(UserChoice);        
        database.close();
	}
	
	@Override
	public void onBackPressed()
	{
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		InvokingService.isCallMainActivity = false;
		
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		InvokingService.isCallMainActivity = true;
		startService(new Intent(this, InvokingService.class));
		super.onPause();
		//super.onDestroy();
	}
}
