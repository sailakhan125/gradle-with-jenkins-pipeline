#!/usr/bin/env groovy

import groovy.transform.InheritConstructors;

@InheritConstructors
class UnstableException extends RuntimeException {
}

def gradle(args) {
	def command = "gradle ${args}";
	return isUnix()
		? sh(command)
		: bat(command)
}

noError = true

def stg(name, closure) {
	if (noError) {
		stage name, {
			try {
				closure()
			} catch (UnstableException e) {
				currentBuild.result = 'UNSTABLE'
				noError = false
			}
		}
	}
}

node {
	stg "clean", {
		deleteDir()
	}

	stg "scm", {
		checkout scm
	}

	stg "gradle-clean", {
		gradle "clean"
	}

	stg "compile", {
		gradle "classes"
	}

	stg "compile-tests", {
		gradle "testClasses moreTestClasses"
	}

	stg "test", {
		try {
			gradle "test moreTest"
		} catch (e) {
			throw new UnstableException(e);
		} finally {
			junit "build/**/TEST-*.xml"
		}
	}

	stg "check", {
		try {
			gradle "check"
		} catch (e) {
			throw new UnstableException(e);
		} finally {
			publishHTML([
				allowMissing: false,
				alwaysLinkToLastBuild: true,
				keepAll: true,
				reportDir: 'build/reports/jacoco/jacocoCoverage/html',
				reportFiles: 'index.html',
				reportName: 'Coverage Report'
			])
		}
	}
}
