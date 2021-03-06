/*
 * This file is part of eCobertura.
 * 
 * Copyright (c) 2009, 2010 Joachim Hofer
 * All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package ecobertura.ui.core

import java.util.logging.Logger

import org.eclipse.swt.widgets.Display

import ecobertura.core.CorePlugin
import ecobertura.core.data.CoverageSession
import ecobertura.core.results.CoverageResultsListener
import ecobertura.ui.util.Predef._
import ecobertura.ui.UIPlugin
import ecobertura.ui.views.session.CoverageSessionModel

object CoverageResultsUIListener {
	def register = new CoverageResultsUIListener
}

class CoverageResultsUIListener extends CoverageResultsListener {
	val logger = Logger.getLogger(UIPlugin.pluginId)

	CorePlugin.instance.coverageResultsCollector.addListener(this)
	logger.fine("coverage results ui listener registered")
	
	def unregister = {
		CorePlugin.instance.coverageResultsCollector.removeListener(this)
		logger.fine("coverage results ui listener unregistered")
	}
	
	override def coverageRunCompleted(coverageSession: CoverageSession) = {
		logger.fine("coverage run completed - we have data!")
		logger.fine(coverageSession.toString)
		for (packageData <- coverageSession.packages) {
			logger.fine(packageData.toString)
			for (classData <- packageData.classes) {
				logger.fine(classData.toString)
			}
		}
		Display.getDefault.asyncExec(CoverageSessionModel.get.addCoverageSession(coverageSession))
	}
}
