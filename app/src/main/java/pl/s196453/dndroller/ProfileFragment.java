package pl.s196453.dndroller;



import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */



public class ProfileFragment extends Fragment implements View.OnClickListener {


    private OnFragmentInteractionListener mListener;

    private Spinner strSpinner;
    private Spinner dexSpinner;
    private Spinner conSpinner;
    private Spinner intSpinner;
    private Spinner wisSpinner;
    private Spinner chaSpinner;
    private Spinner acSpinner;
    private Spinner profileSpinner;

    private Button addButton;
    private Button deleteButton;

    private EditText pName;
    //private EditText aClass;

    private Profile prof;
    private MyAppDatabase datab;
    private List<Profile> profiles;

    private Integer[] acvals = new Integer[]{10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
    private Integer[] attribs = new Integer[]{3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19}; //looks bad but getting values from arrsys doesn't want to work with adapters
    //attribs = getActivity().getResources().getIntArray(R.array.attributes_spin); //calling this in oncreateview did not work
    //adaprter requires Integer[], getResources gives me int[], casting seemed not to do anything

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profView = inflater.inflate(R.layout.fragment_profile, container, false);

        datab = MyAppDatabase.getInstance(getContext());

        profiles = datab.profileDAO().getAll();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_dropdown_item, attribs);
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_dropdown_item, acvals);
        ArrayAdapter<Profile> adapterProf = new ArrayAdapter<Profile>(getContext(),android.R.layout.simple_spinner_dropdown_item, profiles);


        profileSpinner = (Spinner) profView.findViewById(R.id.profileSpinner1);
        profileSpinner.setAdapter(adapterProf);
        strSpinner = (Spinner) profView.findViewById(R.id.Strength);
        strSpinner.setAdapter(adapter);
        dexSpinner = (Spinner) profView.findViewById(R.id.dexterity);
        dexSpinner.setAdapter(adapter);
        conSpinner = (Spinner) profView.findViewById(R.id.constitution);
        conSpinner.setAdapter(adapter);
        intSpinner = (Spinner) profView.findViewById(R.id.intelligence);
        intSpinner.setAdapter(adapter);
        wisSpinner = (Spinner) profView.findViewById(R.id.wisdom);
        wisSpinner.setAdapter(adapter);
        chaSpinner = (Spinner) profView.findViewById(R.id.charisma);
        chaSpinner.setAdapter(adapter);
        acSpinner = (Spinner) profView.findViewById(R.id.acSpinner);
        acSpinner.setAdapter(adapter1);

        addButton = (Button) profView.findViewById(R.id.bAdd);
        deleteButton = (Button) profView.findViewById(R.id.bDelete);
        addButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        pName =(EditText) profView.findViewById(R.id.profileName);

        /*populateSpinnerString(profView,strSpinner,R.id.Strength, R.array.attrib_spin2);
        populateSpinner(profView,dexSpinner,R.id.dexterity);  //spinners initialized this way always resulted in a null point exception when referencing
        populateSpinner(profView,conSpinner,R.id.constitution);
        populateSpinner(profView,intSpinner,R.id.intelligence);
        populateSpinner(profView,wisSpinner,R.id.wisdom);
        populateSpinner(profView,chaSpinner,R.id.charisma);*/

        return profView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        datab.destInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bAdd:  test();
                            new Thread(new Runnable() { @Override public void run() {
                                Looper.prepare();
                                createProfile(pName.getText().toString(), (int)strSpinner.getSelectedItem(),(int)dexSpinner.getSelectedItem(),(int)conSpinner.getSelectedItem(),(int)intSpinner.getSelectedItem(),(int)wisSpinner.getSelectedItem(),(int)chaSpinner.getSelectedItem(),(int)acSpinner.getSelectedItem());
                                } }) .start();
                            Toast toast = Toast.makeText(getContext(), "profile created",Toast.LENGTH_SHORT);
                            toast.show();
                break;
            case R.id.bDelete:
                            new Thread(new Runnable() { @Override public void run() {
                                Looper.prepare();
                                delProfile(profileSpinner.getSelectedItem().toString()); } }) .start();
                            Toast toast1 = Toast.makeText(getContext(), "profile deleted",Toast.LENGTH_SHORT);
                            toast1.show();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        //  Update argument type and name
        void onFragmentInteraction(Uri uri);

    }


    public void createProfile(String string, int str, int dex, int con, int intel, int wis, int cha, int ac){
            prof = new Profile(string, str, dex, con, intel, wis, cha, ac);
            datab.profileDAO().insert(prof);
            Log.d("TEST CREATION", "profile created");
        /*datab.profileDAO().update(prof);
        Log.d("TEST UPDATE", "profile updated");
        */
    }

    public void delProfile(String string ){
            int del = datab.profileDAO().deleteProfile(string);
            Log.d("TEST DELETION", "profile deleted, no. of lines deleted " + del);
    }


    public void test(){ //test of taking values from spinners

        Log.d("test begin", "still works");
        String a = pName.getText().toString();
        Log.d("test begin1", "still works name= " +a);
        // int ac = Integer.parseInt(aClass.getText().toString());
        int ac = (int)acSpinner.getSelectedItem();
        Log.d("test begin1", "still works  ac= "+ac);
        String cont = getContext().toString();
        Log.d("IS CONTEX ", "CONTEXT  "+cont);
        int s = (int)strSpinner.getSelectedItem();
        Log.d("test runs", "works?  str= "+s);
        int d = (int)dexSpinner.getSelectedItem();
        int c = (int)conSpinner.getSelectedItem();
        int i = (int)intSpinner.getSelectedItem();
        int w = (int)wisSpinner.getSelectedItem();
        int ch = (int)chaSpinner.getSelectedItem();

        Log.d("test", " "+a+" "+s+" "+d+" "+c+" "+i+" "+w+" "+ch+" "+ac+" ");
    }

    //Using this method results in a null pointer when trying to acces content of spinner
    /*private void populateSpinner(View view, Spinner spinner, int id, ArrayAdapter adapter){
        spinner = (Spinner) view.findViewById(id);
        //ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_dropdown_item, attribs);
        spinner.setAdapter(adapter);
    }*/

    /*private void populateSpinnerString(View view, Spinner spinner, int id, int array){
        spinner = (Spinner) view.findViewById(id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }*/
}


