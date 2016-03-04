package custom;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomActivity extends FragmentActivity implements OnClickListener {
    public void onClick(View paramView) {
        //
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public View setClick(int paramInt) {
        View localView = findViewById(paramInt);
        if (localView != null) {
            localView.setOnClickListener(this);
        }
        return localView;
    }
}