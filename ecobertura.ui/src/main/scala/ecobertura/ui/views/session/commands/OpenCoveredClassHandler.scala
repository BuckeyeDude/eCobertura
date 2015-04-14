/*
 * This file is part of eCobertura.
 * 
 * Copyright (c) 2009-2014 Joachim Hofer
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package ecobertura.ui.views.session.commands

import org.eclipse.ui.part.FileEditorInput

import java.util.logging.Logger
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.viewers.ISelection
import org.eclipse.core.commands._
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.handlers.HandlerUtil

import ecobertura.core.data.CoverageSession

import ecobertura.ui.views.session.CoverageSessionView
import ecobertura.ui.views.session._
import ecobertura.ui.util._

class OpenCoveredClassHandler extends AbstractHandler {
	val logger = Logger.getLogger("ecobertura.ui.views.session.command")
	
	override def execute(event: ExecutionEvent) = {
		val window = HandlerUtil.getActiveWorkbenchWindow(event)
		val page = window.getActivePage
		val view = page.findView(CoverageSessionView.ID).asInstanceOf[CoverageSessionView];
				
		view.selection match {
			case structuredSelection: IStructuredSelection =>
				handleStructuredSelection(structuredSelection.getFirstElement)
			case _ => logger.fine("not a structured selection") /* nothing to do */
		}
		
		def handleStructuredSelection(selectedObject: Any) = selectedObject match {
			case covClass: CoverageSessionClass => handleClassSelection(covClass)
			case _ => logger.fine("not a CoverageSessionClass") /* nothing to do */
		}

		def handleClassSelection(covClass: CoverageSessionClass) = {
			SourceFileFinder.fromSourceFileName(covClass.coverageData.sourceFileName).find(
					file => {
						val defaultEditor = PlatformUI.getWorkbench.getEditorRegistry.getDefaultEditor(file.getName);
						page.openEditor(new FileEditorInput(file), defaultEditor.getId());
					}
			)
		}
		null
	}
}
