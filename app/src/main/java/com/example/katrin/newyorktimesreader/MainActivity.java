package com.example.katrin.newyorktimesreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = findViewById(R.id.pager);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        {
            Fragment fragment = new ArticleListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("category", ArticlesStore.Category.mostEmailed);
            fragment.setArguments(bundle);
            pagerAdapter.addFragment(fragment, "Most Emailed");
        }
        {
            Fragment fragment = new ArticleListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("category", ArticlesStore.Category.mostShared);
            fragment.setArguments(bundle);
            pagerAdapter.addFragment(fragment, "Most Shared");
        }
        {
            Fragment fragment = new ArticleListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("category", ArticlesStore.Category.mostViewed);
            fragment.setArguments(bundle);
            pagerAdapter.addFragment(fragment, "Most Viewed");
        }

        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav:
                Intent intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
                break;
            case R.id.sync:
                ArticlesStore.sync();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }


}
