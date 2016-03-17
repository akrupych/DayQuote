package akrupych.dayquote;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private Context context;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
        context = getApplication();
    }

    public void testRead() {
        assertNotNull(getNextQuote());
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
        for (String s : quotes) {
            Log.d("qwerty", s);
        }
        return quotes;
    }
}