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
package com.belatrixsf.allstars.ui.about;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Collaborator;
import com.belatrixsf.allstars.ui.common.AllStarsPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 09/06/2016.
 */
public class AboutPresenter extends AllStarsPresenter<AboutView> {

    private List<Collaborator> collaborators = new ArrayList<>();

    @Inject
    public AboutPresenter(AboutView view) {
        super(view);
    }

    public void getContacts() {
        view.resetList();
        prepareCollaborators();
        view.addContacts(collaborators);
    }

    private void prepareCollaborators() {
        collaborators.add(new Collaborator("Gianfranco", "Yosida", R.drawable.yosida));
        collaborators.add(new Collaborator("Pedro", "Perez", R.drawable.ic_user));
        collaborators.add(new Collaborator("Pedro", "Perez", R.drawable.ic_user));
        collaborators.add(new Collaborator("Pedro", "Perez", R.drawable.ic_user));
        collaborators.add(new Collaborator("Pedro", "Perez", R.drawable.ic_user));
    }

    @Override
    public void cancelRequests() {

    }

    public List<Collaborator> getCollaboratorsSync() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    // saving state stuff

}