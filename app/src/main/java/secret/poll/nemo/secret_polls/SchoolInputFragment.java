package secret.poll.nemo.secret_polls;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class SchoolInputFragment extends Fragment{
    @BindView(R.id.school_list) RecyclerView mSchoolListView;
    private final String TAG = "SchoolInputFragment";
    private FirebaseDatabase mDB;
    private DatabaseReference mDBRefSchoolList;
    private ArrayList<SchoolClass> mArrSchool = new ArrayList<SchoolClass>();
    private final View.OnClickListener mOnclickListener = new MyOnclickListener();
    private int mCurSchoolIdx = -1;
    private int mPastSchoolIdx = -1;

    @OnClick(R.id.button_select_school)
    public void selectSchool(){
        if(mCurSchoolIdx < 0){
            Toast.makeText(getContext(), "no school selected", Toast.LENGTH_SHORT).show();
        }else{
            //유저 객체에 학교 정보 저장
            UserProfileClass user = UserProfileClass.getUserProfile();
            SchoolClass selectedSchool = mArrSchool.get(mCurSchoolIdx);
            user.setSchoolNum(selectedSchool.getIdx()+"");
            user.setSchoolKind(selectedSchool.getKind());

            //다음으로 넘어간다
            ProfileInputCompleteInterface completeInterface = (ProfileInputCompleteInterface) getContext();
            completeInterface.complete(ProfileInputCompleteInterface.SELECT_SCHOOL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDB = FirebaseDatabase.getInstance();
        mDBRefSchoolList = mDB.getReference("SCHOOL_LIST").child("KOREA");
        View view = inflater.inflate(R.layout.school_input_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDBRefSchoolList.addListenerForSingleValueEvent(mSchoolDataListener);
    }

    private ValueEventListener mSchoolDataListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, dataSnapshot.getChildrenCount()+"");
            for(DataSnapshot schoolData : dataSnapshot.getChildren()){
                SchoolClass schoolObj = schoolData.getValue(SchoolClass.class);
                mArrSchool.add(schoolObj);
            }
            SchoolAdapter adapter = new SchoolAdapter(getContext(), mArrSchool);
            mSchoolListView.setAdapter(adapter);
            mSchoolListView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };


    public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.ViewHolder>{
        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mSchoolNameView;
            public TextView mSchoolCityView;

            public ViewHolder(View itemView){
                super(itemView);
                mSchoolNameView = itemView.findViewById(R.id.item_school_name);
                mSchoolCityView = itemView.findViewById(R.id.item_school_city);
            }
        }

        private List<SchoolClass> mSchools;
        private Context mContext;

        public SchoolAdapter(Context context, List<SchoolClass> schools){
            mSchools = schools;
            mContext = context;
        }

        private Context getmContext(){
            return mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View schoolView = inflater.inflate(R.layout.item_school, parent, false);
            ViewHolder viewHolder = new ViewHolder(schoolView);
            schoolView.setOnClickListener(mOnclickListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(SchoolAdapter.ViewHolder viewHolder, int position) {
            SchoolClass school = mSchools.get(position);
            TextView schoolNameView = viewHolder.mSchoolNameView;
            schoolNameView.setText(school.getName());

            TextView schoolCityView = viewHolder.mSchoolCityView;
            schoolCityView.setText(school.getCity());
        }

        @Override
        public int getItemCount() {
            return mSchools.size();
        }
    }

    public class MyOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            int itemPosition = mSchoolListView.getChildLayoutPosition(view);
            mPastSchoolIdx = mCurSchoolIdx;
            mCurSchoolIdx = itemPosition;

            if(mPastSchoolIdx > -1) {
                View pastSchoolView = mSchoolListView.findViewHolderForAdapterPosition(mPastSchoolIdx).itemView;
                pastSchoolView.setBackgroundColor(getContext().getColor(R.color.white));
            }
            view.setBackgroundColor(getContext().getColor(R.color.colorAccent));
        }
    }


}
