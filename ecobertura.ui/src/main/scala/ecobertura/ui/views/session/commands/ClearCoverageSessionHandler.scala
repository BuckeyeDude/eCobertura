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
package ecobertura.ui.views.session.commands

import org.eclipse.core.commands._
import ecobertura.ui.views.session.CoverageSessionModel

class ClearCoverageSessionHandler extends AbstractHandler {
	override def execute(event: ExecutionEvent) = {
	  if (CoverageSessionModel.get.currentCoverageSession.isEmpty) {
	    null
	  }
	  
	  CoverageSessionModel.get.clearCoverageSession(CoverageSessionModel.get.currentCoverageSession.get)
	  null
	}
}