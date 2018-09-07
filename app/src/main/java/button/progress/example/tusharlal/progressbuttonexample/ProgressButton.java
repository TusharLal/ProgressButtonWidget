package button.progress.example.tusharlal.progressbuttonexample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressButton extends LinearLayout {

    private static final String DEFAULT_TEXT = "Progress Button";
    private static final String DEFAULT_ON_PROGRESS_STARTED_TEXT = "Progress Started";
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";

    private View view;
    private ProgressBar progressBar;
    private TextView textView;
    private LinearLayout linearLayout;

    private boolean showProgressBar = false;
    private String buttonLabel;
    private String onProgressStartedLabel;
    private String onProgressStoppedLabel;

    private Drawable enableBackgroundDrawable;
    private Drawable disableBackgroundDrawable;

//    private int enableBackgroundColor;
//    private int disableBackgroundColor;
    private int progressBarColor;
    private int progressBarSize;
    private int padding;
    private int textColor;
    private float textSize;

    public ProgressButton(Context context) {
        super(context);
        initializeViews(context);
    }

    public ProgressButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViewVariables(context, attrs);
    }

    public ProgressButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViewVariables(Context context, @Nullable AttributeSet attrs) {
        String height = attrs.getAttributeValue(NAMESPACE, "layout_height");
        Log.i("ProgressButton", "layout_height = " + height);
        if (height.contains("dip")) {
            Float aFloat = Float.valueOf(height.replace("dip", ""));
            progressBarSize = getProgressBarHeight(context, aFloat);
        }

        String paddingValue = attrs.getAttributeValue(NAMESPACE, "padding");
        Log.i("ProgressButton", "padding = " + paddingValue);
        if (paddingValue != null && paddingValue.contains("dip")) {
            Float aFloat = Float.valueOf(paddingValue.replace("dip", ""));
            padding = convertDipToPixels(context, aFloat);
        }

        TypedArray typedArray;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton);

        String text = typedArray.getString(R.styleable.ProgressButton_text);
        buttonLabel = text == null ? DEFAULT_TEXT : text;
        text = typedArray.getString(R.styleable.ProgressButton_textOnProgressStarted);
        onProgressStartedLabel = text == null ? DEFAULT_ON_PROGRESS_STARTED_TEXT : text;
        text = typedArray.getString(R.styleable.ProgressButton_textOnProgressStopped);
        onProgressStoppedLabel = text == null ? buttonLabel : text;

        enableBackgroundDrawable = typedArray.getDrawable(R.styleable.ProgressButton_enableBackgroundDrawable);
        disableBackgroundDrawable = typedArray.getDrawable(R.styleable.ProgressButton_disableBackgroundDrawable);

//        enableBackgroundColor = typedArray.getColor(R.styleable.ProgressButton_enableBackgroundColor, 0xFF4f4f4f);
//        disableBackgroundColor = typedArray.getColor(R.styleable.ProgressButton_disableBackgroundColor, 0xFF4f4f4f);
        progressBarColor = typedArray.getColor(R.styleable.ProgressButton_progressBarColor, 0xFF4f4f4f);
        textColor = typedArray.getColor(R.styleable.ProgressButton_textColor, 0xFF000000);
        textSize = typedArray.getDimension(R.styleable.ProgressButton_textSize, convertDipToPixels(context, 4));

        initializeViews(context);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.progress_button, this);
        progressBar = view.findViewById(R.id.progressButton_ProgressBar);
        textView = view.findViewById(R.id.progressButton_TextView);
        setTextColor(textColor);
        setTextSize(textSize);
        linearLayout = view.findViewById(R.id.progressButton_LinearLayout);

        progressBar.getIndeterminateDrawable().setColorFilter(progressBarColor, PorterDuff.Mode.MULTIPLY);
        if (progressBarSize > 0) setProgressBarSize(progressBarSize);
        textView.setText(buttonLabel);
        linearLayout.setBackground(enableBackgroundDrawable);
        if (padding > 1) {
            setPadding(padding);
        }
    }

    public static int getProgressBarHeight(Context context, float dips) {
        if (dips > 20)
            return (int) ((dips - 15) * context.getResources().getDisplayMetrics().density + 0.5f);
        else return 0;
    }

    public static int convertDipToPixels(Context context, float dips) {
        return (int) (dips * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public boolean onViewClick() {
        if (isEnabled()) {
            setProgressBarVisibility(VISIBLE);
            setText(onProgressStartedLabel);
            setBackground(disableBackgroundDrawable);
            setShowProgressBar(true);
            setEnabled(false);
            return true;
        }
        return false;
    }

    @Override
    public void setBackgroundColor(int color) {
        linearLayout.setBackgroundColor(color);
    }

    @Override
    public void setBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackground(background);
        } else {
            linearLayout.setBackgroundDrawable(background);
        }
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    public void setPadding(int padding) {
        setPadding(padding, padding, padding, padding);
    }

    public void processCompleted(boolean hadError) {
        setProgressBarVisibility(GONE);
        if (hadError) {
            resetButton();
        } else {
            setText(onProgressStoppedLabel);
            setBackground(disableBackgroundDrawable);
            setEnabled(false);
        }
        setShowProgressBar(false);
    }

    public void resetButton() {
        setText(buttonLabel);
        setProgressBarVisibility(GONE);
        setBackground(enableBackgroundDrawable);
        setEnabled(true);
    }

    public void setShowProgressBar(boolean flag) {
        showProgressBar = flag;
    }

    public boolean isShowProgressBarVisible() {
        return showProgressBar;
    }

    public void setProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
        if (visibility == VISIBLE) showProgressBar = true;
        else showProgressBar = false;
    }

    public void setProgressBarColor(int color) {
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    public void setProgressBarColor(int color, PorterDuff.Mode mode) {
        progressBar.getIndeterminateDrawable().setColorFilter(color, mode);
    }

    public void setProgressBarSize(int size) {
        progressBar.getLayoutParams().width = size;
        progressBar.getLayoutParams().height = size;
        progressBar.invalidate();
    }

    public void setText(int stringId) {
        textView.setText(stringId);
    }

    public void setText(String string) {
        textView.setText(string);
    }

    public void setOnProgressStartedText(int stringId) {
        onProgressStartedLabel = getResources().getString(stringId);
    }

    public void setOnProgressStoppedText(String string) {
        onProgressStoppedLabel = string;
    }

    public void setOnProgressStoppedText(int stringId) {
        onProgressStoppedLabel = getResources().getString(stringId);
    }

    public void setOnProgressStartedText(String string) {
        onProgressStartedLabel = string;
    }

    public void setTextColor(int colorId) {
        textView.setTextColor(colorId);
    }

    public void setTextSize(float textSize) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void setTextSize(int typedValue, float textSize) {
        textView.setTextSize(typedValue, textSize);
    }

    public String getText() {
        return textView.getText().toString();
    }
}
