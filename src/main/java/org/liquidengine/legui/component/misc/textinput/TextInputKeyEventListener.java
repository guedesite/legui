package org.liquidengine.legui.component.misc.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.listener.KeyEventListener;
import org.liquidengine.legui.system.context.Context;

import static org.liquidengine.legui.util.TextUtil.findNextWord;
import static org.liquidengine.legui.util.TextUtil.findPrevWord;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class TextInputKeyEventListener implements KeyEventListener {

    /**
     * Used to handle {@link KeyEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(KeyEvent event) {
        TextInput gui = (TextInput) event.getComponent();
        int key = event.getKey();
        boolean pressed = event.getAction() != GLFW_RELEASE;
        if (key == GLFW_KEY_LEFT && pressed) {
            keyLeftAction(gui, event.getMods());
        } else if (key == GLFW_KEY_RIGHT && pressed) {
            keyRightAction(gui, event.getMods());
        } else if ((key == GLFW_KEY_UP || key == GLFW_KEY_HOME) && pressed) {
            keyUpAndHomeAction(gui, event.getMods());
        } else if ((key == GLFW_KEY_DOWN || key == GLFW_KEY_END) && pressed) {
            keyDownAndEndAction(gui, event.getMods());
        } else if (key == GLFW_KEY_BACKSPACE && pressed) {
            keyBackSpaceAction(gui, event.getMods());
        } else if (key == GLFW_KEY_DELETE && pressed) {
            keyDeleteAction(gui, event.getMods());
        } else if (key == GLFW_KEY_V && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            pasteAction(gui, event.getContext());
        } else if (key == GLFW_KEY_C && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            copyAction(gui, event.getContext());
        } else if (key == GLFW_KEY_X && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            cutAction(gui, event.getContext());
        }
    }

    /**
     * Used to cut some string from text input and put it to clipboard.
     *
     * @param gui          gui to work with.
     * @param leguiContext context.
     */
    private void cutAction(TextInput gui, Context leguiContext) {
        if (gui.isEditable()) {
            String s = gui.getSelection();
            if (s != null) {
                int start = gui.getStartSelectionIndex();
                int end = gui.getEndSelectionIndex();
                if (start > end) {
                    int swap = start;
                    start = end;
                    end = swap;
                }
                gui.getTextState().delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
                glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
            }
        } else {
            copyAction(gui, leguiContext);
        }
    }

    /**
     * Used to copy selected text to clipboard.
     *
     * @param gui          gui.
     * @param leguiContext context.
     */
    private void copyAction(TextInput gui, Context leguiContext) {
        String s = gui.getSelection();
        if (s != null) {
            glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
        }
    }

    /**
     * Used to paste clipboard data to gui element.
     *
     * @param gui          gui to paste
     * @param leguiContext context.
     */
    private void pasteAction(TextInput gui, Context leguiContext) {
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            int caretPosition = gui.getCaretPosition();
            String s = glfwGetClipboardString(leguiContext.getGlfwWindow());
            if (s != null) {
                textState.insert(caretPosition, s);
                gui.setCaretPosition(caretPosition + s.length());
            }
        }
    }

    /**
     * Delete action. Used to delete selected text or symbol after caret or word after caret.
     *
     * @param gui  gui to remove data from text state.
     * @param mods key mods.
     */
    private void keyDeleteAction(TextInput gui, int mods) {
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            int caretPosition = gui.getCaretPosition();
            int start = gui.getStartSelectionIndex();
            int end = gui.getEndSelectionIndex();
            if (start > end) {
                start = gui.getEndSelectionIndex();
                end = gui.getStartSelectionIndex();
            }
            if (start == end && caretPosition != textState.length()) {
                if ((mods & GLFW_MOD_CONTROL) != 0) {
                    end = findNextWord(textState.getText(), caretPosition);
                    textState.delete(start, end);
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                } else {
                    textState.deleteCharAt(caretPosition);
                    gui.setStartSelectionIndex(caretPosition);
                    gui.setEndSelectionIndex(caretPosition);
                }
            } else {
                textState.delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
            }
        }
    }

    /**
     * Backspace action. Deletes selected text or symbol before caret or words before caret.
     *
     * @param gui  gui to remove text data.
     * @param mods key mods.
     */
    private void keyBackSpaceAction(TextInput gui, int mods) {
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            int caretPosition = gui.getCaretPosition();
            int start = gui.getStartSelectionIndex();
            int end = gui.getEndSelectionIndex();
            if (start > end) {
                start = gui.getEndSelectionIndex();
                end = gui.getStartSelectionIndex();
            }
            if (start == end && caretPosition != 0) {
                if ((mods & GLFW_MOD_CONTROL) != 0) {
                    start = findPrevWord(textState.getText(), caretPosition);
                    textState.delete(start, end);
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                } else {
                    int newCaretPosition = caretPosition - 1;
                    textState.deleteCharAt(newCaretPosition);
                    gui.setCaretPosition(newCaretPosition);
                    gui.setStartSelectionIndex(newCaretPosition);
                    gui.setEndSelectionIndex(newCaretPosition);
                }
            } else {
                textState.delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
            }
        }
    }

    private void keyDownAndEndAction(TextInput gui, int mods) {
        int newCaretPosition = gui.getTextState().length();
        gui.setEndSelectionIndex(newCaretPosition);
        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);

    }

    private void keyUpAndHomeAction(TextInput gui, int mods) {
        int newCaretPosition = 0;
        gui.setEndSelectionIndex(newCaretPosition);
        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);
    }

    private void keyRightAction(TextInput gui, int mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();
        int newCaretPosition = caretPosition + 1;
        // reset if out of bounds
        if (newCaretPosition >= textState.length()) {
            newCaretPosition = textState.length();
        }
        if ((mods & GLFW_MOD_CONTROL) != 0) {
            newCaretPosition = findNextWord(gui.getTextState().getText(), caretPosition);
        }

        gui.setEndSelectionIndex(newCaretPosition);

        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }

        gui.setCaretPosition(newCaretPosition);
    }

    private void keyLeftAction(TextInput gui, int mods) {
        int caretPosition = gui.getCaretPosition();
        int newCaretPosition = caretPosition - 1;
        // reset if out of bounds.
        if (newCaretPosition <= 0) {
            newCaretPosition = 0;
        }
        if ((mods & GLFW_MOD_CONTROL) != 0) {
            newCaretPosition = findPrevWord(gui.getTextState().getText(), caretPosition);
        }
        gui.setEndSelectionIndex(newCaretPosition);
        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof TextInputKeyEventListener;
    }
}