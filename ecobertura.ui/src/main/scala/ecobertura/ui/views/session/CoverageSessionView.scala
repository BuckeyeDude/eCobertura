package ecobertura.ui.views.session

import java.util.logging.Logger

import org.eclipse.swt.widgets._
import org.eclipse.swt.SWT;

import org.eclipse.jface.layout.TreeColumnLayout
import org.eclipse.jface.viewers._

import org.eclipse.ui._
import org.eclipse.ui.part._

import ecobertura.ui.util.Predef._
import ecobertura.ui.views.session.labels._

object CoverageSessionView {
	/**
	 * The ID of the view as specified by the extension.
	 */
	def ID = "ecobertura.ui.views.session.CoverageSessionView"
		
	private val logger = Logger.getLogger("ecobertura.ui.views.session") //$NON-NLS-1$
}

class CoverageSessionView extends ViewPart {
	import CoverageSessionView.logger
	
	private var viewer: TreeViewer = null

	class NameSorter extends ViewerSorter
	
	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	override def createPartControl(parent: Composite) = {
//		val swtTreeTable = new Tree(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL)
//		swtTreeTable.setHeaderVisible(true)
//		swtTreeTable.setLinesVisible(true)
		
		viewer = new TreeViewer(parent, SWT.SINGLE)
		val swtTreeTable = viewer.getTree
		swtTreeTable.setHeaderVisible(true)
		
		val treeColumnLayout = new TreeColumnLayout
		parent.setLayout(treeColumnLayout)
		
		addTreeColumn("Name", SWT.LEFT, 7).setLabelProvider(new NameLabelProvider)
		addTreeColumn("Lines", SWT.RIGHT, 1).setLabelProvider(new LinesCoveredLabelProvider)
		addTreeColumn("Total", SWT.RIGHT, 1).setLabelProvider(new LinesTotalLabelProvider)
		addTreeColumn("%", SWT.RIGHT, 1).setLabelProvider(new LinesPercentageLabelProvider)
		
		viewer.setContentProvider(CoverageSessionModel.get)
		viewer.setSorter(new NameSorter)
		viewer.setInput(CoverageSessionRoot)
		viewer.expandAll
		
		CoverageSessionModel.get.addListener(new CoverageSessionListener {
			override def sessionReset = {
				logger.fine("Viewer has received sessionReset event")
				viewer.setInput(CoverageSessionRoot)
				viewer.expandAll
			}
		})

		// Create the help context id for the viewer's control
		// PlatformUI.getWorkbench.getHelpSystem.setHelp(viewer.getControl, "ecobertura.ui.viewer")

		def addTreeColumn(name: String, alignment: Int, weight: Int) = {
			val column = new TreeViewerColumn(viewer, alignment)
			column.getColumn.setText(name)
			column.getColumn.setAlignment(alignment)
			treeColumnLayout.setColumnData(column.getColumn, new ColumnWeightData(weight))
			
			column
		}
	}

	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	override def setFocus = {
		viewer.getControl.setFocus
	}
}
