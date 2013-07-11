package com.antonio081014.multidatabasetable;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private List<ProfilePhoto> listOfProfilePhoto;
    private ListView list;
    private DatabaseHandler db;

    BaseAdapter myAdapter = new BaseAdapter() {

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    if (convertView != null)
		return convertView;
	    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.row_layout, parent, false);
	    TextView conTV = (TextView) rowView.findViewById(R.id.tv_contact);
	    TextView proTV = (TextView) rowView.findViewById(R.id.tv_profile);

	    conTV.setText(db.getContact(
		    listOfProfilePhoto.get(position).get_belongTo()).getName());
	    proTV.setText(listOfProfilePhoto.get(position).getName());

	    return rowView;
	}

	@Override
	public long getItemId(int position) {
	    return 0;
	}

	@Override
	public Object getItem(int position) {
	    return null;
	}

	@Override
	public int getCount() {
	    return listOfProfilePhoto.size();
	}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	db = new DatabaseHandler(this);
	List<Contact> contacts = db.getAllContacts();
	if (contacts.size() == 0) {
	    db.addContact(new Contact("antonio081014", "9100000000"));
	    db.addContact(new Contact("antonio", "9199999999"));
	    contacts = db.getAllContacts();
	}
	Log.i("Main contacts", contacts.toString());
	listOfProfilePhoto = db.getAllProfiles();
	if (listOfProfilePhoto.size() == 0) {
	    db.addProfilePhoto(new ProfilePhoto(contacts.get(0).getID(),
		    "Beauty1"));
	    db.addProfilePhoto(new ProfilePhoto(contacts.get(1).getID(),
		    "Beauty2"));
	    db.addProfilePhoto(new ProfilePhoto(contacts.get(0).getID(),
		    "Beauty3"));
	    db.addProfilePhoto(new ProfilePhoto(contacts.get(1).getID(),
		    "Beauty4"));
	    db.addProfilePhoto(new ProfilePhoto(contacts.get(0).getID(),
		    "Beauty5"));
	    db.addProfilePhoto(new ProfilePhoto(contacts.get(1).getID(),
		    "Beauty6"));
	    db.addProfilePhoto(new ProfilePhoto(contacts.get(0).getID(),
		    "Beauty7"));
	    db.addProfilePhoto(new ProfilePhoto(contacts.get(1).getID(),
		    "Beauty8"));
	    db.addProfilePhoto(new ProfilePhoto(contacts.get(0).getID(),
		    "Beauty9"));

	    listOfProfilePhoto = db.getAllProfilesWithContact(contacts.get(0)
		    .getID());
	    // listOfProfilePhoto = db.getAllProfiles();
	} else {
	    listOfProfilePhoto = db.getAllProfilesWithContact(contacts.get(1)
		    .getID());
	}
	Log.i("Main Profile", listOfProfilePhoto.toString());

	list = (ListView) findViewById(R.id.listView1);
	list.setAdapter(myAdapter);
    }
}