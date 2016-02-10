package fragments;

import com.example.jsongetrequest.R;
import com.example.jsongetrequest.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentA extends Fragment {

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate){
		return inflater.inflate(R.layout.fragment_a, container, false);
	}
	
	
}
