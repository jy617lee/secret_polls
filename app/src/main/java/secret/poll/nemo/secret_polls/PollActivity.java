package secret.poll.nemo.secret_polls;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class PollActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.questionTxt) TextView mQuestionTxt;
    @BindView(R.id.answer_0)    TextView mAnswer0;
    @BindView(R.id.answer_1)    TextView mAnswer1;
    @BindView(R.id.answer_2)    TextView mAnswer2;
    @BindView(R.id.answer_3)    TextView mAnswer3;

    private final String TAG = "PollActivity";
    private int mCurPollListNum = -1;
    private int mCurPollQuestionNum = -1;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBRefUserCntQuestion;
    private DatabaseReference mDBRefQuestionList;
    private DatabaseReference mDBRefPollResult;
    private ArrayList<String> questionArr;
    private final int MAX = 9;
    private String[][] mContactList;
    private int mContactNum;
    private final int REQUEST_READ_CONTACTS_PERMISSION = 123;

    private int[] idxForAnswers = new int[4];
    private TextView[] answerTxt;
    private UserProfileClass user;
    private String myPhoneNum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        ButterKnife.bind(this);

        user = UserProfileClass.getUserProfile();
        myPhoneNum = user.getPhoneNum();
        mDB = FirebaseDatabase.getInstance();
        mDBRefUserCntQuestion = mDB.getReference("USER_CNT_QUESTION").child(user.getPhoneNum());
        mDBRefQuestionList = mDB.getReference("QUESTION_LIST");
        mDBRefPollResult = mDB.getReference("POLL_RESULT");
        getCurPollListNum();
        mAnswer0.setOnClickListener(this);
        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);

        answerTxt = new TextView[]{mAnswer0, mAnswer1, mAnswer2, mAnswer3};
        getContactListPermission();
    }

    public void getContactListPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CONTACTS);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS_PERMISSION);
        }else{
            mContactList = ContactList.getContactList(getContentResolver());
            mContactNum = ContactList.getContactNum();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mContactList = ContactList.getContactList(getContentResolver());
                    mContactNum = ContactList.getContactNum();
                    return;
                }
                Toast.makeText(getApplicationContext(), "전화번호부 권한 허용필수", Toast.LENGTH_SHORT).show();
                finish();
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public void getCurPollListNum(){
        mDBRefUserCntQuestion.addListenerForSingleValueEvent(mUserCntQuestionListener);
    }

    private ValueEventListener mUserCntQuestionListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getValue() == null) {
                mDBRefUserCntQuestion.setValue(0);
                mCurPollListNum = 0;
            } else {
                mCurPollListNum = Integer.parseInt(dataSnapshot.getValue().toString());
            }
            //퀴즈 10개를 받아와야지!
            mDBRefQuestionList.child(mCurPollListNum + "").addListenerForSingleValueEvent(mQuestionListListener);
        }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
    };

    private ValueEventListener mQuestionListListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            questionArr = new ArrayList<String>(10);
            for(DataSnapshot questiondata : dataSnapshot.getChildren()){
                String question = questiondata.getValue(String.class);
                questionArr.add(question);
            }
            mCurPollQuestionNum = 0;
            startPoll(questionArr, mCurPollQuestionNum);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };

    public void startPoll(ArrayList<String> questionArr, int pollQuestionNum){
        //질문 바꾸고
        mQuestionTxt.setText(questionArr.get(pollQuestionNum));

        //선택지 바꾸기
        shuffleAnswer();
    }

    public void shuffleAnswer(){
        for(int i = 0; i < 4; i++){
            Random r = new Random();
            int idxTemp = r.nextInt(mContactNum);
            String name = mContactList[idxTemp][ContactList.NAME];
            idxForAnswers[i] = idxTemp;
            answerTxt[i].setText(name);
        }
    }

    @Override
    public void onClick(View view) {
        int pollSelectNum = -1;
        switch(view.getId()){
            case R.id.answer_0 : {
                pollSelectNum = 0;
                break;
            }
            case R.id.answer_1 : {
                pollSelectNum = 1;
                break;
            }
            case R.id.answer_2 : {
                pollSelectNum = 2;
                break;
            }
            case R.id.answer_3 : {
                pollSelectNum = 3;
                break;
            }
        }
        //해당 전화번호에 해당 답변 넣어서 db에 보내기
        //1. 전화번호 get
        String phoneNum = mContactList[idxForAnswers[pollSelectNum]][ContactList.PHONE_NUM];

        // question_idx
        final DatabaseReference resultRef = mDBRefPollResult.child(phoneNum).push();
        final PollResultClass pollRes = new PollResultClass(mCurPollListNum, mCurPollQuestionNum, myPhoneNum);

        resultRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer currentValue = mutableData.getValue(Integer.class);
                if (currentValue == null) {
                    mutableData.setValue(1);
                } else {
                    mutableData.setValue(currentValue + 1);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                resultRef.setValue(pollRes);
            }
        });
        //다음 질문으로 넘어가기
        if(++mCurPollQuestionNum > MAX){
            Toast.makeText(getApplicationContext(), "퀴즈 끝", Toast.LENGTH_SHORT).show();
        }else{
            startPoll(questionArr, mCurPollQuestionNum);
        }
    }
}
