package com.example.vividize_unleashyourself.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.text.toHtml
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.vividize_unleashyourself.databinding.FragmentJournalEntryBinding
import com.example.vividize_unleashyourself.feature_vms.JournalingViewModel
import com.example.vividize_unleashyourself.feature_vms.undoRedoAction
import com.google.android.material.internal.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
    private val viewModel: JournalingViewModel by activityViewModels()

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
        inputMethManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val visualEditor = binding.textField
        val toolbar = binding.formattingToolbar


        binding.textField.setOnClickListener {
            if (isKeyboardOpen()) {
                hideKeyboard()
            } else {
                showKeyboard()
            }
        }



        toolbar.enableTaskList()
        toolbar.setToolbarItems(
            ToolbarItems.BasicLayout(
                ToolbarAction.HEADING,
                ToolbarAction.BOLD,
                ToolbarAction.ITALIC,
                ToolbarAction.UNDERLINE,
                ToolbarAction.STRIKETHROUGH,
                ToolbarAction.LIST,
                ToolbarAction.LINK,
                ToolbarAction.ALIGN_LEFT,
                ToolbarAction.ALIGN_CENTER,
                ToolbarAction.ALIGN_RIGHT
            )
        )
        toolbar.findViewById<View>(org.wordpress.aztec.R.id.media_button_container).visibility =
            GONE


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


            }

            override fun onToolbarHeadingButtonClicked() {

            }

            override fun onToolbarHtmlButtonClicked() {
            }

            override fun onToolbarListButtonClicked() {
            }

            override fun onToolbarMediaButtonClicked(): Boolean {
                return false
            }

        }
        Aztec.with(visualEditor, toolbar, toolbarClickListener)

        visualEditor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nichts zu tun hier
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Nichts zu tun hier
            }

            override fun afterTextChanged(s: Editable?) {
                lifecycleScope.launchWhenStarted {

                    viewModel.editAction.collectLatest {
                        when (it) {
                            undoRedoAction.UNDO -> {
                                visualEditor.undo()
                                viewModel.contentBuffer(s!!.toHtml())

                                viewModel.resetActionTrigger()
                            }
                            undoRedoAction.REDO -> {
                                visualEditor.redo()
                                viewModel.contentBuffer(s!!.toHtml())

                                viewModel.resetActionTrigger()

                            }
                            else -> {
                                viewModel.contentBuffer(s!!.toHtml())

                            }
                        }

                    }

                }

            }
        })


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
        if (viewModel.currentContent.value != "") {
            viewModel.saveEntry()
        }
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    }
}
