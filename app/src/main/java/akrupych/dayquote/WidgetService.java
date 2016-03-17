package akrupych.dayquote;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("qwerty", "WidgetService.onGetViewFactory");
        return new MyRemoteViewsFactory(this);
    }

}
