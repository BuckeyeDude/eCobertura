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
package ecobertura.core.util

import java.util.concurrent.Semaphore

import ecobertura.core.CorePlugin
import ecobertura.core.data.CoverageSession
import ecobertura.core.results.CoverageResultsListener

object LaunchTracker {
	def prepareLaunch = new LaunchTracker
}

class LaunchTracker {
	private var session: Option[CoverageSession] = None

	private val launchRunning = new Semaphore(1)
	
	registerCoverageResultsListener
	launchRunning.acquire
	
	private def registerCoverageResultsListener = {
		CorePlugin.instance.coverageResultsCollector addListener new CoverageResultsListener {
			override def coverageRunCompleted(session: CoverageSession) = {
				assert(session != null)
				LaunchTracker.this.session = Some(session)
				launchRunning.release
			}
		}
	}
	
	def waitForAndRetrieveCoverageResults = {
		launchRunning.acquire
		launchRunning.release

		assert(session != None)
		session.get
	}
}