package com.example.grade_tracker;



import java.util.List;
import java.util.Map;

import big.bang.grade_tracker.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class BaseActivity extends Activity 
{
	public 	int Er_Fail_Transmit = 2000001;
	public  int Er_Fail_HostMessage = 2000002;
	public  int Er_Success = 2000000;
	private int myViewId;
	private String transFail = "";
	public int maxpositions;
	
	Activity myActivity;
    ProgressDialog myProgressDialog = null;
    public TextView iTxtTitle = null; 
	private	int mMesgNum = 0;
	private int Messg_Item_Click = 1;
	int hi;
	
	public static class Globals{
		static String selected_class;
	}
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
    }

    public void zSetTitle(String title)
    {
    	if (iTxtTitle != null)
    		iTxtTitle.setText(title);
    }
    
	public void zSetBusy( String Tag, String SubTitle, String CustMessage)
	{
	    // Display an indeterminate Progress-Dialog \
        myProgressDialog = ProgressDialog.show(this,SubTitle, getString(R.string.please_wait_wait), true);
        final String sTag = Tag;
        new Thread() { 
            public void run(){
 				try
 				{
 					final Object nResl =  zRunThread(sTag);
 		        	runOnUiThread(new Runnable() {
 		    			@SuppressWarnings("unchecked")
						public void run()
 		    			{
 		 					//zRunThread(sTag, (HashTableIg<String>) nResl);
 		    			}
 		        	});
				} 
				catch (Exception e)
				{				
					int zz =1;
				}
				// Dismiss the Dialog 
                myProgressDialog.dismiss();
       		 }
        }.start();
    }
	
	
	public Object zRunThread(String sTag)
	{
		return null;
	}

	/*public void zRunThread(String sTag, HashTableIg<String> nResl)
	{
	}*/
	
	public String zGetVar(String idName)
	{
	        return BaseRout.getVar(idName, "");
	}

    public void zSetVar(String idName, String newValue)
	{
	        BaseRout.setVar(idName, newValue);
	}

	public  ListView zListViewfId(int whichId)
	{
		return (ListView) findViewById(whichId);	
	}
	
	public RadioGroup zRadioGroupfId(int whichId)
	{
		return (RadioGroup)findViewById(whichId);
	}
	
	public void zCreateRadioGroup(int whichId, String[] options, String[] tags, Context c, int defaultopt)
	{
		RadioButton[] roptions = new RadioButton[options.length];
		for (int x = 0; x< roptions.length; x++)
		{
			roptions[x] = new RadioButton(c);
			roptions[x].setText("   " + options[x]);
			if (tags != null)
				roptions[x].setTag(tags[x]);
			zRadioGroupfId(whichId).addView(roptions[x], x);
		}
		if (defaultopt >=0)
			((RadioButton)zRadioGroupfId(whichId).getChildAt(defaultopt)).setChecked(true);
		else
			((RadioButton)zRadioGroupfId(whichId).getChildAt(0)).setChecked(true);
	}
	
	public void zCreateRadioGroup(int whichId, String[] options, String[] tags, Context c)
	{
		zCreateRadioGroup(whichId, options, tags, c, -1);
	}
	public void zCreateRadioGroup(int whichId, String[] options, Context c)
	{
		zCreateRadioGroup(whichId, options, null, c);
	}
	
	public RadioButton zRadioButtonfId(int whichId)
	{
		return (RadioButton)findViewById(whichId);
	}
	
	public RadioButton zGetCheckedRadioButton(int whichId)
	{
		if (zRadioGroupfId(whichId).getCheckedRadioButtonId() >0)
			return (RadioButton) findViewById(zRadioGroupfId(whichId).getCheckedRadioButtonId());
		else
			return null;
	}
	
	public String zGetCheckedRadioText(int whichId)
	{
		return zGetCheckedRadioButton(whichId).getText().toString();
	}
	
	public String zGetCheckedRadioTag(int whichId)
	{
		return zGetCheckedRadioButton(whichId).getTag().toString();
	}
	
	public int zGetCheckedRadioIndex(int whichId)
	{
		for (int x=0; x< zRadioGroupfId(whichId).getChildCount(); x++){
			if (zRadioGroupfId(whichId).getChildAt(x) == zGetCheckedRadioButton(whichId))
				return x;
		}
		return -1;
	}
	
	public ImageView zImageViewfId(int whichId)
	{
		return (ImageView) findViewById(whichId);
	}

	public  Button zButtonfId(int whichId)
	{
		return (Button) findViewById(whichId);	
	}

	public  void zButtonSetText(int whichId, String newValue)
	{
		zButtonfId(whichId).setText(newValue);	
	}

	public  String zButtonGetText(int whichId)
	{
		return zButtonfId(whichId).getText().toString();	
	}
	
	public  ListView zLoadListView(int whichId, String[] COUPONS, Boolean hookClick, String tag)
	{
        ListView mCouponList = (ListView) findViewById(whichId);
        mCouponList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, COUPONS));
        if (hookClick)
        	zHookListView(tag, whichId);
        return mCouponList;
	}
	
	public ListView zLoadListView(int whichId, String[] COUPONS, int layout, Boolean hookClick, String tag)
	{
		ListView mCouponList = (ListView) findViewById(whichId);
        mCouponList.setAdapter(new ArrayAdapter<String>(this, layout, COUPONS));
        if (hookClick)
        	zHookListView(tag, whichId);
        return mCouponList;
	}
	
	public  ListView zLoadListView(int whichId, String[] COUPONS)
	{
        return zLoadListView(whichId, COUPONS, false, null);
	}
	
	public void zHookListView(String tag, int whichId)
	{	
        ListView mCouponList = (ListView) findViewById(whichId);
        if (tag != null)
        	mCouponList.setTag(tag);
        mCouponList.setOnItemClickListener(new OnItemClickListener() 
        {
        	 public void onItemClick(AdapterView<?> a, View v, int position, long id) 
        	 {
        		 OnListClick((String) v.getTag(), a, v, position, id);
        	 }
        });
    }

	public void OnListClick(String Tag, AdapterView<?> a, View v, int position, long id) 
	{
	}

	public String zGetToggleButtonValue(int whichId)
	{
		ToggleButton tb = (ToggleButton) this.zButtonfId(whichId);
		if (tb.isChecked())
			return "Y";
		return"N";
	}
	
	public String zGetToggleTag (int whichId)
	{
		if(this.zGetToggleButtonValue(whichId)=="Y")
			return ("Y-" + this.zButtonfId(whichId).getTag().toString());
		return ("N-"+this.zButtonfId(whichId).getTag().toString());
	}
	
	public void zSetToggleButton(Boolean ischecked, int whichId)
	{
		ToggleButton tb = (ToggleButton) this.zButtonfId(whichId);
		if (ischecked)
			tb.setChecked(ischecked);
		else
			tb.setChecked(ischecked);
	}

	
	public void zHookButtonView(String tag, int whichId)
	{	
        Button mCouponList = zButtonfId(whichId);
        if (tag != null)
        	mCouponList.setTag(tag);
        mCouponList.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View arg0) {
				String Tag = (String) arg0.getTag();
				if (Tag != null)
				{
					if (Tag.equalsIgnoreCase("@Title_Back")){
						OnTitleBack( (String) arg0.getTag(), arg0);
						return;
					}
				}
				OnButtonClick((String) arg0.getTag(), arg0);
			}
        });
    }


	public void OnTitleBack( String Tag, View dialog) 
	{
		finish();
	}
	
	
	public void OnButtonClick( String Tag, View dialog) 
	{
	}

	
	public void zHookEditText(int whichId, String tag)
	{	
        EditText mCouponList = this.zEditTextfId(whichId);
        if (tag != null)
        	mCouponList.setTag(tag);
        	/*mCouponList.addTextChangedListener(new TextWatcher(mCouponList) { 
        	 
            @Override 
            public void handle_onTextChanged(int vid, CharSequence s, int start, int before, int count) 
            { 
            	OnTextChanged(vid, s, start, before, count); 
            } 
       
            @Override 
            public void handle_beforeTextChanged(int vid, CharSequence s, int start, int count, int after) { 
            	OnBeforeTextChanged(vid,s,start, count, after); 
            } 
       
            @Override 
            public void handle_afterTextChanged(int vid, Editable s) {
            	OnAfterTextChanged(vid); 
            }
        });*/
    }
	

	public boolean zCheckEditEmpty(int keyId, String erMesg)
	{
		if (this.zGetEditText(keyId).equalsIgnoreCase(""))
		{
			this.zEditTextfId(keyId).setError(erMesg);
			return true;
		}
		return false;
	}

	public boolean zCheckEditLen(int keyId, int minLen, String erMesg, int maxLen, String erMesg2 )
	{
		int bufLen = this.zGetEditText(keyId).length();
		if ( minLen >= 0)
		{
			if (bufLen < minLen)
			{
				this.zEditTextfId(keyId).setError(erMesg);
				return true;
			}
		}
		if ( maxLen >= 0)
		{
			if (bufLen > maxLen)
			{
				this.zEditTextfId(keyId).setError(erMesg2);
				return true;
			}
		}
		return false;
	}
	
	
    public void OnTextChanged(int vid, CharSequence s, int start, int before, int count)     { 
    } 

    public void OnBeforeTextChanged(int vid, CharSequence s, int start, int count, int after) { 
    } 

    public void OnAfterTextChanged(int vid) { 
    }
	
	
	public void zHookTouchView(int whichId, String tag)
	{	
        TextView mCouponList = zTextViewfId(whichId);
        if (tag != null)
        	mCouponList.setTag(tag);
        mCouponList.setOnTouchListener(new OnTouchListener(){
        	public boolean onTouch(View v, MotionEvent event) 
            {
            		return OnTouchClick((String) v.getTag(), v, event, event.getAction());
            }
        });
    }

	public boolean OnTouchClick(String Tag, View v, MotionEvent event, int evt_action)
	{
			return true;
	}
	

	public  String zGetTextView(int whichId)
	{
		return zTextViewfId(whichId).getText().toString();	
	}

	public  void zSetTextView(int whichId, String newValue)
	{
		TextView aTxt = zTextViewfId(whichId);
		if (aTxt != null)
			aTxt.setText(newValue);	
	}
	
	
	public  TextView zTextViewfId(int whichId)
	{
		return (TextView) findViewById(whichId);	
	}
	
	public  String zGetEditText(int whichId)
	{
		return zEditTextfId(whichId).getText().toString();	
	}

    public void zSetEditText(boolean b, int txtUserid, String string) {
    	if (b)
    		zSetEditText(txtUserid, zGetVar(string));
    	else
    		zSetVar(string, zGetEditText(txtUserid));
	}
	
	public  void zSetEditText(int whichId, String newValue)
	{
		zEditTextfId(whichId).setText(newValue);	
	}
	
	
	public  EditText zEditTextfId(int whichId)
	{
		EditText et = (EditText) findViewById(whichId);
		return et;	
	}

	
	private DialogInterface.OnClickListener onClickOK = new DialogInterface.OnClickListener() {
	    @Override		
		public void onClick(DialogInterface dialog, int which) {
			OnClickOK(mMesgNum , dialog, which);
		}
	};
	
	private DialogInterface.OnClickListener onClickCancel = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			OnClickCancel(mMesgNum , dialog, which);
		}
	};
	
	private View.OnClickListener onClickok = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			OnClickOK(mMesgNum, null, 0, v);
			
		}
	};	
	
	
	public  Spinner zSpinnerfId(int whichId)
	{
		return (Spinner) findViewById(whichId);	
	}

	public  String zSpinnerGetText(int whichId)
	{
		return ((Spinner) findViewById(whichId)).getSelectedItem().toString();	
	}

	public  int zSpinnerSelectedIndex(int whichId)
	{
		return ((Spinner) findViewById(whichId)).getSelectedItemPosition();	
	}
	
	public  Spinner zLoadSpinner(int whichId, String[] COUPONS, Boolean hookClick, String tag, int R_layout)
	{
		Spinner mCouponList = (Spinner) findViewById(whichId);
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, COUPONS);
        ad.setDropDownViewResource(R_layout);
        mCouponList.setAdapter(ad);
        if (hookClick)
        	zHookSpinner(tag, whichId);
        return mCouponList;
	}

	
	public  Spinner zLoadSpinner(int whichId, String[] COUPONS)
	{
        return zLoadSpinner(whichId, COUPONS, false, null, android.R.layout.simple_spinner_dropdown_item);
	}
	
	public void zHookSpinner(String tag, int whichId)
	{	
		Spinner mCouponList = (Spinner) findViewById(whichId);
        if (tag != null)
        	mCouponList.setTag(tag);
        mCouponList.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				OnSpinnerItemSelected((String) arg1.getTag(), arg0, arg1, arg2, arg3);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
    }

	public void OnSpinnerItemSelected(String Tag, AdapterView<?> arg0, View arg1, int arg2, long arg3) { 
	}
	

   public void OnClickOK(int mesgNum, DialogInterface dialog, int which) 
   {
   }
   
   public void OnClickOK(int mesgNum, AlertDialog dialog, int which, View layout){
	   
   }
	
	public void OnClickCancel(int mesgNum, DialogInterface dialog, int which) 
	{
	}
	
	public void OnClickCancel(int mesgNum, AlertDialog dialog, int which, View layout){
		
	}

	public void MessageBox(int mesgNum, String Title, String Message, Boolean doOK, Boolean doCancel)
	{
		MessageBox(mesgNum, -1, Title, Message, doOK, doCancel, getResources().getString(R.string.ok), getResources().getString(R.string.cancel));
	}
	
	public void MessageBox(int mesgNum, String Title, String Message, Boolean doOK, Boolean doCancel, String ok, String cancel){
		MessageBox(mesgNum, -1, Title, Message, doOK, doCancel, ok, cancel);
	}
	
	public void MessageBox(int mesgNum, int which_Icon, String Title, String Message, Boolean doOK, Boolean doCancel){
		MessageBox(mesgNum, which_Icon, Title, Message, doOK, doCancel, getResources().getString(R.string.ok), getResources().getString(R.string.cancel));
	}
	
	public void MessageBox(int mesgNum, int which_Icon, String Title, String Message, Boolean doOK, Boolean doCancel, String ok, String cancel)
	{
		mMesgNum  = mesgNum;
		AlertDialog.Builder aDiag = new AlertDialog.Builder(this);
		aDiag.setTitle(Title); 
		aDiag.setMessage(Message);
		aDiag.setCancelable(false);

		aDiag.setPositiveButton(ok, onClickOK); 
		
		if 	(doCancel)
			aDiag.setNegativeButton(cancel, onClickCancel);
		if (which_Icon > 0)
			aDiag.setIcon(which_Icon);
		aDiag.show();
	}

	public void MessageBox(int mesgNum, String Title, String Message, Boolean doOK)
	{
		MessageBox(mesgNum, -1, Title, Message, true, false,  getResources().getString(R.string.ok), getResources().getString(R.string.cancel));
	}


	public void MessageBox(int mesgNum, int which_Icon, String Title, String Message)
	{
		MessageBox(mesgNum, which_Icon, Title, Message, true, false, getResources().getString(R.string.ok), getResources().getString(R.string.cancel));
	}


	public void zFrmShow(Object aclass)
	{
        Intent i = new Intent(myActivity, (Class<?>) aclass);
        startActivity(i);       	
	}
	
	public void zFrmShowClear(Object aclass)  //clears activities back to the passed view and shows the passed view
	{
		Intent intent = new Intent(myActivity, (Class<?>)aclass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	public void zFrmShowDelay(final Object aclass, int secondsdelay)
	{
		new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				zFrmShow(aclass);
				finish();
			}
			
		}, secondsdelay *1000);
	}
	
	public void zShowUrl(String url)
	{
 		Uri uri = Uri.parse("http://" + url); 
 		Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
 		startActivity(intent);	
	}

	/*public HashTableIg<String> zSendServer(String tranType, String[] baseVariables, String[] activityFields)
	{
		return zSendServer(tranType, baseVariables, activityFields, null);
	}*/
	
	
	public void errorcheck(Exception e)
	{
		Log.e("ERROR", "ERROR IN CODE: " + e.toString());
		e.printStackTrace();
	}
	
	public class ReceiptAdapter extends SimpleAdapter{
		
		private int[] colors = new int[]{0x30ffffff, 0x30808080};
		public ReceiptAdapter(Context context,	List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
			super(context, data, resource, from, to); 
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View view = super.getView(position,  convertView, parent);
			int colorPos = position % colors.length;
			view.setBackgroundColor(colors[colorPos]);
			return view;
		}
	}
	
	void zOnListClick(int position){
		
	}
	
	public void zMakeToast(String Message, int length){
		if (length == 0)
			length = Toast.LENGTH_SHORT;
		else
			length = Toast.LENGTH_LONG;
		
		Toast.makeText(getBaseContext(), Message, length).show();
	}
	

	
	/*public HashTableIg<String> zSendServer(String tranType, String[] baseVariables, String[] activityFields, String JSON_Data)
	{
		String bigResult = "";
		
		if (baseVariables != null)
		{
			for (int i=0; i < baseVariables.length; i ++)
			{
				bigResult += baseVariables[i] + "\b" +baseVariables[i+1] + "\b" ;
				i ++; 
			}
		}

		if (activityFields != null)
		{
			for (int i=0; i < activityFields.length; i ++)
			{
				bigResult += activityFields[i] + "\b" +activityFields[i+1] + "\b" ;
				i ++; 
			}
		}
		

		HashTableIg<String> tst = JsonClient.connect("mobserver/" + tranType + ".php", tranType,(String[]) bigResult.split("\b"), JSON_Data) ;
		return tst;
    }
	
	public void zSetServer(){
		final String[] servers = {"Dev Server", "QA Server", "Marketing Server", "Prod Server"};
		final String[] server_port={"6480","6680","6481","6481 "};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select the mobile server that you want to use:");
		builder.setItems(servers, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
		    	BaseRout.setVar("mSERVER_PORT", server_port[which]);
				Toast.makeText(getBaseContext(), "Using " + servers[which], Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
	}
	
	public void zEditExpiry(String coupon1, String coupon2){
		AlertDialog.Builder builder;
		AlertDialog alertDlg;
		LayoutInflater inflater = (LayoutInflater) myActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.expdate_msgbox, (ViewGroup) findViewById(R.id_edit_exp.layout_root));
		
		String[] expMonths = getResources().getStringArray(R.array.expmonths);
		String[] expYear = null;
    	expYear = "2012,2013,2014,2015,2016,2017,2018,2019,2020".split(",");
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_item, expMonths);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	((Spinner) layout.findViewById(R.id_edit_exp.exp_date)).setAdapter(adapter);
    	adapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_item, expYear);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	((Spinner) layout.findViewById(R.id_edit_exp.exp_year)).setAdapter(adapter);
    	
    	//this.zLoadSpinner(R.id_edit_exp.exp_date, expMonths, myActivity);
    	//this.zLoadSpinner(R.id_edit_exp.exp_year, expYear, myActivity);
    	((TextView) layout.findViewById(R.id_edit_exp.card_info)).setText(coupon1 + " " + coupon2);
		
		builder = new AlertDialog.Builder(myActivity);
		builder.setView(layout);
		builder.setTitle(getString(R.string.update_expiry_date));
		builder.setPositiveButton(getString(R.string.update), new DialogInterface.OnClickListener(){   //needs to be moved into an overclass in the showcards class

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Globals.update_expyear = (String) ((Spinner)layout.findViewById(R.id_edit_exp.exp_year)).getSelectedItem();
				Globals.update_expmonth = (String) Integer.toString(((Spinner)layout.findViewById(R.id_edit_exp.exp_date)).getSelectedItemPosition());
				zSetBusy("frm_registration_details_update_exp", getString(R.string.updating_expiry_wait), "");	
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
			
		});
		alertDlg = builder.create();
		alertDlg.show();
		
		
	}
	
	public HashTableIg<String> zSendServerError (HashTableIg<String> tst){
		if (tst.getString("TransmitFailure").equalsIgnoreCase("") == false)
	    {
			MessageBox(Er_Fail_Transmit, getString(R.string.failed_contact_server), tst.getString("TransmitFailure"), true, false);
			return null;
	    }
	    if (tst.getString("MobRetCode").equalsIgnoreCase("0") == false)
	    {
	    	if (tst.getString("action").equalsIgnoreCase("DisplayMessage"))
		    {
	    		MessageBox(Er_Fail_HostMessage, getString(R.string.failed), tst.getString("DisplayMessage"), true, false);
		    }
	    }
		return tst;
	}
	
	public void runTrans(String Tag, View dialog){
		
	}
	
	public void zCheckRequirePin (final String Tag, final View view, final boolean IsList, final int position, final String message){
		if(zGetVar("R.id_regdetails_basic.btn_require_pin").equals("Y-R.id_regdetails_basic.btn_require_pin") || Tag.equalsIgnoreCase("SetPIN")){
			
			AlertDialog.Builder builder;
			final AlertDialog alertDlg;
			LayoutInflater inflater = (LayoutInflater) myActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.pin_msgbox, (ViewGroup) findViewById(R.id.layout_root));
	    	
	    	builder = new AlertDialog.Builder(myActivity);
			builder.setView(layout);
			builder.setTitle(message);
			if (Tag.equalsIgnoreCase("SetPIN")){
				if (zGetVar("pinexists").equalsIgnoreCase("Y")){
					builder.setTitle(getString(R.string.enter_old_pin));
				}
			}
			

			builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
						
				}
			});
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
				
			});
			alertDlg = builder.create();
			alertDlg.show();
			alertDlg.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Tag.equalsIgnoreCase("SetPIN")){
						zUpdatePin(alertDlg, layout, Tag, view, IsList, position);
					}
					else{
						zCheckPinforTrans(layout, IsList, Tag, view, position, alertDlg);
					}
				}
			});
		}
		else{
			if(IsList)
				runListTrans(Tag, view, position);
			else
				runTrans(Tag, view);
		}
	}
	
	public void zUpdatePin(AlertDialog alertDlg, View layout, String Tag, View view, boolean IsList, int position){
		if (zGetVar("pinexists").equalsIgnoreCase("Y")){
			HashTableIg<String> nResl = zSendServer("CheckPIN", new String[]{"PIN", 
					((EditText)layout.findViewById(R.id_pin.pin_code)).getText().toString()}, null);
			nResl = zSendServerError(nResl);
			if (nResl != null){
				if (nResl.getString("Action").equalsIgnoreCase("PINPassed")){
					zSetVar("pinexists", "N");
					zCheckRequirePin(Tag, view, IsList, position, getString(R.string.enter_new_pin));
					alertDlg.dismiss();
				}
				else{
					((EditText)layout.findViewById(R.id_pin.pin_code)).setError(getString(R.string.incorrect_pin));
				}
			}
			else
				((EditText)layout.findViewById(R.id_pin.pin_code)).setError(getString(R.string.failed_contact_server));
		}
		else
		{
			if (Globals.set_pin.equalsIgnoreCase("")){
				Globals.set_pin = ((EditText)layout.findViewById(R.id_pin.pin_code)).getText().toString();
				zCheckRequirePin(Tag, view, IsList, position, getString(R.string.enter_new_pin_again) );
				alertDlg.dismiss();
			}
			else{
				if (Globals.set_pin.equalsIgnoreCase(((EditText)layout.findViewById(R.id_pin.pin_code)).getText().toString())){
					HashTableIg<String> nResl = zSendServer("updatepin", new String[]{"CustOID", zGetVar("CustOID"), "PIN", 
							((EditText)layout.findViewById(R.id_pin.pin_code)).getText().toString(), "usepin", "Y"}, null);
					nResl = zSendServerError(nResl);
					if (nResl != null){
						if (nResl.getString("Action").equalsIgnoreCase("pinpassed")){
							zSetVar("pinexists", "Y");
							Globals.set_pin="";
							Toast.makeText(myActivity, getString(R.string.pin_succes_pass), Toast.LENGTH_LONG).show();
							alertDlg.dismiss();
						}
					}
				}
				else{
					((EditText)layout.findViewById(R.id_pin.pin_code)).setError(getString(R.string.pin_dont_match));
				}
			}
			
		}
	}
	
	public void zCheckPinforTrans(View layout, boolean IsList, String Tag, View view, int position, AlertDialog alertDlg){
		HashTableIg<String> nResl = zSendServer("CheckPIN", new String[]{"CustOID", zGetVar("CustOID"), "PIN", 
				((EditText)layout.findViewById(R.id_pin.pin_code)).getText().toString()}, null);
		nResl = zSendServerError(nResl);
		if (nResl != null){
			if (nResl.getString("Action").equalsIgnoreCase("PINPassed")){
				if(IsList)
					runListTrans(Tag, view, position);
				else
					runTrans(Tag, view);
				alertDlg.dismiss();
			}
			else{
				if (nResl.getString("Action").equalsIgnoreCase("PINdoesnotexist"))
				{
					zSetVar("pinexists", "N");
					zSetVar("R.id_regdetails_basic.btn_require_pin", "N");
					MessageBox(0, getString(R.string.pin_doesnot_exist),getString(R.string.pin_doesnot_exist_mesg),false);
					if(IsList)
						runListTrans(Tag, view, position);
					else
						runTrans(Tag, view);
				}
				else{
					try{
					((EditText)layout.findViewById(R.id_pin.pin_code)).setError(getString(R.string.incorrect_pin));
					}catch(Exception e){errorcheck(e);};
				}
			}
		}
		else
			((EditText)layout.findViewById(R.id_pin.pin_code)).setError(getString(R.string.failed_contact_server));
	}
	
	public void zCheckRequirePin(String Tag, View dialog, String message){
		zCheckRequirePin(Tag, dialog, false, 0, message);
	}

	public void runListTrans(String Tag, View v, int position) {
		// TODO Auto-generated method stub
		
	}
	
	public void zLoginMessage(final int messageNum){
		mMesgNum  = messageNum;
		AlertDialog.Builder builder;
		final AlertDialog alertDlg;
		LayoutInflater inflater = (LayoutInflater) myActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.login_msgbox, (ViewGroup) findViewById(R.id.layout_root));
		((EditText)layout.findViewById(R.id_login_msg.user)).setText(zGetVar("login_userid"));
    	
    	builder = new AlertDialog.Builder(myActivity);
		builder.setView(layout);
		builder.setTitle(getString(R.string.login_to_continue));
		
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();	
			}	
		});
		builder.setNeutralButton(getString(R.string.register), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//zFrmShow(frm_registration_details_basicActivity.class);
			}
		});
		builder.setCancelable(false);
		
		alertDlg = builder.create();
		alertDlg.show();
		alertDlg.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OnClickOK(messageNum, alertDlg, 0, layout);
				
			}
		});
		
	}
	
	public void zCreateNotification(String Titleshort, String Titlelong, String Mesg, int icon, Object aclass){
		NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, Titleshort, System.currentTimeMillis());
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		Intent intent = new Intent(myActivity, (Class<?>) aclass);
		PendingIntent activity = PendingIntent.getActivity(myActivity, 0, intent, 0);
		notification.setLatestEventInfo(myActivity, Titlelong, Mesg, activity);
		notification.number+=1;
		notificationManager.notify(0, notification);
	}
	
	public String zArrayListToString(ArrayList<String> alist){
		return zArrayListToString(alist, "!");
	}
	
	public String zArrayListToString(ArrayList<String> alist, String sep)
	{
		String ret = "";
		for (int x = 0; x < alist.size(); x++)
		{
			ret = ret+  alist.get(x) + sep;
		}
		ret = ret.substring(0, ret.lastIndexOf(sep));
		return ret;
	}
	
	public String zArrayToString(String[] array)
	{
		String ret="";
		for (int x= 0; x<array.length; x++){
			ret = ret + array[x] +"!";
		}
		return ret;
	}
	
	//Food Service Specific Functions
	
	public void zCheckSession(){
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		int now = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (now >= 5 && now < 11)
			this.zSetVar("session", "breakfast");
		if (now >= 11 && now < 15)
			this.zSetVar("session", "lunch");
		if (now >= 15 && now < 22)
			this.zSetVar("session", "dinner");
		if (now >=22 && now < 5)
			this.zSetVar("session", "late");
	}
	
	public void onStart()
	{
		super.onStart();
		zCheckSession();
	}*/
}


