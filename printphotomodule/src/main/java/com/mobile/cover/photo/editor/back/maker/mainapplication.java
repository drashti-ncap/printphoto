package com.mobile.cover.photo.editor.back.maker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;



import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Notification.Notification_model.notification_main_response;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.SplashScreen;
import com.mobile.cover.photo.editor.back.maker.testing_modules.ImageData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.onesignal.OneSignal;


public abstract class mainapplication extends MultiDexApplication {

    private static final String APPSFLYER_TOKEN = "rAygu6MHr2HZbf2B2hUhZm";
    public static mainapplication sInstance;
    protected List<String> listTestDevice;

    public static ArrayList<ImageData> org_selectedImages = new ArrayList();
    public static ArrayList<ImageData> temp_org_selectedImages = new ArrayList();
    public static ArrayList<ImageData> selectedImages = new ArrayList();
    public static int VIDEO_HEIGHT = 480;
    public static int VIDEO_WIDTH = 720;
    public static boolean TwoDServiceisBreak = false;
    public static boolean ThreeDServiceisBreak = false;
    public static boolean MoreServiceisBreak = false;
    public static boolean threeD_Service_On_Off_Flag = false;
    public static boolean towD_Service_On_Off_Flag = false;
    public static boolean More_Service_On_Off_Flag = false;
    public static boolean Save_Service_On_Off_Flag = false;
    public static boolean changeBackground_Flag = false;
    public static boolean no_music_command = false;
    public static boolean frame_command = false;
    public static boolean error_in_save_video = false;
    public static ArrayList<String> videoImages = new ArrayList();
    public static ArrayList<ImageData> imageDatas = new ArrayList<>();
    public static ArrayList<String> tempSelection = new ArrayList<>();
    public CallbackManager callbackManager;
    public AccessTokenTracker accessTokenTracker;
    public ProfileTracker profileTracker;
    public HashMap<String, ArrayList<ImageData>> allAlbum = new HashMap<>();
    public boolean isEditModeEnable = false;
    public boolean isFromSdCardAudio = false;
    public int min_pos = Integer.MAX_VALUE;
    private RequestQueue requestQueue;
    // img
    private ArrayList<String> allFolder;
    private String selectedFolderId = "";
    private int frame = -1;
    private int bg = -1;
    private int startBgSlide = -1;
    private int endBgSlide = -1;
    private File file = null;
    private float second = 2.0f;

    private final String ADJUST_TOKEN = "cc4jvudppczk";
    private final String EVENT_PURCHASE_ADJUST = "gzel1k";
    private final String EVENT_AD_IMPRESSION_ADJUST = "gzel1k";

    public synchronized static mainapplication getsInstance() {
        return sInstance;
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            Log.i("TAG", "trimCache: FAILED");
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        if (dir != null) {
            return dir.delete();
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        this.listTestDevice = new ArrayList();

       /* if (SharePreferenceUtils.getInstallTime(this) == 0L) {
            SharePreferenceUtils.setInstallTime(this);
        }

        AppUtil.currentTotalRevenue001Ad = SharePreferenceUtils.getCurrentTotalRevenue001Ad(this);*/

        try {

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            String token = task.getResult();

                            // Log and toast
                            String msg = getString(R.string.msg_token_fmt, token);
                            Log.d("TAG", msg);

                            Share.firebaseToken= msg;
                        }
                    });

            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
            OneSignal.initWithContext(this);
            OneSignal.setAppId(/*"79d4e9af-1afd-480d-b5e4-78226f679e27"*/"52ceeaf2-8f6d-4caa-af18-3035658b3e4e");
            OneSignal.promptForPushNotifications();

