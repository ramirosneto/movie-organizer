package custom;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomFragment extends Fragment implements OnClickListener {
    public void onClick(View paramView) {
        //
    }

    public View setTouchNClick(View paramView) {
        paramView.setOnClickListener(this);
        return paramView;
    }
}