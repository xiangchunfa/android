package com.zqds.client.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MyTextWatcher implements TextWatcher {

	private String TAG = "MyTextWatcher";
	private EditText numberEditText;
	int beforeLen = 0;  
    int afterLen = 0;
	
    
    
	public MyTextWatcher(EditText numberEditText) {
		super();
		this.numberEditText = numberEditText;
		
	}

	 public String removeAllSpace(String str) {  
	       String tmpstr=str.replace(" ","");  
	       return tmpstr;  
	   } 
	 /**
	  *是否需要空格 
	  */
	 public boolean isSpace(int length){
		   if(length%5==0){
			   return true;
		   }
		 return false;
	 }
	 
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		 String txt = numberEditText.getText().toString();  
		 Log.d(TAG, "mEditText = " + removeAllSpace(txt) + ".");
         afterLen = txt.length();  
         Log.d(TAG, "beforeLen = " + beforeLen + "afterLen = " + afterLen);
         if (afterLen > beforeLen) {  
             if (isSpace(afterLen)) {  
                 numberEditText.setText(new StringBuffer(txt).insert(  
                         txt.length() - 1, " ").toString());  
                 numberEditText.setSelection(numberEditText.getText()  
                         .length());  
                 Log.d(TAG, "selection = " +numberEditText.getText()  
                         .length());
             }  
         } else {  
             if (txt.startsWith(" ")) {  
                 numberEditText.setText(new StringBuffer(txt).delete(  
                         afterLen - 1, afterLen).toString());  
                 numberEditText.setSelection(numberEditText.getText()  
                         .length());  
                 Log.d(TAG, "else start space");
             }  
         }  
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		beforeLen = arg0.length();  
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

}
