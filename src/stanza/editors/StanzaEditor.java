package stanza.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class StanzaEditor extends TextEditor {

	private ColorManager colorManager;

	public StanzaEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new StanzaConfiguration(colorManager));
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
