package ru.alex.sibrivertest.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;

import ru.alex.sibrivertest.R;
import ru.alex.sibrivertest.adapter.Adapter;
import ru.alex.sibrivertest.ormlitedatabase.helper.HelperFactory;
import ru.alex.sibrivertest.service.ApiGetData;
import ru.alex.sibrivertest.service.ApiPostData;

public class TestList extends Fragment implements Adapter.ViewHolder.ClickListener {
    private View rootView;
    private Adapter rvAdapter;
    private ActionBarActivity abActivity;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private TextView emptyData;
    private ApiPostData post;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_fragment, container, false);
        swipeContainer = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeContainer);
        HelperFactory.setDatabaseHelper(getActivity());
        rvAdapter = new Adapter(this, getActivity());
        emptyData = (TextView)rootView.findViewById(R.id.emptyData);
        new ApiGetData(getActivity(), emptyData).execute();
        rvAdapter.notifyDataSetChanged();
        abActivity = (ActionBarActivity) getActivity();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        return rootView;
    }

    public static TestList getInstance(TestList testList) {
        if (testList == null) {
            testList = new TestList();
        }
        return testList;
    }

    @Override
    public void onItemClicked(int position) {
        if (actionMode != null) {
            toggleSelection(position);
        }
    }

    @Override
    public boolean onItemLongClicked(int position) {
        if (actionMode == null) {
            actionMode = abActivity.startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
        return true;
    }

    private void toggleSelection(int position) {
        rvAdapter.toggleSelection(position);
        int count = rvAdapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.selected_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuRemove:
                    try {
                        rvAdapter.removeItems(rvAdapter.getSelectedItems());
                        post = new ApiPostData();
                        post.sendData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            rvAdapter.clearSelection();
            actionMode = null;
        }
    }

    private void refreshItems(){
        new ApiGetData(getActivity(), emptyData).execute();
        rvAdapter.clear();
        onItemRereshComplete();
    }

    private void onItemRereshComplete(){
        try {
            rvAdapter.restoreItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rvAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(rvAdapter);
        swipeContainer.setRefreshing(false);
    }

}
