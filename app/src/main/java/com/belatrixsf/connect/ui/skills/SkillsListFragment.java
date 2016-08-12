package com.belatrixsf.connect.ui.skills;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.SkillListAdapter;
import com.belatrixsf.connect.entities.Keyword;
import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.ui.common.EndlessRecyclerOnScrollListener;
import com.belatrixsf.connect.ui.common.views.DividerItemDecoration;
import com.belatrixsf.connect.ui.skills.Add.AddSkillActivity;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.SnackbarUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.SkillsListPresenterModule;

import java.util.List;

import butterknife.Bind;

/**
 * Created by echuquilin on 4/08/16.
 */
public class SkillsListFragment extends BelatrixConnectFragment implements SkillsListView, SkillListAdapter.KeywordListener {

    private static SkillsListPresenter skillsListPresenter;
    private SkillListAdapter skillListAdapter;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    @Bind(R.id.skills) RecyclerView skillssRecyclerView;
    @Bind(R.id.no_data_textview) TextView noDataTextView;
    @Bind(R.id.button_add_skill) Button addSkillButton;
    @Bind(R.id.refresh_keywords) SwipeRefreshLayout skillsRefreshLayout;
    @Bind(R.id.progressBar) ProgressBar loadingProgressBar;
    @Bind(R.id.coordinator_keywords) CoordinatorLayout skillsCoordinatorLayout;

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
        skillsListPresenter.getSkills(PreferencesManager.get().getEmployeeId(),false);
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                skillsListPresenter.callNextPage();
            }
        };
        skillListAdapter = new SkillListAdapter();
        skillListAdapter.setKeywordListener(this);
        skillssRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        skillssRecyclerView.setAdapter(skillListAdapter);
        skillssRecyclerView.setLayoutManager(linearLayoutManager);
        skillssRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), android.R.drawable.divider_horizontal_bright)));

        skillsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                skillsListPresenter.refreshSkills(true);
            }
        });
        skillsRefreshLayout.setColorSchemeResources(R.color.swipe_refresh);

        addSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddSkillActivity.makeIntent(getActivity()));
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        if (loadingProgressBar != null) {
            loadingProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressIndicator() {
        if (loadingProgressBar != null) {
            loadingProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideRefreshData() {
        if (skillsRefreshLayout != null) {
            skillsRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDestroyView() {
        skillsListPresenter.cancelRequests();
        super.onDestroyView();
    }

    @Override
    public void addSkills(List<Keyword> skills) {
        skillListAdapter.add(skills);
    }

    @Override
    public void resetList() {
        skillListAdapter.reset();
    }

    @Override
    public void showNoDataView(String message) {
        noDataTextView.setText(message);
        noDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDataView() {
        noDataTextView.setVisibility(View.GONE);
    }

    @Override
    public void showDeleteConfirmationDialog(String message, final Keyword keyword) {
        DialogUtils.createConfirmationDialog(getActivity(), message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                skillsListPresenter.confirmDelete(keyword.getName());
            }
        }, null).show();
    }

    @Override
    public void showDeletedInformation(String skillName) {
        String message = skillName + " " + getString(R.string.dialog_confirmation_deleted);
        SnackbarUtils.createInformationSnackBar(message, skillsCoordinatorLayout,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nothing
            }
        }).show();
    }

    public static void refreshFromAddSkill(){
        skillsListPresenter.refreshSkills(false);
    }

    @Override
    public void onKeywordSelected(int position) {
        skillsListPresenter.onSkillSelected(position);
    }
}
