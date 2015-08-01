package trandat.smartlearn;

import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
	Vector<Integer> SetOfQuestions = new Vector<Integer>();
	Vector<Integer> randomAnswerDisplay = new Vector<Integer>();
	Integer element;
	Random random = new Random();
	MyDatabase database;
	Vector<Vector<String>> result = new Vector<Vector<String>>();
	
	String UserAnswer = "";
	int NumberOfCorrectAnswer = 0;
	
	Button btn_next,btn_check,btn_answer1,btn_answer2,btn_answer3,btn_answer4;
	TextView txt_content;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_answer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //set screen orientation on landscape
        
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
        element = 0;
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
        } 
        
        //handle database
        database = new MyDatabase(this);
        database.OpenConnecttion();
        database.LoadDataFromFile();
        result = database.GetDataBaseOnGradeAndID(UserChoice);        
        database.close();
        
        //initialize for text view content
        txt_content = (TextView)findViewById(R.id.txt_content);
        
        
        /*txt_content.setText(result.firstElement().firstElement());
        btn_answer1.setText(result.firstElement().elementAt(1));
        btn_answer2.setText(result.firstElement().elementAt(2));
        btn_answer3.setText(result.firstElement().elementAt(3));
        btn_answer4.setText(result.firstElement().elementAt(4));*/
        
        
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
				btn_check.setActivated(false);
				btn_check.setVisibility(View.INVISIBLE);
				btn_next.setActivated(true);
				btn_next.setVisibility(View.VISIBLE);
				
				if(UserAnswer.equals(result.firstElement().elementAt(1)))
				{
					NumberOfCorrectAnswer ++;
				}
			}
		});
		
		
	}
	
	/**
	 * Purpose: Display question for user
	 */
	public void Display()
	{
		//random sequence to display the answer in button answer
		element = 0;
        for(int i = 0; i< 4; i++)
        {
    		element = random.nextInt(30);
    		if(randomAnswerDisplay.contains(element) ||
    			element == 0)
    		{
    			//SetOfQuestions.remove(element);
    			i--;
    		}
    		else
    			randomAnswerDisplay.add(element);
        } 
        
        //display question and answer in to correspond place
        while(!SetOfQuestions.isEmpty())
        {
        	txt_content.setText(result.elementAt(SetOfQuestions.firstElement()).firstElement());
        	btn_answer1.setText(result.elementAt(SetOfQuestions.firstElement()).elementAt(randomAnswerDisplay.elementAt(0)));
            btn_answer2.setText(result.elementAt(SetOfQuestions.firstElement()).elementAt(randomAnswerDisplay.elementAt(1)));
            btn_answer3.setText(result.elementAt(SetOfQuestions.firstElement()).elementAt(randomAnswerDisplay.elementAt(2)));
            btn_answer4.setText(result.elementAt(SetOfQuestions.firstElement()).elementAt(randomAnswerDisplay.elementAt(3)));
        }
	}
}