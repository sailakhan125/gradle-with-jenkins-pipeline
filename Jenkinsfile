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

node ("git && gradle && jdk8") {
	try {
		stage("clean") {
			deleteDir()
		}
	
		stage("scm") {
			checkout scm
		}
	
		stage("gradle-clean") {
			gradle "clean"
		}
	
		stage("compile") {
			gradle "classes"
		}
	
		stage("compile-tests") {
			gradle "testClasses"
		}
	
		stage("test") {
			try {
				gradle "test"
			} catch (e) {
				throw new UnstableException(e);
			} finally {
				junit "build/**/TEST-*.xml"
			}
		}
	
		stage("check") {
			gradle "check"
		}
	} catch (UnstableException e) {
		currentBuild.result = 'UNSTABLE';
	}
}
