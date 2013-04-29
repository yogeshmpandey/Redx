package com.redx.dev;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Search {
	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";
	public static final String DATABASE_NAME = "DonorCity";
	public static final String DATABASE_TABLE = "searchci";
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
					PHONE + " INTEGER NOT NULL, "+
					EMAIL + " TEXT NOT NULL);"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	public Search(Context c){
		ourContext = c;		
	}
	public Search open(){
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
		cv.put(PHONE, phone2);
		cv.put(EMAIL,email2);
		return ourDatabase.insert(DATABASE_TABLE,null,cv);
	}
	public String getData() {
		String[] columns = new String[]{NAME,PHONE,EMAIL};
		Cursor c = ourDatabase.query(DATABASE_TABLE, null, null, null,null,null,null);
		String result="";
		int iName = c.getColumnIndex(NAME);
		int iPhone =c.getColumnIndex(PHONE);
		int iEmail = c.getColumnIndex(EMAIL);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			result = result+c.getString(iName)+"\n"+c.getString(iPhone)+"\n"+c.getString(iEmail)+",";
		}
		return result;		
	}
	public void deleteAll(){
		ourDatabase.delete(DATABASE_TABLE,null,null);
	}
	
}