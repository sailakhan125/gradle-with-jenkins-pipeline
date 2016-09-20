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
		satge name, {
			try {
				closure
			} catch (UnstableException e) {
				currentBuild.result = 'UNSTABLE'
				noError = false
			}
		}
	}
}

node ("git && gradle && jdk8") {
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
		gradle "testClasses"
	}

	stg "test", {
		try {
			gradle "test"
		} catch (e) {
			throw new UnstableException(e);
		} finally {
			junit "build/**/TEST-*.xml"
		}
	}

	stg "check", {
		gradle "check"
	}
}
