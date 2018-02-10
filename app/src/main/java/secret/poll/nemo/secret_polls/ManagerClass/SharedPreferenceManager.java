package secret.poll.nemo.secret_polls.ManagerClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class SharedPreferenceManager {
    private static SharedPreferenceManager mSPManager;
    private Context mContext;
    private SharedPreferences mSharedPreference;
    SharedPreferences.Editor editor;
    public SharedPreferenceManager(Context context){
        mContext = context;
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static SharedPreferenceManager getSharedPreferenceManaer(Context context){
        if(mSPManager == null){
            mSPManager = new SharedPreferenceManager(context);
        }
        return mSPManager;
    }

    public void setValue(String key, String value){
        editor = mSharedPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setValue(String key, int value){
        editor = mSharedPreference.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setValue(String key, boolean value){
        editor = mSharedPreference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Object getValue(String key, Class<?> cls, Object defVal){
        Object resVal = null;
        if(cls == String.class){
             resVal = mSharedPreference.getString(key, (String) defVal);
        }else if(cls == int.class){
            resVal = mSharedPreference.getInt(key, (int) defVal);
        }else if(cls == boolean.class){
            resVal = mSharedPreference.getBoolean(key, (boolean) defVal);
        }
        return resVal;
    }
}
