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

import org.eclipse.core.commands._
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.FileDialog
import org.eclipse.ui.handlers.HandlerUtil
import org.eclipse.ui.handlers.RadioState

import ecobertura.core.cobertura.CoberturaWrapper
import ecobertura.core.data.CoverageSession
import ecobertura.ui.views.session.CoverageSessionModel

class SelectRecentCoverageSessionHandler extends AbstractHandler {
	override def execute(event: ExecutionEvent) = {
		if (!HandlerUtil.matchesRadioState(event)) {
			selectCoverageSession(event)

		    val currentState = event.getParameter(RadioState.PARAMETER_ID)
		    HandlerUtil.updateRadioState(event.getCommand, currentState)
		}
		
		null // handlers must return null
	}
	
	private def selectCoverageSession(event: ExecutionEvent) = {
		val displayNameOfSelectedSession = event.getParameter(
				"ecobertura.ui.views.session.commands.selectRecentCoverageSession.session")
		println("selected session: " + displayNameOfSelectedSession)
		CoverageSessionModel.get.selectCoverageSession(displayNameOfSelectedSession)
	}
}
