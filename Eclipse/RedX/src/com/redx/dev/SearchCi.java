package com.redx.dev;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SearchCi {
	public static final String NAME = "name";
	public static final String TIME = "time";
	public static final String VENUE = "venue";
	public static final String DATABASE_NAME = "CampCity";
	public static final String DATABASE_TABLE = "campci";
	public static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME,null,DATABASE_VERSION);			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {			
			db.execSQL("CREATE TABLE "+ DATABASE_TABLE + " ("+
					NAME + " TEXT NOT NULL, "+
					TIME + " TEXT NOT NULL, "+
					VENUE + " TEXT NOT NULL);"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	public SearchCi(Context c){
		ourContext = c;		
	}
	public SearchCi open(){
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	public void close(){
		ourHelper.close();
	}
	public long createEntry(String name2, String phone2, String email2) {
		ContentValues cv = new ContentValues();
		cv.put(NAME, name2);
		cv.put(TIME, phone2);
		cv.put(VENUE,email2);
		return ourDatabase.insert(DATABASE_TABLE,null,cv);
	}
	public String getData() {
		String[] columns = new String[]{NAME,TIME,VENUE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, null, null, null,null,null,null);
		String result="";
		int iName = c.getColumnIndex(NAME);
		int iTime =c.getColumnIndex(TIME);
		int iVenue = c.getColumnIndex(VENUE);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			String time = c.getString(iTime).replace('_','/');
			time = time.replace('.',':');
			String venue = c.getString(iVenue).replace('.',':');
			result = result+c.getString(iName)+"\n"+time+"\n"+venue+",";
		}
		return result;		
	}
	public void deleteAll(){
		ourDatabase.delete(DATABASE_TABLE,null,null);
	}
	
}