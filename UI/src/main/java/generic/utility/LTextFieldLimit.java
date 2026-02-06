package generic.utility;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LTextFieldLimit extends PlainDocument {

    private final int offset;
    private final int limit;

    public LTextFieldLimit(int limit, int offset) {
        super();
        this.limit = limit;
        this.offset = offset;
    }

    public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit) {
            if (this.offset != 0)
                super.insertString(this.offset, str, attr);
            else
                super.insertString(offset, str, attr);
        }
    }

}
