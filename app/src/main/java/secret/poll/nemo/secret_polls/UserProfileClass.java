package secret.poll.nemo.secret_polls;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class UserProfileClass {
    private String phoneNum;
    private String name;
    private String gender;
    private String schoolKind;
    private String schoolNum;
    private int grade;
    private static UserProfileClass userProfile;

    public UserProfileClass(){}

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSchoolKind(String schoolKind) {
        this.schoolKind = schoolKind;
    }

    public void setSchoolNum(String schoolNum) {
        this.schoolNum = schoolNum;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getSchoolKind() {
        return schoolKind;
    }

    public String getSchoolNum() {
        return schoolNum;
    }

    public int getGrade() {
        return grade;
    }

    public static UserProfileClass getUserProfile(){
        if(userProfile == null){
            userProfile = new UserProfileClass();
        }
        return userProfile;
    }

}
