package hu.plezervi.elte.gfmwa.todonotesapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hu.plezervi.elte.gfmwa.todonotesapp.R;
import hu.plezervi.elte.gfmwa.todonotesapp.models.Todo;

/**
* Created by plezerv on 2013.09.28..
*/
public class TodoDetailerFragment extends Fragment {

    private ViewPager viewPager;
    private Todo todo;

    private class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        private Todo todo;
        TodoFirstPageFragment first;
        TodoSecondPageFragment second;

        public MyViewPagerAdapter(FragmentManager manager, Todo todo) {
            super(manager);
            this.todo = todo;
        }

        @Override
        public Fragment getItem(int i) {
            switch(i) {
                case 0:
                    first = new TodoFirstPageFragment();
                    first.setTodo(todo);
                    return first;
                case 1:
                    second = new TodoSecondPageFragment();
                    second.setTodo(todo);
                    return second;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle (int position) {
            switch (position) {
                case 0: return getResources().getString(R.string.todocontent);
                case 1: return getResources().getString(R.string.tododetail);
            }
            return null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("todo", todo);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tododetailer, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.detailerpager);
        if(savedInstanceState != null) {
            todo = (Todo)savedInstanceState.getSerializable("todo");
        }
        return v;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
        if(viewPager != null) {
            viewPager.setAdapter(new MyViewPagerAdapter(getFragmentManager(), todo));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(todo != null) {
            viewPager.setAdapter(new MyViewPagerAdapter(getFragmentManager(), todo));
        }
    }
}
