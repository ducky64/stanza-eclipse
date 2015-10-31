package stanza.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class StanzaConfiguration extends SourceViewerConfiguration {
	private StanzaScanner scanner;
	private ColorManager colorManager;

	public StanzaConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	protected StanzaScanner getStanzaScanner() {
		if (scanner == null) {
			scanner = new StanzaScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IStanzaColorConstants.DEFAULT))));
		}
		return scanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(
        		getStanzaScanner());
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        return reconciler;
	}

}