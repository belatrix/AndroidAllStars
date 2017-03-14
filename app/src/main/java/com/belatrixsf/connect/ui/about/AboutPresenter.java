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

    private List<Collaborator> collaboratorsList = new ArrayList<>();

    @Inject
    public AboutPresenter(AboutView view) {
        super(view);
    }

    public void getContacts() {
        view.resetList();
        prepareCollaborators();
        view.addContacts(collaboratorsList);
    }

    private void prepareCollaborators() {
        collaboratorsList = new ArrayList<>();
        collaboratorsList.add(new Collaborator("Antonella", "Manna", R.drawable.amanna));
        collaboratorsList.add(new Collaborator("Carlos", "Piñan", R.drawable.cpinan));
        collaboratorsList.add(new Collaborator("Diego", "Velásquez", R.drawable.dvelasquez));
        collaboratorsList.add(new Collaborator("Eduardo", "Chuquilin", R.drawable.echuquilin));
        collaboratorsList.add(new Collaborator("Flavio", "Franco", R.drawable.ffranco));
        collaboratorsList.add(new Collaborator("Gianfranco", "Yosida", R.drawable.gyosida));
        collaboratorsList.add(new Collaborator("Gladys", "Cuzcano", R.drawable.gcuzcano));
        collaboratorsList.add(new Collaborator("Ivan", "Cerrate", R.drawable.icerrate));
        collaboratorsList.add(new Collaborator("Javier", "Valdivia", R.drawable.jvaldivia));
        collaboratorsList.add(new Collaborator("Jo", "Yep", R.drawable.jyep));
        collaboratorsList.add(new Collaborator("Jorge", "Boneu", R.drawable.jboneu));
        collaboratorsList.add(new Collaborator("Karla", "Cerron", R.drawable.kcerron));
        collaboratorsList.add(new Collaborator("Lucia", "Castro", R.drawable.lcastro));
        collaboratorsList.add(new Collaborator("Luis", "Barzola", R.drawable.lbarzola));
        collaboratorsList.add(new Collaborator("Pedro", "Carrillo", R.drawable.pcarrillo));
        collaboratorsList.add(new Collaborator("Rodrigo", "Gonzalez", R.drawable.rgonzalez));
        collaboratorsList.add(new Collaborator("Sergio", "Infante", R.drawable.sinfante));
    }

    @Override
    public void cancelRequests() {

    }

    public List<Collaborator> getCollaboratorsSync() {
        return collaboratorsList;
    }

    // saving state stuff

    public void loadPresenterState(List<Collaborator> collaboratorsList) {
        this.collaboratorsList = collaboratorsList;
    }

}