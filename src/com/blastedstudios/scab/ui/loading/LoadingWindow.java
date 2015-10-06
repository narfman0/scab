package com.blastedstudios.scab.ui.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.util.ui.ScabWindow;

public class LoadingWindow extends ScabWindow{
	private final ILoadingWindowExecutor executor;
	
	public LoadingWindow(Skin skin, ILoadingWindowExecutor executor) {
		super("", skin);
		this.executor = executor;
		add(new ScabTextButton("Loading", skin));
		pack();
		setX(Gdx.graphics.getWidth()/2 - getWidth()/2);
		setY(Gdx.graphics.getHeight()/2 - getHeight()/2);
	}
	
	@Override public void act(float delta){
		super.act(delta);
		if(executor != null)
			if(executor.act(delta))
				remove();
	}
	
	public interface ILoadingWindowExecutor{
		/**
		 * @return true if finished loading
		 */
		boolean act(float delta);
	}
}
