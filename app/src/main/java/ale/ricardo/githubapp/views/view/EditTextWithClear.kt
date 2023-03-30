package ale.ricardo.githubapp.views.view

import ale.ricardo.githubapp.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class EditTextWithClear @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle) :
        AppCompatEditText(context, attrs, defStyleAttr) {

    private var iconDrawable: Drawable? = null
    private var searchDrawable:Drawable? = null

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextWithClear, 0, 0)
            .apply {
                try {
                    getResourceId(R.styleable.EditTextWithClear_clearIcon,0).also {
                        if (0!=it){
                            iconDrawable = ContextCompat.getDrawable(context,it)
                        }
                    }

                    getResourceId(R.styleable.EditTextWithClear_searchIcon,0)
                        .also {
                            if (it!=0){
                                searchDrawable = ContextCompat.getDrawable(context,it)
                            }
                        }


                }finally {
                    //回收资源
                    recycle()
                }
            }
        setCompoundDrawablesRelativeWithIntrinsicBounds(searchDrawable, null, null, null)
    }


    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        toggleClearIcon()

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { evn ->
            //删除icon的点击事件
            iconDrawable?.let {
                if (evn.action == MotionEvent.ACTION_UP && evn.x > width - it.intrinsicWidth - 20 && evn.x < width + 20 && evn.y > height / 2 - it.intrinsicHeight / 2 - 20 && evn.y < height / 2 + it.intrinsicHeight / 2 + 20) {
                    text?.clear()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }


    private fun toggleClearIcon() {
        val icon = if (text?.isNotEmpty() == true) iconDrawable else null
        setCompoundDrawablesRelativeWithIntrinsicBounds(searchDrawable, null, icon, null)
    }

}