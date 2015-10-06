package com.blastedstudios.scab.ui.gameplay.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.scab.ui.gameplay.GameplayScreen;
import com.blastedstudios.scab.util.IConsoleCommand;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.util.ui.ScabWindow;
import com.blastedstudios.scab.world.WorldManager;

public class ConsoleWindow extends ScabWindow {
	private final TextField text;
	private final Table historyTable;
	
	public ConsoleWindow(final Skin skin, final WorldManager world, 
			final GameplayScreen screen, final EventListener listener) {
		super("Console", skin);
		historyTable = new Table(skin);
		redrawHistory();
		for(IConsoleCommand command : PluginUtil.getPlugins(IConsoleCommand.class))
			command.initialize(world, screen);
		text = new TextField("", skin);
		text.setMessageText("<enter command>");
		TextButton executeButton = new ScabTextButton("Execute", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				execute();
			}
		});
		TextButton closeButton = new ScabTextButton("Close", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.handle(event);
			}
		});
		add(new ScrollPane(historyTable)).colspan(3);
		row();
		add(text);
		add(executeButton);
		add(closeButton);
		setSize(Gdx.graphics.getWidth()-8f, Gdx.graphics.getHeight()/2f);
	}
	
	private void redrawHistory(){
		historyTable.clear();
		for(ConsoleOutputStruct struct : History.items){
			historyTable.add(struct.output, "default", struct.color);
			historyTable.row();
		}
	}

	/**
	 * Interpret text and execute command therein
	 */
	public void execute() {
		History.add(text.getText(), Color.BLACK);
		try{
			String[] tokens = text.getText().split(" ");
			for(IConsoleCommand command : PluginUtil.getPlugins(IConsoleCommand.class))
				for(String match : command.getMatches())
					try{
						if(tokens[0].matches(match))
							command.execute(tokens);
					}catch(Exception e){
						Log.error("ConsoleWindow.execute", "Failed to execute command: " + text.getText());
						e.printStackTrace();
					}
		}catch(Exception e){
			e.printStackTrace();
		}
		redrawHistory();
	}
}
