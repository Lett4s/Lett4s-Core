package generic.utility;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class LDocumentSizeFilter extends DocumentFilter {

    private final int maxlength;

    public LDocumentSizeFilter(int maxlength) {
        this.maxlength = maxlength;
    }

    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
        if ((fb.getDocument().getLength() + str.length()) <= maxlength) {
            super.insertString(fb, offs, str, a);
        }
    }

}
