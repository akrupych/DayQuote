package akrupych.dayquote;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;

    public MyRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == 1) {
            RemoteViews quoteView = new RemoteViews(context.getPackageName(), R.layout.quote);
            quoteView.setTextViewText(R.id.quote, getNextQuote());
            return quoteView;
        }
        return new RemoteViews(context.getPackageName(), position == 0 ? R.layout.top_decor : R.layout.bottom_decor);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private String getNextQuote() {
        try {
            Random random = new Random();
            String[] files = context.getAssets().list("data");
            Log.d("qwerty", Arrays.toString(files));
            String selectedFile = files[random.nextInt(files.length)];
            List<String> quotes = parseQuotes(selectedFile);
            return quotes.get(random.nextInt(quotes.size()));
        } catch (IOException e) {
            e.printStackTrace();
            return "...";
        }
    }

    private List<String> parseQuotes(String fileName) throws IOException {
        List<String> quotes = new ArrayList<>();
        InputStream stream = context.getAssets().open("data/" + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            if (!line.isEmpty()) {
                quotes.add(line);
            }
        }
        reader.close();
        for (int i = 0; i < quotes.size(); i++) {
            if (!quotes.get(i).matches("[0-9]+\\. .*")) {
                quotes.set(i - 1, quotes.get(i - 1) + "\n" + quotes.get(i));
                quotes.remove(i--);
            }
        }
        reader.close();
        return quotes;
    }
}
