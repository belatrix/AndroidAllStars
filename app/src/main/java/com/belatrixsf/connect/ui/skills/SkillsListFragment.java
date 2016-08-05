package com.belatrixsf.connect.ui.skills;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.SkillListAdapter;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.SkillsListPresenterModule;

/**
 * Created by echuquilin on 4/08/16.
 */
public class SkillsListFragment extends BelatrixConnectFragment implements SkillsListView{

    private SkillsListPresenter skillsListPresenter;
    private SkillListAdapter skillListAdapter;

    public static SkillsListFragment newInstance(){
        SkillsListFragment skillsListFragment = new SkillsListFragment();
        return skillsListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_skills_list, container, false);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        skillsListPresenter = belatrixConnectApplication.getApplicationComponent()
                .skillsListComponent(new SkillsListPresenterModule(this))
                .skillsListPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            restorePresenterState(savedInstanceState);
        }
        skillsListPresenter.getSkills();
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                contactsListPresenter.callNextPage();
            }
        };
        skillListAdapter = new SkillListAdapter(this);
        contactsRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        contactsRecyclerView.setAdapter(contactsListAdapter);
        contactsRecyclerView.setLayoutManager(linearLayoutManager);
        contactsRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));
    }
}
