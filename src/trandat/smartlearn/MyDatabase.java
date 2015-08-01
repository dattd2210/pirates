package trandat.smartlearn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author pirates
 *	Name: MyDatabase
 *	Purpose: Handle anything involve database such as: connect database, insert, update, delete or get data form 
 *			database
 */
public class MyDatabase {
	private static final String DATABASE_NAME = "QUESTIONS";
	private static final String TABLE_NAME = "Question";
	private static final String QuestionID = "QuestionID";
	private static final String Grade = "Grade";
	private static final String Content = "Content";
	private static final String Answer1 = "Answer1";
	private static final String Answer2 = "Answer2";
	private static final String Answer3 = "Answer3";
	private static final String Answer4 = "Answer4";
	private static final String Priority = "Priority";
	
	
	private static SQLiteDatabase database;
	private static Context context;
	private OpenHelper openHelper;
	
	/**
	 * Constructor
	 * @param c
	 */
	public MyDatabase(Context c)
	{
		MyDatabase.context = c;
	}
	
	/**
	 * Purpose: connect to database
	 * @return
	 * @throws SQLException
	 */
	public MyDatabase OpenConnecttion() throws SQLException
	{
		openHelper = new OpenHelper(context);
		database = openHelper.getWritableDatabase();
		return this;
	}
	
	/**
	 * close connect 
	 */
	public void close()
	{
		openHelper.close();
	}
	
	/**
	 * load SQL statement from text file
	 */
	public void LoadDataFromFile()
	{
		BufferedReader br  = null;
		try 
		{			 
			AssetManager am = context.getAssets();
			InputStream is = am.open("SQLQuery.txt"); //text file in assets folder
			
			br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			
			String temp = "";
			
			while((temp = br.readLine()) != null)
			{
				try
				{
					database.execSQL(temp);
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				
			}
			br.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		/*database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		database.execSQL("CREATE TABLE " + TABLE_NAME + " ("
				+ QuestionID + " INT PRIMARY KEY, "
				+ Grade  + " INT, "
				+ Content  + " TEXT, "
				+ Answer1  + " TEXT, " 
				+ Answer2  + " TEXT, "
				+ Answer3  + " TEXT, "
				+ Answer4  + " TEXT, "
				+ Priority + " INT);");*/
	}
	
	/**
	 * 
	 * @param ID
	 * @return
	 */
	/*public Vector<Vector<String>> GetDataBaseOnGradeAndID(Vector<Integer> ID)
	{
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		
		//String[] columns = new String[] {QuestionID,Grade,Content,Answer1,Answer2,Answer3,Answer4};
		//Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
		
		int questionID = 0;
		String SelectQuery;
		
		//String[] columns = new String[] {Content,Answer1,Answer2,Answer3,Answer4};
		Cursor cursor ;//= database.query(TABLE_NAME, columns, null, null, null, null, null);
		SelectQuery = "SELECT  * FROM " + TABLE_NAME;
		cursor = null;			
		
		cursor = database.rawQuery(SelectQuery, null);
		
		
		while(!ID.isEmpty())
		{
			questionID = ID.firstElement();
			cursor.moveToPosition(questionID-1);
			
			if(cursor != null && cursor.getCount()>0)
			{
				//cursor.moveToFirst();
				Vector<String> element = new Vector<String>();
				for(int i = 2; i< 7; i++)
				{
					if(cursor.getString(i) != null)
						element.add(cursor.getString(i));
				}
				element.add(cursor.toString());
				element.add(cursor.getString(cursor.getColumnIndex(Content)));
				//cursor.close();
				result.add(element);
			}
			ID.remove(0);
		}
		
		return result;
	}*/
	
	
	
	public Vector<Vector<String>> GetDataBaseOnGradeAndID(int grade)
	{
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		
		//String[] columns = new String[] {QuestionID,Grade,Content,Answer1,Answer2,Answer3,Answer4};
		//Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
		
		int count = 0;
		//String SelectQuery;
		
		//String[] columns = new String[] {Content,Answer1,Answer2,Answer3,Answer4};
		Cursor cursor ;//= database.query(TABLE_NAME, columns, null, null, null, null, null);
		/*SelectQuery = "SELECT  * FROM " + TABLE_NAME;
		cursor = null;			
		
		cursor = database.rawQuery(SelectQuery, null);*/
		String[] columns = {Content,Answer1,Answer2,Answer3,Answer4,QuestionID, Priority};
		cursor = database.query(TABLE_NAME, columns, "Grade=?", new String[]{""+grade}, null, null, Priority);
		
		if(cursor != null && cursor.getCount() > 0)
		{
			cursor.moveToFirst();
			while(count < 30 && !cursor.isLast())
			{
				Vector<String> element = new Vector<String>();
				for(int i = 0; i< 7; i++)
				{
					if(cursor.getString(i) != null)
						element.add(cursor.getString(i));
				}
				result.add(element);
				
				cursor.moveToNext();
				count ++;
			}
		}
		
		return result;
	}
	
	/**
	 * Update Priority of a question
	 * @param ID: ID of question want to udpate
	 * @param newPriority
	 */
	public void updatePriority(int ID, int newPriority)
	{
		 ContentValues data = new ContentValues();
		 data.put(Priority,newPriority);
		 database.update(TABLE_NAME, data, QuestionID +" = " + ID, null);
	}
	
	/**
	 * Purpose: add new question into database
	 * @param Content: content of question
	 * @param Answer1: correct answer
	 * @param Answer2
	 * @param Answer3
	 * @param Answer4
	 */
	public void insert(int grade, String content,String answer1,String answer2,String answer3,String answer4)
	{
		int questionID = getNumberOfRecordInDB() + 2;
		ContentValues data = new ContentValues();
		data.put(Grade, grade);
		data.put(QuestionID, questionID);
		data.put(Content, content);
		data.put(Answer1, answer1);
		data.put(Answer2, answer2);
		data.put(Answer3, answer3);
		data.put(Answer4, answer4);
		data.put(Priority,0);
		database.insert(TABLE_NAME, null, data);
	}
	
	/**
	 * Purpose: get number of record in database
	 * @return
	 */
	public int getNumberOfRecordInDB()
	{
		return database.rawQuery("Select * from "+TABLE_NAME, null).getCount();
	}
	
	

	/**
	 * 
	 * @author pirates
	 *	Class OpenhHelper
	 *	Purpose: Handler for Create table and Upgrade table in database
	 */
	private static class OpenHelper extends SQLiteOpenHelper {
		public OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, 1);
		}
	
		@Override
		public void onCreate(SQLiteDatabase arg0) {
			//arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			arg0.execSQL("CREATE TABLE " + TABLE_NAME + " ("
					+ QuestionID + " INT PRIMARY KEY, "
					+ Grade  + " INT, "
					+ Content  + " TEXT, "
					+ Answer1  + " TEXT, " 
					+ Answer2  + " TEXT, "
					+ Answer3  + " TEXT, "
					+ Answer4  + " TEXT, "
					+ Priority + " INT);");
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(arg0);
		}
	}
}
