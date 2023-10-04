package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.databinding.FragmentJournalEntryBinding
import org.wordpress.aztec.Aztec
import org.wordpress.aztec.AztecTextFormat
import org.wordpress.aztec.ITextFormat
import org.wordpress.aztec.toolbar.AztecToolbar
import org.wordpress.aztec.toolbar.IAztecToolbarClickListener
import org.wordpress.aztec.toolbar.ToolbarAction
import org.wordpress.aztec.toolbar.ToolbarItems

private lateinit var binding: FragmentJournalEntryBinding

class JournalEntryFragment<ITextFormat : Any> : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJournalEntryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val visualEditor = binding.textField
        val toolbar = binding.formattingToolbar

        toolbar.setToolbarItems(
            ToolbarItems.BasicLayout(
                ToolbarAction.BOLD,
                ToolbarItems.PLUGINS,
                ToolbarAction.LIST
            )
        )
        toolbar.enableTaskList()

        val toolbarClickListener = object : IAztecToolbarClickListener {


            override fun onToolbarCollapseButtonClicked() {

                // Code für den "Einklappen"-Button
            }

            override fun onToolbarExpandButtonClicked() {
                // Code für den "Ausklappen"-Button
            }

            override fun onToolbarFormatButtonClicked(
                format: org.wordpress.aztec.ITextFormat,
                isKeyboardShortcut: Boolean
            ) {
                TODO("Not yet implemented")
            }

//            override fun onToolbarFormatButtonClicked(
//                format: ITextFormat,
//                isKeyboardShortcut: Boolean
//            ) {
//
//                when (format) {
//                    AztecTextFormat.FORMAT_BOLD -> {
//                        Toast.makeText(context, "Fett formatiert", Toast.LENGTH_SHORT).show()
//                    }
//                    AztecTextFormat.FORMAT_ITALIC -> {
//                        Toast.makeText(context, "Kursiv formatiert", Toast.LENGTH_SHORT).show()
//                    }
//                  // ... andere Formate hier
//                }
//
//            }

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

//            override fun onToolbarUndoButtonClicked() {
//                // Code für den "Rückgängig"-Button
//            }
//
//            override fun onToolbarRedoButtonClicked() {
//                // Code für den "Wiederholen"-Button
//            }
        }
        val aztec = Aztec.with(visualEditor, toolbar, toolbarClickListener)
    }
}
