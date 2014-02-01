package fr.eyzox.minecraftPattern.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fr.eyzox.minecraftPattern.gui.menu.MCPGUIMenuBar;
import fr.eyzox.minecraftPattern.gui.optionpanel.OptionPanel;
import fr.eyzox.minecraftPattern.gui.panel.MCPattern2DPanel;
import fr.eyzox.minecraftPattern.gui.toolbox.MCToolBox;


@SuppressWarnings("serial")
public class Core extends JFrame {

	public static final String version = " Alpha 0.1";
	private static MCToolBox toolbox;
	private static MCPatternModel model;
	private static MCPattern2DPanel view;
	private static OptionPanel optionPanel;
	private static MCPGUIMenuBar menu;

	private static Properties properties;
	
	private static Core core;

	public Core() {
		super("Minecraft Pattern GUI");

		Config conf = new Config();
		properties = conf.loadConfig();

		model = new MCPatternModel();
		toolbox = new MCToolBox();
		view = new MCPattern2DPanel();
		optionPanel = new OptionPanel();
		menu = new MCPGUIMenuBar();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JSplitPane splitPaneInfoView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,view, optionPanel);
		splitPaneInfoView.setOneTouchExpandable(true);
		splitPaneInfoView.setContinuousLayout(true);


		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,splitPaneInfoView, toolbox);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);


		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(splitPane, BorderLayout.CENTER);

		setJMenuBar(menu);

		// Set the size of app
		int width = Integer.parseInt(conf.getDefaultValue("resolution-x"));
		int height = Integer.parseInt(conf.getDefaultValue("resolution-y")); 
		try {
			width = Integer.parseInt(properties.getProperty("resolution-x"));
		}catch(java.lang.NumberFormatException nfe) {
			properties.setProperty("resolution-x", ""+width);
			Config.saveProp(properties);
		}
		try {
			height = Integer.parseInt(properties.getProperty("resolution-y"));
		}catch(java.lang.NumberFormatException nfe) {
			properties.setProperty("resolution-y", ""+height);
			Config.saveProp(properties);
		}

		this.setPreferredSize(new Dimension(width,height));
		pack();
		splitPaneInfoView.setDividerLocation(0.75);
		splitPane.setDividerLocation(0.75);
		setLocationRelativeTo(null);
		setVisible(true);
		
		core = this;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(!Core.getModel().isSaved()) Core.getMenu().askForSave();
			}
		});
	}



	public static void main(String[] agrs) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.err.println(e.getLocalizedMessage()+"\n"+e.getCause());
		}
		new Core();
	}

	public static MCToolBox getToolbox() {
		return toolbox;
	}

	public static MCPatternModel getModel() {
		return model;
	}

	public static MCPattern2DPanel getView() {
		return view;
	}

	public static OptionPanel getOptionPanel() {
		return optionPanel;
	}

	public static MCPGUIMenuBar getMenu() {
		return menu;
	}

	public static Properties getProperties() {
		return properties;
	}



	public static Core getCore() {
		return core;
	}
}
