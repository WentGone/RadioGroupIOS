package cn.mdruby.radiobuttonforios;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by Went_Gone on 2018/3/30.
 */

public class RadioButtonSwitch extends android.support.v7.widget.AppCompatRadioButton{

    public RadioButtonSwitch(Context context) {
        this(context,null);
    }

    public RadioButtonSwitch(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadioButtonSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
