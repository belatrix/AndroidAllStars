package com.belatrixsf.allstars.ui.contacts.keyword;

import android.os.Bundle;

import com.belatrixsf.allstars.entities.Keyword;
import com.belatrixsf.allstars.ui.common.AllStarsFragment;
import com.belatrixsf.allstars.utils.AllStarsApplication;
import static com.belatrixsf.allstars.ui.contacts.keyword.ContactsKeywordListActivity.KEYWORD_KEY;

/**
 * Created by PedroCarrillo on 5/12/16.
 */
public class ContactsKeywordListFragment extends AllStarsFragment {

    public static ContactsKeywordListFragment newInstance(Keyword keyword) {
        ContactsKeywordListFragment contactsKeywordListFragment = new ContactsKeywordListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEYWORD_KEY, keyword);
        contactsKeywordListFragment.setArguments(bundle);
        return contactsKeywordListFragment;
    }

    @Override
    protected void initDependencies(AllStarsApplication allStarsApplication) {

    }

}
