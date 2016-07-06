/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrixsf.connect.ui.about;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.adapters.TeamListAdapter;
import com.belatrixsf.connect.entities.Collaborator;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.di.modules.presenters.AboutPresenterModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by icerrate on 09/06/2016.
 */
public class AboutFragment extends BelatrixConnectFragment implements AboutView {

    public static final String COLLABORATORS_KEY = "_collaborators_key";

    private AboutPresenter aboutPresenter;
    private TeamListAdapter collaboratorListAdapter;

    @Bind(R.id.collaborators)
    RecyclerView collaboratorsRecyclerView;

    public static AboutFragment newInstance() {
        AboutFragment aboutFragment = new AboutFragment();
        return aboutFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        aboutPresenter = belatrixConnectApplication.getApplicationComponent()
                .aboutComponent(new AboutPresenterModule(this))
                .aboutPresenter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        if (savedInstanceState != null) {
            restorePresenterState(savedInstanceState);
        }
        aboutPresenter.getContacts();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        savePresenterState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restorePresenterState(Bundle savedInstanceState) {
        List<Collaborator> collaborators = savedInstanceState.getParcelableArrayList(COLLABORATORS_KEY);
        aboutPresenter.setCollaborators(collaborators);
    }

    private void savePresenterState(Bundle outState) {
        List<Collaborator> collaborators = aboutPresenter.getCollaboratorsSync();
        if (collaborators != null && collaborators instanceof ArrayList) {
            outState.putParcelableArrayList(COLLABORATORS_KEY, (ArrayList<Collaborator>) collaborators);
        }
    }

    private void initViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        collaboratorListAdapter = new TeamListAdapter();
        collaboratorsRecyclerView.setAdapter(collaboratorListAdapter);
        collaboratorsRecyclerView.setNestedScrollingEnabled(false);
        collaboratorsRecyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void addContacts(List<Collaborator> collaborators) {
        collaboratorListAdapter.add(collaborators);
    }

    @Override
    public void resetList() {
        collaboratorListAdapter.reset();
    }
}