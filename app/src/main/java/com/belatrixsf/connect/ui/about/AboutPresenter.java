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

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Collaborator;
import com.belatrixsf.connect.ui.common.BelatrixConnectPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icerrate on 09/06/2016.
 */
public class AboutPresenter extends BelatrixConnectPresenter<AboutView> {

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
        collaborators = new ArrayList<>();
        collaborators.add(new Collaborator("Carlos", "Piñan", R.drawable.cpinan));
        collaborators.add(new Collaborator("Flavio", "Franco", R.drawable.ffranco));
        collaborators.add(new Collaborator("Gianfranco", "Yosida", R.drawable.gyosida));
        collaborators.add(new Collaborator("Gladys", "Cuzcano", R.drawable.gcuzcano));
        collaborators.add(new Collaborator("Ivan", "Cerrate", R.drawable.icerrate));
        collaborators.add(new Collaborator("Javier", "Valdivia", R.drawable.jvaldivia));
        collaborators.add(new Collaborator("Jo", "Yep", R.drawable.jyep));
        collaborators.add(new Collaborator("Jorge", "Benou", R.drawable.jbenou));
        collaborators.add(new Collaborator("Luis", "Barzola", R.drawable.lbarzola));
        collaborators.add(new Collaborator("Pedro", "Carrillo", R.drawable.pcarrillo));
        collaborators.add(new Collaborator("Rodrigo", "Gonzalez", R.drawable.rgonzalez));
        collaborators.add(new Collaborator("Sergio", "Infante", R.drawable.sinfante));
    }

    @Override
    public void cancelRequests() {

    }

    public List<Collaborator> getCollaboratorsSync() {
        return collaborators;
    }

    // saving state stuff

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

}