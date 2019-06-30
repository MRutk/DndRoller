package pl.s196453.dndroller;


import android.arch.lifecycle.LiveData;
import android.content.Context;
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

//TODO:Spinner z profilami do onCreateView tu i w rollFragment update listy tylko przy przejściu między fragmentami czyli własnie on createview chyba

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
    private LiveData<List<Profile>> allProf;

    private Integer[] acvals = new Integer[]{10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
    private Integer[] attribs = new Integer[]{3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19}; //looks bad but getting values from arrsys doesn't want to work with adapters
    //attribs = getActivity().getResources().getIntArray(R.array.attributes_spin); //calling this in oncreateview did not work
    //adaprter requires Integer[], getResources gives me int[], casting seemed not to do anything

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profView = inflater.inflate(R.layout.fragment_profile, container, false);

        datab = MyAppDatabase.getInstance(getContext());

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, attribs);
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<Integer>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, acvals);


        profileSpinner = (Spinner) profView.findViewById(R.id.spinner4);
        //testSpinner.setAdapter(adapter);
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
        // =(EditText) profView.findViewById(R.id.armorClass);

        /*strT =(EditText) profView.findViewById(R.id.strTx);
        dexT =(EditText) profView.findViewById(R.id.dexTx);
        conT =(EditText) profView.findViewById(R.id.conTx);
        intT =(EditText) profView.findViewById(R.id.intTx);
        wisT =(EditText) profView.findViewById(R.id.wisTx);
        chaT =(EditText) profView.findViewById(R.id.charTx);*/

        /*populateSpinnerString(profView,strSpinner,R.id.Strength, R.array.attrib_spin2);
        populateSpinner(profView,dexSpinner,R.id.dexterity);  //spinners always resulted in a null point exception
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
    }

    //createProfile(pName.getText().toString(), (int)strSpinner.getSelectedItem(),(int)dexSpinner.getSelectedItem(),(int)conSpinner.getSelectedItem(),(int)intSpinner.getSelectedItem(),(int)wisSpinner.getSelectedItem(),(int)chaSpinner.getSelectedItem(),Integer.parseInt(aClass.getText().toString()));
    //createProfile(a,s,d,c,i,w,ch,ac);
    //delProfile(pName.getText().toString());
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
                                delProfile(pName.getText().toString()); } }) .start();
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
        if(string == "zero" || string =="default") {
            prof = new Profile(string, str, dex, con, intel, wis, cha, ac);
            datab.profileDAO().insert(prof);
            Log.d("TEST CREATION", "profile created");
        /*datab.profileDAO().update(prof);
        Log.d("TEST UPDATE", "profile updated");
        */
        }
        else{Log.d("TEST INS CONDITION","tried to change one of the default profiles");}
    }

    public void delProfile(String string ){
        if(string == "zero" || string =="default") {
            int del = datab.profileDAO().deleteProfile(string);
            Log.d("TEST DELETION", "profila deleted, no. of lines deleted " + del);
        }
        else{Log.d("TEST DEL CONDITION","tried to delete one of the default profiles");}
    }

    public void getProfiles(){ //this will go in a thread
        allProf = datab.profileDAO().getAllProfiles();
    }

    private void populateSpinnerString(View view, Spinner spinner, int id, LiveData liveData) {
        //spinner = (Spinner) view.findViewById(id);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), liveData, android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
        // adapter def. and spinner fill should go here
    }



    public void test(){

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

        //int spinTest = (int)testSpinner.getSelectedItem();
        //Log.d("SPINNER TEST","SPINNER item value "+spinTest); //success only if full initialization of spinner is in onCreateView

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

       /*// Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

     /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

     /*private EditText strT; //no longer used, got spinners to work
    private EditText dexT;
    private EditText conT;
    private EditText intT;
    private EditText wisT;
    private EditText chaT;*/

     /*String a ;              //temporary for testing, a lot broke down
    int ac,s,d,c,i,w,ch;*/

    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

}