            OneSignal.setNotificationOpenedHandler(osNotificationOpenedResult -> {
                try {
                    Log.e("DATA", "notificationOpened: " + osNotificationOpenedResult.stringify());
                    Log.e("DATA", "notificationOpened: " + osNotificationOpenedResult.stringify());

                    Gson gson = new Gson();
                    notification_main_response notification_main_response = gson.fromJson(osNotificationOpenedResult.stringify(), notification_main_response.class);
                    if (notification_main_response.getNotification().getPayload().getAdditionalData() != null) {
                        if (notification_main_response.getNotification().getPayload().getAdditionalData().getCategoryId() != null) {
                            Share.notification_category_id = Integer.valueOf(notification_main_response.getNotification().getPayload().getAdditionalData().getCategoryId());
                        }
                        if (notification_main_response.getNotification().getPayload().getAdditionalData().getCategoryName() != null) {
                            Share.notification_category_name = notification_main_response.getNotification().getPayload().getAdditionalData().getCategoryName();
                        }
                        if (notification_main_response.getNotification().getPayload().getAdditionalData().getOffer() != null) {
                            Share.offer_display = notification_main_response.getNotification().getPayload().getAdditionalData().getOffer().equalsIgnoreCase("1");
                        }
                    }

                    Log.e("NOTIFICATION_DATA", "notificationOpened: " + Share.notification_category_id);
                    Log.e("NOTIFICATION_DATA", "notificationOpened: " + Share.notification_category_name);

                    try {
                        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);

                    } catch (android.content.ActivityNotFoundException anfe) {
                        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.e("NOTIFICATION_DATA", "notificationOpened: " + e.toString());
                    try {
                        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException anfe) {
                        Log.e("NOTIFICATION_DATA", "notificationOpened: " + anfe.toString());
                    }

                }

            });

            FacebookSdk.sdkInitialize(getApplicationContext());
            Fresco.initialize(this);
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

