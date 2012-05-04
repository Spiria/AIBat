package tabs;

import aibat.OsuFileChecker;
import aibat.OsuFileParser;

public class osuDiffTab extends AIBatTab {

    private OsuFileChecker ofc;
    private final String diffName;

    public osuDiffTab(OsuFileParser ofp) {
	super();
	ofc = ofp.getOsuFileChecker();
	diffName = ofp.getDiff();
	fillAllContent();
	showText(allContentToString(FORMAT_TO_HTML));
    }

    @Override
    protected void fillAllContent() {
	allContent.put("Snapping", ofc.getSnapCheck());
	allContent.put("Breaks", ofc.getBreaksCheck());
	allContent.put("Spinner Length", ofc.getSpinLengthCheck());
	allContent.put("Spinner New Combo", ofc.getSpinNCCheck());
	allContent.put("Catmull Sliders", ofc.getCatmullCheck());
	allContent.put("Stack Leniency", ofc.getStackLenCheck());
	allContent.put("Non-Downbeat Kiai Times", ofc.getWrongKiais());
	allContent.put("Preview Point", ofc.getPreviewCheck());
    }

    @Override
    public String getTabName() {
	return diffName;
    }

}