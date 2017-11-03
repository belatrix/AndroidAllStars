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
package com.belatrixsf.connect.ui.event.detail;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.belatrixsf.connect.R;
import com.belatrixsf.connect.entities.Event;
import com.belatrixsf.connect.managers.PreferencesManager;
import com.belatrixsf.connect.ui.common.BelatrixConnectFragment;
import com.belatrixsf.connect.utils.BelatrixConnectApplication;
import com.belatrixsf.connect.utils.Constants;
import com.belatrixsf.connect.utils.DialogUtils;
import com.belatrixsf.connect.utils.di.modules.presenters.EventDetailPresenterModule;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by icerrate on 27/06/2016.
 * modified by dvelasquez on 21/02/2017
 */
public class EventDetailFragment extends BelatrixConnectFragment implements EventDetailView {

    public static final String EVENT_ID_KEY = "_event_id_key";
    public static final String EVENT_KEY = "_event_key";
    public static final String EMPLOYEE_ID_KEY = "_employee_id_key";

    private EventDetailPresenter eventDetailPresenter;
    private EventDetailFragmentListener eventDetailFragmentListener;


    @Bind(R.id.date) TextView dateTextView;
    @Bind(R.id.location) TextView locationTextView;
    @Bind(R.id.title) TextView titleTextView;
    @Bind(R.id.description) TextView descriptionTextView;
    @Bind(R.id.collaborators) TextView collaboratorsCountTextView;
    @Bind(R.id.participants) TextView participantsCountTextView;
    @Bind(R.id.btn_register) Button eventRegisterButton;
    @BindString(R.string.event_dialog_confirm_register) String eventConfirmRegister;
    @BindString(R.string.event_dialog_confirm_unregister) String eventConfirmUnregister;
    @BindString(R.string.event_dialog_result_registered) String eventResultRegistered;
    @BindString(R.string.event_dialog_result_unregistered) String eventResultUnregistered;
    @BindString(R.string.title_main) String dialogTitle;

    public static EventDetailFragment newInstance(Integer eventId) {
        Bundle bundle = new Bundle();
        if (eventId != null) {
            bundle.putInt(EventDetailActivity.EVENT_ID_KEY, eventId);
        }
        EventDetailFragment accountFragment = new EventDetailFragment();
        accountFragment.setArguments(bundle);
        return accountFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        castOrThrowException(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        castOrThrowException(context);
    }

    private void castOrThrowException(Context context) {
        try {
            eventDetailFragmentListener = (EventDetailFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement EventDetailFragmentListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Integer eventId;
        boolean hasArguments = (getArguments() != null && getArguments().containsKey(EventDetailActivity.EVENT_ID_KEY));
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else if (hasArguments) {
            eventId = getArguments().getInt(EventDetailActivity.EVENT_ID_KEY);
            eventDetailPresenter.setEventId(eventId);
            eventDetailPresenter.setEmployeeId(PreferencesManager.get().getEmployeeId());
            eventDetailPresenter.loadEventDetail();
        }
    }

    @Override
    protected void initDependencies(BelatrixConnectApplication belatrixConnectApplication) {
        eventDetailPresenter = belatrixConnectApplication.getApplicationComponent()
                .eventDetailComponent(new EventDetailPresenterModule(this))
                .eventDetailPresenter();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState(outState);
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        Event event = savedInstanceState.getParcelable(EVENT_KEY);
        Integer eventId = savedInstanceState.getInt(EVENT_ID_KEY, 0);
        Integer employeeId = savedInstanceState.getInt(EMPLOYEE_ID_KEY, 0);
        eventDetailPresenter.setEmployeeId(employeeId);
        eventDetailPresenter.setEventId(eventId);
        eventDetailPresenter.loadPresenterState(event);
    }

    private void saveState(Bundle outState) {
        outState.putInt(EVENT_ID_KEY, eventDetailPresenter.getEventId());
        outState.putParcelable(EVENT_KEY, eventDetailPresenter.getEvent());
        outState.putInt(EMPLOYEE_ID_KEY, eventDetailPresenter.getEmployeeId());
    }



    @Override
    public void showDateTime(String dateTime) {
        dateTextView.setText(dateTime);
    }

    @Override
    public void showTitle(String title) {
        fragmentListener.setTitle(title);
        titleTextView.setText(title);
    }

    @Override
    public void showDescription(String description) {
        descriptionTextView.setText(description);
    }

    @Override
    public void showPicture(final String profilePicture) {
        eventDetailFragmentListener.showPicture(profilePicture);
    }

    @Override
    public void onDestroyView() {
        eventDetailPresenter.cancelRequests();
        super.onDestroyView();
    }

    @Override
    public void enableRegister() {
        eventRegisterButton.setBackgroundResource(R.drawable.selector_primary_button_inverse);
        TextViewCompat.setTextAppearance(eventRegisterButton, R.style.Button_Primary_Inverse);
        eventRegisterButton.setText(getString(R.string.string_register));
        eventRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                DialogUtils.createConfirmationDialogWithTitle(getActivity(),dialogTitle,eventConfirmRegister, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eventDetailPresenter.requestRegister(Constants.EVENT_REGISTER_ACTION);
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                            }
                        }
                ).show();
               */
                String url = eventDetailPresenter.getEvent().getRegistrationURL();
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    DialogUtils.createSimpleDialog(getActivity(),getString(R.string.app_name),"You don't have any app that could open this links");
                }


            }
        });
        eventRegisterButton.setVisibility(View.VISIBLE);
    }


    @Override
    public void enableUnregister() {
        //TODO: improve button style for registration
        eventRegisterButton.setBackgroundResource(R.drawable.selector_primary_button);
        TextViewCompat.setTextAppearance(eventRegisterButton, R.style.Button_Primary);
        eventRegisterButton.setBackgroundResource(R.drawable.selector_primary_button);
        eventRegisterButton.setText(getString(R.string.string_unregister));
        eventRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.createConfirmationDialogWithTitle(getActivity(),dialogTitle, eventConfirmUnregister, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eventDetailPresenter.requestRegister(Constants.EVENT_UNREGISTER_ACTION);
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                ).show();
            }
        });
    }


    @Override
    public void showRegisterResult() {
            DialogUtils.createInformationDialog(getActivity(),eventResultRegistered,dialogTitle ,null).show();
    }

    @Override
    public void showUnregisterResult() {
        DialogUtils.createInformationDialog(getActivity(),eventResultUnregistered, dialogTitle,null).show();
    }
}