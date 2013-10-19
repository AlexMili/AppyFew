package com.superone.superone.activities;

import java.io.File;
import java.util.ArrayList;

import com.superone.superone.AppConfiguration;
import com.superone.superone.db.DatabaseHelperTimelapse;
import com.superone.superone.R;
import com.superone.superone.models.TimelapseModel;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_main extends FragmentActivity implements ActionBar.TabListener{

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);

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
                    Fragment fragment1 = new FeedSectionFragment();
                    return fragment1;
                case 1:
                    Fragment fragment2 = new ListSectionFragment();
                    return fragment2;
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
                    return "Feeds";
                case 1:
                    return "Project";
                case 2:
                    return "Like";
            }
            return null;
        }
    }

    public static class ListSectionFragment extends Fragment implements AdapterView.OnItemClickListener{

        private DatabaseHelperTimelapse db;
        private ArrayList<TimelapseModel> _productlist = new ArrayList<TimelapseModel>();
        private ArrayList<Bitmap> thumbnails = new ArrayList<Bitmap>();
        private Bitmap defaultThumbnail;
        private ListView mainListView;
        private ListAdapter adapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
            mainListView = (ListView) rootView.findViewById(R.id.listTimelapse);
            defaultThumbnail = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_thumbnail);

            db = new DatabaseHelperTimelapse(rootView.getContext());
            db.getWritableDatabase();
            _productlist = db.getTimelapse();

            for(int i = thumbnails.size(); i < _productlist.size(); i++)
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
            }

            adapter = new ListAdapter(rootView.getContext());
            adapter.notifyDataSetChanged();
            mainListView.setAdapter(adapter);
            mainListView.invalidateViews();
            mainListView.setOnItemClickListener(this);
            return rootView;
        }

        @Override
        public void onResume()
        {
            super.onResume();

            _productlist = db.getTimelapse();

            for(int i = thumbnails.size(); i < _productlist.size(); i++)
            {
                if(new File(AppConfiguration.rootDirectory + _productlist.get(i).getMainUrl()).exists())
                    thumbnails.add(ThumbnailUtils.createVideoThumbnail(AppConfiguration.rootDirectory + _productlist.get(i).getMainUrl(),MediaStore.Images.Thumbnails.MINI_KIND));
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
            Intent intent = new Intent(view.getContext(), Activity_timelapse.class);
            intent.putExtra("ModelPosition", (int) id);
            startActivity(intent);
        }

        private class ListAdapter extends BaseAdapter {
            LayoutInflater inflater;
            ViewHolder viewHolder;

            public ListAdapter(Context context) {
                inflater = LayoutInflater.from(context);
            }

            @Override
            public int getCount() {
                return _productlist.size();
            }

            @Override
            public Object getItem(int position) {
                return _productlist.get(position);
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
                    viewHolder.txt_date = (TextView) convertView.findViewById(R.id.txtdisplaydate);
                    viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);

                    convertView.setTag(viewHolder);

                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                //set text of the label
                viewHolder.txt_name.setText(_productlist.get(position).getName().trim());
                viewHolder.txt_date.setText(_productlist.get(position).getDate());

                //set thumbnail
                if(position < thumbnails.size() && thumbnails.get(position) != null && thumbnails.get(position).getHeight() > 2)
                    viewHolder.thumbnail.setImageBitmap(thumbnails.get(position));
                else
                    viewHolder.thumbnail.setImageBitmap(defaultThumbnail);

                // set id
                convertView.setId(_productlist.get(position).getId());

                return convertView;

            }
        }

        private class ViewHolder{
            TextView txt_name;
            TextView txt_date;
            ImageView thumbnail;
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_feeds, container, false);
            return rootView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_timelapse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(Activity_main.this, Activity_new.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
