package com.grupo2tbd.thinkink;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.grupo2tbd.thinkink.Views.InformacionPerfilFragment;
import com.grupo2tbd.thinkink.Views.Picture_list;

/**
 * Created by cris_ on 21/12/2015.
 */
public class PerfilTatuador extends FragmentActivity {

    public PerfilTatuador() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_tatuador);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPagerPerfil);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int pos){
            switch(pos) {

                case 0: return InformacionPerfilFragment.newInstance();
                case 1: return Picture_list.newInstance();
                default: return InformacionPerfilFragment.newInstance();
        }

    }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
