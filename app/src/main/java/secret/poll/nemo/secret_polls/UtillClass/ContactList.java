package secret.poll.nemo.secret_polls.UtillClass;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by jeeyu_000 on 2018-02-08.
 */

public class ContactList {
    private static String[][] contactList = null;
    public static final int NAME = 0;
    public static final int PHONE_NUM = 1;
    private static int contactNum = -1;

    public static String[][] getContactList(ContentResolver cr) {
        if (contactList == null) {
            //컨택 리스트 가져오쟈쟈
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if ((cur == null ? 0 : cur.getCount()) > 0) {
                int idx = 0;
                contactList = new String[cur.getCount()][2];
                contactNum = cur.getCount();
                //[START contact while]
                while (cur != null && cur.moveToNext()) {
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME
                    ));
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String phoneNum = null;
                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);

                        while (pCur.moveToNext()) {
                            phoneNum = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));

                        }

                        pCur.close();
                    }

                    name = checkValidName(name);
                    phoneNum = checkValidNumber(phoneNum);
                    if (name != null && phoneNum != null) {
                        contactList[idx][NAME] = name;
                        contactList[idx][PHONE_NUM] = phoneNum;
                        idx++;
                    }else{
                        Log.d("TAG", "TAK");
                    }
                }
                //[END contact while]
                contactNum = idx;
            }
        }
        return contactList;
    }

    public static String checkValidName(String name){
       if(name == null || name.trim().equals("")){
           return null;
       }
        return name;
    }

    public static String checkValidNumber(String phoneNum){
        if(phoneNum == null || phoneNum.equals("") || phoneNum.length() < 6){
            return null;
        }

        if(phoneNum.contains("-") || phoneNum.contains("*") || phoneNum.contains("+") || phoneNum.contains(" ")){
            phoneNum = phoneNum.replace("_", "");
            phoneNum = phoneNum.replace("*", "");
            phoneNum = phoneNum.replace("+", "");
            phoneNum = phoneNum.replace(" ", "");
            phoneNum = phoneNum.trim();
        }
        return phoneNum;
    }

    public static int getContactNum(){
        return contactNum;
    }
}
