package pl.s196453.dndroller;

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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RollFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RollFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RollFragment extends Fragment implements View.OnClickListener{
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    //  Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;

    private MyAppDatabase datab;
    private List<Profile> profiles;
    private Profile prof;

    private Spinner specThrow; //specialThrowSpinner
    private Spinner regThrow; //regTSpinner
    private Spinner diceNo; //noDicespinner
    private Spinner sidesNo; //noSidesSpinner
    private Spinner profSpiner; //profileSpinner

    private Button profileButton;
    private Button rollSpecB;
    private Button rollRegB;
    private Button rollCustB;

    private Integer[] diceNumbers = new Integer[]{1,2,3,4,5,6,7,8,9,10}; //looks bad explained in ProfileFragment.java (adapter does like int values form resource)
    private Integer[] sideNumbers = new Integer[]{3,4,6,8,10,12,20,100};

    private Profile loadedProf;

    public RollFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RollFragment.
     */
    /*//  Rename and change types and number of parameters
    public static RollFragment newInstance(String param1, String param2) {
        RollFragment fragment = new RollFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rollView = inflater.inflate(R.layout.fragment_roll, container, false);

        datab = MyAppDatabase.getInstance(getContext());

        profiles = datab.profileDAO().getAll();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.special_throws, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.regular_throws, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, diceNumbers);
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, sideNumbers);
        ArrayAdapter<Profile> adapterProf = new ArrayAdapter<Profile>(getContext(),android.R.layout.simple_spinner_dropdown_item, profiles);

        profSpiner = (Spinner) rollView.findViewById(R.id.profileSpinner);
        profSpiner.setAdapter(adapterProf);
        specThrow = (Spinner) rollView.findViewById(R.id.specialThrowSpinner);
        specThrow.setAdapter(adapter);
        regThrow = (Spinner) rollView.findViewById(R.id.regularTSpinner);
        regThrow.setAdapter(adapter1);
        diceNo = (Spinner) rollView.findViewById(R.id.noDicespinner);
        diceNo.setAdapter(adapter2);
        sidesNo = (Spinner) rollView.findViewById(R.id.noSidesSpinner);
        sidesNo.setAdapter(adapter3);

        profileButton = (Button) rollView.findViewById(R.id.bLoad);
        rollSpecB = (Button) rollView.findViewById(R.id.bRoll);
        rollRegB = (Button) rollView.findViewById(R.id.bRoll2);
        rollCustB = (Button) rollView.findViewById(R.id.bRoll3);

        profileButton.setOnClickListener(this);
        rollSpecB.setOnClickListener(this);
        rollRegB.setOnClickListener(this);
        rollCustB.setOnClickListener(this);





        /*populateSpinnerString(rollView,specThrow,R.id.specialThrowSpinner,R.array.special_throws);
        populateSpinnerString(rollView,regThrow,R.id.regularTSpinner,R.array.regular_throws);
        populateSpinnerInt(rollView,diceNo,R.id.noDicespinner,diceNumbers);
        populateSpinnerInt(rollView,sidesNo,R.id.noSidesSpinner,sideNumbers);
        */
        return rollView;
    }

    //  Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bLoad:
                new Thread(new Runnable() { @Override public void run() {
                    Looper.prepare();
                    loadProfile(profSpiner.getSelectedItem().toString());
                } }) .start();
                Toast toast = Toast.makeText(getContext(), "profile loaded",Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.bRoll:

                break;
            case R.id.bRoll2:

                break;
            case R.id.bRoll3:

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

    public void loadProfile(String string){
        if(string != null) {
            loadedProf = datab.profileDAO().getProfile(string);
            Log.d("LOAD TEST", "Profile loaded, profile name " + loadedProf.getName());
        }
        else{Log.d("LOAD TEST", "tried to load null profile" );}
    }

   /* private void populateSpinnerString(View view, Spinner spinner, int id, int array){
        spinner = (Spinner) view.findViewById(id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }*/

   /* private void populateSpinnerInt(View view, Spinner spinner, int id, Integer[] array){
        spinner = (Spinner) view.findViewById(id);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, array);
        spinner.setAdapter(adapter);
    }*/
}
