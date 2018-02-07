package secret.poll.nemo.secret_polls;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class UserProfileClass {
    private String mPhoneNum;
    private String mName;
    private String mGender;
    private String mSchoolKind;
    private String mSchoolNum;
    private int mGrade;
    private static UserProfileClass userProfile;

    public UserProfileClass(){}

    public void setmPhoneNum(String mPhoneNum) {
        this.mPhoneNum = mPhoneNum;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public void setmSchoolKind(String mSchoolKind) {
        this.mSchoolKind = mSchoolKind;
    }

    public void setmSchoolNum(String mSchoolNum) {
        this.mSchoolNum = mSchoolNum;
    }

    public void setmGrade(int mGrade) {
        this.mGrade = mGrade;
    }

    public static UserProfileClass getUserProfile(){
        if(userProfile == null){
            userProfile = new UserProfileClass();
        }
        return userProfile;
    }

}
