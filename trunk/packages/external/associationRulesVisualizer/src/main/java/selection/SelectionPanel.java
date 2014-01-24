/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *    SelectionPanel.java
 *    Copyright (C) 2003 DESS IAGL of Lille
 *
 */


package selection;

import rules.*;
import sortedListPanel.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

/**
 *
 * @author  beleg
 * @author Mark Hall (modifications)
 */
public class SelectionPanel
	extends javax.swing.JPanel
	implements ListSelectionListener {

	private MultipleListSelectionListener theListener;

	/** Creates new form SelectionPanel */
	public SelectionPanel() {
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents() {
		ParameterComparatorFactory factory = null;
		try {
			factory = new ParameterComparatorFactory("rules.RulesComparator");
		} catch (BadClassException e) {
			e.printStackTrace();
		}
		ToolTipRenderer ttr = new ToolTipRenderer();

		visu1List = new SortedListPanel(factory, 2, ttr, "Rules");
		visu1List.setMultipleSelection();
		visu1List.addListSelectionListener(this);
		Sorter theSorter = visu1List.getSorter();
/*		visu2Panel = new SortedListPanel(factory, 2, ttr, "N Dimensional Line");
		visu2Panel.setSorter(theSorter);
		visu2Panel.addListSelectionListener(this);
		visu2Panel.setMultipleSelection();
		visu3Panel = new SortedListPanel(factory, 2, ttr, "Double Decker Plot");
		visu3Panel.setSorter(theSorter);
		visu3Panel.addListSelectionListener(this); */

		setLayout(new java.awt.GridLayout(1, 1));

		JPanel visu1Panel = new JPanel();
		visu1Panel.setLayout(new BorderLayout());
		visu1Panel.add(visu1List, BorderLayout.CENTER);
		visu1Panel.setBorder(new javax.swing.border.TitledBorder("3D Representation"));

		csp = new CriteriaSelectorPanel();
		visu1Panel.add(csp, BorderLayout.SOUTH);

		add(visu1Panel);

/*		add(visu2Panel);
 
		add(visu3Panel); */

	}

	public void openPerformed(Rule[] rules, String[] criteres) {
		Rule[] visu1Rules = new Rule[rules.length];
/*		Rule[] visu2Rules = new Rule[rules.length];
		Rule[] visu3Rules = new Rule[rules.length]; */
		for (int i = 0; i < rules.length; i++) {
			visu1Rules[i] = rules[i];
//			visu2Rules[i] = rules[i];
			//			visu3Rules[i] = rules[i];			
		}
		RulesFilter rf = new RulesFilter(rules);
//		visu3Rules = rf.getRules();

		String[] newCriteres = new String[criteres.length + 2];
		newCriteres[0] = "nbAttributes";
		newCriteres[1] = "conclusion";
		for (int i = 0; i < criteres.length; i++)
			newCriteres[i + 2] = criteres[i];

		visu1List.setContents(visu1Rules, newCriteres);
/*		visu2Panel.setContents(visu2Rules, newCriteres);
		visu3Panel.setContents(visu3Rules, newCriteres); */
		csp.setContents(criteres);
	}		

	public void addMultipleListSelectionListener(MultipleListSelectionListener listener) {
		theListener = listener;
	}

	public void valueChanged(ListSelectionEvent e) {
		int listIndex = 0;
		boolean isEmpty = false;
		SortedListPanel theSource = (SortedListPanel) e.getSource();
		if (theSource == visu1List)
			listIndex = 1;
		if (theSource == visu2Panel)
			listIndex = 2;
		if (theSource == visu3Panel)
			listIndex = 3;
		Object[] objects = theSource.getSelectedElements();
		if (objects == null || objects.length == 0)
			isEmpty = true;
		MultipleListSelectionEvent event =
			new MultipleListSelectionEvent(
				this,
				listIndex,
				isEmpty,
				e.getFirstIndex(),
				e.getLastIndex(),
				e.getValueIsAdjusting());
		theListener.valueChanged(event);
	}

	public String[] getSelectedCriteria() {
		return csp.getSelectedElements();
	}

	public void setSelectedRules(int i, Rule[] rules) {
		switch (i) {
			case 1 :
				visu1List.setSelectedElements(rules);
				break;
			case 2 :
				visu2Panel.setSelectedElements(rules);
				break;
			case 3 :
				visu3Panel.setSelectedElements(rules);
				break;
		}
	}

	public Rule[] getSelectedRules(int i) {
		Object[] theObjects = null;
		Rule[] theRules = null;
		switch (i) {
			case 1 :
				theObjects = (visu1List.getSelectedElements());
				break;
			case 2 :
				theObjects = (visu2Panel.getSelectedElements());
				break;
			case 3 :
				theObjects = (visu3Panel.getSelectedElements());
				break;
		}
		if (theObjects != null && theObjects.length > 0) {
			theRules = new Rule[theObjects.length];
			for (int j = 0; j < theObjects.length; j++) {
				theRules[j] = (Rule) theObjects[j];
			}
		}

		return theRules;
	}

	// Variables declaration - do not modify
	private SortedListPanel visu3Panel;
	private SortedListPanel visu2Panel;
	private SortedListPanel visu1List;
	private CriteriaSelectorPanel csp;
	// End of variables declaration

}
