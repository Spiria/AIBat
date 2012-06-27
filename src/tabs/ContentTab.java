package tabs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import aibat.AIBatWindow;
import aibat.Util;

public abstract class ContentTab extends JPanel {
    // allContent maps section titles to their content
    protected Map<String, String> allContent = new TreeMap<String, String>();

    protected String tabName = "default";

    // Add "(?: - )?" to include the " - " in the regex
    private static final String TIME_REGEX = "(\\d{2}:\\d{2}:\\d{3}(?: \\(\\d+\\))?)";
    private static final String LINK_REGEX = "<a href=\"$1\">$1</a>";
    public static final String FORMAT_TO_HTML = "<b><a href=\"%2$s\" style=\"font-family:georgia;font-size:16\">%1$s</a></b><br />%3$s<br />";
    public static final String FORMAT_TO_PLAIN = "<b><u><a style=\"font-family:georgia;font-size:16\">%1$s</a></u></b><br />%4$s<br />";
    public static final String FORMAT_TO_BBCODE = "%2$s\n";

    private JEditorPane textArea = new JEditorPane();

    public ContentTab() {
	drawGUI();
	// fillAllContent();
	// showText(allContentToString(FORMAT_TO_HTML));
    }

    // TODO Keep from scrolling to bottom
    private void drawGUI() {
	setBackground(Color.LIGHT_GRAY);

	Font font = UIManager.getFont("Label.font");
	String bodyRule = "body { font-family: " + font.getFamily() + "; "
		+ "font-size: " + font.getSize() + "pt; }";
	// Format textArea to do HTML
	textArea.setEditorKit(new HTMLEditorKit());
	textArea.setForeground(Color.BLACK);
	((HTMLDocument) textArea.getDocument()).getStyleSheet().addRule(
		bodyRule);

	textArea.setEditable(false);
	// TODO fix so it's not *always* TEXT_CURSOR
	// textArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
	textArea.addHyperlinkListener(new HyperlinkCopier());
	textArea.setText("Content Pane");
	JScrollPane scrollPane = new JScrollPane(textArea);

	// Replace with cool code
	scrollPane.setPreferredSize(new Dimension(600, 500));
	this.add(scrollPane);

    }

    public void showText(String toShow) {
	if (toShow == null || toShow.length() == 0) {
	    textArea.setText("<a style=\"font-family:georgia;font-size:16\">Nothing to see!</a>");
	    return;
	}
	textArea.setText(toShow);
    }

    public String getTabName() {
	for (String s : allContent.values())
	    if (s != null && s.length() > 0)
		return "*" + tabName;
	return tabName;
    }

    private class HyperlinkCopier implements HyperlinkListener {
	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
	    if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
		String toCopy = e.getDescription();
		if (toCopy.matches(TIME_REGEX)) {
		    Util.copyStringToClipboard(toCopy, Util.copyMessage(toCopy));
		}
		else {
		    toCopy = toCopy.replaceAll("<br />", "\n");
		    toCopy = "[quote=\"" + AIBatWindow.VERSION + "\"]" + toCopy
			    + "[/quote]";
		    Util.copyStringToClipboard(toCopy, Util.copyMessage(toCopy));
		}
	    }
	}
    }

    protected abstract void fillAllContent();

    protected String allContentToString(String format) {
	StringBuffer result = new StringBuffer();
	for (Map.Entry<String, String> entry : allContent.entrySet()) {

	    String title = entry.getKey();
	    String content = entry.getValue();

	    if (content == null || content.length() == 0)
		continue;

	    // This messy part displays an html version but links to BBCode
	    // FIXME figure out why " as in Suggested Tags cuts off in copy from
	    // hyperlink but not from copy all warnings
	    String HTMLContent = content.replaceAll(TIME_REGEX, LINK_REGEX)
		    .replaceAll("\\n", "<br />") + "<br />";
	    String BBCodeContent = "[b][u]" + title + "[/u][/b]\n" + content;
	    String plainContent = content.replaceAll("\\n", "<br />");
	    result.append(String.format(format, title, BBCodeContent,
		    HTMLContent, plainContent));
	}
	return result.toString();
    }

    // public static void main(String[] args) {
    // System.out.println(String.format(FORMAT_TO_HTML, "1d", "2d", "HTML"));
    // }

    // Autogenerated GUI stuff.
    // GridBagLayout gridBagLayout = new GridBagLayout();
    // gridBagLayout.columnWidths = new int[] { 154, 520, 0 };
    // gridBagLayout.rowHeights = new int[] { 129, 0, 303, 0 };
    // gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE
    // };
    // gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0,
    // Double.MIN_VALUE };
    // setLayout(gridBagLayout);
    //
    // GridBagConstraints gbc_scrollPane = new GridBagConstraints();
    // gbc_scrollPane.gridheight = 3;
    // gbc_scrollPane.fill = GridBagConstraints.BOTH;
    // gbc_scrollPane.insets = new Insets(5, 0, 5, 50);
    // gbc_scrollPane.gridx = 1;
    // gbc_scrollPane.gridy = 0;
    // add(scrollPane, gbc_scrollPane);
    //
    // GridBagConstraints gbc_lblNavigationPane = new GridBagConstraints();
    // gbc_lblNavigationPane.insets = new Insets(0, 0, 5, 5);
    // gbc_lblNavigationPane.gridx = 0;
    // gbc_lblNavigationPane.gridy = 1;
    // add(lblNavigationPane, gbc_lblNavigationPane);
    //
    // GridBagConstraints gbc_dtrpnNavigationPane = new GridBagConstraints();
    // gbc_dtrpnNavigationPane.insets = new Insets(0, 5, 0, 5);
    // gbc_dtrpnNavigationPane.fill = GridBagConstraints.BOTH;
    // gbc_dtrpnNavigationPane.gridx = 0;
    // gbc_dtrpnNavigationPane.gridy = 2;
    // dtrpnNavigationPane.setBackground(Color.LIGHT_GRAY);
    // dtrpnNavigationPane.setText("Contents");
    // add(dtrpnNavigationPane, gbc_dtrpnNavigationPane);

}
