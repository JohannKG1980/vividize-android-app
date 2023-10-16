package com.example.vividize_unleashyourself.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.vividize_unleashyourself.databinding.FragmentJournalEntryBinding
import com.google.android.material.internal.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import org.wordpress.aztec.Aztec
import org.wordpress.aztec.AztecTextFormat
import org.wordpress.aztec.ITextFormat
import org.wordpress.aztec.toolbar.IAztecToolbarClickListener
import org.wordpress.aztec.toolbar.ToolbarAction
import org.wordpress.aztec.toolbar.ToolbarItems


@AndroidEntryPoint
class JournalEntryFragment : Fragment() {

    private lateinit var binding: FragmentJournalEntryBinding
    private lateinit var inputMethManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJournalEntryBinding.inflate(layoutInflater)
        inputMethManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val visualEditor = binding.textField
        val toolbar = binding.formattingToolbar

        val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                if (isKeyboardOpen()) {
                    hideKeyboard()
                } else {
                    showKeyboard()
                }
                return super.onSingleTapUp(e)
            }
        })

        binding.textField.setOnClickListener {
            if (isKeyboardOpen()) {
                hideKeyboard()
            } else {
                showKeyboard()
            }
        }



        toolbar.setToolbarItems(
            ToolbarItems.BasicLayout(
                ToolbarAction.BOLD,
                ToolbarAction.ITALIC,
                ToolbarAction.UNDERLINE,
                ToolbarAction.STRIKETHROUGH,
                ToolbarAction.LIST,
                ToolbarAction.ORDERED_LIST,
                ToolbarAction.LINK,
                ToolbarAction.ALIGN_LEFT,
                ToolbarAction.ALIGN_CENTER,
                ToolbarAction.ALIGN_RIGHT
            )
        )
//        toolbar.enableTaskList()

        val toolbarClickListener = object : IAztecToolbarClickListener {


            override fun onToolbarCollapseButtonClicked() {

                // Code für den "Einklappen"-Button
            }

            override fun onToolbarExpandButtonClicked() {
                // Code für den "Ausklappen"-Button
            }


            override fun onToolbarFormatButtonClicked(
                format: ITextFormat,
                isKeyboardShortcut: Boolean,
            ) {

                when (format) {
                    AztecTextFormat.FORMAT_BOLD -> {
                        Toast.makeText(context, "Fett formatiert", Toast.LENGTH_SHORT).show()
                    }

                    AztecTextFormat.FORMAT_ITALIC -> {
                        Toast.makeText(context, "Kursiv formatiert", Toast.LENGTH_SHORT).show()
                    }
                    // ... andere Formate hier
                }

            }

            override fun onToolbarHeadingButtonClicked() {
                // Code für den "Überschrift"-Button
                Toast.makeText(context, "Überschrift ausgewählt", Toast.LENGTH_SHORT).show()

            }

            override fun onToolbarHtmlButtonClicked() {
                // Code für den "HTML"-Button
            }

            override fun onToolbarListButtonClicked() {
                // Code für den "Liste"-Button
            }

            override fun onToolbarMediaButtonClicked(): Boolean {
                // Code für den "Medien"-Button
                return false
            }

        }
        val aztec = Aztec.with(visualEditor, toolbar, toolbarClickListener)


    }

    private fun isKeyboardOpen(): Boolean {
        val rect = Rect()
        activity?.window?.decorView?.getWindowVisibleDisplayFrame(rect)
        val screenHeight = activity?.window?.decorView?.height ?: 0
        val keypadHeight = screenHeight - rect.bottom
        return keypadHeight > screenHeight * 0.15
    }

    private fun showKeyboard() {
        inputMethManager.showSoftInput(binding.textField, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        inputMethManager.hideSoftInputFromWindow(binding.textField.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    }
}
