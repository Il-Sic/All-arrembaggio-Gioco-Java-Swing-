package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class FinestraGioco extends JFrame
{
    private JFrame jframe;
    public FinestraGioco (PannelloGioco pannello)
    {
        jframe = new JFrame ();

        jframe.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        jframe.add (pannello);
        jframe.setLocationRelativeTo (null);
        jframe.setResizable (false);
        jframe.pack();
        jframe.setVisible (true);
        jframe.addWindowFocusListener (new WindowFocusListener()
        {
            @Override
            public void windowGainedFocus (WindowEvent e)
            {

            }
            @Override
            public void windowLostFocus (WindowEvent e)
            {
                pannello.getGioco().windowFocusLost ();
            }
        });
    }
}
