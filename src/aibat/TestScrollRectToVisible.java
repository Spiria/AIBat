package aibat;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;


public class TestScrollRectToVisible extends JPanel
{
    private static final int MAX_LOOP = 10000;

    private DefaultListModel listModel = new DefaultListModel();

    private JTextArea textarea = new JTextArea( 20, 30 );

    private JList jList = new JList( listModel );

    JScrollPane textareaScrollPane = new JScrollPane( textarea );


    public TestScrollRectToVisible()
    {
        jList.addListSelectionListener( new ListSelectionListener()
        {
            public void valueChanged( ListSelectionEvent e )
            {
                if ( !e.getValueIsAdjusting() )
                {
                    String text = jList.getSelectedValue().toString();
                    text += ": ";

                    String docText = textarea.getText();
                    int index = docText.indexOf( text );
                    if ( index < 0 )
                    {
                        return;
                    }
                    try
                    {
                        Rectangle rect = textarea.modelToView( index );
                        textarea.scrollRectToVisible( rect );
                    }
                    catch ( BadLocationException e1 )
                    {
                        e1.printStackTrace();
                    }
                }
            }
        } );

        jList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        StringBuilder strBuilder = new StringBuilder();
        for ( int i = 0; i < MAX_LOOP; i++ )
        {
            String text = String.valueOf( i );
            listModel.addElement( text );
            strBuilder.append( text + ": abcdefghijklmnopqrstuvwxyz" + "\n" );
        }
        textarea.setText( strBuilder.toString() );

        setLayout( new BorderLayout() );
        add( textareaScrollPane, BorderLayout.CENTER );
        add( new JScrollPane( jList ), BorderLayout.EAST );
    }


    private static void createAndShowUI()
    {
        JFrame frame = new JFrame( "TestScrollRectToVisible" );
        frame.getContentPane().add( new TestScrollRectToVisible() );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible( true );
    }


    public static void main( String[] args )
    {
        java.awt.EventQueue.invokeLater( new Runnable()
        {
            public void run()
            {
                createAndShowUI();
            }
        } );
    }
}