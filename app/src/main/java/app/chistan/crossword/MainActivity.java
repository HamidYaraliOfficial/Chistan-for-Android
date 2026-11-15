package app.chistan.crossword;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Chistan: the whole game lives in assets/www and runs inside one WebView.
 *
 * Files are served to the page from a fixed https origin (see HOST) by intercepting
 * the requests, so nothing is read from the network and the app needs no permissions.
 * Using a real origin instead of file:// gives the page normal localStorage behaviour
 * (the game keeps your progress there) and avoids file:// cross-origin quirks.
 */
public class MainActivity extends Activity {

    /** Virtual host used only inside the WebView. Requests to it never leave the device. */
    private static final String HOST = "appassets.androidplatform.net";
    private static final String START_URL = "https://" + HOST + "/index.html";
    private static final String ASSET_DIR = "www";

    /**
     * Runs inside the page when the system Back button is pressed. It closes an open
     * dialog first, then leaves a puzzle through the game's own Back button, and only
     * reports "exit" when the level list is showing.
     */
    private static final String BACK_SCRIPT =
            "(function(){"
            + "var d=document.querySelector('dialog[open]');"
            + "if(d){d.close();return 'dialog';}"
            + "var g=document.getElementById('view-game');"
            + "var b=document.getElementById('btn-back');"
            + "if(g&&!g.hidden&&b){b.click();return 'back';}"
            + "return 'exit';"
            + "})()";

    /** Asks the game to write its state to storage (it listens for this event). */
    private static final String SAVE_SCRIPT =
            "try{window.dispatchEvent(new Event('pagehide'));}catch(e){}";

    private WebView web;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        web = new WebView(this);
        web.setBackgroundColor(getColor(R.color.page_bg));
        web.setOverScrollMode(View.OVER_SCROLL_NEVER);
        setContentView(web, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);     // localStorage: saved game, progress and settings
        s.setAllowFileAccess(false);      // assets are served through shouldInterceptRequest
        s.setAllowContentAccess(false);
        s.setSupportZoom(false);          // the game has its own zoom buttons
        s.setBuiltInZoomControls(false);
        s.setTextZoom(100);               // keep the layout the same whatever the system font size

        boolean debuggable = (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        if (debuggable) {
            WebView.setWebContentsDebuggingEnabled(true);   // chrome://inspect, debug builds only
        }

        web.setWebViewClient(new AssetClient());
        web.loadUrl(START_URL);
    }

    @Override
    public void onBackPressed() {
        if (web == null) {
            finish();
            return;
        }
        web.evaluateJavascript(BACK_SCRIPT, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String result) {
                // result is a JSON string such as "\"back\""; anything but a handled case leaves the app
                if (result == null || result.contains("exit") || result.equals("null")) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        if (web != null) {
            web.evaluateJavascript(SAVE_SCRIPT, null);
            web.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (web != null) {
            web.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (web != null) {
            ViewGroup parent = (ViewGroup) web.getParent();
            if (parent != null) {
                parent.removeView(web);
            }
            web.destroy();
            web = null;
        }
        super.onDestroy();
    }

    /** Serves files from assets/www for the virtual host and blocks everything else. */
    private final class AssetClient extends WebViewClient {

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Uri uri = request.getUrl();
            if (!HOST.equals(uri.getHost())) {
                return response("text/plain", 403, "Forbidden", new byte[0]);
            }

            String path = uri.getPath();
            if (path == null || path.length() == 0 || path.equals("/")) {
                path = "/index.html";
            }
            if (path.contains("..")) {
                return response("text/plain", 404, "Not Found", new byte[0]);
            }

            try {
                InputStream in = getAssets().open(ASSET_DIR + path);
                return stream(mimeFor(path), in);
            } catch (IOException e) {
                return response("text/plain", 404, "Not Found", new byte[0]);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            // The game has no outside links. Anything that is not our own page is ignored.
            return !HOST.equals(request.getUrl().getHost());
        }
    }

    private static WebResourceResponse stream(String mime, InputStream data) {
        String encoding = isText(mime) ? "utf-8" : null;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cache-Control", "no-cache");
        return new WebResourceResponse(mime, encoding, 200, "OK", headers, data);
    }

    private static WebResourceResponse response(String mime, int status, String reason, byte[] body) {
        return new WebResourceResponse(mime, "utf-8", status, reason,
                new HashMap<String, String>(), new ByteArrayInputStream(body));
    }

    private static boolean isText(String mime) {
        return mime.startsWith("text/") || mime.equals("application/javascript")
                || mime.equals("application/json") || mime.equals("image/svg+xml");
    }

    private static String mimeFor(String path) {
        String p = path.toLowerCase(Locale.ROOT);
        if (p.endsWith(".html") || p.endsWith(".htm")) return "text/html";
        if (p.endsWith(".js")) return "application/javascript";
        if (p.endsWith(".css")) return "text/css";
        if (p.endsWith(".json")) return "application/json";
        if (p.endsWith(".woff2")) return "font/woff2";
        if (p.endsWith(".woff")) return "font/woff";
        if (p.endsWith(".svg")) return "image/svg+xml";
        if (p.endsWith(".png")) return "image/png";
        if (p.endsWith(".txt")) return "text/plain";
        return "application/octet-stream";
    }
}