            callbackManager = CallbackManager.Factory.create();
            accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                }
            };
            profileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {

                    if (newProfile != null) {
                        try {
                            SharedPrefs.save(getApplicationContext(), SharedPrefs.ProfileId, newProfile.getId());
                        } catch (Exception e) {
                            Log.e("EXCEPTION", "onCurrentProfileChanged: " + e.getLocalizedMessage());
                        }
                    }
                }
            };


            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
            ImageLoader.getInstance().init(config);


            requestQueue = Volley.newRequestQueue(this);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public AccessTokenTracker getAccessTokenTracker() {
        return accessTokenTracker;
    }

    public ProfileTracker getProfileTracker() {
        return profileTracker;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }


    /// img_selected


    public HashMap<String, ArrayList<ImageData>> getAllAlbum() {
        return allAlbum;
    }

    public ArrayList<ImageData> getImageByAlbum(String folderId) {
        imageDatas = getAllAlbum().get(folderId);
        if (imageDatas == null) {
            return new ArrayList();
        }
        return imageDatas;
    }

    public void addSelectedImage(ImageData imageData) {
        Log.e("TAG", "imagePath :" + imageData.imagePath);

        if (Share.end_slide_selected_or_not && !Share.isPreviewActivity) {


            selectedImages.add(selectedImages.size() - 1, imageData);
            org_selectedImages.add(org_selectedImages.size() - 1, imageData);
            temp_org_selectedImages.add(temp_org_selectedImages.size() - 1, imageData);
            imageData.imageCount++;

        } else {
            selectedImages.add(imageData);
            org_selectedImages.add(imageData);
            temp_org_selectedImages.add(imageData);
            imageData.imageCount++;
        }
    }

    public void removeSelectedImage(int imageData) {
        if (imageData <= selectedImages.size()) {
            Log.e("TAG", "image imagePath : ==>" + selectedImages.get(imageData).imagePath);
            ImageData imageData2 = selectedImages.remove(imageData);
            org_selectedImages.remove(imageData);
            temp_org_selectedImages.remove(imageData);
            imageData2.imageCount--;
        }
    }

    public float getSecond() {
        return this.second;
    }

    public void setSecond(float second) {
        this.second = second;
    }

    public String getSelectedFolderId() {
        return this.selectedFolderId;
    }

    public void setSelectedFolderId(String selectedFolderId) {
        this.selectedFolderId = selectedFolderId;
    }

    public Typeface getApplicationTypeFace() {
        return null;
    }

    public int getFrame() {
        return this.frame;
    }

    public void setFrame(int data) {
        this.frame = data;
    }

    public int getStartBgSlide() {
        return startBgSlide;
    }

    public void setStartBgSlide(int startBgSlide) {
        this.startBgSlide = startBgSlide;
    }

    public int getEndBgSlide() {
        return endBgSlide;
    }

    public void setEndBgSlide(int endBgSlide) {
        this.endBgSlide = endBgSlide;
    }

    public int getBg() {
        return this.bg;
    }

    public void setBg(int data) {
        this.bg = data;
    }

    public File getEffect() {
        return this.file;
    }

    public void setEffect(File data) {
        this.file = data;
    }

    public void initArray() {
        videoImages = new ArrayList();
    }

    public ArrayList<ImageData> getSelectedImages() {
        return selectedImages;
    }

    public ArrayList<ImageData> getOrgSelectedImages() {
        return org_selectedImages;
    }

    public ArrayList<ImageData> getTempOrgSelectedImages() {
        return temp_org_selectedImages;
    }

    public void getFolderList() {
        try {
            allFolder = new ArrayList();
            allAlbum = new HashMap();
            String[] projection = new String[]{"_data", "_id", "bucket_display_name", "bucket_id",
                    "datetaken"};
            String orderBy = "bucket_display_name";
            Cursor cur = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, "bucket_display_name");

            if (cur.getCount() == 0) {
                Log.e("TAG", "cur : " + cur.getCount());
                Share.image_not_found = cur.getCount();
                Toast.makeText(sInstance, "No images available!!", Toast.LENGTH_SHORT).show();
            } else {
                if (cur.moveToFirst()) {
                    int bucketColumn = cur.getColumnIndex("bucket_display_name");
                    int bucketIdColumn = cur.getColumnIndex("bucket_id");
                    int dateColumn = cur.getColumnIndex("datetaken");
                    setSelectedFolderId(cur.getString(bucketIdColumn));
                    do {
                        ImageData data = new ImageData();

                        String path_of_image = cur.getString(cur.getColumnIndex("_data"));
                        data.imagePath = path_of_image;
                        data.temp_imagePath = path_of_image;
                        data.temp_org_imagePath = path_of_image;


                        ArrayList<ImageData> temp = org_selectedImages;
                        for (int i = 0; i < temp.size(); i++) {
                            if (path_of_image.equalsIgnoreCase(temp.get(i).temp_org_imagePath)) {
                                data.setSelected(true);
                            } else {
                                data.setSelected(false);
                            }
                        }

                        File file = new File(path_of_image);
                        Log.e("CHECKPATH", "getFolderList: check path--->"+data.imagePath );
                        Log.e("CHECKPATH", "getFolderList: file exist--->"+file.exists() );
                        if (!data.imagePath.endsWith(".gif") && file.exists()) {
                            String date = cur.getString(dateColumn);
                            String folderName = cur.getString(bucketColumn);
                            String folderId = cur.getString(bucketIdColumn);
                            if (!this.allFolder.contains(folderId)) {
                                this.allFolder.add(folderId);
                            }
                            ArrayList<ImageData> imagePath = this.allAlbum.get(folderId);
                            if (imagePath == null) {
                                imagePath = new ArrayList();
                            }
                            data.folderName = folderName;
                            imagePath.add(data);
                            allAlbum.put(folderId, imagePath);
                        }
                    } while (cur.moveToNext());
                }
            }
        } catch (Exception e) {
            Log.e("EXCEPTION", "getFolderList: " + e.getLocalizedMessage());
            Log.e("EXCEPTION", "getFolderList: " + e.getMessage());
        }
    }
}
