import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

/*Since normal buttons cannot be displayed (rendered) in table cell, this class is responsible customisation (add button in cell)
 by implementing TableCellRenderer Interface
 Reference Link: https://docs.oracle.com/javase/8/docs/api/javax/swing/table/TableCellRenderer.html */
class ButtonCellRenderer implements TableCellRenderer {
    private Book book;

    //get current book
    public Book setDisplayBook(Book book) {
        return this.book = book;
    }

    // get the (Patron) borrower of the current book
    private Patron getBorrower(Book book) {
        Loan loan = book.getLoan();
        return loan.getPatron();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTableCellRendererComponent'");
    }
}