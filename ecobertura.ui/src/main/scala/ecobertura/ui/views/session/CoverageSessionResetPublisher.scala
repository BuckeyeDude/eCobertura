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
package ecobertura.ui.views.session

import ecobertura.core.data.CoverageSession

import java.util.logging.Logger

trait CoverageSessionResetPublisher {
	private val logger = Logger.getLogger("ecobertura.ui.views.session") //$NON-NLS-1$

	private var listeners: List[Option[CoverageSession] => Unit] = Nil

	def addSessionResetListener(listener: Option[CoverageSession] => Unit) =
		listeners ::= listener
	
	def removeSessionResetListener(listener: Option[CoverageSession] => Unit) = 
		listeners.filterNot(_ == listener)
		
	protected def fireSessionReset(coverageSession: Option[CoverageSession]) = {
		logger.fine("coverage session reset...")
		listeners.foreach(_(coverageSession))
	}
}
