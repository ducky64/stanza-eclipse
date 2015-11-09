package stanza.editors;

import java.util.*;
import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.SWT;

public class StanzaScanner extends RuleBasedScanner {
	protected IToken getToken(Color color) {
		return getToken(color, 0);
	}

	protected IToken getToken(Color color, int style) {
		return new Token(new TextAttribute(color, null, style));
	}

	protected void addRulesToWordRule(WordRule wordRule, String[] words, IToken token) {
		for (String word: words) {
			wordRule.addWord(word, token);
		}
	}

	public StanzaScanner(ColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();

		// Keywords
		WordRule keywordRule = new WordRule(new IWordDetector() {
			@Override
			public boolean isWordStart(final char c) {
				return Character.isLetter(c);
			}

			@Override
			public boolean isWordPart(final char c) {
				return Character.isLetter(c);
			}
		});
		IToken keywordToken = getToken(manager.getColor(IStanzaColorConstants.KEYWORD), SWT.BOLD);
		String[] keywords = {
			"val", "var",
			"public",
			"fn", "defn",
			"defpackage",
			"defclass", "definterface", "defstruct",
			"defmulti", "defmethod",
			"if", "else", "when",
			"for", "while", "let",
			"import"
		};
		addRulesToWordRule(keywordRule, keywords, keywordToken);

		rules.add(keywordRule);

		// Comments
		rules.add(new SingleLineRule(";", "\n",
				getToken(manager.getColor(IStanzaColorConstants.COMMENT))));

		// Strings
		rules.add(new SingleLineRule("\"", "\"",
				getToken(manager.getColor(IStanzaColorConstants.STRING))));

		// Generic whitespace rule
		rules.add(new WhitespaceRule(new IWhitespaceDetector() {
            public boolean isWhitespace(char c) {
                return Character.isWhitespace(c);
             }
          }));

		IRule[] rulesArray = rules.toArray(new IRule[rules.size()]);
		setRules(rulesArray);
	}
}
