package binh.le.game.firebase.dao;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import binh.le.game.firebase.FirebaseHelper;
import binh.le.game.firebase.model.User;
import binh.le.game.ultis.Constants;

public class UserDao {
    public static final String TAG = UserDao.class.getSimpleName();

    private FirebaseAuth mAuth;
    protected FirebaseDatabase firebase;
    private StorageReference StorageRef;

    public UserDao(FirebaseDatabase firebase, StorageReference StorageRef) {
        this.firebase = firebase;
        mAuth = FirebaseAuth.getInstance();
        this.StorageRef = StorageRef;
    }

    /**
     * Sign in firebase with
     *
     * @param email
     * @param password
     */
    public LiveData<Task<AuthResult>> signIn(String email, String password) {
        MediatorLiveData<Task<AuthResult>> liveData = new MediatorLiveData<>();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user.isEmailVerified()) {
                            Log.d(TAG, "isEmailVerified");
                            // Sign in success, update UI with the signed-in user's information
                            liveData.setValue(task);
                        } else {
                            Log.d(TAG, "not isEmailVerified");
                            String url = "http://www.example.com/verify?uid=" + user.getUid();
                            ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                                    .setUrl(url)
                                    .setHandleCodeInApp(false)
                                    // The default for this is populated with the current android package name.
                                    .setAndroidPackageName("binh.le.game", false, null)
                                    .build();

                            user.sendEmailVerification(actionCodeSettings).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // email sent
                                        // after email is sent just logout the user and finish this activity
                                        FirebaseAuth.getInstance().signOut();
                                        Log.d(TAG, "send isSuccessful");
                                        liveData.setValue(null);
                                    } else {
                                        // email not sent, so display message and restart the activity or do whatever you wish to do
                                        Log.d(TAG, "send fail " + task.getException());
                                    }
                                }
                            });
                        }
                    } else {
                        // If sign in with user not found, sign-up new user.
                        if (task.getException() instanceof FirebaseAuthInvalidUserException &&
                                ((FirebaseAuthInvalidUserException) task.getException()).getErrorCode().equals(FirebaseHelper.ERROR_USER_NOT_FOUND)) {
                            liveData.addSource(signUp(email, password), liveData::setValue);
                        } else {
                            liveData.setValue(task);
                        }
                    }
                });
        return liveData;
    }

    /**
     * Sign up firebase with
     *
     * @param email
     * @param password
     */
    public LiveData<Task<AuthResult>> signUp(String email, String password) {
        MediatorLiveData<Task<AuthResult>> liveData = new MediatorLiveData<>();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "not isEmailVerified");
                        String url = "http://www.example.com/verify?uid=" + user.getUid();
                        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                                .setUrl(url)
                                .setHandleCodeInApp(false)
                                // The default for this is populated with the current android package name.
                                .setAndroidPackageName("binh.le.game", false, null)
                                .build();

                        user.sendEmailVerification(actionCodeSettings).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // email sent
                                    // after email is sent just logout the user and finish this activity
                                    FirebaseAuth.getInstance().signOut();
                                    Log.d(TAG, "send isSuccessful");
                                    liveData.setValue(null);
                                } else {
                                    // email not sent, so display message and restart the activity or do whatever you wish to do
                                    Log.d(TAG, "send fail " + task.getException());

                                }
                            }
                        });
                    }
                });
        return liveData;
    }

    /**
     * Update {@link User} info
     *
     * @param userId   *
     */
    public LiveData<Boolean> updateUserInfo(String userId, String email) {
        MediatorLiveData<Boolean> result = new MediatorLiveData<>();
        firebase.getReference(Constants.USER_PATH)
                .child(userId).updateChildren(
                new User(userId, email).toMap(),(databaseError, databaseReference) -> {
                    result.postValue(true);
                });
        return result;
    }
}
