/*package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends Activity {


    private static EditText username;
    private static EditText password;
    private static TextView attempt;
    private static Button login_button;
    int attempt_counter=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton();
    }

    public void LoginButton(){
        username = (EditText)findViewById(R.id.editText_user);
        password = (EditText)findViewById(R.id.editText_password);
        attempt = (TextView)findViewById(R.id.textView_attempt);
        login_button = (Button)findViewById(R.id.button_login);

        attempt.setText(Integer.toString(attempt_counter));

        login_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (username.getText().toString().equals("user") &&
                                password.getText().toString().equals("pass")){
                            String passwordToHash = password.getText().toString();
                            String generatedPassword = null;
                            try {
                                // Create MessageDigest instance for MD5
                                MessageDigest md = MessageDigest.getInstance("MD5");
                                //Add password bytes to digest
                                md.update(passwordToHash.getBytes());
                                //Get the hash's bytes
                                byte[] bytes = md.digest();
                                //This bytes[] has bytes in decimal format;
                                //Convert it to hexadecimal format
                                StringBuilder sb = new StringBuilder();
                                for(int i=0; i< bytes.length ;i++)
                                {
                                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                                }
                                //Get complete hashed password in hex format
                                generatedPassword = sb.toString();
                                Toast.makeText(getApplicationContext(), ""+generatedPassword,
                                        Toast.LENGTH_LONG).show();
                            }
                            catch (NoSuchAlgorithmException e)
                            {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Login.this,"Username and password is NOT correct",
                                    Toast.LENGTH_SHORT).show();
                            attempt_counter--;
                            attempt.setText(Integer.toString(attempt_counter));
                            if(attempt_counter==0)
                                login_button.setEnabled(false);
                        }
               //     }
                }
        );
    }
    /*
    String passwordToHash = sc.next();
		String generatedPassword = null;

		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add password bytes to digest
			md.update(passwordToHash.getBytes());
			//Get the hash's bytes
			byte[] bytes = md.digest();
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			generatedPassword = sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
     */

  /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

//}
//*/

package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.gordonramsdd.R;

public class Login extends Activity implements OnClickListener{
    EditText UsernameEt, PasswordEt;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        UsernameEt = (EditText) findViewById (R.id.et_username);
        PasswordEt = (EditText) findViewById (R.id.et_password);
        mLoginButton = (Button) findViewById (R.id.buttonLogin);
        mLoginButton.setOnClickListener (this);
    }



    @Override
    public void onClick(View view) {

        switch (view.getId ()){
            case R.id.buttonLogin:
                System.out.print ("");
                String username = UsernameEt.getText ().toString ();
                String password = PasswordEt.getText ().toString ();
                String type = "login";
                BackgroundWorker backgroundWorker = new BackgroundWorker (this);
                /*if(*/System.out.println(backgroundWorker.execute (type, username, password))/*.equals("com.example.gordonramsdd.BackgroundWorker@8dc5fb0")){*/;
                    Intent i = new Intent(view.getContext(),MainActivity.class);
                    startActivity(i);
                //}
                break;
        }

    }
}