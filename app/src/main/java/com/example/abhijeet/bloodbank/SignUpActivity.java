package com.example.abhijeet.bloodbank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
//

public class SignUpActivity extends AppCompatActivity implements OnItemSelectedListener{
    private android.support.v7.widget.Toolbar toolbar;

    ContactDbHelper myDb;
    EditText editName,editEmail,editPassword;
    Button btnSignup;

    //
    EditText fname,email,phno,pass,cnfpass;
    Button submit;
    AwesomeValidation awesomeValidation;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolBar);

        //Spinner for dropdown
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.group, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Setting toolbar as default
        setSupportActionBar(toolbar);

        //Database connect
        myDb = new ContactDbHelper(this);

        editName = (EditText) findViewById(R.id.fullname);
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        btnSignup =(Button) findViewById(R.id.button);
       // signUp();
//}
//validation starts
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        updateUI();
    }

    private void updateUI() {

        fname =(EditText)findViewById(R.id.fullname);
      //  lname =(EditText)findViewById(R.id.lname);
        email =(EditText)findViewById(R.id.email);
        pass =(EditText)findViewById(R.id.password);
        cnfpass =(EditText)findViewById(R.id.confirm);
        phno =(EditText)findViewById(R.id.mobile);
        submit =(Button)findViewById(R.id.button);

        String regexPassword = "[a-zA-Z0-9\\s]+";
        awesomeValidation.addValidation(SignUpActivity.this,R.id.fullname, "[a-zA-Z\\s]+",R.string.fnameerr);
       // awesomeValidation.addValidation(SignUpActivity.this,R.id.lname,"[a-zA-Z\\s]+",R.string.lnameerr);
        awesomeValidation.addValidation(SignUpActivity.this,R.id.email,android.util.Patterns.EMAIL_ADDRESS,R.string.emailerr);
        awesomeValidation.addValidation(SignUpActivity.this,R.id.mobile, RegexTemplate.TELEPHONE,R.string.phoneerr);

        awesomeValidation.addValidation(SignUpActivity.this,R.id.password,regexPassword,R.string.passerr);
        awesomeValidation.addValidation(SignUpActivity.this,R.id.confirm,R.id.password,R.string.cnfpasserr);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (awesomeValidation.validate()){

                    Toast.makeText(SignUpActivity.this, "Data Received Succesfully", Toast.LENGTH_SHORT).show();
                   // signUp();
                }

                else

                {
                    Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
//validation ends


    //database part
    public void  signUp(){
        btnSignup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.addContact(1,editName.getText().toString(),editEmail.getText().toString());
                        if (isInserted==true)
                            Toast.makeText(SignUpActivity.this,"Data Insered",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(SignUpActivity.this,"Data not Insered",Toast.LENGTH_LONG).show();

                    }
                }

        );
    }
    //Spinner action
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text= parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
