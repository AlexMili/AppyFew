package com.happyfew.happyfew.activities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyfew.happyfew.AppConfiguration;
import com.happyfew.happyfew.R;
import com.happyfew.happyfew.async.GetRequest;
import com.happyfew.happyfew.async.PostRequest;
import com.happyfew.happyfew.json.UserData;
import com.happyfew.happyfew.utils.IdUtils;
import com.happyfew.happyfew.utils.PackageUtils;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Activity_main extends FragmentActivity implements ActionBar.TabListener,AppConfiguration{

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        Log.d(debugTag, "HELLO--------------------------------------------------------------------------------------");
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    //Fragment fragment1 = new FeedSectionFragment();
                    Fragment fragment2 = new ListSectionFragment();
                    //return fragment1;
                    return fragment2;
                case 1:
                    //Fragment fragment2 = new ListSectionFragment();
                    Fragment fragment1 = new FeedSectionFragment();
                    //return fragment2;
                    return fragment1;
                case 2:
                    Fragment fragment3 = new MakeSectionFragment();
                    return fragment3;
                default:
                    Fragment fragment = new FeedSectionFragment();
                    return fragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0:
                    return "BONS PLANS";
                case 1:
                    return "Profil";
                case 2:
                    return "Like";
            }
            return null;
        }
    }

    public static class ListSectionFragment extends Fragment implements AdapterView.OnItemClickListener{

        /*private DatabaseHelperTimelapse db;
        private ArrayList<TimelapseModel> _productlist = new ArrayList<TimelapseModel>();*/
        private ArrayList<Bitmap> thumbnails = new ArrayList<Bitmap>();
        private Bitmap defaultThumbnail;
        private ListView mainListView;
        private ListAdapter adapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
            mainListView = (ListView) rootView.findViewById(R.id.listTimelapse);
            //defaultThumbnail = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_thumbnail);

            /*db = new DatabaseHelperTimelapse(rootView.getContext());
            db.getWritableDatabase();
            _productlist = db.getTimelapse();*/

            /*for(int i = thumbnails.size(); i < _productlist.size(); i++)
            {
                if(new File(AppConfiguration.rootDirectory + _productlist.get(i).getMainUrl()).exists())
                {
                    thumbnails.add(ThumbnailUtils.createVideoThumbnail(AppConfiguration.rootDirectory + _productlist.get(i).getMainUrl(),
                            MediaStore.Images.Thumbnails.MINI_KIND));
                }
                else
                {
                    thumbnails.add(null);
                }
            }*/
            for(int i = thumbnails.size(); i < 3; i++) {
                if(new File(test).exists())
                    thumbnails.add(ThumbnailUtils.createVideoThumbnail(test,MediaStore.Images.Thumbnails.MINI_KIND));
                else
                    thumbnails.add(null);
            }

            adapter = new ListAdapter(rootView.getContext());
            adapter.notifyDataSetChanged();
            mainListView.setAdapter(adapter);
            mainListView.invalidateViews();
            mainListView.setOnItemClickListener(this);
            Log.d(debugTag, "TRUC--------------------------------------------------------------------------------------");
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();

            //_productlist = db.getTimelapse();

            /*for(int i = thumbnails.size(); i < _productlist.size(); i++)
            {
                if(new File(AppConfiguration.rootDirectory + _productlist.get(i).getMainUrl()).exists())
                    thumbnails.add(ThumbnailUtils.createVideoThumbnail(AppConfiguration.rootDirectory + _productlist.get(i).getMainUrl(),MediaStore.Images.Thumbnails.MINI_KIND));
                else
                    thumbnails.add(null);
            }*/
            for(int i = thumbnails.size(); i < 3; i++)
            {
                if(new File(test).exists())
                    thumbnails.add(ThumbnailUtils.createVideoThumbnail(test,MediaStore.Images.Thumbnails.MINI_KIND));
                else
                    thumbnails.add(null);
            }

            adapter = new ListAdapter(getView().getContext());
            adapter.notifyDataSetChanged();
            mainListView.setAdapter(adapter);
            mainListView.invalidateViews();
            mainListView.setOnItemClickListener(this);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*Intent intent = new Intent(view.getContext(), Activity_timelapse.class);
            intent.putExtra("ModelPosition", (int) id);
            startActivity(intent);*/
        }

        private class ListAdapter extends BaseAdapter {
            LayoutInflater inflater;
            ViewHolder viewHolder;

            public ListAdapter(Context context) {
                inflater = LayoutInflater.from(context);
            }

            @Override
            public int getCount() {
                //return _productlist.size();
                return COUNT;
            }

            @Override
            public Object getItem(int position) {
                //return _productlist.get(position);
                return "HELLO";
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    convertView = inflater.inflate(R.layout.row_project, null);
                    viewHolder = new ViewHolder();

                    viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txtdisplaypname);
                    viewHolder.txt_cat = (TextView) convertView.findViewById(R.id.txtcatname);
                    viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);

                    viewHolder.layoutColor = (RelativeLayout) convertView.findViewById(R.id.layoutColor);

                    convertView.setTag(viewHolder);

                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                //set text of the label
                viewHolder.txt_name.setText(/*_productlist.get(position).getName().trim()*/endingTime[position]);
                viewHolder.layoutColor.setBackgroundColor(colors[position]);
                viewHolder.txt_cat.setText(/*_productlist.get(position).getDate()*/cat[position]);
                Log.d(debugTag, "POSITION : " + position+ " - "+endingTime[position]+cat[position]);
                //set thumbnail
                /*if(position < thumbnails.size() && thumbnails.get(position) != null && thumbnails.get(position).getHeight() > 2)
                    viewHolder.thumbnail.setImageBitmap(thumbnails.get(position));
                else*/
                    //viewHolder.thumbnail.setImageBitmap(defaultThumbnail);
                viewHolder.thumbnail.setImageResource(bans[position]);

                // set id
                convertView.setId(/*_productlist.get(position).getId()*/2);

                return convertView;

            }
        }

        private class ViewHolder{
            TextView txt_name;
            TextView txt_cat;
            ImageView thumbnail;
            RelativeLayout layoutColor;
        }
    }
    public static class MakeSectionFragment extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_feeds, container, false);



            return rootView;
        }
    }

    public static class FeedSectionFragment extends Fragment {

        private Button generateB;

        private Switch swMode;
        private Switch swGarden;
        private Switch swTech;
        private Switch swTravel;
        private Switch swShow;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_feeds, container, false);

            generateB = (Button) rootView.findViewById(R.id.btn_generate);
            generateB.setOnClickListener(generateListener);

            swTech = (Switch) rootView.findViewById(R.id.tech);
            swMode = (Switch) rootView.findViewById(R.id.mode);
            swGarden = (Switch) rootView.findViewById(R.id.garden);
            swTravel = (Switch) rootView.findViewById(R.id.travel);
            swShow = (Switch) rootView.findViewById(R.id.show);

            swTech.setOnCheckedChangeListener(checkedListener);
            swMode.setOnCheckedChangeListener(checkedListener);
            swGarden.setOnCheckedChangeListener(checkedListener);
            swTravel.setOnCheckedChangeListener(checkedListener);
            swShow.setOnCheckedChangeListener(checkedListener);

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();

            UserData data = new UserData();
            ObjectMapper mapper = new ObjectMapper();

            UserData reading = null;
            try { reading = mapper.readValue(new File(jsonFilePath), UserData.class); }
            catch (IOException e) { e.printStackTrace(); }

            if(reading != null) {
                swTech.setChecked(reading.tech);
                swMode.setChecked(reading.mode);
                swGarden.setChecked(reading.garden);
                swTravel.setChecked(reading.travel);
                swShow.setChecked(reading.show);
            }
        }

        public View.OnClickListener generateListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(debugTag, "COUCOU");
                Collection col = PackageUtils.getInstalledApps(getActivity().getPackageManager());

                Iterator it = col.iterator();

                while(it.hasNext()) {
                    ApplicationInfo info = (ApplicationInfo) it.next();

                    if(info != null) {
                        if(info.packageName.equals("com.github.mobile")) {
                            Log.d(debugTag, info.packageName);
                            swTech.setChecked(true);
                        }
                    }
                }
            }
        };

        private CompoundButton.OnCheckedChangeListener checkedListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                Log.d(debugTag, button.getId() + " IS CHECKED : " + isChecked);

                UserData data = new UserData();

                data.id = IdUtils.getAndroidId(getActivity());
                data.imei = IdUtils.getImei((TelephonyManager)getActivity().getSystemService( getActivity().TELEPHONY_SERVICE ));

                try { data.serialNumber = IdUtils.getSerialNumber(); }
                catch (Exception e) { e.printStackTrace(); }

                data.simSerial = IdUtils.getSimSerial(( TelephonyManager)getActivity().getSystemService( getActivity().TELEPHONY_SERVICE ));

                data.mode = swMode.isChecked();
                data.garden = swGarden.isChecked();
                data.tech = swTech.isChecked();
                data.travel = swTravel.isChecked();
                data.show = swShow.isChecked();

                ObjectMapper mapper = new ObjectMapper();

                try { mapper.writeValue(new File(jsonFilePath), data); }
                catch (IOException e) { e.printStackTrace(); }


                String myJson = null;
                try { myJson = readFileAsString(jsonFilePath, "UTF-8"); }
                catch (IOException e) { e.printStackTrace(); }

                Log.d(debugTag, myJson);

                GetRequest get = new GetRequest();
                Log.d(debugTag, serverURL+"?jsonuser="+ Uri.encode(myJson));
                get.execute(new String[]{serverURL+"?jsonuser="+ Uri.encode(myJson)});

                /*switch(button.getId()) {
                    case R.id.mode:

                        break;
                    case R.id.garden:

                        break;
                    case R.id.tech:

                        break;
                    case R.id.travel:

                        break;
                    case R.id.show:

                        break;
                }*/
            }

        };

        public static String readFileAsString(String fileName, String charsetName) throws java.io.IOException {
            java.io.InputStream is = new java.io.FileInputStream(fileName);
            try {
                final int bufsize = 4096;
                int available = is.available();
                byte[] data = new byte[available < bufsize ? bufsize : available];
                int used = 0;
                while (true) {
                    if (data.length - used < bufsize) {
                        byte[] newData = new byte[data.length << 1];
                        System.arraycopy(data, 0, newData, 0, used);
                        data = newData;
                    }
                    int got = is.read(data, used, data.length - used);
                    if (got <= 0) break;
                    used += got;
                }
                return charsetName != null ? new String(data, 0, used, charsetName) : new String(data, 0, used);
            }
            finally { is.close(); }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_timelapse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.menu_settings:
                Intent intent = new Intent(Activity_main.this, Activity_new.class);
                startActivity(intent);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*public void swClick(View v, boolean checked) {
        Log.d(debugTag, "CHECKED : " +checked);
    }*/
}