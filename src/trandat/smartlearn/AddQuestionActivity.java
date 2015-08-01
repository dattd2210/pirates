package trandat.smartlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author pirates
 * Name: AddQuestionActivity
 * Purpose: Handle for user add question to database
 */
public class AddQuestionActivity extends Activity{

	Button btn_Ok, btn_Exit;
	EditText edtxt_grade,edtxt_content,edtxt_correctAnswer,edtxt_answer1,edtxt_answer2,edtxt_answer3;
	MyDatabase database = new MyDatabase(AddQuestionActivity.this);
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);
        
        //initialize component in this content view
        btn_Ok = (Button)findViewById(R.id.btn_OK);
        btn_Exit = (Button)findViewById(R.id.btn_Exit);
        
        edtxt_grade = (EditText)findViewById(R.id.edtxt_Grade);
        edtxt_content = (EditText)findViewById(R.id.edtxt_content);
        edtxt_correctAnswer = (EditText)findViewById(R.id.edtxt_correctAnswer);
        edtxt_answer1 = (EditText)findViewById(R.id.edtxt_Answer1);
        edtxt_answer2 = (EditText)findViewById(R.id.edtxt_Answer2);
        edtxt_answer3 = (EditText)findViewById(R.id.edtxt_Answer3);
        
        btn_Exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddQuestionActivity.this);
				alertDialog.setTitle("Thông báo");
				alertDialog.setMessage("Bạn có chắc chắn muốn thoát không?");
				
				 alertDialog.setPositiveButton("Đồng ý",
				     new DialogInterface.OnClickListener() 
				 {
				         public void onClick(DialogInterface dialog, int which) 
				         {
				        	 AddQuestionActivity.super.onBackPressed();
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
        
        //add correct value into the database
        btn_Ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edtxt_grade.getText().toString().equals("") ||
						edtxt_content.getText().toString().equals("") ||
						edtxt_correctAnswer.getText().toString().equals("") ||
								edtxt_answer1.getText().toString().equals("") ||
										edtxt_answer2.getText().toString().equals("") ||
												edtxt_answer3.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(),
		                     "Vui lòng nhập đầy đủ dữ liệu!", Toast.LENGTH_LONG).show();
				}
				else
				{	
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddQuestionActivity.this);
					alertDialog.setTitle("Thông báo");
					alertDialog.setMessage("Bạn có chắc chắn muốn thêm câu hỏi này không?");
					
					 alertDialog.setPositiveButton("Đồng ý",
					     new DialogInterface.OnClickListener() 
					 {
					         public void onClick(DialogInterface dialog, int which) 
					         {
					        	database.OpenConnecttion();
							    database.insert(Integer.parseInt(edtxt_grade.getText().toString()),
							    		edtxt_content.getText().toString(), edtxt_correctAnswer.getText().toString(),
							    		edtxt_answer1.getText().toString(), edtxt_answer2.getText().toString(), 
							    		edtxt_answer3.getText().toString());        
						        database.close();
						        Toast.makeText(getApplicationContext(), "Đã thêm thành công!", Toast.LENGTH_SHORT).show();
						        edtxt_answer1.setText("");
						        edtxt_answer2.setText("");
						        edtxt_answer3.setText("");
						        edtxt_content.setText("");
						        edtxt_correctAnswer.setText("");
						        edtxt_grade.setText("");
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
			}
		});
	}
}
