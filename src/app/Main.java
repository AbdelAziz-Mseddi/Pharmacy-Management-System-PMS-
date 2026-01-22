package app;

import javax.swing.SwingUtilities;
import user_interface.LoginFrame;

public class Main 
{
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(LoginFrame::new);
		
	}
}
