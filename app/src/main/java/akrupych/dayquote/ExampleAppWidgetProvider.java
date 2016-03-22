package akrupych.dayquote;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class ExampleAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            widget.setRemoteAdapter(R.id.host_view, intent);
            widget.setPendingIntentTemplate(R.id.host_view, PendingIntent.getBroadcast(
                    context, 0, getClickIntent(context, appWidgetId), 0));
            appWidgetManager.updateAppWidget(appWidgetId, widget);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.host_view);
        }
    }

    private Intent getClickIntent(Context context, int appWidgetId) {
        return new Intent(context, getClass())
                .setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
                .putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});
    }
}
